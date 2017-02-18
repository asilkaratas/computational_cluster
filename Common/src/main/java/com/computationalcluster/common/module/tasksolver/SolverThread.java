package com.computationalcluster.common.module.tasksolver;


import org.apache.log4j.Logger;

import com.computationalcluster.common.module.BaseTaskSolverModule;
import com.computationalcluster.common.module.TerminableThread;

public class SolverThread extends TerminableThread {
	private static final Logger logger = Logger.getLogger(SolverThread.class);
	
	private final BaseTaskSolverModule taskSolverModule;
	private final Object lock = new Object();
	
	private SolverThreadStatus status = null;
	private SolverTask task = null;
	
	public SolverThread(int id, BaseTaskSolverModule taskSolverModule) {
		setName("SolverThread-" + id);
		this.taskSolverModule = taskSolverModule;
		
		status = new SolverThreadStatus();
	}
	
	public SolverThreadStatus getStatus() {
		return status;
	}
	
	public void setStatus(SolverThreadStatus status) {
		this.status = status;
	}
	
	public void setSolverTask(SolverTask task){
		logger.info("setSolverTask...");
		synchronized (lock) {			
			this.task = task;
			
			try{
                lock.notify();                      
            }catch (Exception e) {
            	logger.error(e.getMessage());
            }
		}
	}
	
	public void run() {
		while(running) {
			synchronized (lock) {
                try{
                	logger.info("waiting...");
                	lock.wait();
                	logger.info("running");
                	task.run();
                	logger.info("done");
                	taskSolverModule.putIdleSolverThread(this);
                }catch (Exception e) {
                	logger.error(e.getMessage());
                }
            }
		}
	}
	
}