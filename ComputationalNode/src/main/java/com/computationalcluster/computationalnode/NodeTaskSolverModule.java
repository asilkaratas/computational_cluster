package com.computationalcluster.computationalnode;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import pl.edu.pw.mini.se2.TaskSolver;

import com.computationalcluster.common.enums.TaskType;
import com.computationalcluster.common.module.BaseTaskSolverModule;
import com.computationalcluster.common.module.tasksolver.SolverThread;
import com.computationalcluster.common.module.tasksolver.SolverThreadStatus;
import com.computationalcluster.computationalnode.tasksolver.SolveCallback;
import com.computationalcluster.computationalnode.tasksolver.SolveTask;

public class NodeTaskSolverModule extends BaseTaskSolverModule {
	private static final Logger logger = Logger.getLogger(NodeTaskSolverModule.class);
	
	public NodeTaskSolverModule(int threadCount) {
		super(threadCount);
	}
	
	public void solve(BigInteger problemId, BigInteger taskId, byte[] data, String problemType, long timeout, SolveCallback callback){
		logger.debug("solve");
		final TaskSolver taskSolver = getTaskSolverFactory().getTaskSolverInstance(data, problemType);
		logger.debug("taskSolver:" + taskSolver);
		
		final SolveTask solveTask = new SolveTask(taskSolver, taskId, data, timeout, callback);
		
		final SolverThread solverThread = getIdleSolverThread();
		final SolverThreadStatus status = solverThread.getStatus();
		synchronized (status) {
			status.setProblemId(problemId);
			status.setProblemType(problemType);
			status.setTaskType(TaskType.SOLVE);
		}
		solverThread.setSolverTask(solveTask);
	}
	
}
