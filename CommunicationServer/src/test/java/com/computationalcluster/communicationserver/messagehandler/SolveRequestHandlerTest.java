package com.computationalcluster.communicationserver.messagehandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
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
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.SolveRequestResponse;
import com.computationalcluster.common.messagevalidator.ValidMessageFactory;
import com.computationalcluster.communicationserver.module.WaitingMessage;
import com.computationalcluster.communicationserver.module.taskmanager.DivideProblemMessage;

public class SolveRequestHandlerTest extends BaseHandlerTest {
	
	private SolveRequestHandler solveRequestHandler = null;
	
	@Before
	public void setUp() {
		super.setUp();
		solveRequestHandler = new SolveRequestHandler(getCommunicationServer());
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void validatorErrorTest() {
		BigInteger clientId = new BigInteger("1");
		SolveRequest solveRequest = ValidMessageFactory.createSolveRequest();
		solveRequest.setData(null);
		
		ClientMessage clientMessage = new ClientMessage(clientId, solveRequest);
		solveRequestHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getTaskManagerModule(), times(0)).addMessage(any(WaitingMessage.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.SOLVE_REQUEST_NULL_PROBLEM_DATA, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	
	@Test
	public void validTest() {
		BigInteger clientId = new BigInteger("1");
		SolveRequest solveRequest = ValidMessageFactory.createSolveRequest();
		
		ClientMessage clientMessage = new ClientMessage(clientId, solveRequest);
		solveRequestHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		ArgumentCaptor<DivideProblemMessage> waitingMessageCaptor = ArgumentCaptor.forClass(DivideProblemMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getTaskManagerModule(), times(1)).addMessage(waitingMessageCaptor.capture());
		verify(getProblemStatusModule(), times(1)).getNextProblemId();
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof SolveRequestResponse);
		
		assertNotNull(waitingMessageCaptor.getValue());
		assertEquals(solveRequest.getId(), waitingMessageCaptor.getValue().getProblemId());
		assertEquals(solveRequest.getProblemType(), waitingMessageCaptor.getValue().getProblemType());
	}
	
}
