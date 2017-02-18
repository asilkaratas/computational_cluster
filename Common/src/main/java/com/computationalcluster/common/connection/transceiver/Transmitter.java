package com.computationalcluster.common.connection.transceiver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.module.TerminableThread;
import com.computationalcluster.common.queue.ClientMessageQueue;
import com.computationalcluster.common.serializer.Serializer;

public class Transmitter extends TerminableThread {
	private static final Logger logger = Logger.getLogger(Transmitter.class);
	
	private final OutputStream outputStream;
	private final ClientMessageQueue outputQueue;
	private final Serializer serializer ;
	private final TransmitterCallback callback;
	
	public Transmitter(OutputStream outputStream, ClientMessageQueue outputQueue, Serializer serializer, TransmitterCallback callback) {
		setName("Transmitter");
		this.outputStream = outputStream;
		this.outputQueue = outputQueue;
		this.serializer = serializer;
		this.callback = callback;
	}
	
	private boolean write(ByteArrayOutputStream stream) {
		try {
			stream.write(Transceiver.EOF);
			stream.writeTo(outputStream);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return false;
		}
		logger.debug("Message sent.");
		return true;
	}
	
	public void run() {
		logger.debug("Transmitter run");
		
		while(running) {
			synchronized (outputQueue.getLock()) {
				if(outputQueue.hasMessage()) {
            		final ClientMessage clientMessage = outputQueue.getMessage();
            		final ByteArrayOutputStream stream = (ByteArrayOutputStream)serializer.writeObject(clientMessage.getMessage());
            		logger.info("here");
            		if(!write(stream)) {
            			callback.onTransmitterConnectionLost();
            		}
    			} else {
    				try {
    					outputQueue.getLock().wait();
					} catch (InterruptedException e) {
						logger.info(e.getMessage());
					}
    			}
            }
		}
		
		logger.debug("Transmitter quits");
	}
	
}
