package com.computationalcluster.communicationserver.messagehandler;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.constants.ErrorMessage;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ErrorType;
import com.computationalcluster.common.enums.ThreadState;
import com.computationalcluster.common.messagehandler.ClientMessageHandler;
import com.computationalcluster.common.messages.Error;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.messages.Status.Threads.Thread;
import com.computationalcluster.common.messagevalidator.MessageValidatorUtil;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;
import com.computationalcluster.communicationserver.module.componentstatus.ThreadStatus;

public class StatusHandler implements ClientMessageHandler{
	private static final Logger logger = Logger.getLogger(StatusHandler.class);
	
	private final CommunicationServer communicationServer;
	
	public StatusHandler(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
	}
	
	@Override
	public void handle(ClientMessage clientMessage) {
		final Status status = (Status)clientMessage.getMessage();
		final BigInteger clientId = clientMessage.getClientId();
		
		final ComponentStatusModule componentStatusModule = communicationServer.getComponentStatusModule();
		final ComponentStatus componentStatus = componentStatusModule.getComponent(status.getId());
		
		if(componentStatus == null) {
			sendUnknownSenderError(clientId);
			return;
		}
		
		System.out.println("Status message recieved from clientId:" + clientId + " type:" + componentStatus.getClientComponentType().getName());
		
		if(status.getThreads().getThread().size() != componentStatus.getThreadCount()) {
			sendInvalidThreadCountError(clientId);
			return;
		}
		
		synchronized (componentStatus) {
			final List<Thread> threadList = status.getThreads().getThread();
			final List<ThreadStatus> threadStatusList = componentStatus.getThreadStatusList();
			for(int i = 0; i < threadList.size(); i++) {
				final Thread thread = threadList.get(i);
				final ThreadStatus threadStatus = threadStatusList.get(i);
				
				threadStatus.setHowLong(thread.getHowLong());
				threadStatus.setProblemInstanceId(thread.getProblemInstanceId());
				threadStatus.setTaskId(thread.getTaskId());
				threadStatus.setThreadState(ThreadState.fromName(thread.getState()));
				threadStatus.setProblemType(thread.getProblemType());
			}
			
			componentStatusModule.updateComponentStatus(componentStatus);
		}
		
		notifyForWaitingMessages(componentStatus.getClientComponentType());
		
		logger.info("No operation message sent to id:" + clientMessage.getClientId());
		final NoOperation noOperation = new NoOperation();
		final ClientMessage responseMessage = new ClientMessage(clientMessage.getClientId(), noOperation);
		communicationServer.getProxy().sendMessage(responseMessage);
		
	}
	
	private void notifyForWaitingMessages(ClientComponentType clientComponentType) {
		switch(clientComponentType){
			case TASK_MANAGER:
				communicationServer.getTaskManagerModule().notifyQueue();
				break;
				
			case COMPUTATIONAL_NODE:
				communicationServer.getComputationalNodeModule().notifyQueue();
				break;
		default:
			break;
		}
	}
	
	
	private void sendInvalidThreadCountError(BigInteger clientId) {
		final Error error = new Error();
		error.setErrorType(ErrorType.INVALID_OPERATION.getName());
		error.setErrorMessage(ErrorMessage.STATUS_HANDLER_INVALID_THREAD_COUNT);
		
		logger.error(error.getErrorMessage());
		
		final ClientMessage responseMessage = new ClientMessage(clientId, error);
		communicationServer.getProxy().sendMessage(responseMessage);
	}
	
	private void sendUnknownSenderError(BigInteger clientId) {
		final Error error = new Error();
		error.setErrorType(ErrorType.UNKNOWN_SENDER.getName());
		error.setErrorMessage(ErrorMessage.STATUS_HANDLER_UNKNOWN_SENDER);
		
		logger.error(error.getErrorMessage());
		
		final ClientMessage responseMessage = new ClientMessage(clientId, error);
		communicationServer.getProxy().sendMessage(responseMessage);
	}

}
