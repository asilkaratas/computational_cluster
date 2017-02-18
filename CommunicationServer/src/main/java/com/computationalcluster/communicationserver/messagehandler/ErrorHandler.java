package com.computationalcluster.communicationserver.messagehandler;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.communicationserver.CommunicationServer;

public class ErrorHandler implements ClientMessageHandler{
	private static final Logger logger = Logger.getLogger(ErrorHandler.class);
	
	private final CommunicationServer communicationServer;
	
	public ErrorHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public void handle(ClientMessage clientMessage) {
		final Error message = (Error)clientMessage.getMessage();
		final BigInteger clientId = clientMessage.getClientId();
		logger.info("Error message recieved from id:" + clientId);
		
		final Error error = MessageValidatorUtil.getError(message);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(clientId, error);
			communicationServer.getProxy().sendMessage(errorMessage);
			return;
		}
		
		logger.info("Error message:" + message.getErrorMessage());
	}
	
}
