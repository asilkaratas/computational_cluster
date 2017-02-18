package com.computationalcluster.common.connection.transceiver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.common.queue.ClientMessageQueue;
import com.computationalcluster.common.serializer.Serializer;
import com.computationalcluster.common.serializer.SerializerFactory;

public class Transceiver implements RecieverCallback, TransmitterCallback {
	private static final Logger logger = Logger.getLogger(Transceiver.class);
	
	public static final char EOF = (char)23;
	
	private final Socket socket;
	private final BigInteger id;
	private final ClientMessageQueue inputQueue;
	private final ClientMessageQueue outputQueue;
	private final TransceiverCallback callback;
	private final Serializer serializer;
	
	private Reciever reciever = null;
	private Transmitter transmitter = null;
	
	private boolean closed = false;
	
	public Transceiver(Socket socket, BigInteger id, 
			ClientMessageQueue inputQueue, ClientMessageQueue outputQueue,
			TransceiverCallback callback) {
		
		this.socket = socket;
		this.id = id;
		this.callback = callback;
		this.inputQueue = inputQueue;
		this.outputQueue = outputQueue;
		this.serializer = SerializerFactory.getDefaultXMLSerializer();
	}
	
	public Serializer getSerializer() {
		return serializer;
	}

	public Socket getSocket() {
		return socket;
	}

	public ClientMessageQueue getInputQueue() {
		return inputQueue;
	}

	public ClientMessageQueue getOutputQueue() {
		return outputQueue;
	}

	public void start() throws IOException {
		logger.debug("Transceiver started");
		
		final InputStream inputStream = socket.getInputStream();
		final OutputStream outputStream = socket.getOutputStream();
		
		reciever = new Reciever(inputStream, serializer, this);
		transmitter = new Transmitter(outputStream, outputQueue, serializer, this);
		
		reciever.start();
		transmitter.start();
	}
	
	public synchronized void  close() {
		if(closed) return;
		closed = true;
		
		logger.debug("Tranceiver closing.");
		
		if(reciever != null) {
			reciever.terminate();
		}
		
		if(transmitter != null) {
			transmitter.terminate();
			
			synchronized (outputQueue.getLock()) {
				outputQueue.getLock().notify();
			}
		}
		
		if(socket != null) {
			try {
				socket.getInputStream().close();
				socket.getOutputStream().close();
				socket.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		
		callback.onConnectionLost(this);
		
		logger.info("Tranceiver closed.");
	}
	
	public BigInteger getId() {
		return id;
	}

	@Override
	public void onRecieverConnectionLost() {
		close();
	}

	@Override
	public void onTransmitterConnectionLost() {
		close();
	}
	
	@Override
	public void onMessageRecieved(Object message) {
		final Error error = MessageValidatorUtil.getError(message);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(id, error);
			outputQueue.addMessage(errorMessage);
			return;
		}
		
		final ClientMessage clientMessage = new ClientMessage(id, message);
		inputQueue.addMessage(clientMessage);
	}
	
}
