package com.computationalcluster.communicationserver;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;

import com.computationalcluster.common.config.ServerConfig;
import com.computationalcluster.communicationserver.module.ComponentStatusModule;
import com.computationalcluster.communicationserver.module.ComputationalNodeModule;
import com.computationalcluster.communicationserver.module.TaskManagerModule;
import com.computationalcluster.communicationserver.module.componentstatus.ComponentStatus;

public class CommunicationServerTest {
	
	private ServerConfig serverConfig = null;
	private CommunicationServerProxy communicationProxy = null;
	private ComponentStatus componentStatus = null;
	private ComponentStatusModule componentStatusModule = null;
	private ComputationalNodeModule computationalNodeModule = null;
	private TaskManagerModule taskManagerModule = null;
	
	private CommunicationServer communicationServer = null;
	
	@Before
	public void setUp() {
		serverConfig = mock(ServerConfig.class);
		when(serverConfig.getTimeout()).thenReturn((long)10000);
		
		communicationProxy = mock(CommunicationServerProxy.class);
		
		communicationServer = new CommunicationServer(serverConfig, communicationProxy);
	}
	
}
