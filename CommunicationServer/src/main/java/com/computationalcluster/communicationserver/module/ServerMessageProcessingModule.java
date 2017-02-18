package com.computationalcluster.communicationserver.module;


import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.SolutionRequest;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.module.ClientMessageProcessingModule;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.messagehandler.ErrorHandler;
import com.computationalcluster.communicationserver.messagehandler.RegisterHandler;
import com.computationalcluster.communicationserver.messagehandler.SolutionsHandler;
import com.computationalcluster.communicationserver.messagehandler.SolutionRequestHandler;
import com.computationalcluster.communicationserver.messagehandler.SolvePartialProblemsHandler;
import com.computationalcluster.communicationserver.messagehandler.SolveRequestHandler;
import com.computationalcluster.communicationserver.messagehandler.StatusHandler;

public class ServerMessageProcessingModule extends ClientMessageProcessingModule {
	
	private final ComponentStatusModule componentStatusModule;
	
	public ServerMessageProcessingModule(CommunicationServer communicationServer) {
		setName("ServerMessageProcessingModule");
		
		setMessageQueue(communicationServer.getProxy().getInputQueue());
		componentStatusModule = communicationServer.getComponentStatusModule();
		
		addMessageHandler(Register.class, new RegisterHandler(communicationServer));
		addMessageHandler(Status.class, new StatusHandler(communicationServer));
		addMessageHandler(SolveRequest.class, new SolveRequestHandler(communicationServer));
		addMessageHandler(SolutionRequest.class, new SolutionRequestHandler(communicationServer));
		addMessageHandler(Solutions.class, new SolutionsHandler(communicationServer));
		addMessageHandler(SolvePartialProblems.class, new SolvePartialProblemsHandler(communicationServer));
		addMessageHandler(Error.class, new ErrorHandler(communicationServer));
		
		setWaitForMessage(false);
	}
	
	@Override
	protected void postAction() {
		componentStatusModule.removeInactiveComponents();
	}
	
}
