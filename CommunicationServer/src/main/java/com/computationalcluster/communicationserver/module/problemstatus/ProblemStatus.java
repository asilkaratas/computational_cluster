package com.computationalcluster.communicationserver.module.problemstatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems.PartialProblem;
import com.computationalcluster.common.messages.SolveRequest;

public class ProblemStatus {
	private static final Logger logger = Logger.getLogger(ProblemStatus.class);
	
	private final BigInteger problemId;
	private final HashMap<BigInteger, PartialProblemStatus> partialProblemStatusMap;
	
	private SolveRequest solveRequest = null;
	private SolvePartialProblems solvePartialProblems = null;
	private Solutions partialSolutions = null;
	private Solutions finalSolution = null;
	private int totalProblems = 0;
	
	public ProblemStatus(BigInteger problemId) {
		this.problemId = problemId;
		partialProblemStatusMap = new HashMap<>();
	}
	
	public int getTotalProblems() {
		return totalProblems;
	}
	
	public void setSolveRequest(SolveRequest solveRequest) {
		this.solveRequest = solveRequest;
	}
	
	public Solutions getPartialSolutions() {
		return partialSolutions;
	}
	
	public Solutions getFinalSolution() {
		return finalSolution;
	}
	
	public void setFinalSolution(Solutions finalSolution) {
		this.finalSolution = finalSolution;
	}
	
	public SolveRequest getSolveRequest() {
		return solveRequest;
	}
	
	public SolvePartialProblems getSolvePartialProblems() {
		return solvePartialProblems;
	}
	
	public void setSolvePartialProblems(SolvePartialProblems solvePartialProblems) {
		this.solvePartialProblems = solvePartialProblems;
		this.partialSolutions = null;
		this.finalSolution = null;
		this.partialProblemStatusMap.clear();
		
		final List<PartialProblem> partialProblemList = solvePartialProblems.getPartialProblems().getPartialProblem();
		totalProblems = partialProblemList.size();
		
		for(int i = 0; i < totalProblems; i++) {
			final PartialProblem partialProblem = partialProblemList.get(i);
			final PartialProblemStatus partialProblemStatus = new PartialProblemStatus(partialProblem.getTaskId());
			partialProblemStatus.setPartialProblem(partialProblem);
			
			partialProblemStatusMap.put(partialProblem.getTaskId(), partialProblemStatus);
		}
	}
	
	public BigInteger getProblemId() {
		return problemId;
	}
	
	public boolean allProblemsSolved() {
		return getSolvedPartialProblems().size() == partialProblemStatusMap.size();
	}
	
	public List<PartialProblemStatus> getSolvedPartialProblems() {
		final List<PartialProblemStatus> unsolvedList = new ArrayList<>();
		
		final Iterator<Entry<BigInteger, PartialProblemStatus>> it = partialProblemStatusMap.entrySet().iterator();
	    while (it.hasNext()) {
	    	final Entry<BigInteger, PartialProblemStatus> pair = it.next();
	    	final PartialProblemStatus status = pair.getValue();
	        logger.info("status:" +status.getState());
	        if(status.getState().equals(PartialProblemState.SOLVED)) {
				unsolvedList.add(status);
			}
	    }
		
		return unsolvedList;
	}
	
	public List<PartialProblemStatus> getUnsolvedPartialProblems() {
		List<PartialProblemStatus> unsolvedList = new ArrayList<>();
		
		Iterator<Entry<BigInteger, PartialProblemStatus>> it = partialProblemStatusMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<BigInteger, PartialProblemStatus> pair = it.next();
	        PartialProblemStatus status = pair.getValue();
	        if(status.getState().equals(PartialProblemState.UNSOLVED)) {
				unsolvedList.add(status);
			}
	    }
		
		return unsolvedList;
	}
	
	public PartialProblemStatus getPartialProblemStatus(BigInteger taskId) {
		if(partialProblemStatusMap.containsKey(taskId)) {
			return partialProblemStatusMap.get(taskId);
		}
		return null;
	}
	
	public void createPartialSolution() {
		SolutionsList solutionsList = new SolutionsList();
		
		Solutions solutions = new Solutions();
		solutions.setSolutionsList(solutionsList);
		solutions.setCommonData(solveRequest.getData());
		solutions.setId(getProblemId());
		solutions.setProblemType(solveRequest.getProblemType());
		
		Iterator<Entry<BigInteger, PartialProblemStatus>> it = partialProblemStatusMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry<BigInteger, PartialProblemStatus> pair = it.next();
	        PartialProblemStatus status = pair.getValue();
	        
	        solutionsList.getSolution().add(status.getPartialSolution());
	    }
	    
	    this.partialSolutions = solutions;
	}
	
	
}
