package com.computationalcluster.taskmanager.tasksolver;

import com.computationalcluster.common.module.tasksolver.SolverTask;

import pl.edu.pw.mini.se2.TaskSolver;

public class MergeTask implements SolverTask {
	private final TaskSolver taskSolver;
	private final byte[][] solutions;
	private final MergeCallback callback;
	
	public MergeTask(TaskSolver taskSolver, byte[][] solutions, MergeCallback callback){
		this.taskSolver = taskSolver;
		this.solutions = solutions;
		this.callback = callback;
	}
	
	@Override
	public void run() {
		final byte[] solution = taskSolver.MergeSolution(solutions);
		callback.onComplete(solution);
	}
}
