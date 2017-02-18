package com.computationalcluster.common.module;

public class TerminableThread extends Thread {
	protected volatile boolean running = true;
	
	public void terminate() {
		running = false;
	}
}
