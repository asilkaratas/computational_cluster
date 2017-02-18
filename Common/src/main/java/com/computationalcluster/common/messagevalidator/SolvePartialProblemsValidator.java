package com.computationalcluster.common.messagevalidator;

import java.util.List;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems.PartialProblem;

public class SolvePartialProblemsValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final SolvePartialProblems solvePartialProblems = (SolvePartialProblems)message;
		
		if(solvePartialProblems.getId() == null) {
			error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_ID);
			return error;
		}
		
		if(solvePartialProblems.getCommonData() == null) {
			error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_COMMON_DATA);
			return error;
		}
		
		if(solvePartialProblems.getProblemType() == null || 
				solvePartialProblems.getProblemType().isEmpty()) {
			error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_INVALID_PROBLEM_TYPE);
			return error;
		}
		
		if(solvePartialProblems.getSolvingTimeout() == null || 
				solvePartialProblems.getSolvingTimeout().longValue() <= 0) {
			error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_INVALID_TIMEOUT);
			return error;
		}
		
		final PartialProblems partialProblems = solvePartialProblems.getPartialProblems();
		if(partialProblems == null) {
			error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEMS);
			return error;
		}
		
		if(partialProblems.getPartialProblem().isEmpty()) {
			error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_EMPTY_PARTIAL_PROBLEMS);
			return error;
		}
		
		final List<PartialProblem> partialProblemList = partialProblems.getPartialProblem();
		for(int i = 0; i < partialProblemList.size(); i++) {
			final PartialProblem partialProblem = partialProblemList.get(i);
			if(partialProblem.getData() == null) {
				error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_DATA);
				return error;
			}
			
			if(partialProblem.getNodeID() == null) {
				error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_NODE_ID);
				return error;
			}
			
			if(partialProblem.getTaskId() == null) {
				error.setErrorMessage(ErrorMessage.SOLVE_PARTIAL_PROBLEM_NULL_PARTIAL_PROBLEM_TASK_ID);
				return error;
			}
		}
		
		return null;
	}

}
