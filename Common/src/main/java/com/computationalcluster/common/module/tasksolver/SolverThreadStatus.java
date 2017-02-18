package com.computationalcluster.common.module.tasksolver;

import java.math.BigInteger;

import com.computationalcluster.common.enums.ProblemType;
import com.computationalcluster.common.enums.TaskType;
import com.computationalcluster.common.enums.ThreadState;

public class SolverThreadStatus {
	
	private BigInteger problemId = null;
	private long timestamp = 0;
	private ThreadState threadState = ThreadState.IDLE;
	private String problemType = null;
	private TaskType taskType = null;
	
	public SolverThreadStatus() {
		timestamp = System.currentTimeMillis();
		threadState = ThreadState.IDLE;
	}
	
	public void setThreadState(ThreadState threadState) {
		timestamp = System.currentTimeMillis();
		this.threadState = threadState;
	}
	
	public ThreadState getThreadState() {
		return threadState;
	}
	
	public void setProblemId(BigInteger problemId) {
		this.problemId = problemId;
	}
	
	public BigInteger getProblemId() {
		return problemId;
	}
	
	public long getTimestamp() {
		return timestamp;
	}
	
	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}
	
	public String getProblemType()  {
		return problemType;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
}
