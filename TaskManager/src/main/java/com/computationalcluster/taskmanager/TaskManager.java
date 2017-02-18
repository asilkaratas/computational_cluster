package com.computationalcluster.taskmanager;


import com.computationalcluster.common.component.SolverClientComponent;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messages.DivideProblem;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.taskmanager.messagehandler.MergeSolutionHandler;
import com.computationalcluster.taskmanager.messagehandler.DivideProblemHandler;

public class TaskManager extends SolverClientComponent {
	
	public TaskManager(CommunicationProxy proxy, TaskSolverModule taskSolverModule){
		
		super(ClientComponentType.TASK_MANAGER, proxy, taskSolverModule);
		
		getMessageProcessingModule().addMessageHandler(DivideProblem.class, new DivideProblemHandler(this));
		getMessageProcessingModule().addMessageHandler(Solutions.class, new MergeSolutionHandler(this));
	}
	
	public TaskSolverModule getTaskSolverModule(){
		return (TaskSolverModule) super.getTaskSolverModule();
	}
	
}
