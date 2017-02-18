package com.computationalcluster.tasksolvers.dvrp;

import org.apache.log4j.Logger;

import com.computationalcluster.tasksolvers.dvrp.parser.DoubleParser;
import com.computationalcluster.tasksolvers.dvrp.parser.IgnoreParser;
import com.computationalcluster.tasksolvers.dvrp.parser.LocationParser;
import com.computationalcluster.tasksolvers.dvrp.parser.IntParser;
import com.computationalcluster.tasksolvers.dvrp.parser.LineParser;

public class ProblemData {
	private static final Logger logger = Logger.getLogger(ProblemData.class);
	
	private static final String THREADS = "THREADS";
	private static final String THREAD_ID = "THREAD_ID";
	private static final String VRPTEST = "VRPTEST";
	private static final String NAME = "NAME";
	private static final String NUM_DEPOTS = "NUM_DEPOTS";
	private static final String NUM_CAPACITIES = "NUM_CAPACITIES";
	private static final String NUM_VISITS = "NUM_VISITS";
	private static final String NUM_LOCATIONS = "NUM_LOCATIONS";
	private static final String NUM_VEHICLES = "NUM_VEHICLES";
	private static final String CAPACITIES = "CAPACITIES";
	private static final String DATA_SECTION = "DATA_SECTION";
	private static final String DEPOTS = "DEPOTS";
	private static final String DEMAND_SECTION = "DEMAND_SECTION";
	private static final String LOCATION_COORD_SECTION = "LOCATION_COORD_SECTION";
	private static final String DEPOT_LOCATION_SECTION = "DEPOT_LOCATION_SECTION";
	private static final String VISIT_LOCATION_SECTION = "VISIT_LOCATION_SECTION";
	private static final String DURATION_SECTION = "DURATION_SECTION";
	private static final String DEPOT_TIME_WINDOW_SECTION = "DEPOT_TIME_WINDOW_SECTION";
	private static final String TIME_AVAIL_SECTION = "TIME_AVAIL_SECTION";
	private static final String COMMENT = "COMMENT";
	private static final String EOF = "EOF";
	
	private String name = null;
	private int numDepots = 0;
	private int numCapacities = 0;
	private int numVisits = 0;
	private int numLocations = 0;
	private int numVehicles = 0;
	private int capacities = 0;
	private int depots = 0;
	private int threads = 0;
	private int threadId = 0;
	
	private DoubleParser requestParser = null;
	private IntParser depotsParser = null;
	private LineParser currentParser = null;
	private LocationParser locationParser = null;
	private DoubleParser depotLocationParser = null;
	private LineParser ignoreParser = null;
	private IntParser avabilityTimesParser = null;
	private IntParser loadingTimesParser = null;
	
	public ProblemData(String data) {
		ignoreParser = new IgnoreParser();
		
		readParam(data);
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumCapacities() {
		return numCapacities;
	}
	
	public int getCapacities() {
		return capacities;
	}
	
	public int getDepots() {
		return depots;
	}
	
	public double[][] getLocations() {
		return locationParser.getData();
	}
	
	public double[] getRequests() {
		return requestParser.getData();
	}
	
	public int[] getAvabilityTimes() {
		return avabilityTimesParser.getData();
	}
	
	public int[] getLoadingTimes() {
		return loadingTimesParser.getData();
	}
	
	public int getNumVehicles() {
		return numVehicles;
	}
	
	public int getThreads() {
		return threads;
	}
	
	public int getThreadId() {
		return threadId;
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
				case VRPTEST:
				case COMMENT:
				case DATA_SECTION:
				case EOF:
					break;
				case THREADS:
					threads = readInt(parts[1]);
					break;
				case THREAD_ID:
					threadId = readInt(parts[1]);
					break;
				case NAME:
					name = parts[1];
					break;
				case NUM_DEPOTS:
					 numDepots = readInt(parts[1]);
					break;
				case NUM_CAPACITIES:
					 numCapacities = readInt(parts[1]);
					break;
				case NUM_VISITS:
					 numVisits = readInt(parts[1]);
					break;
				case NUM_LOCATIONS:
					 numLocations = readInt(parts[1]);
					break;
				case NUM_VEHICLES:
					 numVehicles = readInt(parts[1]);
					break;
				case CAPACITIES:
					 capacities = readInt(parts[1]);
					break;
				case DEPOTS:
					 depotsParser = new IntParser(1);
					 currentParser = depotsParser;
					break;
				case DEMAND_SECTION:
					 requestParser = new DoubleParser(numVisits);
					 currentParser = requestParser;
					break;
				case LOCATION_COORD_SECTION:
					 locationParser = new LocationParser(numLocations - 1);
					 currentParser = locationParser;
					break;
				case DEPOT_LOCATION_SECTION:
					 depotLocationParser = new DoubleParser(numDepots);
					 currentParser = depotLocationParser;
					break;
				case VISIT_LOCATION_SECTION:
					 currentParser = ignoreParser;
					break;
				case DURATION_SECTION:
					loadingTimesParser = new IntParser(numVisits);
					 currentParser = loadingTimesParser;
					break;
				case DEPOT_TIME_WINDOW_SECTION:
					 currentParser = ignoreParser;
					break;
				case TIME_AVAIL_SECTION:
					avabilityTimesParser = new IntParser(numVisits);
					 currentParser = avabilityTimesParser;
					break;
				
				default:
					currentParser.parse(line);
					break;
				}
			}
		}
	}
	
	private int readInt(String str) {
		try {
			if(str != null) {
				return Integer.valueOf(str.trim());
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage());
		}
		
		return -1;
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("threads:" + threads + "\n");
		builder.append("threadId:" + threadId + "\n");
		
		final double[][] locations = getLocations();
		builder.append("locations:\n");
		for(int i = 0; i < numLocations-1; ++i) {
			builder.append("x:" + locations[0][i]  + " y:" + locations[1][i] + "\n");
		}
		
		final double[] requests = getRequests();
		builder.append("requests:\n");
		for(int i = 0; i < numVisits; ++i) {
			builder.append(requests[i] + "\n");
		}
		
		final int[] avaibilityTimes = getAvabilityTimes();
		builder.append("avaibilityTimes:\n");
		for(int i = 0; i < numVisits; ++i) {
			builder.append(avaibilityTimes[i] + "\n");
		}
		
		final int[] loadingTimes = getLoadingTimes();
		builder.append("loadingTimes:\n");
		for(int i = 0; i < numVisits; ++i) {
			builder.append(loadingTimes[i] + "\n");
		}
		
		
		return builder.toString();
	}
}
