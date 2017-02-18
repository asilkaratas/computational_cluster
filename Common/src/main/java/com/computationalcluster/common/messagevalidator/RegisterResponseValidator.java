package com.computationalcluster.common.messagevalidator;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.RegisterResponse;

public class RegisterResponseValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final RegisterResponse registerResponse = (RegisterResponse)message;
		
		if(registerResponse.getId() == null) {
			error.setErrorMessage(ErrorMessage.REGISTER_RESPONSE_NULL_ID);
			return error;
		}
		
		if(registerResponse.getTimeout() <= 0) {
			error.setErrorMessage(ErrorMessage.REGISTER_RESPONSE_INVALID_TIMEOUT);
			return error;
		}
		
		return null;
	}

}
