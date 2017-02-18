package com.computationalcluster.integrationtest;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;
import com.computationalcluster.computationalclient.ComputationalClient;
import com.computationalcluster.computationalnode.ComputationalNode;
import com.computationalcluster.integrationtest.helper.MockCluster;
import com.computationalcluster.taskmanager.TaskManager;

public class MultipleComponentRegisterTest {
	
	private static final Logger logger = Logger.getLogger(MultipleComponentRegisterTest.class);
	
	private MockCluster mockCluster = null;
	private ComputationalNode computationalNode = null;
	private TaskManager taskManager = null;
	
	@Before
	public void setUp() {
		mockCluster = new MockCluster(4444, 5000);
		computationalNode = mockCluster.createComputationalNode(3);
		taskManager = mockCluster.createTaskManager(2);
	}
	
	
	@Test
	public void validTest() throws InterruptedException {
		mockCluster.startServer();
		computationalNode.start();
		taskManager.start();
		
		Thread.sleep(MockCluster.THREAD_SLEEP_TIME);
		assertEquals(1, computationalNode.getId().intValue());
		assertEquals(2, taskManager.getId().intValue());
	}
	
}
