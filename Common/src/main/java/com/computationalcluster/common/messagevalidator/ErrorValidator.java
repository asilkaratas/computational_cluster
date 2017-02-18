package com.computationalcluster.common.messagevalidator;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;

public class ErrorValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final Error errorMessage = (Error)message;
		
		if(errorMessage.getErrorType() == null || 
				errorMessage.getErrorType().isEmpty()) {
			error.setErrorMessage(ErrorMessage.ERROR_INVALID_ERROR_TYPE);
			return error;
		}
		
		
		return null;
	}

}
