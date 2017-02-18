package com.computationalcluster.computationalnode;



import com.computationalcluster.common.config.ClientConfig;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.connection.DefaultClientCommunicationProxy;

public class Main {
	//private static final Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args){
		
		final ClientConfig clientConfig = new ClientConfig("ComputationalNode", args);
		if(!clientConfig.checkIsValidAndShowUsage()) { 
			return;
		}	
		
		final int threadCount = Runtime.getRuntime().availableProcessors();
		
		final CommunicationProxy proxy = new DefaultClientCommunicationProxy(clientConfig);
		final NodeTaskSolverModule taskSolverModule = new NodeTaskSolverModule(threadCount);
		//taskSolverModule.getTaskSolverFactory().addTaskSolverCreator(ProblemType.TSP.getName(), new DVRPTaskSolverCreator());
		taskSolverModule.getTaskSolverFactory().loadTaskSolvers();
		
		final ComputationalNode computationalNode = new ComputationalNode(proxy, taskSolverModule);
		computationalNode.start();
	}
}
