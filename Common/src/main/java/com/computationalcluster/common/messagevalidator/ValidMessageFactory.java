package com.computationalcluster.common.messagevalidator;

import java.math.BigInteger;

import com.computationalcluster.common.enums.ProblemType;
import com.computationalcluster.common.enums.SolutionType;
import com.computationalcluster.common.enums.ThreadState;
import com.computationalcluster.common.messages.ComponentType;
import com.computationalcluster.common.messages.NoOperation;
import com.computationalcluster.common.messages.NoOperation.BackupCommunicationServers;
import com.computationalcluster.common.messages.NoOperation.BackupCommunicationServers.BackupCommunicationServer;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.RegisterResponse;
import com.computationalcluster.common.messages.SolutionRequest;
import com.computationalcluster.common.messages.Solutions;
import com.computationalcluster.common.messages.Solutions.SolutionsList;
import com.computationalcluster.common.messages.Solutions.SolutionsList.Solution;
import com.computationalcluster.common.messages.SolvePartialProblems;
import com.computationalcluster.common.messages.SolveRequest;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.messages.Register.SolvableProblems;
import com.computationalcluster.common.messages.Register.Type;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems;
import com.computationalcluster.common.messages.SolvePartialProblems.PartialProblems.PartialProblem;
import com.computationalcluster.common.messages.Status.Threads;
import com.computationalcluster.common.messages.Status.Threads.Thread;

public class ValidMessageFactory {
	public static SolvePartialProblems createSolvePartialProblems() {
		final PartialProblem partialProblem = new PartialProblem();
		partialProblem.setData(new byte[10]);
		partialProblem.setNodeID(new BigInteger("1"));
		partialProblem.setTaskId(new BigInteger("1"));
		
		final PartialProblems partialProblems = new PartialProblems();
		partialProblems.getPartialProblem().add(partialProblem);
		
		final SolvePartialProblems solvePartialProblems = new SolvePartialProblems();
		solvePartialProblems.setId(new BigInteger("1"));
		solvePartialProblems.setCommonData(new byte[100]);
		solvePartialProblems.setProblemType(ProblemType.TSP.getName());
		solvePartialProblems.setSolvingTimeout(new BigInteger("1000"));
		solvePartialProblems.setPartialProblems(partialProblems);
		
		return solvePartialProblems;
	}
	
	public static Status createStatus() {
		final Thread thread1 = new Thread();
		thread1.setState(ThreadState.IDLE.getName());
		thread1.setHowLong(new BigInteger("1000"));
		
		final Threads threads = new Threads();
		threads.getThread().add(thread1);
		
		final Status status = new Status();
		status.setThreads(threads);
		status.setId(new BigInteger("1"));
		
		return status;
	}
	
	public static SolveRequest createSolveRequest() {
		final SolveRequest solveRequest = new SolveRequest();
		solveRequest.setData(new byte[100]);
		solveRequest.setProblemType(ProblemType.TSP.toString());
		solveRequest.setSolvingTimeout(new BigInteger("10000"));
		
		return solveRequest;
	}
	
	public static Register createRegister() {
		final Type type = new Type();
		type.setValue(ComponentType.COMPUTATIONAL_NODE);
		
		final SolvableProblems solvableProblems = new SolvableProblems();
		solvableProblems.getProblemName().add(ProblemType.TSP.getName());
		
		final Register register = new Register();
		register.setType(type);
		register.setParallelThreads((short)10);
		register.setSolvableProblems(solvableProblems);
		
		return register;
	}
	
	public static Solutions createSolutions() {
		final Solution solution = new Solution();
		solution.setData(new byte[10]);
		solution.setComputationsTime(new BigInteger("1000"));
		solution.setTaskId(new BigInteger("1"));
		solution.setType(SolutionType.PARTIAL.getName());
		
		final SolutionsList solutionsList = new SolutionsList();
		solutionsList.getSolution().add(solution);
		
		final Solutions solutions = new Solutions();
		solutions.setCommonData(new byte[100]);
		solutions.setId(new BigInteger("1"));
		solutions.setProblemType(ProblemType.TSP.getName());
		solutions.setSolutionsList(solutionsList);		
		
		return solutions;
	}
	
	public static SolutionRequest createSolutionRequest() {
		final SolutionRequest solutionRequest = new SolutionRequest();
		solutionRequest.setId(new BigInteger("1"));
		
		return solutionRequest;
	}
	
	public static RegisterResponse createRegisterResponse() {
		final RegisterResponse registerResponse = new RegisterResponse();
		registerResponse.setId(new BigInteger("1"));
		registerResponse.setTimeout(5000);
		
		return registerResponse;
	}
	
	public static NoOperation createNoOperation() {
		final BackupCommunicationServer server = new BackupCommunicationServer();
		server.setAddress("127.0.0.1");
		server.setPort(2000);
		
		final BackupCommunicationServers backupServers = new BackupCommunicationServers();
		backupServers.getBackupCommunicationServer().add(server);
		
		final NoOperation noOperation = new NoOperation();
		noOperation.setBackupCommunicationServers(backupServers);
		
		return noOperation;
	}
	
}
