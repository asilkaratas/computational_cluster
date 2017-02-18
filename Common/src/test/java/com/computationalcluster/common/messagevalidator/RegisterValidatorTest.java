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
import com.computationalcluster.common.messages.Register;

public class RegisterValidatorTest {
	
	private RegisterValidator validator = null;
	
	@Before
	public void setUp() {
		validator = new RegisterValidator();
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void nullTypeTest() {
		Register register = ValidMessageFactory.createRegister();
		register.setType(null);
		
		Error error = validator.getError(register);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.REGISTER_NULL_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void nullComponentTypeTest() {
		Register register = ValidMessageFactory.createRegister();
		register.getType().setValue(null);
		
		Error error = validator.getError(register);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.REGISTER_NULL_COMPONENT_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void nullSolvableProblemsTest() {
		Register register = ValidMessageFactory.createRegister();
		register.setSolvableProblems(null);
		
		Error error = validator.getError(register);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.REGISTER_NULL_SOLVABLE_PROBLEMS, error.getErrorMessage());
	}
	
	@Test
	public void emptySolvableProblemsTest() {
		Register register = ValidMessageFactory.createRegister();
		register.getSolvableProblems().getProblemName().clear();
		
		Error error = validator.getError(register);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.REGISTER_EMPTY_SOLVABLE_PROBLEMS, error.getErrorMessage());
	}
	
	
	@Test
	public void invalidThreadCountTest() {
		Register register = ValidMessageFactory.createRegister();
		register.setParallelThreads((short)0);
		
		Error error = validator.getError(register);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.REGISTER_INVALID_THREAD_COUNT, error.getErrorMessage());
	}
	
	@Test
	public void validTest() {
		Register register = ValidMessageFactory.createRegister();
		
		Error error = validator.getError(register);
		
		assertNull(error);
	}
	
}
