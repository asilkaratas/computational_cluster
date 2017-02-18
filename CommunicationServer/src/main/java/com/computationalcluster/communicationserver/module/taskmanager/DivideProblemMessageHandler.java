package com.computationalcluster.communicationserver.module.taskmanager;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messages.DivideProblem;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.WaitingMessage;
import com.computationalcluster.communicationserver.module.WaitingMessageHandler;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentAssigment;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;

public class DivideProblemMessageHandler implements WaitingMessageHandler{
	private static final Logger logger = Logger.getLogger(DivideProblemMessageHandler.class);
	
	private final CommunicationServer communicationServer;

	public DivideProblemMessageHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public void handle(WaitingMessage waitingMessage) {
		final DivideProblemMessage divideProblemMessage = (DivideProblemMessage)waitingMessage;
		
		final DivideProblem response = divideProblem(divideProblemMessage);
		
		System.out.println("DivideProblem sent to taskManagerId:" + response.getNodeID() + 
				" problemId:" + response.getId());
		
		final ClientMessage clientMessage = new ClientMessage(response.getNodeID(), response);
		communicationServer.getProxy().sendMessage(clientMessage);
	}
	
	private DivideProblem divideProblem(DivideProblemMessage divideProblemMessage) {
		final ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
		final ProblemStatus problemStatus = problemStatusModule.getProblemStatus(divideProblemMessage.getProblemId());
		final SolveRequest solveRequest = problemStatus.getSolveRequest();
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		final ComponentStatus componentStatus = componentStatusModule.getAvailableComponent(ClientComponentType.TASK_MANAGER, solveRequest.getProblemType());
		final long computationalNodes = componentStatusModule.getMaxComputationalNodeThreads();
		
		synchronized (componentStatus) {	
			final ComponentAssigment componentAssigment = new ComponentAssigment(divideProblemMessage);
			componentStatus.getComponentAssigments().add(componentAssigment);
			componentStatus.allocateThreads(1);
		}
		
		final DivideProblem divideProblem = new DivideProblem();
		divideProblem.setId(problemStatus.getProblemId());
		divideProblem.setData(solveRequest.getData());
		divideProblem.setProblemType(solveRequest.getProblemType());
		divideProblem.setComputationalNodes(new BigInteger(String.valueOf(computationalNodes)));
		divideProblem.setNodeID(componentStatus.getClientId());
		
		return divideProblem;
	}
	
}
