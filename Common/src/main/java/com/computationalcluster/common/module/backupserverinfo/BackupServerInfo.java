package com.computationalcluster.common.module.backupserverinfo;

public class BackupServerInfo {
	private String address = null;
	private long port = 0;
	
	public BackupServerInfo(String address, long port) {
		this.address = address;
		this.port = port;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPort() {
		return port;
	}

	public void setPort(long port) {
		this.port = port;
	}
	
	
}
