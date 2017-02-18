package com.computationalcluster.integrationtest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.computationalclient.ComputationalClient;
import com.computationalcluster.computationalnode.ComputationalNode;
import com.computationalcluster.integrationtest.helper.MockCluster;
import com.computationalcluster.taskmanager.TaskManager;

public class SendSolveRequestAfterTMandCNTest {
	
	//private static final Logger logger = Logger.getLogger(SendSolveRequestTest.class);
	
	private MockCluster mockCluster = null;
	private ComputationalClient computationalClient = null;
	private TaskManager taskManager = null;
	private ComputationalNode computationalNode = null;
	
	@Before
	public void setUp() {
		mockCluster = new MockCluster(4444, 5000);
		taskManager = mockCluster.createTaskManager(3);
		computationalNode = mockCluster.createComputationalNode(2);
		computationalClient = mockCluster.createComputationalClient();
	}
	
	
	@Test
	public void validTest() throws InterruptedException {
		mockCluster.startServer();
		taskManager.start();
		computationalNode.start();
		computationalClient.sendSolveRequest();
		
		Thread.sleep(MockCluster.THREAD_SLEEP_TIME);
		
		assertEquals(1, taskManager.getId().intValue());
		assertEquals(2, computationalNode.getId().intValue());
		assertEquals(3, computationalClient.getId().intValue());
		
		ProblemStatusModule problemStatusModule = mockCluster.getCommunicationServer().getProblemStatusModule();
		assertEquals(1, problemStatusModule.getProblemStatusList().size());
		assertNull(problemStatusModule.getProblemStatusList().get(0).getSolvePartialProblems());
		
		
		Thread.sleep(2000);
		assertNotNull(problemStatusModule.getProblemStatusList().get(0).getSolvePartialProblems());
	
		
		//Thread.sleep(500000);
	}
	
}
