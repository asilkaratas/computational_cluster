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
import com.computationalcluster.common.messages.SolveRequest;

public class SolveRequestValidatorTest {
	
	private SolveRequestValidator validator = null;
	
	@Before
	public void setUp() {
		validator = new SolveRequestValidator();
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void nullProblemDataTest() {
		SolveRequest solveRequest = ValidMessageFactory.createSolveRequest();
		solveRequest.setData(null);
		
		Error error = validator.getError(solveRequest);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_REQUEST_NULL_PROBLEM_DATA, error.getErrorMessage());
	}
	
	@Test
	public void invalidProblemTypeTest() {
		SolveRequest solveRequest = ValidMessageFactory.createSolveRequest();
		solveRequest.setProblemType(null);
		
		Error error = validator.getError(solveRequest);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_REQUEST_INVALID_PROBLEM_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void invalidProblemType2Test() {
		SolveRequest solveRequest = ValidMessageFactory.createSolveRequest();
		solveRequest.setProblemType("");
		
		Error error = validator.getError(solveRequest);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_REQUEST_INVALID_PROBLEM_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void invalidTimeoutTest() {
		SolveRequest solveRequest = ValidMessageFactory.createSolveRequest();
		solveRequest.setSolvingTimeout(null);
		
		Error error = validator.getError(solveRequest);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_REQUEST_INVALID_TIMEOUT, error.getErrorMessage());
	}
	
	
	@Test
	public void validTest() {
		SolveRequest solveRequest = ValidMessageFactory.createSolveRequest();
		
		Error error = validator.getError(solveRequest);
		
		assertNull(error);
	}
	
}
