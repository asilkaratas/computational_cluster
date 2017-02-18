package com.computationalcluster.communicationserver.backup.module;


import org.apache.log4j.Logger;

import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.module.ClientMessageProcessingModule;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.messagehandler.RegisterHandler;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;

public class BackupServerMessageProcessingModule extends ClientMessageProcessingModule {
	private static final Logger logger = Logger.getLogger(BackupServerMessageProcessingModule.class);
	
	private final ComponentStatusModule componentStatusModule;
	
	public BackupServerMessageProcessingModule(CommunicationServer communicationServer) {
		setMessageQueue(communicationServer.getProxy().getInputQueue());
		componentStatusModule = communicationServer.getComponentStatusModule();
		
		addMessageHandler(Register.class, new RegisterHandler(communicationServer));
		
		/*
		addMessageHandler(Status.class, new StatusMessageHandler(communicationServer));
		addMessageHandler(SolveRequest.class, new SolveRequestHandler(communicationServer));
		addMessageHandler(SolutionRequest.class, new SolutionRequestHandler(communicationServer));
		addMessageHandler(SolutionMessage.class, new SolutionMessageHandler(communicationServer));
		addMessageHandler(SolvePartialProblems.class, new SolvePartialProblemsHandler(communicationServer));
		*/
	}
	
	public void run() {
		
		while(true){
			if(getMessageQueue().hasMessage()) {
        		handleMessage(getMessageQueue().getMessage());
			}
		}
	}
	
}
