package com.computationalcluster.communicationserver.messagehandler;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.computationalcluster.common.config.ServerConfig;
import com.computationalcluster.communicationserver.CommunicationServer;
import com.computationalcluster.communicationserver.CommunicationServerProxy;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.ComputationalNodeModule;
import com.computationalcluster.communicationserver.module.ProblemStatusModule;
import com.computationalcluster.communicationserver.module.TaskManagerModule;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;
import com.computationalcluster.communicationserver.module.componentstatus.ThreadStatus;

public class BaseHandlerTest {
	
	private ServerConfig serverConfig = null;
	private CommunicationServerProxy communicationProxy = null;
	private ComponentStatus componentStatus = null;
	private ComponentStatusModule componentStatusModule = null;
	private CommunicationServer communicationServer = null;
	private ComputationalNodeModule computationalNodeModule = null;
	private TaskManagerModule taskManagerModule = null;
	private ProblemStatusModule problemStatusModule = null;
	
	@Before
	public void setUp() {
		serverConfig = mock(ServerConfig.class);
		when(serverConfig.getTimeout()).thenReturn((long)10000);
		
		communicationProxy = mock(CommunicationServerProxy.class);
		computationalNodeModule = mock(ComputationalNodeModule.class);
		taskManagerModule = mock(TaskManagerModule.class);
		problemStatusModule = mock(ProblemStatusModule.class);
		
		List<ThreadStatus> threadStatusList = new ArrayList<ThreadStatus>();
		threadStatusList.add(new ThreadStatus());
		
		componentStatus = mock(ComponentStatus.class);
		when(componentStatus.getThreadCount()).thenReturn(1);
		when(componentStatus.getClientId()).thenReturn(new BigInteger("1"));
		when(componentStatus.getThreadStatusList()).thenReturn(threadStatusList);
		
		Answer<ComponentStatus> getComponentAnswer = new Answer<ComponentStatus>() {

			@Override
			public ComponentStatus answer(InvocationOnMock invocation) {
				BigInteger clientId = (BigInteger)invocation.getArguments()[0];
				if(clientId.equals(componentStatus.getClientId())) {
					return componentStatus;
				}
				return null;
			}
		};
		
		componentStatusModule = mock(ComponentStatusModule.class);
		when(componentStatusModule.getComponent(any(BigInteger.class))).thenAnswer(getComponentAnswer);
		
		communicationServer = mock(CommunicationServer.class);
		when(communicationServer.getServerConfig()).thenReturn(serverConfig);
		when(communicationServer.getProxy()).thenReturn(communicationProxy);
		when(communicationServer.getComponentStatusModule()).thenReturn(componentStatusModule);
		when(communicationServer.getComputationalNodeModule()).thenReturn(computationalNodeModule);
		when(communicationServer.getTaskManagerModule()).thenReturn(taskManagerModule);
		when(communicationServer.getProblemStatusModule()).thenReturn(problemStatusModule);
	}
	
	public ServerConfig getServerConfig() {
		return serverConfig;
	}

	public CommunicationServerProxy getCommunicationProxy() {
		return communicationProxy;
	}

	public ComponentStatus getComponentStatus() {
		return componentStatus;
	}

	public ComponentStatusModule getComponentStatusModule() {
		return componentStatusModule;
	}

	public CommunicationServer getCommunicationServer() {
		return communicationServer;
	}

	public ComputationalNodeModule getComputationalNodeModule() {
		return computationalNodeModule;
	}

	public TaskManagerModule getTaskManagerModule() {
		return taskManagerModule;
	}

	public ProblemStatusModule getProblemStatusModule() {
		return problemStatusModule;
	}
}
