package com.computationalcluster.integrationtest.helper;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.communicationserver.CommunicationServer;

public class SendMessageAnswer implements Answer<Void> {

	private CommunicationServer communicationServer = null;
	
	public SendMessageAnswer(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public Void answer(InvocationOnMock invocation) throws Throwable {
		ClientMessage clientMessage = (ClientMessage)invocation.getArguments()[0];
		communicationServer.getProxy().getInputQueue().addMessage(clientMessage);
		return null;
	}

}
