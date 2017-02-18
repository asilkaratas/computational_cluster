package com.computationalcluster.communicationserver.module.componentstatus;

import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.communicationserver.module.WaitingMessage;
import com.computationalcluster.communicationserver.module.computationalnode.SolvePartialProblemsMessage;

public class SolvePartialProblemsAssignment extends ComponentAssigment {
	
	private final SolvePartialProblems solvePartialProblems;
	
	public SolvePartialProblemsAssignment(WaitingMessage message, SolvePartialProblems solvePartialProblems) {
		super(message);
		this.solvePartialProblems = solvePartialProblems;
	}
	
	public SolvePartialProblems getSolvePartialProblems() {
		return solvePartialProblems;
	}

	public void revoke(){
		
	}
}
