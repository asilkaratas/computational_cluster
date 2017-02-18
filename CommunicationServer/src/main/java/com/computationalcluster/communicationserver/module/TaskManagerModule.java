package com.computationalcluster.communicationserver.module;

import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.taskmanager.DivideProblemMessage;
import com.computationalcluster.communicationserver.module.taskmanager.DivideProblemMessageHandler;
import com.computationalcluster.communicationserver.module.taskmanager.MergeSolutionsMessage;
import com.computationalcluster.communicationserver.module.taskmanager.MergeSolutionsMessageHandler;

public class TaskManagerModule extends WaitingMessageProcessingModule {
	
	public TaskManagerModule(CommunicationServer communicationServer) {
		super(communicationServer, ClientComponentType.TASK_MANAGER);
		setName("TaskManagerModule");
		
		addMessageHandler(DivideProblemMessage.class, new DivideProblemMessageHandler(communicationServer));
		addMessageHandler(MergeSolutionsMessage.class, new MergeSolutionsMessageHandler(communicationServer));
	}

}
