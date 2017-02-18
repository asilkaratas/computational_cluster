package com.computationalcluster.common.messagevalidator;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolveRequestResponse;

public class SolveRequestResponseValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final SolveRequestResponse solveRequestResponse = (SolveRequestResponse)message;
		
		
		if(solveRequestResponse.getId() == null) {
			error.setErrorMessage(ErrorMessage.SOLVE_REQUEST_RESPONSE_NULL_ID);
			return error;
		}
		
		return null;
	}

}
