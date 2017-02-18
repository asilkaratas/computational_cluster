package com.computationalcluster.common.connection.transceiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.queue.ClientMessageQueue;
import com.computationalcluster.common.serializer.Serializer;
import com.computationalcluster.common.serializer.SerializerFactory;

public class TransceiverTest {
	
	private ClientMessageQueue inputQueue = null;
	private ClientMessageQueue outputQueue = null;
	private TransceiverCallback callback = null;
	
	private Transceiver transceiver = null;
	private Socket socket = null;
	
	private ByteArrayInputStream inputStream = null;
	private ByteArrayOutputStream outputStream = null;
	
	private Error error = null;
	private boolean socketClosed = false;
	
	
	@Before
	public void setUp() throws IOException {
		
		error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage(ErrorMessage.INVALID_ID);
		
		Serializer serializer = SerializerFactory.getDefaultXMLSerializer();
		
		ByteArrayOutputStream os = (ByteArrayOutputStream)serializer.writeObject(error);
		os.write(Transceiver.EOF);
		inputStream = new ByteArrayInputStream(os.toByteArray());
		outputStream = new ByteArrayOutputStream();
		
		Answer<Void> closeAnswer = new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) throws Throwable {
				socketClosed = true;
				return null;
			}
		};
				
		socket = mock(Socket.class);
		when(socket.getOutputStream()).thenReturn(outputStream);
		when(socket.getInputStream()).thenReturn(inputStream);
		doAnswer(closeAnswer).when(socket).close();
		
		inputQueue = new ClientMessageQueue();
		outputQueue = new ClientMessageQueue();
		callback = mock(TransceiverCallback.class);
		
		transceiver = new Transceiver(socket, new BigInteger("1"), inputQueue, outputQueue, callback);
	}
	
	@Test
	public void inputTest() throws InterruptedException, IOException {
		transceiver.start();
		
		Thread.sleep(1000);
		
		ClientMessage clientMessage = inputQueue.getMessage();
		assertNotNull(clientMessage);
		
		Error recievedError = (Error)clientMessage.getMessage();
		assertNotNull(recievedError);
		assertEquals(error.getErrorType(), recievedError.getErrorType());
		assertEquals(error.getErrorMessage(), recievedError.getErrorMessage());
	}
	
	@Test
	public void outputTest() throws InterruptedException, IOException {
		transceiver.start();
		outputQueue.addMessage(new ClientMessage(error));
		
		Thread.sleep(1000);
		
		String transmittedXML = outputStream.toString();
		transmittedXML = transmittedXML.replace(Transceiver.EOF, '\r');
		
		ByteArrayInputStream stream = new ByteArrayInputStream(transmittedXML.getBytes());
		Error transmittedError = (Error)transceiver.getSerializer().readObject(stream);
		
		
		assertNotNull(transmittedError);
		assertEquals(error.getErrorType(), transmittedError.getErrorType());
		assertEquals(error.getErrorMessage(), transmittedError.getErrorMessage());
	}
	
	@Test
	public void closeTest() {
		
		transceiver.close();
		
		assertTrue(socketClosed);
		
	}
	
}
