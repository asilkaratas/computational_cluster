package com.computationalcluster.common.component;

import java.math.BigInteger;
import java.util.List;

import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ProblemType;
import com.computationalcluster.common.enums.ThreadState;
import com.computationalcluster.common.messagehandler.ErrorHandler;
import com.computationalcluster.common.messagehandler.NoOperationHandler;
import com.computationalcluster.common.messagehandler.RegisterResponseHandler;
import com.computationalcluster.common.messages.ComponentType;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.Register.SolvableProblems;
import com.computationalcluster.common.messages.Register.Type;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.messages.Status.Threads;
import com.computationalcluster.common.module.BackupServerInfoModule;
import com.computationalcluster.common.module.BaseTaskSolverModule;
import com.computationalcluster.common.module.tasksolver.SolverThread;
import com.computationalcluster.common.module.tasksolver.SolverThreadStatus;
import com.computationalcluster.common.utils.MessageString;
import com.computationalcluster.common.messages.Error;

public class SolverClientComponent extends ClientComponent {
	//private static final Logger logger = Logger.getLogger(SolverClientComponent.class);
	
	private final BaseTaskSolverModule taskSolverModule;
	private final BackupServerInfoModule backupServerInfoModule;
	
	public SolverClientComponent(ClientComponentType componentType, 
			CommunicationProxy proxy, 
			BaseTaskSolverModule taskSolverModule) {
		super(componentType, proxy);
		
		this.taskSolverModule = taskSolverModule;
		backupServerInfoModule = new BackupServerInfoModule();
		
		getMessageProcessingModule().addMessageHandler(RegisterResponse.class, new RegisterResponseHandler(this));
		getMessageProcessingModule().addMessageHandler(NoOperation.class, new NoOperationHandler(this));
		getMessageProcessingModule().addMessageHandler(Error.class, new ErrorHandler(this));
	}
	
	public BackupServerInfoModule getBackupServerInfoModule() {
		return backupServerInfoModule;
	}

	public BaseTaskSolverModule getTaskSolverModule() {
		return taskSolverModule;
	}
	
	@Override
	public Register getRegister() {
		System.out.println("Register message is sent");
		
		final SolvableProblems solvableProblems = new SolvableProblems();
		solvableProblems.getProblemName().add(ProblemType.TSP.toString());
		
		final Register register = new Register();
		
		final Type type = new Type();
		type.setValue(ComponentType.fromValue(getComponentType().getName()));
		
		register.setType(type);
		register.setParallelThreads((short) taskSolverModule.getThreadCount());
		register.setSolvableProblems(solvableProblems);
		
		return register;
	}
	
	@Override
	public Status getStatus() {
		final Status status = new Status();
		status.setId(getId());
		
		final int threadCount = taskSolverModule.getThreadCount();
		List<SolverThread> solverThreads = taskSolverModule.getThreads();
		
		final long now = System.currentTimeMillis();
		
		final Threads threads = new Threads();
		
		for(int i = 0; i < threadCount; i++){
			final SolverThread solverThread = solverThreads.get(i);
			final SolverThreadStatus threadStatus = solverThread.getStatus();
			final Threads.Thread thread = new Threads.Thread();
			
			synchronized (threadStatus) {
				thread.setHowLong(new BigInteger(String.valueOf(now - threadStatus.getTimestamp())));
				thread.setState(threadStatus.getThreadState().getName());
				if(threadStatus.getThreadState() == ThreadState.BUSY) {
					thread.setProblemInstanceId(threadStatus.getProblemId());
					thread.setTaskId(new BigInteger(String.valueOf(i)));
					thread.setProblemType(threadStatus.getProblemType());
				}
			}
			
			threads.getThread().add(thread);
		}
		
		status.setThreads(threads);
		
		System.out.println(MessageString.toString(status));
		
		return status;
	}
	
	@Override
	public void terminate() {
		super.terminate();
		taskSolverModule.terminate();
	}
}
