package com.computationalcluster.communicationserver.messagehandler;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList;
import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.WaitingMessage;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentAssigment;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;
import com.computationalcluster.communicationserver.module.componentstatus.SolvePartialProblemsAssignment;
import com.computationalcluster.communicationserver.module.computationalnode.SolvePartialProblemsMessage;
import com.computationalcluster.communicationserver.module.problemstatus.PartialProblemState;
import com.computationalcluster.communicationserver.module.problemstatus.PartialProblemStatus;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;
import com.computationalcluster.communicationserver.module.taskmanager.DivideProblemMessage;
import com.computationalcluster.communicationserver.module.taskmanager.MergeSolutionsMessage;

public class SolutionsHandler implements ClientMessageHandler{
	private static final Logger logger = Logger.getLogger(SolutionsHandler.class);
	
	private final CommunicationServer communicationServer;
	
	public SolutionsHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	@Override
	public void handle(ClientMessage clientMessage) {
		final Solutions solutions = (Solutions)clientMessage.getMessage();
		final BigInteger clientId = clientMessage.getClientId();
		final BigInteger problemId = solutions.getId();
		final String problemType = solutions.getProblemType();
		logger.info("Solution message recieved from id:" + clientId);
		
		final Error error = MessageValidatorUtil.getError(solutions);
		if(error != null) {
			logger.error(error.getErrorMessage());
			final ClientMessage errorMessage = new ClientMessage(clientId, error);
			communicationServer.getProxy().sendMessage(errorMessage);
			return;
		}
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		final ComponentStatus componentStatus = componentStatusModule.getComponent(clientId);
		
		if(componentStatus == null) {
			sendUnknownSenderError(clientId);
			return;
		}
		
		if(componentStatus.getClientComponentType().equals(ClientComponentType.COMPUTATIONAL_NODE)){
			
			logger.info("Solution message recieved from CN id:" + clientMessage.getClientId()); 
			
			final ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
			final ProblemStatus problemStatus = problemStatusModule.getProblemStatus(problemId);
			
			boolean allProblemsSolved = false;
			synchronized (problemStatus) {
				final List<Solution> solutionsList = solutions.getSolutionsList().getSolution();
				for(int i = 0; i < solutionsList.size(); i++){
					
					final Solution partialSolution = solutionsList.get(i);
					PartialProblemStatus partialProblemStatus = problemStatus.getPartialProblemStatus(partialSolution.getTaskId());
					partialProblemStatus.setState(PartialProblemState.SOLVED);
					partialProblemStatus.setPartialSolution(partialSolution);
					
					System.out.println("Partial solution is recieved from clientId:" + clientMessage.getClientId() +
							 " problemId:" + problemId + " taskId:" +  partialSolution.getTaskId());
					
					synchronized (componentStatus) {
						componentStatus.releaseThreads(1);
						removeAssignment(componentStatus, problemStatus, partialSolution.getTaskId());
					}
					
					logger.info("partialSolution taskId:" + partialSolution.getTaskId() + " data:" + partialSolution.getData());
				
					
					allProblemsSolved = problemStatus.allProblemsSolved();
				}
				
				if(allProblemsSolved) {
					System.out.println("All partial problems are recieved problemId:" + problemId);
					
					problemStatus.createPartialSolution();
					final MergeSolutionsMessage mergeSolutionsMessage = new MergeSolutionsMessage(problemStatus.getProblemId(), problemType);
					communicationServer.getTaskManagerModule().addMessage(mergeSolutionsMessage);
				}
			}

		} else if(componentStatus.getClientComponentType().equals(ClientComponentType.TASK_MANAGER)){
			logger.info("Solution message recieved from TM: waiting for CC."); 
			
			ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
			ProblemStatus problemStatus = problemStatusModule.getProblemStatus(problemId);
			problemStatus.setFinalSolution(solutions);
			
			synchronized (componentStatus) {
				componentStatus.releaseThreads(1);
				removeAssignment(componentStatus, problemStatus.getProblemId());
				
			}
		} else {
			sendInvalidComponentTypeError(clientId);
		}
	}
	
	private void removeAssignment(ComponentStatus componentStatus, ProblemStatus problemStatus, BigInteger taskId) {
		List<ComponentAssigment> componentAssigments = componentStatus.getComponentAssigments();
		for(int i = 0; i < componentAssigments.size(); i++) {
			ComponentAssigment componentAssigment = componentAssigments.get(i);
			
			if(componentAssigment.getMessage().getClass().equals(SolvePartialProblemsMessage.class)) {
				SolvePartialProblemsAssignment assignment = (SolvePartialProblemsAssignment)componentAssigment;
				SolvePartialProblemsMessage message = (SolvePartialProblemsMessage)assignment.getMessage();
				if(message.getProblemId() == problemStatus.getProblemId()) {
					logger.debug("Assignment removed problemId:" + message.getProblemId() + " taskId:" + taskId);
					problemStatus.getPartialProblemStatus(taskId).setState(PartialProblemState.SOLVED);
				}
			}
		}
	}
	
	private void removeAssignment(ComponentStatus componentStatus, BigInteger problemId) {
		List<ComponentAssigment> componentAssigments = componentStatus.getComponentAssigments();
		for(int i = 0; i < componentAssigments.size(); i++) {
			ComponentAssigment componentAssigment = componentAssigments.get(i);
			
			if(componentAssigment.getMessage().getClass().equals(MergeSolutionsMessage.class)) {
				MergeSolutionsMessage message = (MergeSolutionsMessage)componentAssigment.getMessage();
				if(message.getProblemId() == problemId) {
					logger.debug("Assignment removed problemId:" + message.getProblemId());
					componentAssigments.remove(i);
				}
			}
		}
	}
	
	
	private void sendUnknownSenderError(BigInteger clientId) {
		Error error = new Error();
		error.setErrorType(ErrorType.UNKNOWN_SENDER.getName());
		error.setErrorMessage(ErrorMessage.SOLUTIONS_HANDLER_UNKNOWN_SENDER);
		
		logger.error(error.getErrorMessage());
		
		ClientMessage responseMessage = new ClientMessage(clientId, error);
		communicationServer.getProxy().sendMessage(responseMessage);
	}
	
	private void sendInvalidComponentTypeError(BigInteger clientId) {
		Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage(ErrorMessage.SOLUTIONS_HANDLER_INVALID_COMPONENT_TYPE);
		
		logger.error(error.getErrorMessage());
		
		ClientMessage responseMessage = new ClientMessage(clientId, error);
		communicationServer.getProxy().sendMessage(responseMessage);
	}
	

}
