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
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.messages.Status.Threads.Thread;
import com.computationalcluster.common.messagevalidator.ValidMessageFactory;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;

public class StatusHandlerTest extends BaseHandlerTest {
	
	private StatusHandler statusMessageHandler = null;
	
	@Before
	public void setUp() {
		super.setUp();
		
		statusMessageHandler = new StatusHandler(getCommunicationServer());
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void validatorErrorTest() {
		BigInteger clientId = new BigInteger("1");
		Status status = ValidMessageFactory.createStatus();
		status.setId(null);
		
		ClientMessage clientMessage = new ClientMessage(clientId, status);
		statusMessageHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getComponentStatusModule(), times(0)).updateComponentStatus(any(ComponentStatus.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.STATUS_NULL_ID, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	@Test
	public void invalidIdErrorTest() {
		BigInteger clientId = getComponentStatus().getClientId();
		Status status = ValidMessageFactory.createStatus();
		status.setId(new BigInteger("2"));
		
		ClientMessage clientMessage = new ClientMessage(clientId, status);
		statusMessageHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getComponentStatusModule(), times(0)).updateComponentStatus(any(ComponentStatus.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.STATUS_HANDLER_INVALID_ID, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	
	@Test
	public void unknownSenderErrorTest() {
		BigInteger clientId = new BigInteger("2");
		Status status = ValidMessageFactory.createStatus();
		status.setId(clientId);
		
		ClientMessage clientMessage = new ClientMessage(clientId, status);
		statusMessageHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getComponentStatusModule(), times(0)).updateComponentStatus(any(ComponentStatus.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.UNKNOWN_SENDER.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.STATUS_HANDLER_UNKNOWN_SENDER, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	/*
	@Test
	public void invalidThreadCountErrorTest() {
		BigInteger clientId = new BigInteger("1");
		Status status = ValidMessageFactory.createStatus();
		Thread thread = status.getThreads().getThread().get(0);
		status.getThreads().getThread().add(thread);
		
		ClientMessage clientMessage = new ClientMessage(clientId, status);
		statusMessageHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getComponentStatusModule(), times(0)).updateComponentStatus(any(ComponentStatus.class));
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.STATUS_HANDLER_INVALID_THREAD_COUNT, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	
	@Test
	public void validTest() {
		BigInteger clientId = new BigInteger("1");
		Status status = ValidMessageFactory.createStatus();
		
		ClientMessage clientMessage = new ClientMessage(clientId, status);
		statusMessageHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		ArgumentCaptor<ComponentStatus> componentStatusCaptor = ArgumentCaptor.forClass(ComponentStatus.class);
		
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getComponentStatusModule(), times(1)).updateComponentStatus(componentStatusCaptor.capture());
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof NoOperation);
		
		assertNotNull(componentStatusCaptor.getValue());
		
	}
	*/
	
	
	
	
}
