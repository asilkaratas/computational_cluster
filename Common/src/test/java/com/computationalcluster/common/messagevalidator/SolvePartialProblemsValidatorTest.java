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
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems.PartialProblem;

public class SolvePartialProblemsValidatorTest {
	
	private SolvePartialProblemsValidator validator = null;
	
	@Before
	public void setUp() {
		
		validator = new SolvePartialProblemsValidator();
		
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void noIdTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		solvePartialProblems.setId(null);
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_ID, error.getErrorMessage());
	}
	
	@Test
	public void nullCommonDataTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		solvePartialProblems.setCommonData(null);
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_COMMON_DATA, error.getErrorMessage());
	}
	
	@Test
	public void invalidProblemTypeTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		solvePartialProblems.setProblemType(null);
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_INVALID_PROBLEM_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void invalidProblemType2Test() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		solvePartialProblems.setProblemType("");
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_INVALID_PROBLEM_TYPE, error.getErrorMessage());
	}
	
	@Test
	public void nullPartialProblemsTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		solvePartialProblems.setPartialProblems(null);
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEMS, error.getErrorMessage());
	}
	
	@Test
	public void emptyPartialProblemsTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		solvePartialProblems.setPartialProblems(new PartialProblems());
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_EMPTY_PARTIAL_PROBLEMS, error.getErrorMessage());
	}
	
	@Test
	public void nullPartialProblemDataTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		PartialProblem partialProblem = solvePartialProblems.getPartialProblems().getPartialProblem().get(0);
		partialProblem.setData(null);
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_DATA, error.getErrorMessage());
	}
	
	@Test
	public void nullPartialProblemNodeIdTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		PartialProblem partialProblem = solvePartialProblems.getPartialProblems().getPartialProblem().get(0);
		partialProblem.setNodeID(null);
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_NODE_ID, error.getErrorMessage());
	}
	
	@Test
	public void nullPartialProblemTaskIdTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		PartialProblem partialProblem = solvePartialProblems.getPartialProblems().getPartialProblem().get(0);
		partialProblem.setTaskId(null);
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNotNull(error);
		assertEquals(ErrorType.INVALID_OPERATION.getName(), error.getErrorType());
		assertEquals(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_TASK_ID, error.getErrorMessage());
	}
	
	@Test
	public void validTest() {
		SolvePartialProblems solvePartialProblems = ValidMessageFactory.createSolvePartialProblems();
		
		Error error = validator.getError(solvePartialProblems);
		
		assertNull(error);
	}
	
}
