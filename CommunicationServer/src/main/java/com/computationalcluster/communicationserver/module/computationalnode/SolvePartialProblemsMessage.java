package com.computationalcluster.communicationserver.module.computationalnode;

import java.math.BigInteger;

import com.computationalcluster.communicationserver.module.WaitingMessage;

public class SolvePartialProblemsMessage implements WaitingMessage {
	
	private final BigInteger problemId;
	private final String problemType;
	private boolean handled = true;
	
	public SolvePartialProblemsMessage(BigInteger problemId, String problemType) {
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
	
	public void setHandled(boolean handled){
		this.handled = handled;
	}

	@Override
	public boolean handled() {
		return handled;
	}
}
