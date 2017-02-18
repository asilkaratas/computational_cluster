package com.computationalcluster.common.enums;

public enum ThreadState {
	IDLE("Idle"), 
	BUSY("Busy");
	
	private String name;
	
	private ThreadState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	public static ThreadState fromName(String name) {
        for (ThreadState type: ThreadState.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }
}
