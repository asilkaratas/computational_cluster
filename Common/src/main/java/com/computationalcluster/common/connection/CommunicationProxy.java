package com.computationalcluster.common.connection;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.queue.ClientMessageQueue;

public interface CommunicationProxy {
	void sendMessage(ClientMessage message);
	ClientMessageQueue getInputQueue();
}
