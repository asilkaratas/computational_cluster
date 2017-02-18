package com.computationalcluster.taskmanager;

import java.math.BigInteger;

import org.apache.log4j.Logger;

import pl.edu.pw.mini.se2.TaskSolver;

import com.computationalcluster.common.enums.TaskType;
import com.computationalcluster.common.module.BaseTaskSolverModule;
import com.computationalcluster.common.module.tasksolver.SolverThread;
import com.computationalcluster.common.module.tasksolver.SolverThreadStatus;
import com.computationalcluster.taskmanager.tasksolver.DivideCallback;
import com.computationalcluster.taskmanager.tasksolver.DivideTask;
import com.computationalcluster.taskmanager.tasksolver.MergeCallback;
import com.computationalcluster.taskmanager.tasksolver.MergeTask;

public class TaskSolverModule extends BaseTaskSolverModule {
	private static final Logger logger = Logger.getLogger(TaskSolverModule.class);
	
	public TaskSolverModule(int threadCount) {
		super(threadCount);
	}
	
	public void divide(BigInteger problemId, byte[] data, int threadCount, String problemType, DivideCallback callback) {
		logger.debug("divide");
		final TaskSolver taskSolver = getTaskSolverFactory().getTaskSolverInstance(data, problemType);
		logger.debug("taskSolver:" + taskSolver.getName());
		
		final DivideTask divideTask = new DivideTask(taskSolver, threadCount, callback);
		
		final SolverThread solverThread = getIdleSolverThread();
		final SolverThreadStatus status = solverThread.getStatus();
		synchronized (status) {
			status.setProblemId(problemId);
			status.setProblemType(problemType);
			status.setTaskType(TaskType.DIVIDE);
		}
		solverThread.setSolverTask(divideTask);
	}
	
	public void mergeSolution(BigInteger problemId, byte[][] solutions, byte[] commonData, String problemType, MergeCallback callback) {
		logger.debug("mergeSolution");
		final TaskSolver taskSolver = getTaskSolverFactory().getTaskSolverInstance(commonData, problemType);
		logger.debug("taskSolver:" + taskSolver);
		
		final MergeTask mergeTask = new MergeTask(taskSolver, solutions, callback);
		final SolverThread solverThread = getIdleSolverThread();
		final SolverThreadStatus status = solverThread.getStatus();
		synchronized (status) {
			status.setProblemId(problemId);
			status.setProblemType(problemType);
			status.setTaskType(TaskType.MERGE);
		}
		solverThread.setSolverTask(mergeTask);
	}
	
	
}
