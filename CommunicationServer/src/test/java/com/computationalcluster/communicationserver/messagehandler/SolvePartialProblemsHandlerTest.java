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
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messagevalidator.ValidMessageFactory;
import com.computationalcluster.communicationserver.module.WaitingMessage;

public class SolvePartialProblemsHandlerTest extends BaseHandlerTest {
	
	private SolvePartialProblemsHandler solvePartialProblemsHandler = null;
	
	@Before
	public void setUp() {
		super.setUp();
		solvePartialProblemsHandler = new SolvePartialProblemsHandler(getCommunicationServer());
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void validatorErrorTest() {
		BigInteger clientId = new BigInteger("1");
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		solvePartialProblems.setId(null);
		
		ClientMessage clientMessage = new ClientMessage(clientId, solvePartialProblems);
		solvePartialProblemsHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getComputationalNodeModule(), times(0)).addMessage(any(WaitingMessage.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_ID, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	@Test
	public void unknownSenderErrorTest() {
		BigInteger clientId = new BigInteger("2");
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		
		ClientMessage clientMessage = new ClientMessage(clientId, solvePartialProblems);
		solvePartialProblemsHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getComputationalNodeModule(), times(0)).addMessage(any(WaitingMessage.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.UNKNOWN_SENDER.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.SENDER_IS_UNKNOWN, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	/*
	@Test
	public void validTest() {
		BigInteger clientId = new BigInteger("1");
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		
		ClientMessage clientMessage = new ClientMessage(clientId, solvePartialProblems);
		solvePartialProblemsHandler.handle(clientMessage);
		
		ArgumentCaptor<WaitingMessage> waitingMessageCaptor = ArgumentCaptor.forClass(WaitingMessage.class);
		
		verify(getCommunicationProxy(), times(0)).sendMessage(any(ClientMessage.class));
		verify(getComputationalNodeModule(), times(1)).addMessage(waitingMessageCaptor.capture());
		
		assertNotNull(waitingMessageCaptor.getValue());
		assertEquals(solvePartialProblems, waitingMessageCaptor.getValue().getMessage());
		assertEquals(solvePartialProblems.getProblemType(), waitingMessageCaptor.getValue().getProblemType());
		
	}
	*/
}
