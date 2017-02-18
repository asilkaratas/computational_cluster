package com.computationalcluster.computationalnode.tasksolver;

import java.math.BigInteger;

import pl.edu.pw.mini.se2.TaskSolver;

import com.computationalcluster.common.module.tasksolver.SolverTask;

public class SolveTask implements SolverTask {

	private final TaskSolver taskSolver;
	private final byte[] data;
	private final SolveCallback callback;
	private final long timeout;
	private final BigInteger taskId;
	
	public SolveTask(TaskSolver taskSolver, BigInteger taskId, byte[] data, long timeout, SolveCallback callback){
		this.taskSolver = taskSolver;
		this.taskId = taskId;
		this.data = data;
		this.timeout = timeout;
		this.callback = callback;
	}
	
	@Override
	public void run() {
		final long timeStamp = System.currentTimeMillis();
		
		final byte[] solution = taskSolver.Solve(data, timeout);
		
		final long now = System.currentTimeMillis();
		final long computationTime = now - timeStamp;
		
		final BigInteger computationTimeBig = new BigInteger(String.valueOf(computationTime));
		
		final boolean timeoutOccured = computationTime < timeout;
		
		callback.onComplete(taskId, solution, computationTimeBig, timeoutOccured);
		
	}

}
