package com.computationalcluster.common.messagevalidator;

import com.computationalcluster.common.messages.Error;

public interface MessageValidator {
	Error getError(Object message);
}
