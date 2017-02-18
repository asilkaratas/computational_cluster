package com.computationalcluster.taskmanager.tasksolver;

import pl.edu.pw.mini.se2.TaskSolver;

import com.computationalcluster.common.module.tasksolver.SolverTask;

public class DivideTask implements SolverTask {
	private final TaskSolver taskSolver;
	private final int threadCount;
	private final DivideCallback callback;
	
	public DivideTask(TaskSolver taskSolver, int threadCount, DivideCallback callback){
		this.taskSolver = taskSolver;
		this.threadCount = threadCount;
		this.callback = callback;
	}
	
	@Override
	public void run() {
		final byte[][] partialProblems = taskSolver.DivideProblem(threadCount);
		callback.onComplete(partialProblems);
	}
	
}
