package com.computationalcluster.common.module;

import java.util.ArrayList;
import java.util.List;

import com.computationalcluster.common.module.backupserverinfo.BackupServerInfo;

public class BackupServerInfoModule {
	
	private List<BackupServerInfo> backupServerInfos = null;
	
	public BackupServerInfoModule(){
		backupServerInfos = new ArrayList<BackupServerInfo>();
	}

	public List<BackupServerInfo> getBackupServerInfos() {
		return backupServerInfos;
	}

	public void setBackupServerInfos(List<BackupServerInfo> backupServerInfos) {
		this.backupServerInfos = backupServerInfos;
	}
}
