package com.computationalcluster.taskmanager;

import com.computationalcluster.common.config.ClientConfig;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.connection.DefaultClientCommunicationProxy;

public class Main {
	
	public static void main(String[] args){
		
		final ClientConfig clientConfig = new ClientConfig("TaskManager", args);
		if(!clientConfig.checkIsValidAndShowUsage()) {
			return;
		}
		
		int threadCount = Runtime.getRuntime().availableProcessors();
		
		final CommunicationProxy proxy = new DefaultClientCommunicationProxy(clientConfig);
		
		final TaskSolverModule taskSolverModule = new TaskSolverModule(threadCount);
		//taskSolverModule.getTaskSolverFactory().addTaskSolverCreator(ProblemType.TSP.getName(), new DVRPTaskSolverCreator());
		taskSolverModule.getTaskSolverFactory().loadTaskSolvers();
		
		final TaskManager taskManager = new TaskManager(proxy,taskSolverModule);
		taskManager.start();
		
	}
}
