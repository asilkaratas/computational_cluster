package com.computationalcluster.communicationserver;

import java.math.BigInteger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.queue.ClientMessageQueue;

public interface CommunicationServerProxy {
	void start();
	void sendMessage(ClientMessage clientMessage);
	ClientMessageQueue getInputQueue();
	void disconnectClient(BigInteger clientId);
}
