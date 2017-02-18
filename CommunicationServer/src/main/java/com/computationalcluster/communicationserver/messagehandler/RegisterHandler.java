package com.computationalcluster.communicationserver.messagehandler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.ComponentType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.Register.SolvableProblems;
import com.computationalcluster.common.messages.Register.Type;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;


public class RegisterHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(RegisterHandler.class);
	
	private final CommunicationServer communicationServer;
	
	public RegisterHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public void handle(ClientMessage clientMessage) {
		logger.info("Register message is handled");
		
		final Register register = (Register)clientMessage.getMessage();
		final BigInteger clientId = clientMessage.getClientId();
		
		final Type type = register.getType();
		final ComponentType componentType = type.getValue();
		final ClientComponentType clientComponentType = ClientComponentType.fromName(componentType.value());
		
		if(clientComponentType != ClientComponentType.COMMUNICATION_SERVER){
			final SolvableProblems solvableProblems = register.getSolvableProblems();
			final List<String> problemTypeNames = solvableProblems.getProblemName();
			final int threadCount = register.getParallelThreads();
			registerComponent(clientId, clientComponentType, threadCount, problemTypeNames);
		}else{
			registerCommunicationServer(clientId);
		}
		
		
		
		System.out.println(clientComponentType.getName() +  " is registered with clientId:" + clientId);
		
		
		sendRegisterResponse(clientId);
		//communicationServer.getProxy().disconnectClient(clientId);
		
		
	}
	
	private void registerCommunicationServer(BigInteger clientId){
		final SolvableProblems solvableProblems = new SolvableProblems();
		final List<String> problemTypeNames = new ArrayList<String>();
		final int threadCount = 1;
		final ComponentStatus componentStatus = new ComponentStatus(clientId, ClientComponentType.COMMUNICATION_SERVER, threadCount, problemTypeNames);
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		componentStatusModule.registerComponent(componentStatus);
	}
	
	private void registerComponent(BigInteger clientId, ClientComponentType clientComponentType, int threadCount, List<String> problemTypeNames) {
		final ComponentStatus componentStatus = new ComponentStatus(clientId, clientComponentType, threadCount, problemTypeNames);
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		componentStatusModule.registerComponent(componentStatus);
	}
	
	private void sendRegisterResponse(BigInteger clientId ) {
		final RegisterResponse registerResponse = new RegisterResponse();
		registerResponse.setId(clientId);
		registerResponse.setTimeout(communicationServer.getServerConfig().getTimeout());
		
		logger.info("Register response is sent clientId:" + clientId);
		
		final ClientMessage responseClientMessage = new ClientMessage(clientId, registerResponse);
		communicationServer.getProxy().sendMessage(responseClientMessage);
	}

}
