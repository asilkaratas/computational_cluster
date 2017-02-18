package com.computationalcluster.communicationserver.module;

import com.computationalcluster.common.connection.Message;

public interface WaitingMessage extends Message {
	String getProblemType();
}
