package com.computationalcluster.tasksolvers.dvrp;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import pl.edu.pw.mini.se2.TaskSolver;
import pl.edu.pw.mini.se2.TaskSolverState;



public class DVRPTaskSolver extends TaskSolver {
	private static final Logger logger = Logger.getLogger(DVRPTaskSolver.class);
	
	private final byte[] problemData;
	private String name = "DVRP-16-en-10";
	
	public DVRPTaskSolver(byte[] problemData) {
		super(problemData);
		this.problemData = problemData;
	}

	@Override
	public byte[][] DivideProblem(int threadCount) {
		final byte[] decodedData = Base64.decodeBase64(problemData);
		final String dataStr = new String(decodedData);
		
		final byte[][] partialProblems = new byte[threadCount][];
		
		
		for(int i = 0; i < threadCount; i++) {
			final StringBuilder builder = new StringBuilder();
			builder.append("THREADS: ");
			builder.append(threadCount);
			builder.append("\n");
			builder.append("THREAD_ID: ");
			builder.append(i);
			builder.append("\n");
			builder.append(dataStr);
			
			final byte[] encodedData = Base64.encodeBase64(builder.toString().getBytes());
			partialProblems[i] = encodedData;
		}
		
		return partialProblems;
	}

	@Override
	public byte[] MergeSolution(byte[][] data) {
		
		String bestSolution = null;
		double minDist = Double.MAX_VALUE;
		for(int i = 0; i < data.length; ++i) {
			final byte[] decoded = Base64.decodeBase64(data[i]);
			final String solution = new String(decoded);
			
			final SolutionData solutionData = new SolutionData(solution);
			if(solutionData.getMinDist() < minDist) {
				minDist = solutionData.getMinDist();
				bestSolution = solution;
			}
		}
		
		logger.info("bestSolution:\n" + bestSolution);
		
		final byte[] encoded = Base64.encodeBase64(bestSolution.getBytes());
		
		return encoded;
	}

	@Override
	public byte[] Solve(byte[] data, long timeout) {
		final byte[] decodedData = Base64.decodeBase64(data);
		
		final String dataStr = new String(decodedData);
		final ProblemData problemData = new ProblemData(dataStr);
		
		logger.info("problemData:\n" + problemData);
		
		final long timeMax = 400 + problemData.getNumVehicles() * 20;
		
		final ProblemInstance problemInstance = new ProblemInstance();
		problemInstance.setId(problemData.getThreadId());
		problemInstance.setCountProc(problemData.getThreads());
		problemInstance.setAvailabilityTimes(problemData.getAvabilityTimes());
		problemInstance.setLoadingTimes(problemData.getLoadingTimes());
		problemInstance.setRequests(problemData.getRequests());
		problemInstance.setLocations(problemData.getLocations());
		problemInstance.setNumVehicle(problemData.getNumVehicles());
		problemInstance.setTimeMax(timeMax);
		problemInstance.run();
	
		
		final StringBuilder builder = new StringBuilder();
		
		builder.append("MIN_DIST: ");
		builder.append(problemInstance.getMinDist());
		builder.append("\nBEST_DIVISIONS:\n");
		
		for(List<Integer> division : problemInstance.getBestDivision()) {
			for(int i = 0; i < division.size(); ++i) {
				builder.append(division.get(i) + 1);
				if(i < division.size() - 1) {
					builder.append(",");
				}else {
					builder.append("\n");
				}
			}
		}
		
		final byte[] solution = builder.toString().getBytes();
		final byte[] encoded = Base64.encodeBase64(solution);
		
		return encoded;
	}

	@Override
	public Exception getException() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public TaskSolverState getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void setException(Exception e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setName(String name) {
		this.name = name;
	}

	@Override
	protected void setState(TaskSolverState state) {
		// TODO Auto-generated method stub
		
	}
	
}
