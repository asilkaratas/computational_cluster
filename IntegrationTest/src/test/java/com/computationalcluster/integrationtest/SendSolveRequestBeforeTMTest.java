package com.computationalcluster.integrationtest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.computationalclient.ComputationalClient;
import com.computationalcluster.integrationtest.helper.MockCluster;
import com.computationalcluster.taskmanager.TaskManager;

public class SendSolveRequestBeforeTMTest {
	
	//private static final Logger logger = Logger.getLogger(SendSolveRequestTest.class);
	
	private MockCluster mockCluster = null;
	private ComputationalClient computationalClient = null;
	private TaskManager taskManager = null;
	
	@Before
	public void setUp() {
		mockCluster = new MockCluster(4444, 5000);
		taskManager = mockCluster.createTaskManager(3);
		computationalClient = mockCluster.createComputationalClient();
	}
	
	
	@Test
	public void validTest() throws InterruptedException {
		mockCluster.startServer();
		computationalClient.sendSolveRequest();
		
		Thread.sleep(MockCluster.THREAD_SLEEP_TIME);
		
		ProblemStatusModule problemStatusModule = mockCluster.getCommunicationServer().getProblemStatusModule();
		assertEquals(1, problemStatusModule.getProblemStatusList().size());
		assertNull(problemStatusModule.getProblemStatusList().get(0).getSolvePartialProblems());
		
		Thread.sleep(MockCluster.THREAD_SLEEP_TIME);
		
		taskManager.start();
		
		Thread.sleep(4000);
		assertEquals(1, taskManager.getId().intValue());
		assertNotNull(problemStatusModule.getProblemStatusList().get(0).getSolvePartialProblems());
		
		
	}
	
}
