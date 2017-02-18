package com.computationalcluster.common.module;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.Message;
import com.computationalcluster.common.messagehandler.MessageHandler;
import com.computationalcluster.common.queue.MessageQueue;

public class MessageProcessingModule<H extends MessageHandler<M>, M extends Message> extends TerminableThread {
	private static final Logger logger = Logger.getLogger(MessageProcessingModule.class);
	
	private final HashMap<Class<?>, H> messageHandlerMap;
	private MessageQueue<M> messageQueue = null;
	
	private boolean waitForMessage = true;
	
	public MessageProcessingModule() {
		setName("MessageProcessingModule");
		messageHandlerMap = new HashMap<Class<?>, H>();
	}
	
	public void setMessageQueue(MessageQueue<M> messageQueue){
		this.messageQueue = messageQueue;
	}
	
	public MessageQueue<M> getMessageQueue(){
		return messageQueue;
	}
	
	public void addMessage(M message) {
		messageQueue.addMessage(message);
	}
	
	public boolean hasMessageHandler(Class<?> clazz) {
		return messageHandlerMap.containsKey(clazz);
	}
	
	public void notifyQueue() {
		messageQueue.notifyLock();
	}
	
	public void addMessageHandler(Class<?> clazz, H messageHandler) {
		messageHandlerMap.put(clazz, messageHandler);
	}
	
	public boolean isWaitForMessage() {
		return waitForMessage;
	}

	public void setWaitForMessage(boolean waitForMessage) {
		this.waitForMessage = waitForMessage;
	}
	
	public void run() {
		while(running){
			M message = null;
			synchronized (messageQueue.getLock()) {
                try{
                	message = getMessage();
                	if(message == null && waitForMessage) {
        				messageQueue.getLock().wait();
        				if(!running) {
        					break;
        				}
        			}
                }catch (Exception e) {
                	logger.error(e.getMessage());
                }
            }
			

			if(message != null) {
				handleMessage(message);
				
				synchronized (messageQueue.getLock()) {
					if(message.handled()) {
						messageQueue.removeMessage(message);
					} else {
						messageQueue.skipMesage(message);
					}
				}
				
			}
			
			postAction();
		}
	}
	
	protected M getMessage() {
		if(messageQueue.hasMessage()) {
			return messageQueue.getMessage();
		}
		return null;
	}
	
	protected void postAction() {
		
	}
	
	protected void handleMessage(M message){
		H messageHandler = messageHandlerMap.get(message.getMessage().getClass());
		if(messageHandler != null) {
			messageHandler.handle(message);
		}else{
			logger.error("Unregistered message type. " + message.getMessage());
		}
	}

	
}
