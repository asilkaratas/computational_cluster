package com.computationalcluster.common.connection.transceiver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.computationalcluster.common.module.TerminableThread;
import com.computationalcluster.common.serializer.Serializer;

public class Reciever extends TerminableThread {
	private static final Logger logger = Logger.getLogger(Reciever.class);
	
	private final BufferedReader inputStream;
	private final Serializer serializer;
	private final RecieverCallback callback;
	
	public Reciever(InputStream inputStream, Serializer serializer, RecieverCallback callback) {
		setName("Reciever");
		this.inputStream = new BufferedReader(new InputStreamReader(inputStream));
		this.serializer = serializer;
		this.callback = callback;
	}
	
	private int read() {
		try {
			return inputStream.read();
		} catch (IOException e) {
			logger.error(e.getMessage());
			return -1;
		}
	}
	
	public void run() {
		logger.debug("Reciever run");

    	StringBuilder builder = new StringBuilder();
    	int s;
        while(running){
        	//logger.debug("Reciever reading.");
            s = read();
            
            if(s == -1){
            	callback.onRecieverConnectionLost();
            } else if(s == Transceiver.EOF) {
            	logger.debug("Message is recieved: " + builder.toString());
            	
            	InputStream stream = new ByteArrayInputStream(builder.toString().getBytes());
            	
            	Object message = serializer.readObject(stream);
            	callback.onMessageRecieved(message);
        		
            	builder = new StringBuilder();
            } else{
            	builder.append((char)s);
            }
        }
        
		logger.debug("Reciever quits");
	}
	
}