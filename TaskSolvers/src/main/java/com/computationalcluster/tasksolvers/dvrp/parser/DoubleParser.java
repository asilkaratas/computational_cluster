package com.computationalcluster.tasksolvers.dvrp.parser;


public class DoubleParser implements LineParser {
	//private static final Logger logger = Logger.getLogger(DoubleParser.class);
	
	private final double[] data;
	private int counter = 0;
	
	public DoubleParser(int size) {
		data = new double[size];
	}
	
	public double[] getData() {
		return data;
	}

	@Override
	public void parse(String line) {
		final String[] parts = line.split(" ");
		final String str = parts.length == 1 ? parts[0] : parts[1];
		//logger.info("line:" + line + " str:" + str + " parts:" + parts.length);
		final double value = Math.abs(Double.valueOf(str).doubleValue());
		data[counter] = value;
		counter ++;
	}
}
