package com.computationalcluster.common.connection;

public interface Message {
	Object getMessage();
	boolean handled();
}
