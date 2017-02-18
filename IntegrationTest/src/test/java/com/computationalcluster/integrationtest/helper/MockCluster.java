package com.computationalcluster.integrationtest.helper;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.computationalcluster.common.component.ClientComponent;
import com.computationalcluster.common.config.ServerConfig;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ProblemType;
import com.computationalcluster.common.queue.ClientMessageQueue;
import com.computationalcluster.common.utils.IdGenerator;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.CommunicationServerProxy;
import com.computationalcluster.computationalclient.ComputationalClient;
import com.computationalcluster.computationalnode.ComputationalNode;
import com.computationalcluster.computationalnode.NodeTaskSolverModule;
import com.computationalcluster.taskmanager.TaskManager;
import com.computationalcluster.taskmanager.TaskSolverModule;
import com.computationalcluster.tasksolvers.dvrp.DVRPTaskSolverCreator;

public class MockCluster {
	private static final Logger logger = Logger.getLogger(MockCluster.class);
	
	public static final int THREAD_SLEEP_TIME = 100;
	
	private IdGenerator idGenerator = null;
	private HashMap<BigInteger, CommunicationProxy> proxyMap = null;
	
	private CommunicationServer communicationServer = null;
	
	public MockCluster(int port, int timeout) {
		idGenerator = new IdGenerator();
		proxyMap = new HashMap<>();
		communicationServer = createCommunicationServer(port, timeout);
	}
	
	public void startServer() {
		communicationServer.start();
	}
	
	public CommunicationServer getCommunicationServer() {
		return communicationServer;
	}
	
	private void addComponent(ClientComponent component) {
		component.setId(idGenerator.getNextId());
		proxyMap.put(component.getId(), component.getProxy());
	}
	
	private CommunicationProxy createMockProxy(CommunicationServer communicationServer) {
		//StartAnswer startAnswer = new StartAnswer();
		SendMessageAnswer sendMessageAnswer = new SendMessageAnswer(communicationServer);
		
		ClientMessageQueue inputQueue = new ClientMessageQueue();
		CommunicationProxy proxy = mock(CommunicationProxy.class);
		when(proxy.getInputQueue()).thenReturn(inputQueue);
		doAnswer(sendMessageAnswer).when(proxy).sendMessage(any(ClientMessage.class));
		//doAnswer(startAnswer).when(proxy).start();
		
		return proxy;
	}
	
	public TaskManager createTaskManager(int threadCount) {
		CommunicationProxy proxy = createMockProxy(communicationServer);
		
		TaskSolverModule taskSolverModule = new TaskSolverModule(threadCount);
		taskSolverModule.getTaskSolverFactory().addTaskSolverCreator(ProblemType.TSP.getName(), new DVRPTaskSolverCreator());
		
		TaskManager taskManager = new TaskManager(proxy, taskSolverModule);
		addComponent(taskManager);
		
		return taskManager;
	}
	
	public ComputationalNode createComputationalNode(int threadCount) {
		CommunicationProxy proxy = createMockProxy(communicationServer);
		
		NodeTaskSolverModule taskSolverModule = new NodeTaskSolverModule(threadCount);
		taskSolverModule.getTaskSolverFactory().addTaskSolverCreator(ProblemType.TSP.getName(), new DVRPTaskSolverCreator());
		
		ComputationalNode computationalNode = new ComputationalNode(proxy, taskSolverModule);
		addComponent(computationalNode);
		
		return computationalNode;
	}
	
	public ComputationalClient createComputationalClient() {
		CommunicationProxy proxy = createMockProxy(communicationServer);
		
		ComputationalClient computationalClient = new ComputationalClient(proxy);
		addComponent(computationalClient);
		
		return computationalClient;
	}
	
	
	private CommunicationServer createCommunicationServer(int port, int timeout) {
		Answer<Void> sendMessageAnswer = new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocation) {
				ClientMessage clientMessage = (ClientMessage)invocation.getArguments()[0];
				//logger.info("clientId:" + clientMessage.getClientId());
				if(proxyMap.containsKey(clientMessage.getClientId())) {
					CommunicationProxy proxy = proxyMap.get(clientMessage.getClientId());
					//logger.info("messageQueue:" + messageQueue);
					proxy.getInputQueue().addMessage(clientMessage);
				}
				
				return null;
			}
		};
		
		ClientMessageQueue inputQueue = new ClientMessageQueue();
		CommunicationServerProxy serverProxy = mock(CommunicationServerProxy.class);
		when(serverProxy.getInputQueue()).thenReturn(inputQueue);
		doAnswer(sendMessageAnswer).when(serverProxy).sendMessage(any(ClientMessage.class));
		
		ServerConfig serverConfig = new ServerConfig("CommunicationServer", port, timeout);
		CommunicationServer communicationServer = new CommunicationServer(serverConfig, serverProxy);
		
		return communicationServer;
	}
	
	
	public void terminateComponent(ClientComponent clientComponent) {
		if(proxyMap.containsKey(clientComponent.getId())){
			proxyMap.remove(clientComponent.getId());
		}
		
		clientComponent.terminate();
	}
}
