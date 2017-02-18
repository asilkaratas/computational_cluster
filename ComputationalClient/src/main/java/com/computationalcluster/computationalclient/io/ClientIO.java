package com.computationalcluster.computationalclient.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

public class ClientIO {
	private static final Logger logger = Logger.getLogger(ClientIO.class);
	
	public byte[] loadProblemData(String filename) {
		byte[] data = null;
		
		try {
		
			File file = new File(filename);
			
			//String path = getClass().getResource(filename).getPath();
			final FileInputStream input = new FileInputStream(file);
			data = IOUtils.toByteArray(input);
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		return data;
	}
	
	public void saveSolution(String filename, byte[] data) {
		
	}
}
