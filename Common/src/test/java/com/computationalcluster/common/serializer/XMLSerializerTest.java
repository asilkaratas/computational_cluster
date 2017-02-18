package com.computationalcluster.common.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;

public class XMLSerializerTest {

	@Test 
	public void readWriteTest(){
		Serializer serializer = SerializerFactory.getDefaultXMLSerializer();
		
		Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage(ErrorMessage.INVALID_ID);
		
		ByteArrayOutputStream outputStream = (ByteArrayOutputStream)serializer.writeObject(error);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		
		Error result = (Error)serializer.readObject(inputStream);
		
		assertNotNull(result);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), result.getErrorType());
		assertEquals(ErrorMessage.INVALID_ID, result.getErrorMessage());
	}
	
}
