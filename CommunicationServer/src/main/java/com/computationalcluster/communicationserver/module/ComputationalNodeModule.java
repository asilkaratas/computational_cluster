package com.computationalcluster.communicationserver.module;

import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.computationalnode.SolvePartialProblemsMessage;
import com.computationalcluster.communicationserver.module.computationalnode.SolvePartialProblemsMessageHandler;

public class ComputationalNodeModule extends WaitingMessageProcessingModule {
	
	public ComputationalNodeModule(CommunicationServer communicationServer) {
		super(communicationServer, ClientComponentType.COMPUTATIONAL_NODE);
		setName("ComputationalNodeModule");
		
		addMessageHandler(SolvePartialProblemsMessage.class, new SolvePartialProblemsMessageHandler(communicationServer));
	}
	
}
