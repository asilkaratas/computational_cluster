package com.computationalcluster.communicationserver.module.componentstatus;

import java.math.BigInteger;

import com.computationalcluster.common.enums.ThreadState;

public class ThreadStatus {
	
	private ThreadState threadState = null;
	private BigInteger taskId = null;
	private BigInteger howLong = null;
	private BigInteger problemInstanceId = null;
	private String problemType = null;
	
	public ThreadStatus() {
		threadState = ThreadState.IDLE;
		howLong = new BigInteger("0");
	}

	public ThreadState getThreadState() {
		return threadState;
	}

	public void setThreadState(ThreadState threadState) {
		this.threadState = threadState;
	}

	public BigInteger getTaskId() {
		return taskId;
	}

	public void setTaskId(BigInteger taskId) {
		this.taskId = taskId;
	}

	public BigInteger getHowLong() {
		return howLong;
	}

	public void setHowLong(BigInteger howLong) {
		this.howLong = howLong;
	}

	public BigInteger getProblemInstanceId() {
		return problemInstanceId;
	}

	public void setProblemInstanceId(BigInteger problemInstanceId) {
		this.problemInstanceId = problemInstanceId;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}
}
