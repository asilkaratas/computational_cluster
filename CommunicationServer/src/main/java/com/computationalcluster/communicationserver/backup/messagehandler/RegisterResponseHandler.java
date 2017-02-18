package com.computationalcluster.communicationserver.backup.messagehandler;


import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.communicationserver.backup.BackupCommunicationServer;


public class RegisterResponseHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(RegisterResponseHandler.class);
	private final BackupCommunicationServer backupCommunicationServer;
	
	public RegisterResponseHandler(BackupCommunicationServer backupCommunicationServer) {
		this.backupCommunicationServer = backupCommunicationServer;
	}
	
	@Override
	public void handle(ClientMessage clientMessage) {
		logger.debug("Register response message is handled");
		
		final RegisterResponse registerResponse = (RegisterResponse)clientMessage.getMessage();
		System.out.println("Register response recieved: id:" + registerResponse.getId() +
				" timeout:" + registerResponse.getTimeout());
		
		backupCommunicationServer.setId(registerResponse.getId());
		
		backupCommunicationServer.getStatusSendingModule().setTimeout(registerResponse.getTimeout());
		backupCommunicationServer.getStatusSendingModule().start();
	}

}
