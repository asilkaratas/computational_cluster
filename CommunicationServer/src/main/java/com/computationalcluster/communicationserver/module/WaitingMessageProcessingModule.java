package com.computationalcluster.communicationserver.module;


import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.module.MessageProcessingModule;
import com.computationalcluster.common.queue.MessageQueue;
import com.computationalcluster.communicationserver.CommunicationServer;


public class WaitingMessageProcessingModule extends MessageProcessingModule<WaitingMessageHandler, WaitingMessage> {
	private static final Logger logger = Logger.getLogger(WaitingMessageProcessingModule.class);
	
	private final CommunicationServer communicationServer;
	private final ClientComponentType clientComponentType;
	
	public WaitingMessageProcessingModule(CommunicationServer communicationServer, ClientComponentType clientComponentType) {
		this.communicationServer = communicationServer;
		this.clientComponentType = clientComponentType;
		
		setMessageQueue(new MessageQueue<WaitingMessage>());
	}
	
	protected WaitingMessage getMessage() {
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		
		synchronized (getMessageQueue().getLock()) {
			final List<WaitingMessage> messages = getMessageQueue().getMessages();
			for(int i = 0; i < messages.size(); i++){
				final WaitingMessage message = messages.get(i);
			
				if(componentStatusModule.hasAvailableComponent(clientComponentType, message.getProblemType())){
					return messages.get(i);
				}
			}
		}
		
		logger.debug("No Matching message");
		return null;
	}
	
	public CommunicationServer getCommunicationServer() {
		return communicationServer;
	}
	
}
