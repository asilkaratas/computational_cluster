package com.computationalcluster.common.connection.transceiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.serializer.Serializer;
import com.computationalcluster.common.serializer.SerializerFactory;

public class RecieverTest {
	//private static final Logger logger = Logger.getLogger(RecieverTest.class);
	
	private Reciever reciever = null;
	private ByteArrayInputStream inputStream = null;
	private Serializer serializer = null;
	private RecieverCallback callback = null;
	private Error error = null;
	
	
	
	@Before
	public void setUp() throws IOException {
		serializer = SerializerFactory.getDefaultXMLSerializer();
		
		error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage(ErrorMessage.INVALID_ID);
		
		ByteArrayOutputStream stream = (ByteArrayOutputStream)serializer.writeObject(error);
		stream.write(Transceiver.EOF);
		inputStream = new ByteArrayInputStream(stream.toByteArray());
		
		callback = mock(RecieverCallback.class);
		
		reciever = new Reciever(inputStream, serializer, callback);
	}
	
	@Test
	public void recieveTest() throws InterruptedException {
		reciever.start();
		
		Thread.sleep(1000);
		
		ArgumentCaptor<Object> messageCaptor = ArgumentCaptor.forClass(Object.class);
		
		verify(callback, times(1)).onMessageRecieved(messageCaptor.capture());
		
		Error recievedError = (Error)messageCaptor.getValue();
		assertNotNull(recievedError);
		assertEquals(error.getErrorType(), recievedError.getErrorType());
		assertEquals(error.getErrorMessage(), recievedError.getErrorMessage());
	}
	

	@Test
	public void terminateTest() throws InterruptedException {
		reciever.start();
		
		reciever.terminate();
		reciever.join();
		
		assertTrue(!reciever.isAlive());
	}
	
}
