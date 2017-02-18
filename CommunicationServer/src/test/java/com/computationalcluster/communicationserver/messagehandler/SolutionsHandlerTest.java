package com.computationalcluster.communicationserver.messagehandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messagevalidator.ValidMessageFactory;
import com.computationalcluster.communicationserver.module.WaitingMessage;

public class SolutionsHandlerTest extends BaseHandlerTest {
	
	private SolutionsHandler solutionsHandler = null;
	
	@Before
	public void setUp() {
		super.setUp();
		
		solutionsHandler = new SolutionsHandler(getCommunicationServer());
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void validatorErrorTest() {
		BigInteger clientId = new BigInteger("1");
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.setId(null);
		
		ClientMessage clientMessage = new ClientMessage(clientId, solutions);
		solutionsHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getTaskManagerModule(), times(0)).addMessage(any(WaitingMessage.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_NULL_ID, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	@Test
	public void unknownSenderErrorTest() {
		BigInteger clientId = new BigInteger("2");
		Solutions solutions = ValidMessageFactory.createSolutions();
		
		ClientMessage clientMessage = new ClientMessage(clientId, solutions);
		solutionsHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getTaskManagerModule(), times(0)).addMessage(any(WaitingMessage.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.UNKNOWN_SENDER.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_HANDLER_UNKNOWN_SENDER, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	@Test
	public void invalidComponentTypeErrorTest() {
		when(getComponentStatus().getClientComponentType()).thenReturn(ClientComponentType.COMMUNICATION_SERVER);
		
		BigInteger clientId = new BigInteger("1");
		Solutions solutions = ValidMessageFactory.createSolutions();
		
		ClientMessage clientMessage = new ClientMessage(clientId, solutions);
		solutionsHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getTaskManagerModule(), times(0)).addMessage(any(WaitingMessage.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_HANDLER_INVALID_COMPONENT_TYPE, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	/*
	@Test
	public void computationalNodeValidTest() {
		when(getComponentStatus().getClientComponentType()).thenReturn(ClientComponentType.COMPUTATIONAL_NODE);
		
		BigInteger clientId = new BigInteger("1");
		Solutions solutions = ValidMessageFactory.createSolutions();
		
		ClientMessage clientMessage = new ClientMessage(clientId, solutions);
		solutionsHandler.handle(clientMessage);
		
		ArgumentCaptor<WaitingMessage> waitingMessageCapture = ArgumentCaptor.forClass(WaitingMessage.class);
		
		verify(getCommunicationProxy(), times(0)).sendMessage(any(ClientMessage.class));
		verify(getTaskManagerModule(), times(1)).addMessage(waitingMessageCapture.capture());
		
		assertNotNull(waitingMessageCapture.getValue());
		
	}
	
	
	@Test
	public void taskManagerValidTest() {
		when(getComponentStatus().getClientComponentType()).thenReturn(ClientComponentType.TASK_MANAGER);
		
		BigInteger clientId = new BigInteger("1");
		Solutions solutions = ValidMessageFactory.createSolutions();
		
		ClientMessage clientMessage = new ClientMessage(clientId, solutions);
		solutionsHandler.handle(clientMessage);
		
		ArgumentCaptor<Solutions> solutionsCapture = ArgumentCaptor.forClass(Solutions.class);
		
		verify(getCommunicationProxy(), times(0)).sendMessage(any(ClientMessage.class));
		verify(getTaskManagerModule(), times(0)).addMessage(any(WaitingMessage.class));
		
		assertNotNull(solutionsCapture.getValue());
		
	}
	
	*/
	
	
}
