package com.computationalcluster.communicationserver.module.taskmanager;

import java.math.BigInteger;

import com.computationalcluster.communicationserver.module.WaitingMessage;

public class MergeSolutionsMessage implements WaitingMessage {
	
	private final BigInteger problemId;
	private final String problemType;
	
	public MergeSolutionsMessage(BigInteger problemId, String problemType) {
		this.problemId = problemId;
		this.problemType = problemType;
	}

	public BigInteger getProblemId() {
		return problemId;
	}

	@Override
	public Object getMessage() {
		return this;
	}

	@Override
	public String getProblemType() {
		return problemType;
	}

	@Override
	public boolean handled() {
		return true;
	}
}
