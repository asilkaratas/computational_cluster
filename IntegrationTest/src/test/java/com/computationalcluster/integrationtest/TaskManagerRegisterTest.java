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

public class TaskManagerRegisterTest {
	
	private static final Logger logger = Logger.getLogger(TaskManagerRegisterTest.class);
	
	private MockCluster mockCluster = null;
	private TaskManager taskManager = null;
	
	@Before
	public void setUp() {
		mockCluster = new MockCluster(4444, 5000);
		taskManager = mockCluster.createTaskManager(3);
	}
	
	
	@Test
	public void validTest() throws InterruptedException {
		mockCluster.startServer();
		taskManager.start();
		
		Thread.sleep(MockCluster.THREAD_SLEEP_TIME);
		assertEquals(1, taskManager.getId().intValue());
		
	}
	
}
