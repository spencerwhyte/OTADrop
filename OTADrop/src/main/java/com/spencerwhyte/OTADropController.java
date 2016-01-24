package com.spencerwhyte;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.NSNumber;
import com.dd.plist.NSObject;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;


@Controller
public class OTADropController {


	static Logger log = Logger.getLogger(OtaDropApplication.class.getName());

	/*
	 * Returns the page that displays the information for an app that has already been uploaded
	 */
	@RequestMapping("/air/{uploadID}/")
	@ResponseBody
	public String download(@PathVariable String uploadID,Model model){
		System.out.println("Download");
		// TODO: Make sure that the uploadID actually exists, if it deosn't, display a different view

		return "UploadID: " + uploadID;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST, produces={ "application/json"})
	public @ResponseBody HashMap<String, String> upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException, ParserConfigurationException, ParseException, SAXException, PropertyListFormatException{
		String publishId = UUID.randomUUID().toString();
		log.debug("Request received");
		String uploadPath = file.getOriginalFilename();

		String outputFolder = "/Users/spencerwhyte/temp";


		File convFile = new File(uploadPath);
		file.transferTo(convFile);


		byte[] buffer = new byte[1024];
		try{

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




		}catch(IOException ex){
			ex.printStackTrace(); 
		}

		String payloadDirectoryString  = outputFolder + "/Payload/";
		log.debug("The payload directory was found to be: "+ payloadDirectoryString);
		File payloadDirectory = new File(payloadDirectoryString);

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

			NSDictionary rootDict = (NSDictionary)PropertyListParser.parse(plistFile);
			String bundleIdentifier = rootDict.objectForKey("CFBundleIdentifier").toString();
			log.debug("Bundle identifier: "+ bundleIdentifier);
		}else{
			log.debug("That app directory was empty");
		}

		//*.app/info.plist

		HashMap<String, String> map = new HashMap<String, String>();
		map.put("url","https://www.otadrop.com/air/" + publishId);
		return map;
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

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String homePage(Model model){
		System.out.println("HomePage");
		return "homePage";
	}




}
