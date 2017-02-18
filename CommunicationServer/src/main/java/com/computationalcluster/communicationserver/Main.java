package com.computationalcluster.communicationserver;


import com.computationalcluster.common.config.ServerConfig;

public class Main {
	
	public static void main(String[] args) {
		
		final ServerConfig serverConfig = new ServerConfig("CommunicationServer", args);
		if(!serverConfig.checkIsValidAndShowUsage()) {
			return;
		}
		
		final CommunicationServerProxy proxy = new DefaultCommunicationServerProxy(serverConfig);
		final CommunicationServer communicationServer = new CommunicationServer(serverConfig, proxy);
		communicationServer.start();
	}
}
