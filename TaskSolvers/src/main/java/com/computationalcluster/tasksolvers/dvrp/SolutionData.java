package com.computationalcluster.tasksolvers.dvrp;

import org.apache.log4j.Logger;

public class SolutionData {
	private static final Logger logger = Logger.getLogger(SolutionData.class);
	
	private static final String MIN_DIST = "MIN_DIST";
	private static final String BEST_DIVISIONS = "BEST_DIVISIONS";
	
	private double minDist = 0;
	private String divisions = null;
	
	public SolutionData(String data) {
		
		readParam(data);
	}
	
	public double getMinDist() {
		return minDist;
	}
	
	public String getDivisions() {
		return divisions;
	}
	
	private void readParam(String data) {
		final String[] lines = data.split(System.getProperty("line.separator"));
		
		for(String line : lines) {
			//logger.info("line:" + line);
			line = line.trim();
			final String[] parts = line.split(" ");
			String firstPart = parts[0].trim();
			final int columnIndex = firstPart.indexOf(":");
			if(columnIndex != -1){
				firstPart = firstPart.substring(0, columnIndex);
			}
			
			//logger.info("firstPart:" + firstPart);
			if(parts.length > 0) {
				switch (firstPart) {
				case MIN_DIST:
					minDist = readDouble(parts[1]);
					break;
					
				case BEST_DIVISIONS:
					divisions = new String();
					break;
					
				default:
					divisions += line;
					break;
				}
				
			}
			
		}
	}
	
	private double readDouble(String str) {
		try {
			if(str != null) {
				return Double.valueOf(str.trim());
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		}
		
		return -1;
	}
		
}
