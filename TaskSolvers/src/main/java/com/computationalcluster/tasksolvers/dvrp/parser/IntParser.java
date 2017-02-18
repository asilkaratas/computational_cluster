package com.computationalcluster.tasksolvers.dvrp.parser;


public class IntParser implements LineParser {
	//private static final Logger logger = Logger.getLogger(IntParser.class);
	
	private final int[] data;
	private int counter = 0;
	
	public IntParser(int size) {
		data = new int[size];
	}
	
	public int[] getData() {
		return data;
	}

	@Override
	public void parse(String line) {
		String[] parts = line.split(" ");
		String str = parts.length == 1 ? parts[0] : parts[1];
		//logger.info("line:" + line + " str:" + str + " parts:" + parts.length);
		int value = Math.abs(Integer.valueOf(str).intValue());
		data[counter] = value;
		counter ++;
	}
}
