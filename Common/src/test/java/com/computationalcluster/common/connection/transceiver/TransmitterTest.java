package com.computationalcluster.common.connection.transceiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.queue.ClientMessageQueue;
import com.computationalcluster.common.serializer.Serializer;
import com.computationalcluster.common.serializer.SerializerFactory;

public class TransmitterTest {
	//private static final Logger logger = Logger.getLogger(TransmitterTest.class);
	
	private Transmitter transmitter = null;
	private ByteArrayOutputStream outputStream = null;
	private ClientMessageQueue outputQueue = null;
	private Serializer serializer = null;
	private TransmitterCallback callback = null;
	
	@Before
	public void setUp() throws IOException {
		outputQueue = new ClientMessageQueue();
		serializer = SerializerFactory.getDefaultXMLSerializer();
		outputStream = new ByteArrayOutputStream();
		
		callback = mock(TransmitterCallback.class);
		transmitter = new Transmitter(outputStream, outputQueue, serializer, callback);
	}
	
	@Test
	public void transmitTest() throws InterruptedException {
		transmitter.start();
		
		Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage(ErrorMessage.INVALID_ID);
		
		outputQueue.addMessage(new ClientMessage(error));
		
		//Sleeping is necessary because transmitter has own thread. 
		//I have to wait for some time to make sure writing is finished
		Thread.sleep(1000);
		
		//EOF should replaced with \r or removed to be able to deserialize the object.
		String transmittedXML = outputStream.toString();
		transmittedXML = transmittedXML.replace(Transceiver.EOF, '\r');
		
		ByteArrayInputStream stream = new ByteArrayInputStream(transmittedXML.getBytes());
		Error transmittedError = (Error)serializer.readObject(stream);
		
		assertNotNull(transmittedError);
		assertEquals(error.getErrorType(), transmittedError.getErrorType());
		assertEquals(error.getErrorMessage(), transmittedError.getErrorMessage());
	}
	

	@Test
	public void terminateTest() throws InterruptedException {
		transmitter.start();
		
		transmitter.terminate();
		transmitter.join();
		
		assertTrue(!transmitter.isAlive());
	}
	
}
