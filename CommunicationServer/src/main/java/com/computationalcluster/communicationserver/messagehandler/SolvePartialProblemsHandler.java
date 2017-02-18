package com.computationalcluster.communicationserver.messagehandler;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.WaitingMessage;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentAssigment;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;
import com.computationalcluster.communicationserver.module.computationalnode.SolvePartialProblemsMessage;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;
import com.computationalcluster.communicationserver.module.taskmanager.DivideProblemMessage;
import com.computationalcluster.communicationserver.module.taskmanager.MergeSolutionsMessage;

public class SolvePartialProblemsHandler implements ClientMessageHandler{
	private static final Logger logger = Logger.getLogger(SolvePartialProblemsHandler.class);
	
	private final CommunicationServer communicationServer;
	
	
	public SolvePartialProblemsHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	@Override
	public void handle(ClientMessage clientMessage) {
		final SolvePartialProblems solvePartialProblems = (SolvePartialProblems)clientMessage.getMessage();
		final BigInteger clientId = clientMessage.getClientId();
		final String problemType = solvePartialProblems.getProblemType();
		
		logger.info("Solve partial problems is recieved from id:" + clientId + 
				" problemType:" + problemType);
		
		final Error error = MessageValidatorUtil.getError(solvePartialProblems);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(clientId, error);
			communicationServer.getProxy().sendMessage(errorMessage);
			return;
		}
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		final ComponentStatus componentStatus = componentStatusModule.getComponent(clientId);
		
		if(componentStatus == null) {
			sendUnknownSenderError(clientId);
			return;
		}
		
		final ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
		final ProblemStatus problemStatus = problemStatusModule.getProblemStatus(solvePartialProblems.getId());
		problemStatus.setSolvePartialProblems(solvePartialProblems);
		
		synchronized (componentStatus) {
			componentStatus.releaseThreads(1);
			removeAssignment(componentStatus, problemStatus.getProblemId());
		}
		
		System.out.println("Solve partial problems is recieved from clientId:" + clientId + 
				" problemId:" + problemStatus.getProblemId() + " problemType:" + problemType);
		final WaitingMessage solvePartialProblemsMessage = new SolvePartialProblemsMessage(problemStatus.getProblemId(), problemType);
		communicationServer.getComputationalNodeModule().addMessage(solvePartialProblemsMessage);
	}
	
	private void removeAssignment(ComponentStatus componentStatus, BigInteger problemId) {
		final List<ComponentAssigment> componentAssigments = componentStatus.getComponentAssigments();
		for(int i = 0; i < componentAssigments.size(); i++) {
			final ComponentAssigment componentAssigment = componentAssigments.get(i);
			
			if(componentAssigment.getMessage().getClass().equals(DivideProblemMessage.class)) {
				final DivideProblemMessage message = (DivideProblemMessage)componentAssigment.getMessage();
				if(message.getProblemId() == problemId) {
					componentAssigments.remove(i);
					logger.debug("Assignment removed problemId:" + message.getProblemId());
				}
			}
		}
	}
	
	private void sendUnknownSenderError(BigInteger clientId) {
		final Error error = new Error();
		error.setErrorType(ErrorType.UNKNOWN_SENDER.getName());
		error.setErrorMessage(ErrorMessage.SENDER_IS_UNKNOWN);
		
		logger.error(error.getErrorMessage());
		
		final ClientMessage responseMessage = new ClientMessage(clientId, error);
		communicationServer.getProxy().sendMessage(responseMessage);
	}

}
