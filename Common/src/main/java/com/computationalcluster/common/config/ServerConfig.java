package com.computationalcluster.common.config;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Logger;

public class ServerConfig {
	
	private static final Logger logger = Logger.getLogger(ServerConfig.class);
	
	private static final String PORT = "port";
	private static final String BACKUP = "backup";
	private static final String MASTER_ADDRESS = "mAddress";
	private static final String MASTER_PORT = "mPort";
	private static final String TIMEOUT = "t";
	
	private final String programName;
	private final Options options;
	
	
	private int port = 0;
	private long timeout = 0;
	private boolean backup = false;
	private String masterAddress = null;
	private int masterPort = 0;
	
	private boolean valid = false;
	
	public ServerConfig(String programName, int port, long timeout) {
		this(programName, port, timeout, false, null, 0);
	}
	
	public ServerConfig(String programName, int port, long timeout, 
			boolean backup, String masterAddress, int masterPort) {
		this.programName = programName;
		this.options = null;
		this.port = port;
		this.timeout = timeout;
		this.backup = backup;
		this.masterAddress = masterAddress;
		this.masterPort = masterPort;
		
		validate();
	}
	
	public ServerConfig(String programName, String[] args) {
		this.programName = programName;
		
		options = new Options();
		options.addOption(PORT, true, "port number");
		options.addOption(BACKUP, false, "set as backup server");
		options.addOption(MASTER_ADDRESS, true, "IPv4 address or IPv6 address or host name");
		options.addOption(MASTER_PORT, true, "portNumber");
		options.addOption(TIMEOUT, true, "time in seconds");
		
		final CommandLineParser parser = new DefaultParser();
		
		try {
		    final CommandLine line = parser.parse(options, args);
		    
		    if(line.hasOption(PORT)) {
		    	final String portVlue = line.getOptionValue(PORT);
		    	try {
		    		port = Integer.valueOf(portVlue);
		    	} catch(NumberFormatException e) {
		    		logger.error(e.getMessage());
		    	}
		    }
		    
		    if(line.hasOption(TIMEOUT)) {
		    	final String timeoutValue = line.getOptionValue(TIMEOUT);
		    	try {
		    		timeout = Long.valueOf(timeoutValue);
		    	} catch(NumberFormatException e) {
		    		logger.error(e.getMessage());
		    	}
		    }
		    
		    if(line.hasOption(BACKUP)) {
		    	backup = true;
		    	
		    	if(line.hasOption(MASTER_ADDRESS)) {
		    		final String masterAddressValue = line.getOptionValue(MASTER_ADDRESS);
				    if(masterAddressValue != null || masterAddressValue != ""){
				    	masterAddress = masterAddressValue;
				    }
			    }
		    	
		    	if(line.hasOption(MASTER_PORT)) {
			    	final String portValue = line.getOptionValue(MASTER_PORT);
			    	try {
			    		masterPort = Integer.valueOf(portValue);
			    	} catch(NumberFormatException e) {
			    		logger.error(e.getMessage());
			    	}
			    }
		    }
		    
		    validate();
		    
		} catch(ParseException e) {
		    logger.error(e.getMessage());
		} 
	}
	
	private void validate() {
		if((!backup && port != 0 && timeout != 0) ||
	    	(backup && port != 0 && timeout != 0 && masterAddress != null && masterPort != 0)) {
	    	valid = true;
	    	showConfig();
	    }
	}
	
	public int getPort() {
		return port;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public long getTimeout() {
		return timeout;
	}
	
	public boolean isBackup() {
		return backup;
	}
	
	public String getMasterAddress() {
		return masterAddress;
	}
	
	public int getMasterPort() {
		return masterPort;
	}
	
	public boolean checkIsValidAndShowUsage(){
		if(!valid){
			showUsage();
		}
		return valid;
	}
	
	private void showConfig() {
		String config = null;
		if(backup){
			config = String.format("%s config: -%s %d -%s %d -%s -%s %s -%s -%d", 
					programName, 
					PORT, port, 
					TIMEOUT, timeout,
					BACKUP,
					MASTER_ADDRESS, masterAddress,
					MASTER_PORT, masterPort);
		} else {
			config = String.format("%s config: -%s %d -%s %d", 
					programName, 
					PORT, port, 
					TIMEOUT, timeout);
		}
		System.out.println(config);
	}
	
	private void showUsage() {
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp(programName, options);
	}
}
