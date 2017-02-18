package com.computationalcluster.computationalclient.console.state;

import java.math.BigInteger;

import com.computationalcluster.computationalclient.ProblemInfo;
import com.computationalcluster.computationalclient.console.ComputationalClientConsole;

public class SendSolutionRequestState implements ConsoleState {

	private final ComputationalClientConsole console;
	
	public SendSolutionRequestState(ComputationalClientConsole console){
		this.console = console;
	}
	
	@Override
	public void enter() {
		System.out.println("\nSend solution request");
		System.out.print("Enter Problem Id:");
		
		final String enteredProblemId = console.readLine();
		
		System.out.println("ProblemId '" + enteredProblemId + "' entered.");
		
		final BigInteger problemId = new BigInteger(enteredProblemId);
		final ProblemInfo problemInfo = console.getComputationalClient().getProblem(problemId);
		if(problemInfo != null) {
			System.out.println("Solution request is sent.");
			console.getComputationalClient().sendSolutionRequest(problemId);
		} else {
			System.out.println("Problem Id is not found.");
		}
		
		console.changeState(console.getReadCommandState());
	}

}
