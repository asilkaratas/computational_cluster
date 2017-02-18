package com.computationalcluster.tasksolvers.dvrp;

import pl.edu.pw.mini.se2.TaskSolver;
import pl.edu.pw.mini.se2.TaskSolverCreator;

public class DVRPTaskSolverCreator extends TaskSolverCreator {

	@Override
	public TaskSolver getTaskSolverInstance(byte[] data) {
		return new DVRPTaskSolver(data);
	}

}
