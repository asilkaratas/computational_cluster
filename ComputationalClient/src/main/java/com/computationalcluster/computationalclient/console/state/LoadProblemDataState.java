package com.computationalcluster.computationalclient.console.state;

import com.computationalcluster.computationalclient.console.ComputationalClientConsole;

public class LoadProblemDataState implements ConsoleState {

	private final ComputationalClientConsole console;
	
	public LoadProblemDataState(ComputationalClientConsole console){
		this.console = console;
	}
	
	@Override
	public void enter() {
		System.out.println("\nLoad problem data");
		System.out.print("Enter Filename:");
		
		final String filename = console.readLine();
		
		System.out.println("Filename '" + filename + "' entered.");
		
		console.getComputationalClient().loadProblemData(filename);
		
		if(console.getComputationalClient().hasProblemData()) {
			System.out.println("ProblemData is loaded.");
		} else {
			System.out.println("ProblemData is not loaded.");
		}
		
		console.changeState(console.getReadCommandState());
	}

}
