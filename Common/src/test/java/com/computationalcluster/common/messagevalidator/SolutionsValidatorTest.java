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
import com.computationalcluster.common.messages.Solutions;

public class SolutionsValidatorTest {
	
	private SolutionsValidator validator = null;
	
	@Before
	public void setUp() {
		validator = new SolutionsValidator();
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void nullIdTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.setId(null);
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_NULL_ID, error.getErrorMessage());
	}
	
	@Test
	public void nullCommonDataTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.setCommonData(null);
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_NULL_COMMON_DATA, error.getErrorMessage());
	}
	
	@Test
	public void invalidProblemTypeTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.setProblemType(null);
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_INVALID_PROBLEM_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void invalidProblemType2Test() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.setProblemType("");
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_INVALID_PROBLEM_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void nullSolutionsListTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.setSolutionsList(null);
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_NULL_SOLUTIONS_LIST, error.getErrorMessage());
	}
	
	@Test
	public void emptySolutionsListTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.getSolutionsList().getSolution().clear();
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_EMPTY_SOLUTIONS_LIST, error.getErrorMessage());
	}
	
	@Test
	public void nullSolutionDataTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.getSolutionsList().getSolution().get(0).setData(null);
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_NULL_SOLUTION_DATA, error.getErrorMessage());
	}
	
	@Test
	public void invalidComputationsTimeTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.getSolutionsList().getSolution().get(0).setComputationsTime(null);
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_INVALID_COMPUTATIONS_TIME, error.getErrorMessage());
	}
	
	@Test
	public void nullTaskIdTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.getSolutionsList().getSolution().get(0).setTaskId(null);
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_NULL_TASK_ID, error.getErrorMessage());
	}
	
	@Test
	public void invalidSolutionTypeTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.getSolutionsList().getSolution().get(0).setType(null);
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_INVALID_SOLUTION_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void invalidSolutionType2Test() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		solutions.getSolutionsList().getSolution().get(0).setType("");
		
		Error error = validator.getError(solutions);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLUTIONS_INVALID_SOLUTION_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void validTest() {
		Solutions solutions = ValidMessageFactory.createSolutions();
		
		Error error = validator.getError(solutions);
		
		assertNull(error);
	}
	
}
