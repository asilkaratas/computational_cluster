package com.computationalcluster.common.connection.transceiver;

public interface RecieverCallback {
	void onMessageRecieved(Object message);
	void onRecieverConnectionLost();
}
