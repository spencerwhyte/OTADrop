package com.spencerwhyte.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spencerwhyte.configuration.OtaDropApplication;
import com.spencerwhyte.dao.AppDAO;
import com.spencerwhyte.model.App;

@Controller
public class AirController {
	static Logger log = Logger.getLogger(OtaDropApplication.class.getName());
	@Autowired
    private AppDAO appDAO;
	
	@RequestMapping("/air/{uploadId}/manifest.plist")
	public String downloadManifest(@PathVariable String uploadId,Model model){
		log.debug("Downloading manifest.plist for " + uploadId);
		
		App currentApp = appDAO.get(uploadId);
		String softwarePackageURL = "https://www.otadrop.com/air/"+uploadId+"/App.ipa";
		
		model.addAttribute("bundleIdentifier", currentApp.getBundleId());
		model.addAttribute("url", softwarePackageURL);
		model.addAttribute("bundleVersion", currentApp.getBundleVersion());
		model.addAttribute("title", currentApp.getName());
		
		return "manifest";
	}
	
	
	/*
	 * Returns the page that displays the information for an app that has already been uploaded
	 */
	@RequestMapping("/air/{uploadId}")
	@ResponseBody
	public String download(@PathVariable String uploadId,Model model){
		System.out.println("Download");
		// TODO: Make sure that the uploadID actually exists, if it deosn't, display a different view
		App currentApp = appDAO.get(uploadId);
		return "UploadID: " + uploadId + " Name: "+ currentApp.getName() + " bundle identifier:" + currentApp.getBundleId();
	}
	
}
