package com.computationalcluster.communicationserver;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.computationalcluster.common.config.ServerConfig;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.connection.Server;
import com.computationalcluster.common.connection.ServerCallback;
import com.computationalcluster.common.queue.ClientMessageQueue;

public class DefaultCommunicationServerProxy implements CommunicationServerProxy, ServerCallback {
	private static final Logger logger = Logger.getLogger(DefaultCommunicationServerProxy.class);
	
	private final Server server;

	public DefaultCommunicationServerProxy(ServerConfig serverConfig) {
		server = new Server(serverConfig.getPort(), this);
	}
	
	@Override
	public void start() {
		server.start();
	}
	
	@Override
	public void sendMessage(ClientMessage clientMessage) {
		server.sendMessage(clientMessage);
	}

	@Override
	public void onConnectionLost(Server server, BigInteger id) {
		logger.info("onConnectionLost:" + id);
	}

	@Override
	public ClientMessageQueue getInputQueue() {
		return server.getInputQueue();
	}

	@Override
	public void disconnectClient(BigInteger clientId) {
		server.disconnectClient(clientId);
	}

}
