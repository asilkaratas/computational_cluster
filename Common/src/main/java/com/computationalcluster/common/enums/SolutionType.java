package com.computationalcluster.common.enums;

public enum SolutionType {
	ONGOING("Ongoing"), 
	PARTIAL("Partial"), 
	FINAL("Final");
	
	private String name;
	
	private SolutionType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
}
