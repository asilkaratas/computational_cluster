package com.computationalcluster.computationalclient;

import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;

public class ProblemInfo {
	private byte[] problemData = null;
	private Solution solution = null;
	
	public byte[] getProblemData() {
		return problemData;
	}
	public void setProblemData(byte[] problemData) {
		this.problemData = problemData;
	}
	public Solution getSolution() {
		return solution;
	}
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	
	
}
