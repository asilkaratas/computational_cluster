package com.computationalcluster.common.messagevalidator;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.Error;

public class SolveRequestValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final SolveRequest solveRequest = (SolveRequest)message;
		
		if(solveRequest.getData() == null) {
			error.setErrorMessage(ErrorMessage.SOLVE_REQUEST_NULL_PROBLEM_DATA);
			return error;
		}
		
		if(solveRequest.getProblemType() == null || 
				solveRequest.getProblemType().isEmpty()) {
			error.setErrorMessage(ErrorMessage.SOLVE_REQUEST_INVALID_PROBLEM_TYPE);
			return error;
		}
		
		if(solveRequest.getSolvingTimeout() == null || 
				solveRequest.getSolvingTimeout().longValue() <= 0) {
			error.setErrorMessage(ErrorMessage.SOLVE_REQUEST_INVALID_TIMEOUT);
			return error;
		}
		
		return null;
	}

}
