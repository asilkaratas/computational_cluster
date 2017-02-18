package com.computationalcluster.computationalclient.messagehandler;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolveRequestResponse;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.computationalclient.ComputationalClient;

public class SolveRequestResponseHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(SolutionMessageHandler.class);
	
	private final ComputationalClient computationalClient;
	
	public SolveRequestResponseHandler(ComputationalClient computationalClient){
		this.computationalClient = computationalClient;
	}

	@Override
	public void handle(ClientMessage clientMessage) {
		final SolveRequestResponse solveRequestResponse = (SolveRequestResponse)clientMessage.getMessage();
		
		final Error error = MessageValidatorUtil.getError(solveRequestResponse);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(computationalClient.getId(), error);
			computationalClient.getProxy().sendMessage(errorMessage);
			return;
		}
		
		System.out.println("\nSolveRequestResponse is recieved.");
		System.out.println("problemId:" + solveRequestResponse.getId());
		
		computationalClient.addProblem(solveRequestResponse.getId());
	}

}
