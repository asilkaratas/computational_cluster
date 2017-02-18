package com.computationalcluster.computationalnode;


import com.computationalcluster.common.component.SolverClientComponent;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.computationalnode.messagehandler.SolvePartialProblemsHandler;

public class ComputationalNode extends SolverClientComponent {
	
	public ComputationalNode(CommunicationProxy proxy, NodeTaskSolverModule taskSolverModule) {
		super(ClientComponentType.COMPUTATIONAL_NODE, proxy, taskSolverModule);
		
		getMessageProcessingModule().addMessageHandler(SolvePartialProblems.class, new SolvePartialProblemsHandler(this));
	}
	
	public NodeTaskSolverModule getTaskSolverModule(){
		return (NodeTaskSolverModule) super.getTaskSolverModule();
	}
	
}
