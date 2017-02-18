package com.computationalcluster.common.messagevalidator;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.Register.SolvableProblems;
import com.computationalcluster.common.messages.Register.Type;

public class RegisterValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final Register register = (Register)message;
		final Type type = register.getType();
		
		if(type == null) {
			error.setErrorMessage(ErrorMessage.REGISTER_NULL_TYPE);
			return error;
		}
		
		if(type.getValue() == null || 
				ClientComponentType.fromName(type.getValue().value()) == null) {
			error.setErrorMessage(ErrorMessage.REGISTER_NULL_COMPONENT_TYPE);
			return error;
		}
		
		final ClientComponentType clientComponentType = ClientComponentType.fromName(type.getValue().value());
		if(clientComponentType != ClientComponentType.COMMUNICATION_SERVER){
			final SolvableProblems solvableProblems = register.getSolvableProblems();
			if(solvableProblems == null) {
				error.setErrorMessage(ErrorMessage.REGISTER_NULL_SOLVABLE_PROBLEMS);
				return error;
			}
			
			if(solvableProblems.getProblemName().isEmpty()) {
				error.setErrorMessage(ErrorMessage.REGISTER_EMPTY_SOLVABLE_PROBLEMS);
				return error;
			}
			
			
			int threadCount = register.getParallelThreads();
			if(threadCount <= 0) {
				error.setErrorMessage(ErrorMessage.REGISTER_INVALID_THREAD_COUNT);
				return error;
			}
		}
		
		
		return null;
	}

}
