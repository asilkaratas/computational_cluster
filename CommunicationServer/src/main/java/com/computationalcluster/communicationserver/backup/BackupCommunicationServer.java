package com.computationalcluster.communicationserver.backup;

import java.math.BigInteger;
import java.util.List;

import com.computationalcluster.common.component.ClientComponent;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ThreadState;
import com.computationalcluster.common.messages.ComponentType;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.Register.Type;
import com.computationalcluster.common.messages.Status.Threads;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.module.tasksolver.SolverThread;
import com.computationalcluster.common.module.tasksolver.SolverThreadStatus;
import com.computationalcluster.common.utils.MessageString;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.backup.messagehandler.NoOperationHandler;
import com.computationalcluster.communicationserver.backup.messagehandler.RegisterResponseHandler;

public class BackupCommunicationServer extends ClientComponent {
	
	private final CommunicationServer communicationServer;
	
	public BackupCommunicationServer(CommunicationServer communicationServer, CommunicationProxy proxy) {
		super(ClientComponentType.COMMUNICATION_SERVER, proxy);
		this.communicationServer = communicationServer;
		
		getMessageProcessingModule().addMessageHandler(RegisterResponse.class, new RegisterResponseHandler(this));
		getMessageProcessingModule().addMessageHandler(NoOperation.class, new NoOperationHandler(this));
	}
	
	@Override
	public Register getRegister() {
		System.out.println("Register message is sent");
		
		final Type type = new Type();
		type.setValue(ComponentType.COMMUNICATION_SERVER);
		type.setPort(communicationServer.getServerConfig().getPort());
		
		final Register register = new Register();
		register.setType(type);
		
		return register;
	}
	
	@Override
	public Status getStatus() {
		final Status status = new Status();
		status.setId(getId());
		
		final int threadCount = 1;
		
		final long now = System.currentTimeMillis();
		
		final Threads threads = new Threads();
		
		for(int i = 0; i < threadCount; i++){
			final Threads.Thread thread = new Threads.Thread();
			
			thread.setHowLong(new BigInteger("10000"));
			thread.setState(ThreadState.IDLE.getName());
			threads.getThread().add(thread);
		}
		
		status.setThreads(threads);
		
		System.out.println(MessageString.toString(status));
		
		return status;
	}
}
