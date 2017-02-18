package com.computationalcluster.common.enums;


public enum ProblemType {
	TSP("TSP"), 
	MRP("MRP");
	
	private String name;
	
	private ProblemType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	public static ProblemType fromName(String name) {
        for (ProblemType type: ProblemType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }
}
