package com.computationalcluster.communicationserver.module;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems.PartialProblem;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentAssigment;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;
import com.computationalcluster.communicationserver.module.componentstatus.SolvePartialProblemsAssignment;
import com.computationalcluster.communicationserver.module.computationalnode.SolvePartialProblemsMessage;
import com.computationalcluster.communicationserver.module.problemstatus.PartialProblemState;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;

public class ComponentStatusModule {
	private static final Logger logger = Logger.getLogger(ComponentStatusModule.class);
	
	private final HashMap<BigInteger, ComponentStatus> componentStatusList;
	private final CommunicationServer communicationServer;
	
	public ComponentStatusModule(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
		componentStatusList = new HashMap<BigInteger, ComponentStatus>();
	}
	
	public void registerComponent(ComponentStatus componentStatus) {
		updateComponentStatus(componentStatus);
	}
	
	public void removeInactiveComponents() {
		final long timeout = communicationServer.getServerConfig().getTimeout() * 1000;
		final long lastTime = System.currentTimeMillis() - timeout;
		
		final Iterator<Entry<BigInteger, ComponentStatus>> it = componentStatusList.entrySet().iterator();
	    while (it.hasNext()) {
	    	final Entry<BigInteger, ComponentStatus> pair = it.next();
	    	final ComponentStatus componentStatus = pair.getValue();
	        
	        if(componentStatus.getTimestamp() < lastTime) {
	        	it.remove();
	        	
	        	reassign(componentStatus);
	        	logger.info(componentStatus.getClientComponentType().getName() + " is removed id:" + componentStatus.getClientId());
	        }
	    }
	}
	
	private void reassign(ComponentStatus componentStatus) {
		final WaitingMessageProcessingModule module = communicationServer.getWaitingMessageProcessingModule(componentStatus.getClientComponentType());
		
		if(module != null) {
			final List<ComponentAssigment> assigments = componentStatus.getComponentAssigments();
			for(int i = 0; i < assigments.size(); i++) {
				final ComponentAssigment assigment = assigments.get(i);
				module.addMessage(assigment.getMessage());
			}
			
			if(module == communicationServer.getComputationalNodeModule()) {
				final ProblemStatusModule problemStatusModule = communicationServer.getProblemStatusModule();
				final List<ComponentAssigment> componentAssigments = componentStatus.getComponentAssigments();
				for(int i = 0; i < componentAssigments.size(); i++) {
					final ComponentAssigment componentAssigment = componentAssigments.get(i);
					
					if(componentAssigment.getMessage().getClass().equals(SolvePartialProblemsMessage.class)) {
						final SolvePartialProblemsAssignment assignment = (SolvePartialProblemsAssignment)componentAssigment;
						final SolvePartialProblemsMessage message = (SolvePartialProblemsMessage)assignment.getMessage();
						final ProblemStatus problemStatus = problemStatusModule.getProblemStatus(message.getProblemId());
						
						final List<PartialProblem> partialProblemList = assignment.getSolvePartialProblems().getPartialProblems().getPartialProblem();
						
						if(message.getProblemId() == problemStatus.getProblemId()) {
							
							for(int j = 0; j < partialProblemList.size(); j++) {
								final PartialProblem partialProblem = partialProblemList.get(j);
								
								problemStatus.getPartialProblemStatus(partialProblem.getTaskId()).setState(PartialProblemState.UNSOLVED);
							}
							//logger.debug("Assignment removed problemId:" + message.getProblemId() + " taskId:" + taskId);
							
						}
					}
				}
			}
			
		}
		
	}
	
	public void updateComponentStatus(ComponentStatus componentStatus){
		componentStatus.setTimestamp(System.currentTimeMillis());
		componentStatusList.put(componentStatus.getClientId(), componentStatus);
	}
	
	public ComponentStatus getComponent(BigInteger id) {
		return componentStatusList.get(id);
	}
	
	public boolean hasAvailableComponent(ClientComponentType clientComponentType, String problemType) {
		return getAvailableComponent(clientComponentType, problemType) != null;
	}
	
	public ComponentStatus getAvailableComponent(ClientComponentType clientComponentType, String problemType) {
		final Iterator<Entry<BigInteger, ComponentStatus>> it = componentStatusList.entrySet().iterator();
	    while (it.hasNext()) {
	    	final Entry<BigInteger, ComponentStatus> pair = it.next();
	    	final ComponentStatus componentStatus = pair.getValue();
	        if(isAvailable(componentStatus, clientComponentType, problemType)){
	        	return componentStatus;
	        }
	    }
		
		return null;
	}
	
	public List<ComponentStatus> getAvailableComponents(ClientComponentType clientComponentType, String problemType) {
		final List<ComponentStatus> availableComponents = new ArrayList<>();
		
		final Iterator<Entry<BigInteger, ComponentStatus>> it = componentStatusList.entrySet().iterator();
	    while (it.hasNext()) {
	    	final Entry<BigInteger, ComponentStatus> pair = it.next();
	    	final ComponentStatus componentStatus = pair.getValue();
	        if(isAvailable(componentStatus, clientComponentType, problemType)){
	        	availableComponents.add(componentStatus);
	        }
	    }
		
		return availableComponents;
	}
	
	private boolean isAvailable(ComponentStatus componentStatus, ClientComponentType clientComponentType, String problemType) {
		if(componentStatus.getClientComponentType().equals(clientComponentType) && 
        		componentStatus.getProblemTypes().contains(problemType) && 
        		!componentStatus.getAvailableThreads().isEmpty()){
        	return true;
        }
		return false;
	}
	
	
	public int getMaxComputationalNodeThreads() {
		int nodeThreads = 0;
		final Iterator<Entry<BigInteger, ComponentStatus>> it = componentStatusList.entrySet().iterator();
	    while (it.hasNext()) {
	    	final Entry<BigInteger, ComponentStatus> pair = it.next();
	    	final ComponentStatus componentStatus = pair.getValue();
	        if(componentStatus.getClientComponentType().equals(ClientComponentType.COMPUTATIONAL_NODE)) {
	        	nodeThreads += componentStatus.getThreadCount();
	        }
	    }
	    
	    return nodeThreads;
	}
	
}
