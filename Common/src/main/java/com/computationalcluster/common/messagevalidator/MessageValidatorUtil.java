package com.computationalcluster.common.messagevalidator;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.computationalcluster.common.messages.DivideProblem;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messages.SolutionRequest;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.SolveRequestResponse;
import com.computationalcluster.common.messages.Status;

public class MessageValidatorUtil {
	private static final Logger logger = Logger.getLogger(MessageValidatorUtil.class);
	
	private static MessageValidatorUtil instance = null;
	public static Error getError(Object message) {
		if(instance == null) {
			instance = new MessageValidatorUtil();
		}
		
		return instance.getErrorMessage(message);
	}
	
	private final HashMap<Class<?>, MessageValidator> validatorMap;
	
	private MessageValidatorUtil() {
		validatorMap = new HashMap<>();
		validatorMap.put(Register.class, new RegisterValidator());
		validatorMap.put(Status.class, new StatusValidator());
		validatorMap.put(SolveRequest.class, new SolveRequestValidator());
		validatorMap.put(SolvePartialProblems.class, new SolvePartialProblemsValidator());
		validatorMap.put(Solutions.class, new SolutionsValidator());
		validatorMap.put(SolutionRequest.class, new SolutionRequestValidator());
		validatorMap.put(RegisterResponse.class, new RegisterResponseValidator());
		validatorMap.put(NoOperation.class, new NoOperationValidator());
		validatorMap.put(Error.class, new ErrorValidator());
		validatorMap.put(DivideProblem.class, new DivideProblemValidator());
		validatorMap.put(SolveRequestResponse.class, new SolveRequestResponseValidator());
		
	}
	
	private Error getErrorMessage(Object message) {
		if(validatorMap.containsKey(message.getClass())) {
			final MessageValidator messageValidator = validatorMap.get(message.getClass());
			return messageValidator.getError(message);
		} else {
			logger.error("Validator doesn't found. message:" + message);
		}
		
		return null;
	}
}
