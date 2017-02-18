package com.computationalcluster.common.enums;


public enum ClientComponentType {
    TASK_MANAGER("TaskManager"),
    COMPUTATIONAL_NODE("ComputationalNode"),
    COMMUNICATION_SERVER("CommunicationServer"),
    COMPUTATIONAL_CLIENT("ComputationalClient");
    
	private String name;
	
	private ClientComponentType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override 
	public String toString() {
		return name;
	}
	
	public static ClientComponentType fromName(String name) {
        for (ClientComponentType type: ClientComponentType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        
        return null;
    }
}
