package com.computationalcluster.computationalclient;

import com.computationalcluster.common.config.ClientConfig;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.connection.DefaultClientCommunicationProxy;
import com.computationalcluster.computationalclient.console.ComputationalClientConsole;

public class Main {
	public static void main(String[] args){
		
		final ClientConfig clientConfig = new ClientConfig("ComputationalClient", args);
		if(!clientConfig.checkIsValidAndShowUsage()) {
			return;
		}
		
		final CommunicationProxy proxy = new DefaultClientCommunicationProxy(clientConfig);
		final ComputationalClient computationalClient = new ComputationalClient(proxy);
		final boolean started = computationalClient.start();
		
		if(started){
			final ComputationalClientConsole console = new ComputationalClientConsole(computationalClient);
			console.start();
		}
		
	}
}
