package com.computationalcluster.communicationserver.backup.messagehandler;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.communicationserver.backup.BackupCommunicationServer;

public class NoOperationHandler implements ClientMessageHandler {

	private final BackupCommunicationServer backupCommunicationServer;
	
	public NoOperationHandler(BackupCommunicationServer backupCommunicationServer){
		this.backupCommunicationServer = backupCommunicationServer;
	}

	@Override
	public void handle(ClientMessage clientMessage) {
		System.out.println("Status response is recieved");
	}

}
