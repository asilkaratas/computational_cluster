package com.computationalcluster.computationalclient;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import com.computationalcluster.common.component.ClientComponent;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.enums.ProblemType;
import com.computationalcluster.common.messages.SolutionRequest;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.SolveRequestResponse;
import com.computationalcluster.computationalclient.io.ClientIO;
import com.computationalcluster.computationalclient.messagehandler.SolutionMessageHandler;
import com.computationalcluster.computationalclient.messagehandler.SolveRequestResponseHandler;

public class ComputationalClient extends ClientComponent {
	
	private final Map<BigInteger, ProblemInfo> problemMap;
	private final ClientIO clientIO;
	
	private byte[] problemData = null;
	
	public ComputationalClient(CommunicationProxy proxy) {
		super(ClientComponentType.COMPUTATIONAL_CLIENT, proxy);
		
		problemMap = new HashMap<BigInteger, ProblemInfo>();
		clientIO = new ClientIO();
		
		getMessageProcessingModule().addMessageHandler(SolveRequestResponse.class, 
											new SolveRequestResponseHandler(this));
		getMessageProcessingModule().addMessageHandler(Solutions.class, 
				new SolutionMessageHandler(this));
	}
	
	
	public void addProblem(BigInteger problemId) {
		final ProblemInfo problemInfo = new ProblemInfo();
		problemInfo.setProblemData(problemData);
		problemData = null;
		
		problemMap.put(problemId, problemInfo);
	}
	
	public ProblemInfo getProblem(BigInteger problemId) {
		if(problemMap.containsKey(problemId)) {
			return problemMap.get(problemId);
		}
		return null;
	}
	
	public void sendSolveRequest() {
		final SolveRequest solveRequest = new SolveRequest();
		//solveRequest.setId(); //server gives it
		
		final byte[] data = Base64.encodeBase64(problemData);
		
		solveRequest.setData(data);
		solveRequest.setSolvingTimeout(new BigInteger("30000"));
		solveRequest.setProblemType(ProblemType.TSP.getName());
		
		getProxy().sendMessage(new ClientMessage(solveRequest));
	}
	
	public void sendSolutionRequest(BigInteger problemId) {
		final SolutionRequest solutionRequest = new SolutionRequest();
		solutionRequest.setId(problemId);
		
		getProxy().sendMessage(new ClientMessage(solutionRequest));
	}
	
	public void loadProblemData(String filename) {
		problemData = clientIO.loadProblemData(filename);
	}
	
	public void saveSolution(BigInteger problemId, String filename) {
		final ProblemInfo problemInfo = problemMap.get(problemId);
		final Solution solution = problemInfo.getSolution();
		final byte[] data = Base64.decodeBase64(solution.getData());
		
		clientIO.saveSolution(filename, data);
	}
	
	public boolean hasProblemData() {
		return problemData != null;
	}

}
