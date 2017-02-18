package com.computationalcluster.common.connection;

import com.computationalcluster.common.config.ClientConfig;
import com.computationalcluster.common.connection.Client;
import com.computationalcluster.common.connection.ClientCallback;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.queue.ClientMessageQueue;

public class DefaultClientCommunicationProxy implements CommunicationProxy, ClientCallback {

	private final Client client;
	
	public DefaultClientCommunicationProxy(ClientConfig clientConfig){
		client = new Client(clientConfig.gerServerAddress(), clientConfig.getServerPort(), this);
	}

	@Override
	public void onConnectionLost(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(ClientMessage message) {
		client.sendMessage(message);
	}

	@Override
	public ClientMessageQueue getInputQueue() {
		return client.getInputQueue();
	}

}
