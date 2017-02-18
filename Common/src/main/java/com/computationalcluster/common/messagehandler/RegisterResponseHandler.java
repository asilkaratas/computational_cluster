package com.computationalcluster.common.messagehandler;

import org.apache.log4j.Logger;

import com.computationalcluster.common.component.SolverClientComponent;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;

public class RegisterResponseHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(RegisterResponseHandler.class);
	
	private final SolverClientComponent clientComponent;
	
	public RegisterResponseHandler(SolverClientComponent clientComponent) {
		this.clientComponent = clientComponent;
	}

	@Override
	public void handle(ClientMessage clientMessage) {
		final RegisterResponse registerResponse = (RegisterResponse)clientMessage.getMessage();
		System.out.println("Register response recieved: id:" + registerResponse.getId() +
				" timeout:" + registerResponse.getTimeout());
		
		final Error error = MessageValidatorUtil.getError(registerResponse);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(clientMessage.getClientId(), error);
			clientComponent.getProxy().sendMessage(errorMessage);
			return;
		}
		
		clientComponent.setId(registerResponse.getId());
		clientComponent.getStatusSendingModule().setTimeout(registerResponse.getTimeout());
		clientComponent.getStatusSendingModule().start();
		clientComponent.getTaskSolverModule().start();
	}
	

}
