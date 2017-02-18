package com.computationalcluster.common.messagehandler;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.computationalcluster.common.component.SolverClientComponent;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messages.Error;

public class ErrorHandler implements ClientMessageHandler{
	private static final Logger logger = Logger.getLogger(ErrorHandler.class);
	
	private final SolverClientComponent clientComponent;
	
	public ErrorHandler(SolverClientComponent clientComponent) {
		this.clientComponent = clientComponent;
	}
	
	@Override
	public void handle(ClientMessage clientMessage) {
		Error message = (Error)clientMessage.getMessage();
		BigInteger clientId = clientMessage.getClientId();
		logger.info("Error message recieved from id:" + clientId);
		
		/*
		Error error = MessageValidatorUtil.getError(message);
		if(error != null) {
			logger.error(error.getErrorMessage());
			ClientMessage errorMessage = new ClientMessage(clientId, error);
			clientComponent.getProxy().sendMessage(errorMessage);
			return;
		}
		*/
		
		logger.info("Error message:" + message.getErrorMessage());
	}
	
}
