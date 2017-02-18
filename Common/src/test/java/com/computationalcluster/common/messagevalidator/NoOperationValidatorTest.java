package com.computationalcluster.common.messagevalidator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.NoOperation;

public class NoOperationValidatorTest {
	
	private NoOperationValidator validator = null;
	
	@Before
	public void setUp() {
		validator = new NoOperationValidator();
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void invalidServerAddressTest() {
		NoOperation noOperation = ValidMessageFactory.createNoOperation();
		noOperation.getBackupCommunicationServers().getBackupCommunicationServer().get(0).setAddress(null);
		
		Error error = validator.getError(noOperation);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.NO_OPERATION_INVALID_SERVER_ADDRESS, error.getErrorMessage());
	}
	
	@Test
	public void invalidServerPortTest() {
		NoOperation noOperation = ValidMessageFactory.createNoOperation();
		noOperation.getBackupCommunicationServers().getBackupCommunicationServer().get(0).setPort(0);
		
		Error error = validator.getError(noOperation);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.NO_OPERATION_INVALID_SERVER_PORT, error.getErrorMessage());
	}
	
	@Test
	public void validTest() {
		NoOperation noOperation = ValidMessageFactory.createNoOperation();
		
		Error error = validator.getError(noOperation);
		
		assertNull(error);
	}
	
}
