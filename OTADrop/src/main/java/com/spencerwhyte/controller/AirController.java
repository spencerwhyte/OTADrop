package com.spencerwhyte.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spencerwhyte.configuration.OtaDropApplication;
import com.spencerwhyte.dao.AppDAO;
import com.spencerwhyte.model.App;
import com.spencerwhyte.processor.AppProcessor;

@Controller
public class AirController {
	static Logger log = Logger.getLogger(OtaDropApplication.class.getName());
	@Autowired
	private AppDAO appDAO;

	String baseUrl =  "https://www.otadrop.com/air/";
	//String baseUrl = "http://127.0.0.1:8080/otadrop/air/";

	@RequestMapping("/air/{uploadId}/manifest.plist")
	public String downloadManifest(@PathVariable String uploadId,Model model){
		log.debug("Downloading manifest.plist for " + uploadId);

		App currentApp = appDAO.get(uploadId);
		String softwarePackageURL = baseUrl+"apps/"+uploadId+".ipa";

		model.addAttribute("bundleIdentifier", currentApp.getBundleId());
		model.addAttribute("url", softwarePackageURL);
		model.addAttribute("bundleVersion", currentApp.getBundleVersion());
		model.addAttribute("title", currentApp.getName());

		return "manifest";
	}


	/*
	 * Returns the page that displays the information for an app that has already been uploaded
	 */
	@RequestMapping("/air/{uploadId:^[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}$}") // Matches UUID only
	public String download(@PathVariable String uploadId,Model model){
		System.out.println("Download");

		// TODO: Make sure that the uploadID actually exists, if it deosn't, display a different view
		App currentApp = appDAO.get(uploadId);

		String manifestUrl = baseUrl + uploadId + "/manifest.plist";
		String iconUrl = baseUrl +"images/"+ uploadId + ".png";

		model.addAttribute("bundleIdentifier", currentApp.getBundleId());
		model.addAttribute("manifestUrl", manifestUrl);
		model.addAttribute("bundleVersion", currentApp.getBundleVersion());
		model.addAttribute("title", currentApp.getName());
		model.addAttribute("iconUrl", iconUrl);
		model.addAttribute("buildNumber", currentApp.getBuildNumber());

		return "download";
	}

}
