package com.spencerwhyte.controller;


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
import org.springframework.beans.factory.annotation.Autowired;
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
import com.spencerwhyte.configuration.OtaDropApplication;
import com.spencerwhyte.dao.AppDAO;
import com.spencerwhyte.model.App;
import com.spencerwhyte.processor.AppProcessor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.ParserConfigurationException;


@Controller
public class DropController {
	static Logger log = Logger.getLogger(OtaDropApplication.class.getName());
	@Autowired
	private AppProcessor appProcessor;
	
	@RequestMapping(value = "/", method = RequestMethod.POST, produces={ "application/json"})
	public @ResponseBody HashMap<String, String> upload(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException, ParserConfigurationException, ParseException, SAXException, PropertyListFormatException{
		log.debug("App was uploaded");
		App app = this.appProcessor.processAppUpload(file);
		if(app != null){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("url","https://www.otadrop.com/air/" + app.getUuid());
			return map;
		}else{
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("error","Failure :(");
			return map;
		}
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String homePage(Model model){
		System.out.println("HomePage");
		return "homePage";
	}




}
