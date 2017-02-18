package com.computationalcluster.taskmanager.messagehandler;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.DivideProblem;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems.PartialProblem;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.taskmanager.TaskManager;
import com.computationalcluster.taskmanager.TaskSolverModule;
import com.computationalcluster.taskmanager.tasksolver.DivideCallback;

public class DivideProblemHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(DivideProblemHandler.class);
	
	private final TaskManager taskManager;
	
	public DivideProblemHandler(TaskManager taskManager){
		this.taskManager = taskManager;
	}

	@Override
	public void handle(ClientMessage clientMessage) {
		
		final TaskSolverModule taskSolverModule = taskManager.getTaskSolverModule();
		final DivideProblem divideProblem = (DivideProblem)clientMessage.getMessage();
		
		System.out.println("Divide Problem message is recieved id:" + divideProblem.getId() + 
					" problemType:" + divideProblem.getProblemType() + 
					" computationalNodes:" + divideProblem.getComputationalNodes());
		
		final Error error = MessageValidatorUtil.getError(divideProblem);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(taskManager.getId(), error);
			taskManager.getProxy().sendMessage(errorMessage);
			return;
		}
		
		if(!taskSolverModule.getTaskSolverFactory().hasProblemType(divideProblem.getProblemType())) {
			sendErrorMessage();
			return;
		}
		
		final int threadCount = divideProblem.getComputationalNodes().intValue();
		final DivideCallback callback = new DivideCallback() {
			
			@Override
			public void onComplete(byte[][] partialProblemDatas) {
				sendPartialProblems(divideProblem, partialProblemDatas);
			}
		};
		
		taskSolverModule.divide(divideProblem.getId(), divideProblem.getData(), threadCount, divideProblem.getProblemType(), callback);
		
	}

	private void sendPartialProblems(DivideProblem divideProblem, byte[][] partialProblemDatas) {
		
		final SolvePartialProblems solvePartialProblems = new SolvePartialProblems();
		solvePartialProblems.setProblemType(divideProblem.getProblemType());
		solvePartialProblems.setId(divideProblem.getId());
		solvePartialProblems.setCommonData(divideProblem.getData());
		solvePartialProblems.setSolvingTimeout(new BigInteger("10"));
		
		final PartialProblems partialProblems = new PartialProblems();
		final int threadCount = partialProblemDatas.length;
		
		for(int i = 0; i < threadCount; i++){
			PartialProblem partialProblem = new PartialProblem();
			partialProblem.setData(partialProblemDatas[i]);
			partialProblem.setTaskId(new BigInteger(String.valueOf(i)));
			partialProblem.setNodeID(taskManager.getId());
			
			partialProblems.getPartialProblem().add(partialProblem);
		}
		
		solvePartialProblems.setPartialProblems(partialProblems);
		
		System.out.println("Partial problems are sent");
		final ClientMessage clientMessage = new ClientMessage(taskManager.getId(), solvePartialProblems);
		taskManager.getProxy().sendMessage(clientMessage);
	}
	
	private void sendErrorMessage() {
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage("Problem type is not registered");
		
		logger.error(error.getErrorMessage());
		
		taskManager.getProxy().sendMessage(new ClientMessage(taskManager.getId(), error));
	}

}
