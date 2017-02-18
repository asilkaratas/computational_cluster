package com.computationalcluster.computationalnode.tasksolver;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

import pl.edu.pw.mini.se2.TaskSolver;

public class SolveTaskTest {
	
	private TaskSolver taskSolver = null;
	private SolveCallback callback = null;
	private SolveTask solveTask = null;
	
	private BigInteger taskId = null;
	private long timeout = 0;
	private byte[] solution = null;
	private byte[] data = null;
	
	@Before
	public void setUp() {
		solution = new byte[10];
		data = new byte[10];
		taskId = new BigInteger("1");
		timeout = 10000;
		
		taskSolver = mock(TaskSolver.class);
		when(taskSolver.Solve(any(byte[].class), eq(timeout))).thenReturn(solution);
		
		callback = mock(SolveCallback.class);
		
		solveTask = new SolveTask(taskSolver, taskId, data, timeout, callback);
	}
	
	@Test
	public void runTest() {
		solveTask.run();
		
		verify(taskSolver, times(1)).Solve(eq(data), eq(timeout));
		verify(callback, times(1)).onComplete(eq(taskId), eq(solution), isA(BigInteger.class), isA(Boolean.class));
	}
}
