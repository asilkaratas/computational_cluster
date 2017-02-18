package com.computationalcluster.computationalclient.console.state;

import com.computationalcluster.computationalclient.console.ComputationalClientConsole;

public class ReadCommandState implements ConsoleState {
	
	private ComputationalClientConsole console = null;
	
	public ReadCommandState(ComputationalClientConsole console){
		this.console = console;
	}
	
	public void enter(){
		System.out.println("\nCommands");
		System.out.println("1. Load problem data");
		System.out.println("2. Send solve request");
		System.out.println("3. Send solution request");
		System.out.println("4. Save solution");
		System.out.print("Enter command:");
		
		String line = console.readLine();
		
		System.out.println("Entered command:" + line + "\n");
		int command = 0;
		try{
			command = Integer.parseInt(line);
		} catch(NumberFormatException e){
			
		}
		
		handleCommand(command);
	}
	
	private void handleCommand(int command){
		switch (command) {
		case 1:
			console.changeState(console.getLoadProblemDataState());
			break;
			
		case 2:
			console.changeState(console.getSendSolveRequestState());
			break;
			
		case 3:
			console.changeState(console.getSendSolutionRequestState());
			break;
			
		case 4:
			console.changeState(console.getSaveSolutionState());
			break;

		default:
			System.out.println(command + " Invalid input");
			enter();
			break;
		}
	}
}
