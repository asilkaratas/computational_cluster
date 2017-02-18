package com.computationalcluster.common.messagevalidator;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolutionRequest;

public class SolutionRequestValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final SolutionRequest solutionRequest = (SolutionRequest)message;
		
		
		if(solutionRequest.getId() == null) {
			error.setErrorMessage(ErrorMessage.SOLUTION_REQUEST_NULL_ID);
			return error;
		}
		
		return null;
	}

}
