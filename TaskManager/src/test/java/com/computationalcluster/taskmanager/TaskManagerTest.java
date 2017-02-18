package com.computationalcluster.taskmanager;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.messages.DivideProblem;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Status;

public class TaskManagerTest {
	
	private TaskManager taskManager = null;
	private CommunicationProxy proxy = null;
	private TaskSolverModule taskSolverModule = null;
	
	@Before
	public void setUp(){
		proxy = mock(CommunicationProxy.class);
		taskSolverModule = mock(TaskSolverModule.class);
		
		taskManager = new TaskManager(proxy, taskSolverModule);
	}
	
	@After
	public void tearDown(){
		
	}
	
	@Test
	public void registerMessageTest() {
		Register register = taskManager.getRegister();
		
		assertNotNull(register);
		verify(taskSolverModule, times(1)).getThreadCount();
	}
	
	@Test
	public void statusMessageTest() {
		Status status = taskManager.getStatus();
		
		assertNotNull(status);
	}
	
	@Test
	public void handlersTest() {
		
		assertTrue(taskManager.getMessageProcessingModule().hasMessageHandler(RegisterResponse.class));
		assertTrue(taskManager.getMessageProcessingModule().hasMessageHandler(DivideProblem.class));
		assertTrue(taskManager.getMessageProcessingModule().hasMessageHandler(Solutions.class));
		assertTrue(taskManager.getMessageProcessingModule().hasMessageHandler(NoOperation.class));
	}
}
