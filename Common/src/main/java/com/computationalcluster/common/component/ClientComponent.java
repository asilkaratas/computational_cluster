package com.computationalcluster.common.component;

import java.math.BigInteger;

import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.connection.CommunicationProxy;
import com.computationalcluster.common.enums.ClientComponentType;
import com.computationalcluster.common.messages.Register;
import com.computationalcluster.common.messages.Status;
import com.computationalcluster.common.module.ClientMessageProcessingModule;
import com.computationalcluster.common.module.StatusSendingModule;

public class ClientComponent {
	
	private final ClientComponentType componentType;
	private final CommunicationProxy proxy;
	private final ClientMessageProcessingModule messageProcessingModule;
	private final StatusSendingModule statusSendingModule;
	private BigInteger id = null;
	
	public ClientComponent(ClientComponentType componentType, CommunicationProxy proxy) {
		this(componentType, proxy, new ClientMessageProcessingModule(), new StatusSendingModule());
	}
	
	public ClientComponent(ClientComponentType componentType,
						   CommunicationProxy proxy,
						   ClientMessageProcessingModule messageProcessingModule, 
						   StatusSendingModule statusSendingModule){
		this.componentType = componentType;
		this.proxy = proxy;
		this.messageProcessingModule = messageProcessingModule;
		this.statusSendingModule = statusSendingModule;
		
		messageProcessingModule.setMessageQueue(proxy.getInputQueue());
		statusSendingModule.setClientComponent(this);
	}
	
	public ClientComponentType getComponentType() {
		return componentType;
	}
	
	public StatusSendingModule getStatusSendingModule(){
		return statusSendingModule;
	}
	
	public ClientMessageProcessingModule getMessageProcessingModule() {
		return messageProcessingModule;
	}
	
	public CommunicationProxy getProxy(){
		return proxy;
	}
	
	public boolean start() {
		messageProcessingModule.start();
		final Register register = getRegister();
		if(register != null){
			proxy.sendMessage(new ClientMessage(getId(), register));
		}
		return true;
	}
	
	public void terminate() {
		messageProcessingModule.terminate();
		statusSendingModule.terminate();
	}
	
	protected Register getRegister() {
		return null;
	}
	
	public Status getStatus(){
		return null;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public BigInteger getId(){
		return id;
	}
	
}
