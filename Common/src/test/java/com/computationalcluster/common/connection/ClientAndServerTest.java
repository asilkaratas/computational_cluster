package com.computationalcluster.common.connection;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.messagevalidator.ValidMessageFactory;

import static org.mockito.Mockito.*;

public class ClientAndServerTest {

	private Server server = null;
	private Client client = null;
	private ServerCallback serverCallback = null;
	private ClientCallback clientCallback = null;
	
	@Before
	public void setUp() {
		serverCallback = mock(ServerCallback.class);
		server = new Server(4444, serverCallback);
		
		clientCallback = mock(ClientCallback.class);
		client = new Client("127.0.0.1", 4444, clientCallback);
		
	}
	
	@Test
	public void registerAndRegisterResponseTest() throws InterruptedException {
		server.start();
		Thread.sleep(1000);
		
		///Sending registerMessage
		Register register = ValidMessageFactory.createRegister();
		client.sendMessage(new ClientMessage(register));
		
		Thread.sleep(1000);
		
		//Processing RegisterMessage
		ClientMessage clientMessage = server.getInputQueue().getMessage();
		Register recievedRegister = (Register)clientMessage.getMessage();
		
		assertNotNull(recievedRegister);
		assertEquals(register.getParallelThreads(), recievedRegister.getParallelThreads());
		assertEquals(new BigInteger("1"), clientMessage.getClientId());
		
		/*
		//Sending RegisterResponse
		RegisterResponse registerResponse = ValidMessageFactory.createRegisterResponse();
		ClientMessage registerResponseMessage = new ClientMessage(clientMessage.getClientId(), registerResponse);
		server.sendMessage(registerResponseMessage);
		server.disconnectClient(clientMessage.getClientId());
		
		Thread.sleep(1000);
		
		RegisterResponse recievedRegisterResponse = (RegisterResponse)client.getInputQueue().getMessage().getMessage();
		assertNotNull(recievedRegisterResponse);
		
		Status status = ValidMessageFactory.createStatus();
		client.sendMessage(new ClientMessage(status));
		
		*/
		//Thread.sleep(200000);
	}
}
