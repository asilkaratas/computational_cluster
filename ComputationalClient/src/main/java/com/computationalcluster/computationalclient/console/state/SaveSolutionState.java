package com.computationalcluster.computationalclient.console.state;

import java.math.BigInteger;

import com.computationalcluster.computationalclient.ProblemInfo;
import com.computationalcluster.computationalclient.console.ComputationalClientConsole;

public class SaveSolutionState implements ConsoleState {

	private final ComputationalClientConsole console;
	
	public SaveSolutionState(ComputationalClientConsole console){
		this.console = console;
	}
	
	@Override
	public void enter() {
		System.out.println("\nSave solution");
		System.out.print("Enter Problem Id:");
		
		final String enteredProblemId = console.readLine();
		
		System.out.println("ProblemId '" + enteredProblemId + "' entered.");
		
		final BigInteger problemId = new BigInteger(enteredProblemId);
		final ProblemInfo problemInfo = console.getComputationalClient().getProblem(problemId);
		if(problemInfo != null) {
			System.out.print("Enter Filename:");
			final String filename = console.readLine();
			console.getComputationalClient().saveSolution(problemId, filename);
			System.out.println("Solution is saved.");
		} else {
			System.out.println("Problem Id is not found.");
		}
		
		console.changeState(console.getReadCommandState());
	}

}
