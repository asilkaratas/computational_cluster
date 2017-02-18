package com.computationalcluster.common.messagehandler;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.component.SolverClientComponent;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.NoOperation.BackupCommunicationServers;
import com.computationalcluster.common.messages.NoOperation.BackupCommunicationServers.BackupCommunicationServer;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.common.module.backupserverinfo.BackupServerInfo;

public class NoOperationHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(NoOperationHandler.class);
	
	private final SolverClientComponent clientComponent;
	
	public NoOperationHandler(SolverClientComponent clientComponent){
		this.clientComponent = clientComponent;
	}

	@Override
	public void handle(ClientMessage clientMessage) {
		logger.info("Status response is recieved");
		final NoOperation noOperation = (NoOperation)clientMessage.getMessage();
		
		final Error error = MessageValidatorUtil.getError(noOperation);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(clientMessage.getClientId(), error);
			clientComponent.getProxy().sendMessage(errorMessage);
			return;
		}
		
		final List<BackupServerInfo> backupServerInfos = new ArrayList<BackupServerInfo>();
		final BackupCommunicationServers servers = noOperation.getBackupCommunicationServers();
		if(servers != null) {
			final List<BackupCommunicationServer> serverList = servers.getBackupCommunicationServer();
			
			for(int i = 0; i < serverList.size(); i++) {
				final BackupCommunicationServer server = serverList.get(i);
				final BackupServerInfo serverInfo = new BackupServerInfo(server.getAddress(), server.getPort());
				backupServerInfos.add(serverInfo);
			}
		}
		logger.info("Status response is handled backups:" + backupServerInfos.size());
		clientComponent.getBackupServerInfoModule().setBackupServerInfos(backupServerInfos);
	}

}
