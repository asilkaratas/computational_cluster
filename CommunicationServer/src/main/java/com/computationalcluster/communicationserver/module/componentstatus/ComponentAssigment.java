package com.computationalcluster.communicationserver.module.componentstatus;

import com.computationalcluster.communicationserver.module.WaitingMessage;

public class ComponentAssigment {
	
	private final WaitingMessage message;
	public ComponentAssigment(WaitingMessage message) {
		this.message = message;
	}
	
	public WaitingMessage getMessage() {
		return message;
	}
	
	public void revoke() {
		
	}
}
