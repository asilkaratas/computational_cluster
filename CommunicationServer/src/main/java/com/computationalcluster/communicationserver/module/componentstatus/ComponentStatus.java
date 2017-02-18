package com.computationalcluster.communicationserver.module.componentstatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ThreadState;
import com.computationalcluster.communicationserver.module.taskmanager.DivideProblemMessage;
import com.computationalcluster.communicationserver.module.taskmanager.MergeSolutionsMessage;

public class ComponentStatus {
	private static final Logger logger = Logger.getLogger(ComponentStatus.class);
	
	private final BigInteger clientId;
	private final ClientComponentType clientComponentType;
	private final List<String> problemTypes;
	private final List<ThreadStatus> threadStatusList;
	private final List<ComponentAssigment> componentAssigments;
	
	private int availableThreadCount = 0;
	private long timestamp = 0;
	
	public ComponentStatus(BigInteger clientId, ClientComponentType clientComponentType, int threadCount, List<String> problemTypes){
		this.clientId = clientId;
		this.clientComponentType = clientComponentType;
		this.problemTypes = new ArrayList<>(problemTypes);
		threadStatusList = new ArrayList<>();
		availableThreadCount = threadCount;
		componentAssigments = new ArrayList<>();
		
		logger.info("availableThreadCount:" + availableThreadCount);
		
		for(int i = 0; i < threadCount; i++) {
			final ThreadStatus threadStaus = new ThreadStatus();
			threadStatusList.add(threadStaus);
		}
	}
	
	public BigInteger getClientId() {
		return clientId;
	}
	
	public ClientComponentType getClientComponentType() {
		return clientComponentType;
	}
	
	public void setTimestamp(long timestamp){
		this.timestamp = timestamp;
	}
	
	public long getTimestamp(){
		return timestamp;
	}

	public int getThreadCount() {
		return threadStatusList.size();
	}
	
	public List<String> getProblemTypes() {
		return problemTypes;
	}
	
	public List<ThreadStatus> getThreadStatusList() {
		return threadStatusList;
	}
	
	public List<ThreadStatus> getAvailableThreads() {
		final List<ThreadStatus> threads = new ArrayList<>();
		
		for(int i = 0; i < threadStatusList.size(); i++) {
			final ThreadStatus threadStatus = threadStatusList.get(i);
			if(threadStatus.getThreadState().equals(ThreadState.IDLE)) {
				threads.add(threadStatus);
			}
		}
		
		return threads;
	}
	
	public int getAvailableThreadCount() {
		return availableThreadCount;
	}
	
	public void allocateThreads(int threads) {
		availableThreadCount -= threads;
	}
	
	public void releaseThreads(int threads) {
		availableThreadCount += threads;
	}
	
	public List<ComponentAssigment> getComponentAssigments() {
		return componentAssigments;
	}
	
	
}
