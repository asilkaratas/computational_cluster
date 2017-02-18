package com.computationalcluster.computationalnode.messagehandler;

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
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems.PartialProblem;
import com.computationalcluster.computationalnode.ComputationalNode;
import com.computationalcluster.computationalnode.NodeTaskSolverModule;
import com.computationalcluster.computationalnode.tasksolver.SolveCallback;

public class SolvePartialProblemsHandler implements ClientMessageHandler {
	private static final Logger logger = Logger.getLogger(SolvePartialProblemsHandler.class);
	
	private final ComputationalNode computationalNode;
	
	public SolvePartialProblemsHandler(ComputationalNode computationalNode){
		this.computationalNode = computationalNode;
	}

	@Override
	public void handle(ClientMessage clientMessage) {
		final SolvePartialProblems solvePartialProblems = (SolvePartialProblems)clientMessage.getMessage();
		
		System.out.println("Solve partial problems recieved problemId:" + solvePartialProblems.getId() + 
				" problemType:" + solvePartialProblems.getProblemType() + " size:" + solvePartialProblems.getPartialProblems().getPartialProblem().size());
		
		final NodeTaskSolverModule taskSolverModule = computationalNode.getTaskSolverModule();
		final String problemType = solvePartialProblems.getProblemType();
		if(!taskSolverModule.getTaskSolverFactory().hasProblemType(problemType)) {
			sendErrorMessage();
			return;
		}
		
		
		
		final BigInteger problemId = solvePartialProblems.getId();
		final long timeout = solvePartialProblems.getSolvingTimeout().longValue();
		final List<PartialProblem> partialProblems = solvePartialProblems.getPartialProblems().getPartialProblem();
		
		logger.info("partialProblems.size():" + partialProblems.size());
		//SolveCallback callback = new SolveCallbackImp(computationalNode, problemId, problemType, partialProblems.size());
		
		for(int i = 0; i < partialProblems.size(); i++){
			
			final SolveCallback callback = new SolveCallback() {
				
				@Override
				public void onComplete(BigInteger taskId, byte[] solutionData,
						BigInteger computationTime, boolean timeoutOccured) {
					
					logger.info("Partial solution ready taskId:" + taskId);
							
					
					final Solution solution = new Solution();
					solution.setData(solutionData);
					solution.setComputationsTime(computationTime);
					solution.setTaskId(taskId);
					solution.setType(SolutionType.PARTIAL.getName());
					solution.setTimeoutOccured(timeoutOccured);
					
					final SolutionsList solutionsList = new SolutionsList();
					solutionsList.getSolution().add(solution);
					
					final Solutions solutions = new Solutions();
					solutions.setCommonData(solvePartialProblems.getCommonData());
					solutions.setId(problemId);
					solutions.setProblemType(problemType);
					solutions.setSolutionsList(solutionsList);
					
					System.out.println("Partial solutions is sent problemId:" + problemId + " taskId:" + taskId);
					final ClientMessage response = new ClientMessage(computationalNode.getId(), solutions);
					computationalNode.getProxy().sendMessage(response);
				}
			};
			
			final PartialProblem partialProblem = partialProblems.get(i);
			taskSolverModule.solve(problemId, 
					partialProblem.getTaskId(),
					partialProblem.getData(), 
					problemType, timeout, callback);
		}
		
	}
	
	
	private void sendErrorMessage() {
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage("Problem type is not registered");
		
		logger.error(error.getErrorMessage());
		
		computationalNode.getProxy().sendMessage(new ClientMessage(error));
	}
	
	/*
	private static class SolveCallbackImp implements SolveCallback {
		private static final Logger logger = Logger.getLogger(SolveCallbackImp.class);
		
		private int solutionCount = 0;
		private int maxSolutionCount = 0;
		private Solutions solutions = null;
		private List<Solution> solutionsList = null;
		private ComputationalNode computationalNode = null;
		
		public SolveCallbackImp(ComputationalNode computationalNode, BigInteger problemId, ProblemType problemType, int maxSolutionCount) {
			this.computationalNode = computationalNode;
			this.maxSolutionCount = maxSolutionCount;
			
			solutions = new Solutions();
			solutions.setCommonData(null);
			solutions.setId(problemId);
			solutions.setProblemType(problemType.getName());
			
			solutions.setSolutionsList(new SolutionsList());
			solutionsList = solutions.getSolutionsList().getSolution();
			
		}

		@Override
		public void onComplete(BigInteger taskId, byte[] solutionData,
				BigInteger computationTime, boolean timeoutOccured) {
			Solution solution = new Solution();
			solution.setData(solutionData);
			solution.setComputationsTime(computationTime);
			solution.setTaskId(taskId);
			solution.setTimeoutOccured(timeoutOccured);
			
			synchronized (solutionsList) {
				solutionsList.add(solution);
				solutionCount ++;
				logger.info(String.format("Solution recieved %d/%d", solutionCount, maxSolutionCount));
				
				if(solutionCount == maxSolutionCount) {
					logger.info("All solutions recieved");
					computationalNode.getProxy().sendMessage(new ClientMessage(solutions));
					dispose();
				}
			}
			
		
		}
		
		private void dispose() {
			computationalNode = null;
		}
		
		@Override
		protected void finalize() throws Throwable {
			// TODO Auto-generated method stub
			//System.out.println("SolveCallbackImp DIEED");
			super.finalize();
		}
		
	}
	*/

}
