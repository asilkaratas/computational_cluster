package com.computationalcluster.common.messagehandler;

import com.computationalcluster.common.connection.Message;

public interface MessageHandler<M extends Message> {
	void handle(M message);
}
