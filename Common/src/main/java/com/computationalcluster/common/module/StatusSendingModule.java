package com.computationalcluster.common.module;

import com.computationalcluster.common.component.ClientComponent;
import com.computationalcluster.common.connection.ClientMessage;
import com.computationalcluster.common.messages.Status;

public class StatusSendingModule extends TerminableThread {

	private long timeout = 0;
	private ClientComponent clientComponent = null;
	
	public StatusSendingModule(){
		setName("StatusSendingModule");
	}
	
	public void setClientComponent(ClientComponent clientComponent) {
		this.clientComponent = clientComponent;
	}
	
	public void setTimeout(long timeout){
		this.timeout = timeout - timeout/10;
	}
	
	public void run() {
		while(running){
			try {
				Thread.sleep(timeout * 1000);
				if(!running) {
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			final Status status = clientComponent.getStatus();
			if(status != null) {
				final ClientMessage clientMessage = new ClientMessage(clientComponent.getId(), status);
				clientComponent.getProxy().sendMessage(clientMessage);
			}
		}
	}
}
