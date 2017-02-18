package com.computationalcluster.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class PrintIpAddress {
	public static void main(String[] args){
		InetAddress address = null;
		
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		
		if(address == null)
			return;
		
		System.out.println("Client Address : " + address);
	}
}
