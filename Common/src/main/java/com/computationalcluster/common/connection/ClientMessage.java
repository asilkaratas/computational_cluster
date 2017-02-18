package com.computationalcluster.common.connection;

import java.math.BigInteger;

public class ClientMessage implements Message {
	
	private final BigInteger clientId;
	private final Object message;
	
	public ClientMessage(Object message){
		this(null, message);
	}
	
	public ClientMessage(BigInteger clientId, Object message) {
		this.clientId = clientId;
		this.message = message;
	}
	
	public BigInteger getClientId() {
		return clientId;
	}
	
	public Object getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return String.format("ClientMessage: clientId:%d message:%s", clientId, message);
	}

	@Override
	public boolean handled() {
		return true;
	}
	
}
