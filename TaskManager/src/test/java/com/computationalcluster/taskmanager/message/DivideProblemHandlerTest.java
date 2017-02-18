package com.computationalcluster.taskmanager.message;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ProblemType;
import com.computationalcluster.common.messages.DivideProblem;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.module.tasksolver.TaskSolverFactory;
import com.computationalcluster.taskmanager.TaskManager;
import com.computationalcluster.taskmanager.TaskSolverModule;
import com.computationalcluster.taskmanager.messagehandler.DivideProblemHandler;
import com.computationalcluster.taskmanager.tasksolver.DivideCallback;

public class DivideProblemHandlerTest {
	
	private CommunicationProxy proxy = null;
	private DivideProblemHandler divideProblemHandler = null;
	private TaskManager taskManager = null;
	private TaskSolverModule taskSolverModule = null;
	private TaskSolverFactory taskSolverFactory = null;
	
	@Before
	public void setUp() {
		
		proxy = mock(CommunicationProxy.class);
		taskSolverFactory = mock(TaskSolverFactory.class);
		when(taskSolverFactory.hasProblemType(any(String.class))).thenReturn(true);
		
		taskSolverModule = mock(TaskSolverModule.class);
		when(taskSolverModule.getTaskSolverFactory()).thenReturn(taskSolverFactory);
		
		taskManager = mock(TaskManager.class);
		when(taskManager.getProxy()).thenReturn(proxy);
		when(taskManager.getTaskSolverModule()).thenReturn(taskSolverModule);
		
		divideProblemHandler = new DivideProblemHandler(taskManager);
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void handleTest() {
		
		RegisterResponse registerResponse = new RegisterResponse();
		registerResponse.setId(new BigInteger("1"));
		registerResponse.setTimeout(50000);
		
		DivideProblem divideProblem = new DivideProblem();
		divideProblem.setData(new byte[100]);
		divideProblem.setProblemType(ProblemType.TSP.getName());
		divideProblem.setComputationalNodes(new BigInteger("3"));
		divideProblem.setId(new BigInteger("1"));
		divideProblem.setNodeID(new BigInteger("1"));
		
		ClientMessage clientMessage = new ClientMessage(divideProblem);
		divideProblemHandler.handle(clientMessage);
		
		verify(taskSolverModule).divide(eq(divideProblem.getId()),
				eq(divideProblem.getData()), 
				eq(divideProblem.getComputationalNodes().intValue()), 
				eq(divideProblem.getProblemType()), 
				isA(DivideCallback.class));
	}
}
