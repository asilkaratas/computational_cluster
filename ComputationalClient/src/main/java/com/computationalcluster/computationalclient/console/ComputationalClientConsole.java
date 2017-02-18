package com.computationalcluster.computationalclient.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.computationalcluster.computationalclient.ComputationalClient;
import com.computationalcluster.computationalclient.console.state.ConsoleState;
import com.computationalcluster.computationalclient.console.state.LoadProblemDataState;
import com.computationalcluster.computationalclient.console.state.ReadCommandState;
import com.computationalcluster.computationalclient.console.state.SaveSolutionState;
import com.computationalcluster.computationalclient.console.state.SendSolutionRequestState;
import com.computationalcluster.computationalclient.console.state.SendSolveRequestState;

public class ComputationalClientConsole {
	private static final Logger logger = Logger.getLogger(ComputationalClientConsole.class);
	
	private final ComputationalClient computationalClient;
	private final BufferedReader reader;
	
	private final ConsoleState readCommandState;
	private final ConsoleState loadProblemDataState;
	private final ConsoleState sendSolveRequestState;
	private final ConsoleState sendSolutionRequestState;
	private final ConsoleState saveSolutionState;
	
	private ConsoleState currentState = null;
	
	public ComputationalClientConsole(ComputationalClient computationalClient){
		this.computationalClient = computationalClient;
		reader = new BufferedReader(new InputStreamReader(System.in));
		
		readCommandState = new ReadCommandState(this);
		loadProblemDataState = new LoadProblemDataState(this);
		sendSolveRequestState = new SendSolveRequestState(this);
		sendSolutionRequestState = new SendSolutionRequestState(this);
		saveSolutionState = new SaveSolutionState(this);
	}
	
	public void start() {
		System.out.println("Wellcome to ComputationalClientConsole");
		changeState(readCommandState);
	}
	
	public String readLine(){
		String line = null;
		
		try {
			line = reader.readLine();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return line;
	}
	
	public void changeState(ConsoleState state){
		currentState = state;
		currentState.enter();
	}
	
	public ConsoleState getReadCommandState(){
		return readCommandState;
	}
	
	public ConsoleState getLoadProblemDataState(){
		return loadProblemDataState;
	}
	
	public ConsoleState getSendSolveRequestState(){
		return sendSolveRequestState;
	}
	
	public ConsoleState getSendSolutionRequestState(){
		return sendSolutionRequestState;
	}
	
	public ConsoleState getSaveSolutionState(){
		return saveSolutionState;
	}
	
	public ComputationalClient getComputationalClient() {
		return computationalClient;
	}
	
}
