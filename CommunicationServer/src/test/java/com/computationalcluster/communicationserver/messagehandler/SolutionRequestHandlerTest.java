package com.computationalcluster.communicationserver.messagehandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolutionRequest;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messagevalidator.ValidMessageFactory;

public class SolutionRequestHandlerTest extends BaseHandlerTest {
	
	private SolutionRequestHandler solutionRequestHandler = null;
	
	@Before
	public void setUp() {
		super.setUp();
		solutionRequestHandler = new SolutionRequestHandler(getCommunicationServer());
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void validatorErrorTest() {
		BigInteger clientId = new BigInteger("1");
		SolutionRequest solutionRequest = ValidMessageFactory.createSolutionRequest();
		solutionRequest.setId(null);
		
		ClientMessage clientMessage = new ClientMessage(clientId, solutionRequest);
		solutionRequestHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.SOLUTION_REQUEST_NULL_ID, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	/*
	@Test
	public void validTest() {
		Solutions solutions = new Solutions();
		
		BigInteger clientId = new BigInteger("1");
		SolutionRequest solutionRequest = ValidMessageFactory.createSolutionRequest();
		
		ClientMessage clientMessage = new ClientMessage(clientId, solutionRequest);
		solutionRequestHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Solutions);
		assertEquals(solutions, messageCaptor.getValue().getMessage());
	}
	*/
	
}
