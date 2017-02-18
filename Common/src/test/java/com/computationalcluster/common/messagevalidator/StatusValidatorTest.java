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
import com.computationalcluster.common.messages.Status;

public class StatusValidatorTest {
	
	private StatusValidator validator = null;
	
	@Before
	public void setUp() {
		validator = new StatusValidator();
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void nullIdTest() {
		Status status = ValidMessageFactory.createStatus();
		status.setId(null);
		
		Error error = validator.getError(status);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.STATUS_NULL_ID, error.getErrorMessage());
	}
	
	@Test
	public void nullThreadsTest() {
		Status status = ValidMessageFactory.createStatus();
		status.setThreads(null);
		
		Error error = validator.getError(status);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.STATUS_NULL_THREADS, error.getErrorMessage());
	}
	
	@Test
	public void emptyThreadsTest() {
		Status status = ValidMessageFactory.createStatus();
		status.getThreads().getThread().clear();
		
		Error error = validator.getError(status);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.STATUS_EMPTY_THREADS, error.getErrorMessage());
	}
	
	@Test
	public void invalidTheadStateTest() {
		Status status = ValidMessageFactory.createStatus();
		status.getThreads().getThread().get(0).setState("");
		
		Error error = validator.getError(status);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.STATUS_INVALID_THREAD_STATE, error.getErrorMessage());
	}
	
	@Test
	public void nullHowLongTest() {
		Status status = ValidMessageFactory.createStatus();
		status.getThreads().getThread().get(0).setHowLong(null);
		
		Error error = validator.getError(status);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.STATUS_NULL_HOW_LONG, error.getErrorMessage());
	}
	
	@Test
	public void validTest() {
		Status status = ValidMessageFactory.createStatus();
		
		Error error = validator.getError(status);
		
		assertNull(error);
	}
	
}
