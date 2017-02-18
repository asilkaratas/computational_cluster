package com.computationalcluster.communicationserver.backup;


import org.apache.log4j.Logger;

import com.computationalcluster.common.config.ServerConfig;
import com.computationalcluster.common.connection.Client;
import com.computationalcluster.common.connection.ClientCallback;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.queue.ClientMessageQueue;

public class BackupCommunicationServerProxy implements CommunicationProxy, ClientCallback{
	private static final Logger logger = Logger.getLogger(BackupCommunicationServerProxy.class);
	
	private final ServerConfig serverConfig;
	private final Client client;

	public BackupCommunicationServerProxy(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
		client = new Client(serverConfig.getMasterAddress(), serverConfig.getMasterPort(), this);
	}
	
	@Override
	public void sendMessage(ClientMessage clientMessage) {
		client.sendMessage(clientMessage);
	}
	
	@Override
	public ClientMessageQueue getInputQueue() {
		return client.getInputQueue();
	}

	@Override
	public void onConnectionLost(Client client) {
		// TODO Auto-generated method stub
		
	}

}
