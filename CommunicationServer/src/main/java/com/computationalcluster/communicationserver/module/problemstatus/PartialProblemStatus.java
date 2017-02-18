package com.computationalcluster.communicationserver.module.problemstatus;

import java.math.BigInteger;

import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems.PartialProblem;


public class PartialProblemStatus {
	
	private final BigInteger taskId;
	private PartialProblemState state = null;
	private PartialProblem partialProblem = null;
	private Solution partialSolution = null;
	
	public PartialProblemStatus(BigInteger taskId) {
		this.taskId = taskId;
		state = PartialProblemState.UNSOLVED;
	}
	
	public BigInteger getTaskId() {
		return taskId;
	}
	
	public void setState(PartialProblemState state) {
		this.state = state;
	}

	public PartialProblemState getState() {
		return state;
	}

	public PartialProblem getPartialProblem() {
		return partialProblem;
	}

	public void setPartialProblem(PartialProblem partialProblem) {
		this.partialProblem = partialProblem;
	}

	public Solution getPartialSolution() {
		return partialSolution;
	}

	public void setPartialSolution(Solution partialSolution) {
		this.partialSolution = partialSolution;
	}

	
}
