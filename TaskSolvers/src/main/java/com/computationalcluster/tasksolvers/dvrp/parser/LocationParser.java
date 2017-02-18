package com.computationalcluster.tasksolvers.dvrp.parser;


public class LocationParser implements LineParser {
	//private static final Logger logger = Logger.getLogger(LocationParser.class);
	
	private final double[][] data;
	private int counter = 0;
	private boolean firstLine = true;
	
	public LocationParser(int size) {
		data = new double[2][size];
	}
	
	public double[][] getData() {
		return data;
	}
	
	@Override
	public void parse(String line) {
		if(firstLine) {
			firstLine = false;
			return;
		}
		
		final String[] parts = line.split(" ");
		//logger.info("line:" + line + " parts:" + parts.length);
		final double x = Double.valueOf(parts[1].trim()).doubleValue();
		final double y = Double.valueOf(parts[2].trim()).doubleValue();
		data[0][counter] = x;
		data[1][counter] = y;
		counter ++;
	}

}
