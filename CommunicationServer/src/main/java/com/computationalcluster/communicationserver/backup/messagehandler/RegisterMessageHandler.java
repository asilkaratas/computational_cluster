package com.computationalcluster.communicationserver.backup.messagehandler;


import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.ComponentType;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.Register.Type;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;


public class RegisterMessageHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(RegisterMessageHandler.class);
	
	private final CommunicationServer communicationServer;
	
	public RegisterMessageHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public void handle(ClientMessage clientMessage) {
		logger.debug("Register message is handled");
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		final Register register = (Register)clientMessage.getMessage();
		
		final Type type = register.getType();
		final ComponentType componentType = type.getValue();
		
		System.out.println("Registering id:" + clientMessage.getClientId() + " type:" + componentType);
		/*
		if(componentType != null) {
			switch(componentType){
			case TASK_MANAGER:
				
				break;
				
			case COMPUTATIONAL_NODE:
				
				break;
				
			case COMMUNICATION_SERVER:
				
				break;
			}
		}
		*/
		
		//ComponentStatus componentStatus = new ComponentStatus(clientMessage.getClientId(), type);
		//componentStatusModule.registerComponent(componentStatus);
		
	}

}
