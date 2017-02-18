package com.computationalcluster.taskmanager;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.computationalcluster.common.enums.ProblemType;
import com.computationalcluster.taskmanager.tasksolver.DivideCallback;
import com.computationalcluster.taskmanager.tasksolver.MergeCallback;
import com.computationalcluster.tasksolvers.dvrp.DVRPTaskSolverCreator;


public class TaskSolverModuleTest {
	private static final Logger logger = Logger.getLogger(TaskSolverModuleTest.class);
	
	private TaskSolverModule taskSolverModule = null;
	
	private DivideCallback divideCallback = null;
	private MergeCallback mergeCallback = null;
	
	@Before
	public void setUp() {
		
		divideCallback = mock(DivideCallback.class);
		mergeCallback = mock(MergeCallback.class);
		
		taskSolverModule = new TaskSolverModule(4);
		taskSolverModule.getTaskSolverFactory().addTaskSolverCreator(ProblemType.TSP.getName(), new DVRPTaskSolverCreator());
		taskSolverModule.start();
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void divideTest() throws InterruptedException {
		logger.info("divideTest");
		int threadCount = 3;
		String problemType = ProblemType.TSP.getName();
		byte[] data = null;
		BigInteger problemId = new BigInteger("1");
		
		taskSolverModule.divide(problemId, data, threadCount, problemType, divideCallback);
		
		Thread.sleep(2000);
		
		verify(divideCallback).onComplete(isA(byte[][].class));
		//verify(divideCallback).onComplete(is);
	}
	
	@Test
	public void mergeSolutionTest() throws InterruptedException {
		logger.info("mergeTest");
		String problemType = ProblemType.TSP.getName();
		byte[][] solutions = null;
		byte[] commonData = null;
		BigInteger problemId = new BigInteger("1");
		
		taskSolverModule.mergeSolution(problemId, solutions, commonData, problemType, mergeCallback);
		//(null, threadCount, problemType, mergeCallback);
		
		Thread.sleep(2000);
		
		verify(mergeCallback).onComplete(isA(byte[].class));
		//verify(divideCallback).onComplete(is);
	}
}
