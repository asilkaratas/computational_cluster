package com.computationalcluster.common.module.tasksolver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.computationalcluster.common.module.BaseTaskSolverModule;



public class SolverThreadTest {
	private static final Logger logger = Logger.getLogger(SolverThreadTest.class);
	
	private SolverThread solverThread = null;
	private SolverTask solverTask = null;
	private BaseTaskSolverModule taskSolverModule = null;
	
	@Before
	public void setUp() {
		
		solverTask = mock(SolverTask.class);
		taskSolverModule = mock(BaseTaskSolverModule.class);
		
		solverThread = new SolverThread(0, taskSolverModule);
		solverThread.start();
		
	}
	
	@After
	public void tearDown(){
		
	}
	/*
	@Test
	public void runTest() throws InterruptedException {
		logger.info("runTest");
		
		solverThread.setSolverTask(solverTask);
		verify(solverTask, times(1)).run();
		solverThread.setSolverTask(solverTask);
		//verify(solverTask, times(2)).run();
		
		Thread.sleep(500);
	}
	*/
}
