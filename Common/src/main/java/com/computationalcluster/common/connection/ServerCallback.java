package com.computationalcluster.common.connection;

import java.math.BigInteger;

public interface ServerCallback {
	void onConnectionLost(Server server, BigInteger id);
}
