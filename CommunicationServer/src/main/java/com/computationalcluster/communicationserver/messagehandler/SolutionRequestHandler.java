package com.computationalcluster.communicationserver.messagehandler;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.enums.ProblemType;
import com.computationalcluster.common.enums.SolutionType;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolutionRequest;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList;
import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;

public class SolutionRequestHandler implements ClientMessageHandler{
	private static final Logger logger = Logger.getLogger(SolutionRequestHandler.class);
	
	private  final CommunicationServer communicationServer;
	
	public SolutionRequestHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public void handle(ClientMessage clientMessage) {
		final SolutionRequest solutionRequest = (SolutionRequest)clientMessage.getMessage();
		
		logger.info("Solution request recieved from:" + clientMessage.getClientId() + " problemId:" + solutionRequest.getId());
		
		final Error error = MessageValidatorUtil.getError(solutionRequest);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(clientMessage.getClientId(), error);
			communicationServer.getProxy().sendMessage(errorMessage);
			return;
		}
		
		final ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
		final ProblemStatus problemStatus = problemStatusModule.getProblemStatus(solutionRequest.getId());
		Solutions solutions = problemStatus.getFinalSolution();
		
		if(solutions != null) {
			logger.info("Solution is sent to id:" + clientMessage.getClientId());
			
			final ClientMessage responseMessage = new ClientMessage(clientMessage.getClientId(), solutions);
			communicationServer.getProxy().sendMessage(responseMessage);
			
		}else{
			final Solution solution = new Solution();
			solution.setData(new byte[10]);
			solution.setTaskId(new BigInteger("1"));
			solution.setComputationsTime(new BigInteger("10"));
			solution.setType(SolutionType.ONGOING.getName());
			
			final SolutionsList solutionList = new SolutionsList();
			solutionList.getSolution().add(solution);
			
			solutions = new Solutions();
			solutions.setCommonData(new byte[10]);
			solutions.setProblemType(ProblemType.TSP.getName());
			solutions.setId(problemStatus.getProblemId());
			solutions.setSolutionsList(solutionList);
			
			final ClientMessage responseMessage = new ClientMessage(clientMessage.getClientId(), solutions);
			communicationServer.getProxy().sendMessage(responseMessage);
			
			
			logger.info("Solution is not ready");
		}
	}
	

}
