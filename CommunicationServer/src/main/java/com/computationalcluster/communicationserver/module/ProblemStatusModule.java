package com.computationalcluster.communicationserver.module;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.module.problemstatus.ProblemStatus;

public class ProblemStatusModule {
	
	private final CommunicationServer communicationServer;
	private final HashMap<BigInteger, ProblemStatus> problemStatusMap;
	
	private BigInteger problemCount = null;
	
	public ProblemStatusModule(CommunicationServer communicationServer) {
		this.communicationServer = communicationServer;
		problemCount = new BigInteger("0");
		problemStatusMap = new HashMap<>();
	}
	
	public BigInteger getNextProblemId() {
		problemCount = problemCount.add(BigInteger.ONE);
		return new BigInteger(problemCount.toByteArray());
	}
	
	public void addProblemStatus(ProblemStatus problemStatus) {
		problemStatusMap.put(problemStatus.getProblemId(), problemStatus);
	}
	
	public ProblemStatus getProblemStatus(BigInteger problemId) {
		if(problemStatusMap.containsKey(problemId)) {
			return problemStatusMap.get(problemId);
		}
		return null;
	}
	
	public List<ProblemStatus> getProblemStatusList() {
		final List<ProblemStatus> list = new ArrayList<ProblemStatus>(problemStatusMap.values());
		return list;
	}
	
}
