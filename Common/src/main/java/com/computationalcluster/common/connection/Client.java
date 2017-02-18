package com.computationalcluster.common.connection;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.transceiver.Transceiver;
import com.computationalcluster.common.connection.transceiver.TransceiverCallback;
import com.computationalcluster.common.queue.ClientMessageQueue;



public class Client implements TransceiverCallback {
	private static final Logger logger = Logger.getLogger(Client.class);
	
	private final String serverIp;
	private final int serverPort;
	private final ClientCallback callback;
	private final ClientMessageQueue inputQueue;
	private final ClientMessageQueue outputQueue;
	
	private Transceiver transceiver = null;
	private boolean connected = false;
	
	public Client(String serverIp, int serverPort, ClientCallback callback) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
		this.callback = callback;
		
		inputQueue = new ClientMessageQueue();
		outputQueue = new ClientMessageQueue();
	}
	
	public ClientMessageQueue getInputQueue(){
		return inputQueue;
	} 
	
	private Socket createSocket() {
		Socket socket = null;
		try {
			socket = new Socket(serverIp, serverPort);
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return socket;
	}
	
	private void connect() {
		final Socket socket = createSocket();
		if(socket != null) {
			transceiver = new Transceiver(socket, null, inputQueue, outputQueue, this);
			try {
				transceiver.start();
				connected = true;
			} catch (IOException e) {
				transceiver.close();
			}
		}
	}
	
	public BigInteger getId() {
		return transceiver == null ? null : transceiver.getId();
	}
	
	public void sendMessage(ClientMessage message) {
		outputQueue.addMessage(message);
		
		if(!connected) {
			connect();
		}
	}

	@Override
	public void onConnectionLost(Transceiver transceiver) {
		connected = false;
		transceiver = null;
		
		callback.onConnectionLost(this);
	}
}
