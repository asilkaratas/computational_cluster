package com.computationalcluster.common.enums;

public enum TaskType {
	DIVIDE("DIVIDE"), 
	MERGE("MERGE"),
	SOLVE("SOLVE");
	
	private String name;
	
	private TaskType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	public static TaskType fromName(String name) {
        for (TaskType taksType: TaskType.values()) {
            if (taksType.name.equals(name)) {
                return taksType;
            }
        }
        throw new IllegalArgumentException(name);
    }
}
