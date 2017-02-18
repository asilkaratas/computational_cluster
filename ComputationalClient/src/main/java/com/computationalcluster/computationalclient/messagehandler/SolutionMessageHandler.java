package com.computationalcluster.computationalclient.messagehandler;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.enums.SolutionType;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.computationalclient.ComputationalClient;
import com.computationalcluster.computationalclient.ProblemInfo;

public class SolutionMessageHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(SolutionMessageHandler.class);
	
	private final ComputationalClient computationalClient;
	
	public SolutionMessageHandler(ComputationalClient computationalClient){
		this.computationalClient = computationalClient;
	}

	@Override
	public void handle(ClientMessage clientMessage) {
		final Solutions solutions = (Solutions)clientMessage.getMessage();
		
		final Error error = MessageValidatorUtil.getError(solutions);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(computationalClient.getId(), error);
			computationalClient.getProxy().sendMessage(errorMessage);
			return;
		}
		
		final Solution solution = solutions.getSolutionsList().getSolution().get(0);
		
		System.out.println("\nSolution is recieved");
		System.out.println("problemId:" + solutions.getId());
		System.out.println("poblemType:" + solutions.getProblemType());
		System.out.println("computationTime:" + solution.getComputationsTime());
		
		if(solution.getType().equals(SolutionType.FINAL.getName())) {
			final BigInteger problemId = solutions.getId();
			ProblemInfo problemInfo = computationalClient.getProblem(problemId);
			if(problemInfo == null) {
				computationalClient.addProblem(problemId);
				problemInfo = computationalClient.getProblem(problemId);
			}
			problemInfo.setSolution(solution);
			final byte[] solutionBytes = Base64.decodeBase64(solution.getData());
			final String solutionString = new String(solutionBytes);
			System.out.println("Final solutions is recieved.");
			System.out.println("Solution:" + solutionString);
			
		} else {
			System.out.println("Computation is ongoing.");
		}
		
		
		
		
	}

}
