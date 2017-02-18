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
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.ComponentType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messagevalidator.ValidMessageFactory;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;

public class RegisterHandlerTest extends BaseHandlerTest {
	
	private RegisterHandler registerMessageHandler = null;
	
	@Before
	public void setUp() {
		super.setUp();
		registerMessageHandler = new RegisterHandler(getCommunicationServer());
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void validatorErrorTest() {
		BigInteger clientId = new BigInteger("1");
		Register register = ValidMessageFactory.createRegister();
		register.setType(null);
		
		ClientMessage clientMessage = new ClientMessage(clientId, register);
		registerMessageHandler.handle(clientMessage);
		
		ArgumentCaptor<ClientMessage> errorMessageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getComponentStatusModule(), times(0)).registerComponent(any(ComponentStatus.class));
		verify(getCommunicationProxy(), times(1)).sendMessage(errorMessageCaptor.capture());
		
		assertNotNull(errorMessageCaptor.getValue());
		assertEquals(clientId, errorMessageCaptor.getValue().getClientId());
		assertTrue(errorMessageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)errorMessageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.REGISTER_NULL_TYPE, ((Error)errorMessageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	
	@Test
	public void taskManagerValidTest() {
		BigInteger clientId = new BigInteger("1");
		Register register = ValidMessageFactory.createRegister();
		register.getType().setValue(ComponentType.TASK_MANAGER);
		
		ClientMessage clientMessage = new ClientMessage(clientId, register);
		registerMessageHandler.handle(clientMessage);
		
		ArgumentCaptor<ComponentStatus> componentCaptor = ArgumentCaptor.forClass(ComponentStatus.class);
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getComponentStatusModule(), times(1)).registerComponent(componentCaptor.capture());
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getTaskManagerModule(), times(1)).notifyQueue();
		
		assertNotNull(componentCaptor.getValue());
		assertEquals(clientId, componentCaptor.getValue().getClientId());
		assertEquals(ClientComponentType.TASK_MANAGER, componentCaptor.getValue().getClientComponentType());
		assertEquals(register.getParallelThreads(), componentCaptor.getValue().getThreadCount());
		assertEquals(register.getSolvableProblems().getProblemName(), componentCaptor.getValue().getProblemTypes());
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof RegisterResponse);
		assertEquals(getServerConfig().getTimeout(), ((RegisterResponse)messageCaptor.getValue().getMessage()).getTimeout());
		
	}
	
	@Test
	public void computationalNodeValidTest() {
		BigInteger clientId = new BigInteger("1");
		Register register = ValidMessageFactory.createRegister();
		register.getType().setValue(ComponentType.COMPUTATIONAL_NODE);
		
		ClientMessage clientMessage = new ClientMessage(clientId, register);
		registerMessageHandler.handle(clientMessage);
		
		ArgumentCaptor<ComponentStatus> componentCaptor = ArgumentCaptor.forClass(ComponentStatus.class);
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(getComponentStatusModule(), times(1)).registerComponent(componentCaptor.capture());
		verify(getCommunicationProxy(), times(1)).sendMessage(messageCaptor.capture());
		verify(getComputationalNodeModule(), times(1)).notifyQueue();
		
		assertNotNull(componentCaptor.getValue());
		assertEquals(clientId, componentCaptor.getValue().getClientId());
		assertEquals(ClientComponentType.COMPUTATIONAL_NODE, componentCaptor.getValue().getClientComponentType());
		assertEquals(register.getParallelThreads(), componentCaptor.getValue().getThreadCount());
		assertEquals(register.getSolvableProblems().getProblemName(), componentCaptor.getValue().getProblemTypes());
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof RegisterResponse);
		assertEquals(getServerConfig().getTimeout(), ((RegisterResponse)messageCaptor.getValue().getMessage()).getTimeout());
		
	}
	
}
