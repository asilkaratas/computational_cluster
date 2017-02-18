package com.computationalcluster.common.messagevalidator;

import java.util.List;

import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.enums.ThreadState;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.messages.Status.Threads;
import com.computationalcluster.common.messages.Status.Threads.Thread;

public class StatusValidator implements MessageValidator {

	@Override
	public Error getError(Object message) {
		
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		
		final Status status = (Status)message;
		
		
		if(status.getId() == null) {
			error.setErrorMessage(ErrorMessage.STATUS_NULL_ID);
			return error;
		}
		
		final Threads threads = status.getThreads();
		if(threads == null) {
			error.setErrorMessage(ErrorMessage.STATUS_NULL_THREADS);
			return error;
		}
		
		if(threads.getThread().isEmpty()) {
			error.setErrorMessage(ErrorMessage.STATUS_EMPTY_THREADS);
			return error;
		}
		
		final List<Thread> threadList = threads.getThread();
		for(int i = 0; i < threadList.size(); i++) {
			final Thread thread = threadList.get(i);
			final ThreadState threadState = ThreadState.fromName(thread.getState());
			if(threadState == null) {
				error.setErrorMessage(ErrorMessage.STATUS_INVALID_THREAD_STATE);
				return error;
			}
			
			if(thread.getHowLong() == null) {
				error.setErrorMessage(ErrorMessage.STATUS_NULL_HOW_LONG);
				return error;
			}
		}
		
		return null;
	}

}
