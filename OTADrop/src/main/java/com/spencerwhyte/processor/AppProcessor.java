package com.spencerwhyte.processor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.spencerwhyte.configuration.OtaDropApplication;
import com.spencerwhyte.converter.IPngConverter;
import com.spencerwhyte.dao.AppDAO;
import com.spencerwhyte.model.App;

@Component
public class AppProcessor {
	static Logger log = Logger.getLogger(OtaDropApplication.class.getName());
	String base  = "/Users/spencerwhyte/temp/";
	String extractionDirectory = base +  "extract";
	String appsDirectory = base + "apps";
	String imagesDirectory = base + "images";
	@Autowired
	private AppDAO appDAO;

	public App processAppUpload(MultipartFile file){
		String publishId = UUID.randomUUID().toString();
		String outputFolder = extractionDirectory + "/" + publishId;

		File convFile = new File(outputFolder + "/App.ipa");

		File appFile = new File(appsDirectory + "/" + publishId + ".ipa");
		File imageFile = new File(imagesDirectory + "/" + publishId + ".png");

		try {
			File folder = new File(outputFolder);
			if(!folder.exists()){
				folder.mkdir();
			}

			file.transferTo(convFile);


			byte[] buffer = new byte[1024];

			//create output directory is not exists

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

			Stream<Path> paths = Files.list(Paths.get(payloadDirectoryString)).filter(new Predicate<Path>() {
				@Override
				public boolean test(Path p) {
					return p.getFileName().toString().endsWith(".app");
				}
			});

			String plistPath = null;
			String appPath = null;
			Iterator<Path> pathsIterator = paths.iterator();
			while(pathsIterator.hasNext()){
				appPath = pathsIterator.next().toString();
				break;
			}
			plistPath = appPath + "/Info.plist";





			Files.copy(convFile.toPath(), appFile.toPath(), StandardCopyOption.REPLACE_EXISTING); // Move the ipa into position for download

			if(plistPath!=null){ // There should just be the .app

				File plistFile = new File(plistPath);

				log.debug("The plist was found to be: "+ plistFile);

				NSDictionary rootDict;

				rootDict = (NSDictionary)PropertyListParser.parse(plistFile);

				String bundleIdentifier = rootDict.objectForKey("CFBundleIdentifier").toString();
				String title = rootDict.objectForKey("CFBundleName").toString();
				String version = rootDict.objectForKey("CFBundleShortVersionString").toString();
				String buildNumber =  rootDict.objectForKey("CFBundleVersion").toString();

				log.debug("PublishID: "+ publishId);
				log.debug("Bundle identifier: "+ bundleIdentifier);
				log.debug("Title: "+ title);
				log.debug("Version: "+ version);
				log.debug("Build Number: "+ buildNumber);

				NSDictionary bundleIcons =  (NSDictionary)rootDict.objectForKey("CFBundleIcons");
				if(bundleIcons != null){
					NSDictionary primaryIcons =  (NSDictionary)bundleIcons.objectForKey("CFBundlePrimaryIcon");
					if(primaryIcons != null){
						NSArray bundleIconFiles =  (NSArray)primaryIcons.objectForKey("CFBundleIconFiles");
						if(bundleIconFiles != null){
							String iconFile = null;
							for(int i = 0 ; i < bundleIconFiles.count(); i++){
								String fullPath = appPath + "/" + bundleIconFiles.objectAtIndex(i);
								String modifiers[] = {"@3x", "@2x", ""};
								for(int j = 0 ; j < modifiers.length; j++){
									File testFile = new File(fullPath + modifiers[j] + ".png");
									if(testFile.exists()){
										iconFile = testFile.getAbsolutePath();
										break;
									}
								}
							}
							log.debug("We found this icon file" + iconFile);
							if(iconFile != null){
								File src = new File(iconFile);
								IPngConverter converter = new IPngConverter(src, imageFile);
								converter.convert();
							}
						}
					}
				}

				// Insert this into the database, then tell the user
				App newApp = new App(publishId, title, bundleIdentifier, version, buildNumber);

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
