package com.computationalcluster.communicationserver;


import java.math.BigInteger;

import com.computationalcluster.common.config.ServerConfig;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.SolveRequestResponse;
import com.computationalcluster.communicationserver.backup.BackupCommunicationServer;
import com.computationalcluster.communicationserver.backup.BackupCommunicationServerProxy;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.ComputationalNodeModule;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.ServerMessageProcessingModule;
import com.computationalcluster.communicationserver.module.TaskManagerModule;
import com.computationalcluster.communicationserver.module.WaitingMessageProcessingModule;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;
import com.computationalcluster.communicationserver.module.taskmanager.DivideProblemMessage;


public class CommunicationServer {
	
	private final ServerConfig serverConfig;
	private final CommunicationServerProxy proxy;
	private final ComponentStatusModule componentStatusModule;
	private final ServerMessageProcessingModule messageProcessingModule;
	private final TaskManagerModule taskManagerModule;
	private final ComputationalNodeModule computationalNodeModule;
	private final ProblemStatusModule problemStatusModule;
	private BackupCommunicationServer backupCommunicationServer = null;
	
	public CommunicationServer(ServerConfig serverConfig, CommunicationServerProxy proxy) {
		this.serverConfig = serverConfig;
		this.proxy = proxy;
		
		componentStatusModule = new ComponentStatusModule(this);
		messageProcessingModule = new ServerMessageProcessingModule(this);
		taskManagerModule = new TaskManagerModule(this);
		computationalNodeModule = new ComputationalNodeModule(this);
		problemStatusModule = new ProblemStatusModule(this);
		
		if(serverConfig.isBackup()){
			final CommunicationProxy backupProxy = new BackupCommunicationServerProxy(serverConfig);
			backupCommunicationServer = new BackupCommunicationServer(this, backupProxy);
		}
	}
	
	public WaitingMessageProcessingModule getWaitingMessageProcessingModule(ClientComponentType clientComponentType) {
		if(clientComponentType.equals(ClientComponentType.COMPUTATIONAL_NODE)) {
			return computationalNodeModule;
		} else if(clientComponentType.equals(ClientComponentType.TASK_MANAGER)) {
			return taskManagerModule;
		}
		
		return null;
	}
	
	public void start() {
		System.out.println("CommunicationServer is starting...");
		
		proxy.start();
		messageProcessingModule.start();
		taskManagerModule.start();
		computationalNodeModule.start();
		
		if(serverConfig.isBackup()){
			backupCommunicationServer.start();
		}
	}
	
	public ProblemStatusModule getProblemStatusModule() {
		return problemStatusModule;
	}
	
	public TaskManagerModule getTaskManagerModule() {
		return taskManagerModule;
	}
	
	public ComputationalNodeModule getComputationalNodeModule() {
		return computationalNodeModule;
	}
	
	public ComponentStatusModule getComponentStatusModule() {
		return componentStatusModule;
	}

	public CommunicationServerProxy getProxy(){
		return proxy;
	}
	
	public ServerConfig getServerConfig() {
		return serverConfig;
	}
	
}
