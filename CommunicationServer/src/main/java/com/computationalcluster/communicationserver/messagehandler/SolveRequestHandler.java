package com.computationalcluster.communicationserver.messagehandler;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.SolveRequestResponse;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.TaskManagerModule;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;
import com.computationalcluster.communicationserver.module.taskmanager.DivideProblemMessage;

public class SolveRequestHandler implements ClientMessageHandler{
	private static final Logger logger = Logger.getLogger(SolveRequestHandler.class);
	
	private final CommunicationServer communicationServer;

	public SolveRequestHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public void handle(ClientMessage clientMessage) {
		final SolveRequest solveRequest = (SolveRequest)clientMessage.getMessage();
		final BigInteger clientId = clientMessage.getClientId();
		final String problemType = solveRequest.getProblemType();
		
		System.out.println("Solve request is recieved from clientId:" + clientId + 
				" problemType:" + problemType);
		
		final SolveRequestResponse response = solveProblem(solveRequest);
		System.out.println("Problem registered problemId:" + response.getId());
		
		final ClientMessage responseMessage = new ClientMessage(clientId, response);
		communicationServer.getProxy().sendMessage(responseMessage);
	}
	
	private SolveRequestResponse solveProblem(SolveRequest solveRequest) {
		final ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
		final BigInteger problemId = problemStatusModule.getNextProblemId();
		final String problemType = solveRequest.getProblemType();
		
		final ProblemStatus problemStatus = new ProblemStatus(problemId);
		problemStatus.setSolveRequest(solveRequest);
		problemStatusModule.addProblemStatus(problemStatus);
		
		final DivideProblemMessage divideProblemMessage = new DivideProblemMessage(problemId, problemType);
		TaskManagerModule taskManagerModule = communicationServer.getTaskManagerModule();
		taskManagerModule.addMessage(divideProblemMessage);
		
		final SolveRequestResponse solveRequestResponse = new SolveRequestResponse();
		solveRequestResponse.setId(problemId);
		
		return solveRequestResponse;
	}
	
	
}
