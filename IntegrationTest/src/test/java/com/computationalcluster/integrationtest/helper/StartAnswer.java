package com.computationalcluster.integrationtest.helper;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StartAnswer implements Answer<Boolean> {
	
	public StartAnswer() {
	}

	@Override
	public Boolean answer(InvocationOnMock invocation) throws Throwable {
		return true;
	}

}
