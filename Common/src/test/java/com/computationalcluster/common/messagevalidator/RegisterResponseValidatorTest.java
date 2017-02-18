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
import com.computationalcluster.common.messages.RegisterResponse;

public class RegisterResponseValidatorTest {
	
	private RegisterResponseValidator validator = null;
	
	@Before
	public void setUp() {
		validator = new RegisterResponseValidator();
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void nullIdTest() {
		RegisterResponse registerResponse = ValidMessageFactory.createRegisterResponse();
		registerResponse.setId(null);
		
		Error error = validator.getError(registerResponse);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.REGISTER_RESPONSE_NULL_ID, error.getErrorMessage());
	}
	
	@Test
	public void invalidTimeoutTest() {
		RegisterResponse registerResponse = ValidMessageFactory.createRegisterResponse();
		registerResponse.setTimeout(0);
		
		Error error = validator.getError(registerResponse);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.REGISTER_RESPONSE_INVALID_TIMEOUT, error.getErrorMessage());
	}
	
	@Test
	public void validTest() {
		RegisterResponse registerResponse = ValidMessageFactory.createRegisterResponse();
		
		Error error = validator.getError(registerResponse);
		
		assertNull(error);
	}
	
}
