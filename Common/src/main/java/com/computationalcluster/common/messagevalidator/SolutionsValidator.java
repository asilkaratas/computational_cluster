package com.computationalcluster.common.messagevalidator;

import java.util.List;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList;
import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;

public class SolutionsValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final Solutions solutions = (Solutions)message;
		
		
		if(solutions.getId() == null) {
			error.setErrorMessage(ErrorMessage.SOLUTIONS_NULL_ID);
			return error;
		}
		
		if(solutions.getCommonData() == null) {
			error.setErrorMessage(ErrorMessage.SOLUTIONS_NULL_COMMON_DATA);
			return error;
		}
		
		if(solutions.getProblemType() == null || 
				solutions.getProblemType().isEmpty()) {
			error.setErrorMessage(ErrorMessage.SOLUTIONS_INVALID_PROBLEM_TYPE);
			return error;
		}
		
		final SolutionsList solutionsList = solutions.getSolutionsList();
		if(solutionsList == null) {
			error.setErrorMessage(ErrorMessage.SOLUTIONS_NULL_SOLUTIONS_LIST);
			return error;
		}
		
		if(solutionsList.getSolution().isEmpty()) {
			error.setErrorMessage(ErrorMessage.SOLUTIONS_EMPTY_SOLUTIONS_LIST);
			return error;
		}
		
		final List<Solution> solutionList = solutionsList.getSolution();
		for(int i = 0; i < solutionList.size(); i++) {
			final Solution solution = solutionList.get(i);
			if(solution == null) {
				error.setErrorMessage(ErrorMessage.SOLUTIONS_NULL_SOLUTION);
				return error;
			}
			
			if(solution.getData() == null) {
				error.setErrorMessage(ErrorMessage.SOLUTIONS_NULL_SOLUTION_DATA);
				return error;
			}
			
			if(solution.getComputationsTime() == null ||
					solution.getComputationsTime().longValue() < 0) {
				error.setErrorMessage(ErrorMessage.SOLUTIONS_INVALID_COMPUTATIONS_TIME);
				return error;
			}
			
			if(solution.getTaskId() == null) {
				error.setErrorMessage(ErrorMessage.SOLUTIONS_NULL_TASK_ID);
				return error;
			}
			
			if(solution.getType() == null || 
					solution.getType().isEmpty()) {
				error.setErrorMessage(ErrorMessage.SOLUTIONS_INVALID_SOLUTION_TYPE);
				return error;
			}
		}
		
		return null;
	}

}
