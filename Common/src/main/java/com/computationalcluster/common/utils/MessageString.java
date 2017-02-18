package com.computationalcluster.common.utils;

import java.util.List;

import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.messages.Status.Threads;

public class MessageString {
	public static String toString(Status status) {
		final StringBuilder builder = new StringBuilder();
		
		builder.append(String.format("status: id:%d \n", status.getId()));
		final List<Threads.Thread> threads = status.getThreads().getThread();
		
		for(int i = 0; i < threads.size(); i++) {
			final Threads.Thread thread = threads.get(i);
			builder.append(String.format("problemInstance:%d taskId:%d problemType:%s state:%s howLong:%d \n", 
					thread.getProblemInstanceId(),
					thread.getTaskId(),
					thread.getProblemType(),
					thread.getState(),
					thread.getHowLong()));
		}
		
		return builder.toString();
	}
}
