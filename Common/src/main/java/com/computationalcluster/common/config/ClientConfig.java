package com.computationalcluster.common.config;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;


public class ClientConfig {
	
	private static final Logger logger = Logger.getLogger(ClientConfig.class);
	
	private static final String ADDRESS = "address";
	private static final String PORT = "port";
	
	private final String programName;
	private final Options options;
	
	private String serverAddress = null;
	private int serverPort = 0;
	private boolean valid = false;
	
	public ClientConfig(String programName, String[] args) {
		this.programName = programName;
		
		options = new Options();
		options.addOption(ADDRESS, true, "IPv4 address or IPv6 address or host name");
		options.addOption(PORT, true, "port number");
		
		final CommandLineParser parser = new DefaultParser();
		
		try {
		    final CommandLine line = parser.parse(options, args);
		    
		    if(line.hasOption(ADDRESS)) {
		    	final String serverAddressValue = line.getOptionValue(ADDRESS);
			    if(serverAddressValue != ""){
			    	serverAddress = serverAddressValue;
			    }
		    }
		    
		    if(line.hasOption(PORT)) {
		    	final String port = line.getOptionValue(PORT);
		    	try {
		    		serverPort = Integer.valueOf(port);
		    	} catch(NumberFormatException e) {
		    		logger.error(e.getMessage());
		    	}
		    }
		    
		    if(serverAddress != null && serverPort != 0) {
		    	valid = true;
		    	showConfig();
		    }
		    
		} catch(ParseException e) {
		    logger.error(e.getMessage());
		} 
	}
	
	public ClientConfig(String serverAddress, int serverPort) {
		this.programName = null;
		this.options = null;
		this.serverAddress = serverAddress;
		this.serverPort = serverPort;
	}
	
	public String gerServerAddress(){
		return serverAddress;
	}
	
	public int getServerPort(){
		return serverPort;
	}
	
	public boolean checkIsValidAndShowUsage(){
		if(!valid){
			showUsage();
		}
		return valid;
	}
	
	private void showConfig() {
		String config = String.format("%s config: -%s %s -%s %d", programName, ADDRESS, serverAddress, PORT, serverPort);
		System.out.println(config);
	}
	
	private void showUsage() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(programName, options);
	}
	
}
