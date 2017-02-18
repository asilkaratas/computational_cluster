package com.computationalcluster.taskmanager.messagehandler;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.enums.SolutionType;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList;
import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.taskmanager.TaskManager;
import com.computationalcluster.taskmanager.TaskSolverModule;
import com.computationalcluster.taskmanager.tasksolver.MergeCallback;

public class MergeSolutionHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(MergeSolutionHandler.class);
	
	private final TaskManager taskManager;
	
	public MergeSolutionHandler(TaskManager taskManager){
		this.taskManager = taskManager;
	}

	@Override
	public void handle(ClientMessage clientMessage) {
		final Solutions solutions = (Solutions)clientMessage.getMessage();
		logger.info("Choose Final Solution message recieved id:" + solutions.getId() + " type:" + solutions.getProblemType());
		
		final Error error = MessageValidatorUtil.getError(solutions);
		if(error != null) {
			logger.error(error.getErrorMessage());
			ClientMessage errorMessage = new ClientMessage(taskManager.getId(), error);
			taskManager.getProxy().sendMessage(errorMessage);
			return;
		}
		
		final TaskSolverModule taskSolverModule = taskManager.getTaskSolverModule();
		final String problemType = solutions.getProblemType();
		
		if(!taskSolverModule.getTaskSolverFactory().hasProblemType(problemType)) {
			sendErrorMessage();
			return;
		}
		
		
		
		final List<Solution> solutionList = solutions.getSolutionsList().getSolution();
		logger.info("solutionList solution:" + solutionList.get(0));
		byte[][] partialSolutionDatas = new byte[solutionList.size()][];
		
		for(int i = 0; i < solutionList.size(); i++) {
			
			partialSolutionDatas[i] = solutionList.get(i).getData();
		}
		
		final MergeCallback callback = new MergeCallback() {
			
			@Override
			public void onComplete(byte[] solutionData) {
				sendResponse(solutions, solutionData);
			}
		};
		
		taskSolverModule.mergeSolution(
				solutions.getId(),
				partialSolutionDatas, 
				solutions.getCommonData(),
				problemType,
				callback);
		
	}
	
	private void sendResponse(Solutions solutions, byte[] solutionData) {
		//response
		final Solutions finalSolution = new Solutions();
		finalSolution.setCommonData(solutions.getCommonData());
		finalSolution.setId(solutions.getId());
		finalSolution.setProblemType(solutions.getProblemType());
		
		final SolutionsList solutionsList = new SolutionsList();
		final Solution solution = new Solution();
		solution.setComputationsTime(new BigInteger("25"));
		solution.setData(solutionData);
		solution.setTaskId(new BigInteger("1"));
		solution.setType(SolutionType.FINAL.getName());
		solution.setTimeoutOccured(false);
		solutionsList.getSolution().add(solution);
		
		
		finalSolution.setSolutionsList(solutionsList);

		System.out.println("Finalsolution is sent problemId:" + finalSolution.getId());
		taskManager.getProxy().sendMessage(new ClientMessage(taskManager.getId(), finalSolution));
	}
	
	private void sendErrorMessage() {
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage("Problem type is not registered");
		
		logger.error(error.getErrorMessage());
		
		taskManager.getProxy().sendMessage(new ClientMessage(taskManager.getId(), error));
	}

}
