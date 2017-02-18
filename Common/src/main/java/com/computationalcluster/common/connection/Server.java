package com.computationalcluster.common.connection;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.transceiver.Transceiver;
import com.computationalcluster.common.connection.transceiver.TransceiverCallback;
import com.computationalcluster.common.module.TerminableThread;
import com.computationalcluster.common.queue.ClientMessageQueue;


public class Server extends TerminableThread implements TransceiverCallback{
	private static final Logger logger = Logger.getLogger(Server.class);
	
	private final int port;
	private final ServerCallback callback;
	private final HashMap<BigInteger, Transceiver> transceiverMap;
	private final ClientMessageQueue inputQueue;
	
	private ServerSocket serverSocker = null;
	private BigInteger counter = null;
	
	public Server(int port, ServerCallback callback) {
		setName("Server:port" + port);
		
		this.port = port;
		this.callback = callback;
		this.transceiverMap = new HashMap<>();	
		
		counter = new BigInteger("0");
		inputQueue = new ClientMessageQueue();
	}
	
	public ClientMessageQueue getInputQueue(){
		return inputQueue;
	}
	
	private Socket accept() {
		Socket socket = null;
		try {
			socket = serverSocker.accept();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return socket;
	}
	
	private ServerSocket createServerSocket() {
		ServerSocket serverSocker = null;
		try {
	    	serverSocker = new ServerSocket(port);
	    } catch(IOException e){
	    	logger.error(e.getMessage());
	    }
		
		return serverSocker;
	}
	
	public void run() {
	    logger.debug("Server is listening");
	    serverSocker = createServerSocket();
	    logger.debug("Waiting...");
	    
	    while (running) {
	    	final Socket socket = accept();
            System.out.println("Connection established: port:" + socket.getInetAddress().getHostAddress());
            
            counter = counter.add(BigInteger.ONE);
            final BigInteger id = new BigInteger(counter.toByteArray());
            logger.debug("id:" + id + " counter:" + counter);
            
            final Transceiver transceiver = new Transceiver(socket, id, inputQueue, new ClientMessageQueue(), this);
	    	try {
				transceiver.start();
				transceiverMap.put(transceiver.getId(), transceiver);
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
	    	
	    	logger.debug("transceiverMap:" + transceiverMap.size());
	    }
	}
	
	public void sendMessage(ClientMessage clientMessage) {
		if(transceiverMap.containsKey(clientMessage.getClientId())) {
			final Transceiver transceiver = transceiverMap.get(clientMessage.getClientId());
			logger.debug("Message is sent to id:" + transceiver.getId() + " m:" + clientMessage.getMessage());
			transceiver.getOutputQueue().addMessage(clientMessage);
		}
	}

	public void disconnectClient(BigInteger clientId) {
		if(transceiverMap.containsKey(clientId)) {
			final Transceiver transceiver = transceiverMap.remove(clientId);
			transceiver.close();
		}
	}

	@Override
	public void onConnectionLost(Transceiver transceiver) {
		transceiverMap.remove(transceiver);
		callback.onConnectionLost(this, transceiver.getId());
	}
}
