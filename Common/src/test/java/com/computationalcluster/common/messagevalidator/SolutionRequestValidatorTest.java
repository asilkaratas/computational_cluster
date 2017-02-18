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
import com.computationalcluster.common.messages.SolutionRequest;

public class SolutionRequestValidatorTest {
	
	private MessageValidator validator = null;
	
	@Before
	public void setUp() {
		validator = new SolutionRequestValidator();
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void nullIdTest() {
		SolutionRequest solutionRequest = ValidMessageFactory.createSolutionRequest();
		solutionRequest.setId(null);
		
		Error error = validator.getError(solutionRequest);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTION_REQUEST_NULL_ID, error.getErrorMessage());
	}
	
	@Test
	public void validTest() {
		SolutionRequest solutionRequest = ValidMessageFactory.createSolutionRequest();
		
		Error error = validator.getError(solutionRequest);
		
		assertNull(error);
	}
	
}
