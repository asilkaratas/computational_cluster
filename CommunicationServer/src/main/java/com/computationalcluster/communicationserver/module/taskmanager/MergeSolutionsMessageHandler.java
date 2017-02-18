package com.computationalcluster.communicationserver.module.taskmanager;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.WaitingMessage;
import com.computationalcluster.communicationserver.module.WaitingMessageHandler;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;

public class MergeSolutionsMessageHandler implements WaitingMessageHandler {
	private static final Logger logger = Logger.getLogger(MergeSolutionsMessageHandler.class);
	
	private final CommunicationServer communicationServer;
	
	public MergeSolutionsMessageHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	@Override
	public void handle(WaitingMessage waitingMessage) {
		final MergeSolutionsMessage message = (MergeSolutionsMessage)waitingMessage;
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		final ComponentStatus componentStatus = componentStatusModule.getAvailableComponent(ClientComponentType.TASK_MANAGER, message.getProblemType());
		final BigInteger taskManagerId = componentStatus.getClientId();
		
		final ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
		final ProblemStatus problemStatus = problemStatusModule.getProblemStatus(message.getProblemId());
		
		System.out.println("Solution message sent to TM taskManagerId:" + taskManagerId + " problemId:" + message.getProblemId());
		final Solutions solutions = problemStatus.getPartialSolutions();
		final List<Solution> solutionList = problemStatus.getPartialSolutions().getSolutionsList().getSolution();
		logger.debug("Ssolutions size:" + solutionList.size() + " solution[0]:" + solutionList.get(0));
		logger.debug("commonData:" + solutions.getCommonData());
		
		final ClientMessage partialSolutionsMessage = new ClientMessage(taskManagerId, solutions);
		communicationServer.getProxy().sendMessage(partialSolutionsMessage);
	}

}
