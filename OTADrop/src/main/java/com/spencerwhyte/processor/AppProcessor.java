package com.spencerwhyte.processor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.spencerwhyte.configuration.OtaDropApplication;
import com.spencerwhyte.dao.AppDAO;
import com.spencerwhyte.model.App;

@Component
public class AppProcessor {
	static Logger log = Logger.getLogger(OtaDropApplication.class.getName());
	
	@Autowired
    private AppDAO appDAO;
	
	public App processAppUpload(MultipartFile file){
		String publishId = UUID.randomUUID().toString();
		String outputFolder = "/Users/spencerwhyte/temp";
		String uploadPath = file.getOriginalFilename();
		
		File convFile = new File(uploadPath);
		try {
			file.transferTo(convFile);
			
			byte[] buffer = new byte[1024];
			

			//create output directory is not exists
			File folder = new File(outputFolder);
			if(!folder.exists()){
				folder.mkdir();
			}

			ZipInputStream zipIn = new ZipInputStream(new FileInputStream(convFile));
			ZipEntry entry = zipIn.getNextEntry();
			// iterates over entries in the zip file
			while (entry != null) {
				String filePath = outputFolder + File.separator + entry.getName();
				if (!entry.isDirectory()) {
					// if the entry is a file, extracts it
					extractFile(zipIn, filePath);
				} else {
					// if the entry is a directory, make the directory
					File dir = new File(filePath);
					dir.mkdir();
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();


			String payloadDirectoryString  = outputFolder + "/Payload/";
			log.debug("The payload directory was found to be: "+ payloadDirectoryString);

			Stream<Path> paths = Files.list(Paths.get(payloadDirectoryString)).filter(p -> p.getFileName()
					.toString().endsWith(".app"));

			String plistPath = null;
			Iterator<Path> pathsIterator = paths.iterator();
			while(pathsIterator.hasNext()){
				plistPath = pathsIterator.next().toString() + "/info.plist";
				break;
			}
			
			if(plistPath!=null){ // There should just be the .app
				
				File plistFile = new File(plistPath);

				log.debug("The plist was found to be: "+ plistFile);
				
				NSDictionary rootDict;
				
				rootDict = (NSDictionary)PropertyListParser.parse(plistFile);

				String bundleIdentifier = rootDict.objectForKey("CFBundleIdentifier").toString();
				String title = rootDict.objectForKey("CFBundleName").toString();
				String version = rootDict.objectForKey("CFBundleShortVersionString").toString();

				log.debug("PublishID: "+ publishId);
				log.debug("Bundle identifier: "+ bundleIdentifier);
				log.debug("Title: "+ title);
				log.debug("Version: "+ version);

				// Insert this into the database, then tell the user
				App newApp = new App(publishId, title, bundleIdentifier, version);

				this.appDAO.add(newApp);

				return newApp;
					
			}else{
				log.debug("That app directory was empty");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[1024];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

	
}
