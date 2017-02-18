package com.computationalcluster.communicationserver.module.problemstatus;


public enum PartialProblemState {
	UNSOLVED("Unsolved"), 
	SOLVED("Solved"),
	SOLVING("Solving");
	
	private final String name;
	
	private PartialProblemState(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	public static PartialProblemState fromName(String name) {
        for (PartialProblemState type: PartialProblemState.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }
}