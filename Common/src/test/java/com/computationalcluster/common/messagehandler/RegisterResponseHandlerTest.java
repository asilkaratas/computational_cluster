package com.computationalcluster.common.messagehandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.computationalcluster.common.component.SolverClientComponent;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messagevalidator.ValidMessageFactory;
import com.computationalcluster.common.module.BaseTaskSolverModule;
import com.computationalcluster.common.module.StatusSendingModule;

public class RegisterResponseHandlerTest {
	
	private CommunicationProxy proxy = null;
	private RegisterResponseHandler registerResponseHandler = null;
	private SolverClientComponent clientComponent = null;
	private StatusSendingModule statusSendingModule = null;
	private BaseTaskSolverModule taskSolverModule = null;
	
	@Before
	public void setUp() {
		proxy = mock(CommunicationProxy.class);
		statusSendingModule = mock(StatusSendingModule.class);
		taskSolverModule = mock(BaseTaskSolverModule.class);
		
		clientComponent = mock(SolverClientComponent.class);
		when(clientComponent.getProxy()).thenReturn(proxy);
		when(clientComponent.getStatusSendingModule()).thenReturn(statusSendingModule);
		when(clientComponent.getTaskSolverModule()).thenReturn(taskSolverModule);
		
		registerResponseHandler = new RegisterResponseHandler(clientComponent);
		
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void validatorErrorTest() {
		BigInteger clientId = new BigInteger("1");
		RegisterResponse registerResponse = ValidMessageFactory.createRegisterResponse();
		registerResponse.setId(null);
		
		ClientMessage clientMessage = new ClientMessage(clientId, registerResponse);
		registerResponseHandler.handle(clientMessage);
		
		verify(clientComponent, times(0)).setId(isA(BigInteger.class));
		verify(statusSendingModule, times(0)).setTimeout(isA(int.class));
		verify(statusSendingModule, times(0)).start();
		verify(taskSolverModule, times(0)).start();
		
		ArgumentCaptor<ClientMessage> messageCaptor = ArgumentCaptor.forClass(ClientMessage.class);
		
		verify(proxy).sendMessage(messageCaptor.capture());
		
		assertNotNull(messageCaptor.getValue());
		assertEquals(clientId, messageCaptor.getValue().getClientId());
		assertTrue(messageCaptor.getValue().getMessage() instanceof Error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), ((Error)messageCaptor.getValue().getMessage()).getErrorType());
		assertEquals(ErrorMessage.REGISTER_RESPONSE_NULL_ID, ((Error)messageCaptor.getValue().getMessage()).getErrorMessage());
		
	}
	
	@Test
	public void validTest() {
		RegisterResponse registerResponse = ValidMessageFactory.createRegisterResponse();
		
		ClientMessage clientMessage = new ClientMessage(registerResponse);
		registerResponseHandler.handle(clientMessage);
		
		verify(clientComponent, times(1)).setId(registerResponse.getId());
		verify(statusSendingModule, times(1)).setTimeout(registerResponse.getTimeout());
		verify(statusSendingModule, times(1)).start();
		verify(taskSolverModule, times(1)).start();
		
	}
}
