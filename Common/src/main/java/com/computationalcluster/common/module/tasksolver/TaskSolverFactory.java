package com.computationalcluster.common.module.tasksolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.computationalcluster.common.enums.ProblemType;

import pl.edu.pw.mini.se2.TaskSolver;
import pl.edu.pw.mini.se2.TaskSolverCreator;

public class TaskSolverFactory {
	
	private final HashMap<String, TaskSolverCreator> creatorMap;
	private final TaskSolverLoader taskSolverLoader;
	
	public TaskSolverFactory() {
		creatorMap = new HashMap<>();
		taskSolverLoader = new TaskSolverLoader();
	}
	
	public void addTaskSolverCreator(String problemType, TaskSolverCreator taskSolverCreator) {
		creatorMap.put(problemType, taskSolverCreator);
	}
	
	public TaskSolverCreator getTaskSolverCreator(String problemType) {
		return creatorMap.get(problemType);
	}
	
	public boolean hasProblemType(String problemType) {
		return creatorMap.containsKey(problemType);
	}
	
	public TaskSolver getTaskSolverInstance(byte[] problemData, String problemType) {
		final TaskSolverCreator taskSolverCreator = creatorMap.get(problemType);
		final TaskSolver taskSolver = taskSolverCreator.getTaskSolverInstance(problemData);
		return taskSolver;
	}
	
	public void loadTaskSolvers() {
		List<TaskSolverCreator> taskSolverCreators = taskSolverLoader.load();
		for(TaskSolverCreator creator : taskSolverCreators) {
			addTaskSolverCreator(ProblemType.TSP.getName(), creator);
		}
	}
	
	public List<String> getProblemTypes() {
		final List<String> problemTypes = new ArrayList<>(creatorMap.keySet());
		return problemTypes;
	}
}
