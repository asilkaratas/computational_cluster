package com.computationalcluster.common.enums;


public enum ErrorType {
	UNKNOWN_SENDER("UnknownSender"), 
	NOT_A_PRIMARY_SERVER("NotAPrimaryServer"),
	INVALID_OPERATION("InvalidOperation"),
	EXCEPTION_OCCURED("ExceptionOccured");
	
	private String name;
	
	private ErrorType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	public static ErrorType fromName(String name) {
        for (ErrorType type: ErrorType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        throw new IllegalArgumentException(name);
    }
}
