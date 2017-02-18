package com.computationalcluster.common.messagevalidator;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.DivideProblem;
import com.computationalcluster.common.messages.Error;

public class DivideProblemValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final DivideProblem divideProblem = (DivideProblem)message;
		if(divideProblem.getId() == null) {
			error.setErrorMessage(ErrorMessage.DIVIDE_PROBLEM_NULL_ID);
			return error;
		}
		
		if(divideProblem.getData() == null) {
			error.setErrorMessage(ErrorMessage.DIVIDE_PROBLEM_NULL_DATA);
			return error;
		}
		
		if(divideProblem.getProblemType() == null || 
				divideProblem.getProblemType().isEmpty()) {
			error.setErrorMessage(ErrorMessage.DIVIDE_PROBLEM_INVALID_PROBLEM_TYPE);
			return error;
		}
		
		if(divideProblem.getComputationalNodes() == null ||
				divideProblem.getComputationalNodes().intValue() == 0) {
			error.setErrorMessage(ErrorMessage.DIVIDE_PROBLEM_INVALID_NODE_COUNT);
			return error;
		}
		
		if(divideProblem.getNodeID() == null) {
			error.setErrorMessage(ErrorMessage.DIVIDE_PROBLEM_NULL_NODE_ID);
			return error;
		}
		
		return null;
	}

}
