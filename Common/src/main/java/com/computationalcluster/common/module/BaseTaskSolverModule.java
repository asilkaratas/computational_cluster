package com.computationalcluster.common.module;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

import com.computationalcluster.common.enums.ThreadState;
import com.computationalcluster.common.module.tasksolver.SolverThread;
import com.computationalcluster.common.module.tasksolver.SolverThreadStatus;
import com.computationalcluster.common.module.tasksolver.TaskSolverFactory;

public class BaseTaskSolverModule {
	private static final Logger logger = Logger.getLogger(BaseTaskSolverModule.class);
	
	private final TaskSolverFactory taskSolverFactory;
	private int threadCount;
	private final List<SolverThread> threads;
	private final List<SolverThread> idleThreads;
	
	private final Semaphore availableThreads;
	
	public BaseTaskSolverModule(int threadCount) {
		this(threadCount, new TaskSolverFactory());
	}
	
	public BaseTaskSolverModule(int threadCount, TaskSolverFactory taskSolverFactory) {
		this.threadCount = threadCount;
		this.taskSolverFactory = taskSolverFactory;
		
		availableThreads = new Semaphore(threadCount, true);
		
		threads = new ArrayList<SolverThread>();
		idleThreads = new ArrayList<SolverThread>();
		for(int i = 0; i < threadCount; i++){
			final SolverThread solverThread = new SolverThread(i, this);
			threads.add(solverThread);
		}
	}
	
	public void start() {
		for(int i = 0; i < threadCount; i++){
			final SolverThread solverThread = threads.get(i);
			solverThread.start();
			idleThreads.add(solverThread);
		}
	}
	
	public TaskSolverFactory getTaskSolverFactory() {
		return taskSolverFactory;
	}
	
	public List<SolverThread> getThreads() {
		return threads;
	}
	
	public int getThreadCount() {
		return threadCount;
	}
	
	public SolverThread getIdleSolverThread() {
		try {
			availableThreads.acquire();
			 
			synchronized (idleThreads) {
				final SolverThread solverThread = idleThreads.remove(0);
				final SolverThreadStatus status = solverThread.getStatus();
				synchronized (status) {
					status.setThreadState(ThreadState.BUSY);
				}
				
				return solverThread;
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
		
		return null;
	}
	
	public void putIdleSolverThread(SolverThread solverThread) {
		synchronized (idleThreads) {
			final SolverThreadStatus status = solverThread.getStatus();
			synchronized (status) {
				status.setThreadState(ThreadState.IDLE);
			}
			idleThreads.add(solverThread);
			availableThreads.release();
		}
	}
	
	public void terminate() {
		for(int i = 0; i < threadCount; i++){
			final SolverThread solverThread = threads.get(i);
			solverThread.terminate();
		}
	}
	
}
