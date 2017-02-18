package com.computationalcluster.computationalclient.console.state;

import com.computationalcluster.computationalclient.console.ComputationalClientConsole;

public class SendSolveRequestState implements ConsoleState {

	private final ComputationalClientConsole console;
	
	public SendSolveRequestState(ComputationalClientConsole console){
		this.console = console;
	}
	
	@Override
	public void enter() {
		if(console.getComputationalClient().hasProblemData()) {
			System.out.println("Solve request is sent.");
			console.getComputationalClient().sendSolveRequest();
		} else {
			System.out.println("Load problem data first.");
		}
		
		console.changeState(console.getReadCommandState());
	}

}
