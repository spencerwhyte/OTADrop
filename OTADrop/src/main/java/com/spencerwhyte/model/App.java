

package com.spencerwhyte.model;

public class App {
	private String uuid;
	private String name;
	private String bundleId;
	private String bundleVersion;
	
	public App(String uuid, String name, String bundleId, String bundleVersion){
		this.setUuid(uuid);
		this.setName(name);
		this.setBundleId(bundleId);
		this.setBundleVersion(bundleVersion);
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getBundleId() {
		return bundleId;
	}

	public void setBundleId(String bundleId) {
		this.bundleId = bundleId;
	}

	public String getBundleVersion() {
		return bundleVersion;
	}

	public void setBundleVersion(String bundleVersion) {
		this.bundleVersion = bundleVersion;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
