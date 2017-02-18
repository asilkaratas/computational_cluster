package com.computationalcluster.communicationserver.module.computationalnode;

import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.WaitingMessage;
import com.computationalcluster.communicationserver.module.WaitingMessageHandler;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentAssigment;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;
import com.computationalcluster.communicationserver.module.componentstatus.SolvePartialProblemsAssignment;
import com.computationalcluster.communicationserver.module.problemstatus.PartialProblemState;
import com.computationalcluster.communicationserver.module.problemstatus.PartialProblemStatus;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;

public class SolvePartialProblemsMessageHandler implements WaitingMessageHandler{
	private static final Logger logger = Logger.getLogger(SolvePartialProblemsMessageHandler.class);
	
	private final CommunicationServer communicationServer;

	public SolvePartialProblemsMessageHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public void handle(WaitingMessage waitingMessage) {
		final SolvePartialProblemsMessage message = (SolvePartialProblemsMessage)waitingMessage;
		
		logger.info("Partial problems are handling:" +
					" problemType:" + message.getProblemType() + " problemId:" + message.getProblemId());
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		final List<ComponentStatus> componentStatusList = componentStatusModule.getAvailableComponents(ClientComponentType.COMPUTATIONAL_NODE, message.getProblemType());
		
		final ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
		final ProblemStatus problemStatus = problemStatusModule.getProblemStatus(message.getProblemId());
		
		synchronized (problemStatus) {
			final List<PartialProblemStatus> unsolvedPartialProblems = problemStatus.getUnsolvedPartialProblems();
			int unsolvedProblemCount = unsolvedPartialProblems.size();
			int partialProblemIndex = 0;
			
			for(int i = 0; i < componentStatusList.size() && unsolvedProblemCount > 0; i++) {
				final ComponentStatus componentStatus = componentStatusList.get(i);
				final int minCount = Math.min(unsolvedProblemCount, componentStatus.getAvailableThreadCount());
				
				logger.info("componentStatus:threadCount:" + componentStatus.getThreadCount() + " minCount:" + minCount + " totalProblems:" + problemStatus.getTotalProblems());
				if(minCount == 0) continue;
				
				final PartialProblems partialProblems = new PartialProblems();
				final SolvePartialProblems solvePartialProblems = new SolvePartialProblems();
				solvePartialProblems.setPartialProblems(partialProblems);
				solvePartialProblems.setCommonData(problemStatus.getSolvePartialProblems().getCommonData());
				solvePartialProblems.setId(problemStatus.getSolvePartialProblems().getId());
				solvePartialProblems.setProblemType(problemStatus.getSolvePartialProblems().getProblemType());
				solvePartialProblems.setSolvingTimeout(problemStatus.getSolvePartialProblems().getSolvingTimeout());
				
				for(int j = 0; j < minCount; j++, partialProblemIndex ++) {
					final PartialProblemStatus partialProblemStatus = unsolvedPartialProblems.get(partialProblemIndex);
					partialProblemStatus.setState(PartialProblemState.SOLVING);
					partialProblems.getPartialProblem().add(partialProblemStatus.getPartialProblem());
				}
				
				componentStatus.allocateThreads(minCount);
				unsolvedProblemCount -= minCount;
				logger.info("unsolvedProblemCount:" + unsolvedProblemCount);
				final SolvePartialProblemsAssignment componentAssignment = new SolvePartialProblemsAssignment(message, solvePartialProblems);
				componentStatus.getComponentAssigments().add(componentAssignment);
				
				final ClientMessage clientMessage = new ClientMessage(componentStatus.getClientId(), solvePartialProblems);
				communicationServer.getProxy().sendMessage(clientMessage);
			}
			logger.info("final unsolvedProblemCount:" + unsolvedProblemCount);
			message.setHandled(unsolvedProblemCount == 0);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				logger.error(e.getMessage());
			}
		}
		
		
		
	}
	
	

}
