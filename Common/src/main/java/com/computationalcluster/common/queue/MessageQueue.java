package com.computationalcluster.common.queue;

import java.util.LinkedList;
import java.util.List;


public class MessageQueue<T> {
	private final List<T> messages;
	private final Object lock = new Object();
	
	public MessageQueue() {
		messages = new LinkedList<T>();
	}
	
	public void addMessage(T message) {
		synchronized (lock) {			
			messages.add(message);
			
			notifyLock();
		}
	}
	
	public void addMessageWithoutNotify(T message) {
		synchronized (lock) {			
			messages.add(message);
		}
	}
	
	public void notifyLock(){
		synchronized (lock) {
			try{
	            lock.notify();                      
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public T getMessage(){
		synchronized (lock) {			
			return messages.remove(0);
		}
	}
	
	public void removeMessage(T message){
		synchronized (lock) {			
			messages.remove(message);
		}
	}
	
	public void skipMesage(T message){
		synchronized (lock) {			
			messages.remove(message);
			messages.add(message);
		}
	}
	
	public boolean hasMessage() {
		synchronized (lock) {			
			return !messages.isEmpty();
		}
	}
	
	public List<T> getMessages() {
		return messages;
	}
	
	public Object getLock(){
		return lock;
	}
	
}
