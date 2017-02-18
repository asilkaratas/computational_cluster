package com.computationalcluster.tasksolvers.dvrp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

public class ProblemInstance implements Runnable {
	private static final Logger logger = Logger.getLogger(ProblemInstance.class);
	
	private long timeMax = 0;
	private double minDist = Double.MAX_VALUE;
	private long counter = -1;
	private List<List<Integer>> bestDivision = new ArrayList<List<Integer>>();
	private int countProc = 0;
	private int id = 0;
	private int numVehicle = 0;
	private double[][] distances = null;
	private double[][] locations = null;
	private double[] requests = null;
	private int[] availablityTimes = null;
	private int[] loadingTimes = null;
	private long assignments = 0;
	private long permutations = 0;
	private List<List<Integer>> currentDivision = null;
	
	public void setTimeMax(long timeMax) {
		this.timeMax = timeMax;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCountProc(int countProc) {
		this.countProc = countProc;
	}
	
	public void setAvailabilityTimes(int[] availabilityTimes) {
		this.availablityTimes = availabilityTimes;
	}
	
	public void setLoadingTimes(int[] loadingTimes) {
		this.loadingTimes = loadingTimes;
	}
	
	public void setRequests(double[] requests) {
		this.requests = requests;
	}
	
	public void setLocations(double[][] locations) {
		this.locations = locations;
	}
	
	public void setNumVehicle(int numVehicle) {
		this.numVehicle = numVehicle;
	}
	
	public double getMinDist() {
		return minDist;
	}
	
	public long getPermutations() {
		return permutations;
	}
	
	public long getAssingnments() {
		return assignments;
	}
	
	public List<List<Integer>> getBestDivision() {
		return bestDivision;
	}
	
	private void initializeDistanceArray() {
		distances = new double[numVehicle][numVehicle + 1];
		
		for(int index1 = 0; index1 < numVehicle; ++index1) {
			distances[index1][numVehicle] = Math.sqrt(locations[0][index1] * locations[0][index1] + 
											 locations[1][index1] * locations[1][index1]);
			
			for(int index2 = 0; index2 < numVehicle; ++index2) {
				distances[index1][index2] = Math.sqrt((locations[0][index1] - locations[0][index2]) * (locations[0][index1] - locations[0][index2]) + 
													  (locations[1][index1] - locations[1][index2]) * (locations[1][index1] - locations[1][index2]));
			}
		}
	}
	
	public void run() {
		initializeDistanceArray();
		nextDivision(0, new ArrayList<List<Integer>>());
	}
	
	private void nextDivision(int n, List<List<Integer>> division) {
		if(n == numVehicle) {
			++counter;
			if(counter % (long)countProc != (long)this.id) {
				return;
			}
			
			++assignments;
			currentDivision = division;
			if(assignments % 1000L == 0L) {
				//System.out.println(String.format("%d:%d:%s", id, assignments, new Date()));
				logger.info(id + ":" + assignments + ":" + new Date());
			}
			//logger.info(id + ":" + assignments + ":" + new Date());
			
			for (List<Integer> source : currentDivision) {
				double sum = 0;
				for(Integer index : source) {
					sum += requests[index];
				}
				if(sum > 100.0)
					return;
			}
			
			generateAllPermutations();
		} else {
			int index = 0;
			for(index = 0; index < division.size(); ++index) {
				List<Integer> divisionList = division.get(index);
				if(divisionList == null) {
					divisionList = new ArrayList<Integer>();
					division.add(divisionList);
				}
				final Integer intN = new Integer(n);
				divisionList.add(intN);
				nextDivision(n + 1, division);
				division.get(index).remove(intN);
			}
			
			division.add(new ArrayList<Integer>());
			List<Integer> divisionList = division.get(index);
			if(divisionList == null) {
				divisionList = new ArrayList<Integer>();
				division.add(divisionList);
			}
			final Integer intN = new Integer(n);
			divisionList.add(intN);
			nextDivision(n + 1, division);
			division.get(index).remove(intN);
			division.remove(division.size() - 1);
		}
	}
	
	private void generateAllPermutations() {
		double num = 0.0;
		final List<List<Integer>> intListList = new ArrayList<List<Integer>>();
		for (List<Integer> intList : currentDivision) {
			final Permute permute = new Permute();
			permute.setAvailableTimes(availablityTimes);
			permute.setDistances(distances);
			permute.setLoadingTimes(loadingTimes);
			permute.setRequests(requests);
			permute.setNumVehicle(numVehicle);
			permute.setTimeMax(timeMax);
			
			final int[] array = new int[intList.size()];
			for(int i = 0; i < array.length; ++i) {
				array[i] = intList.get(i).intValue();
			}
			permute.setPermutation(array);
			num += permute.getMinDist();
			permutations += permute.getPermutations();
			intListList.add(permute.getBestPermutation());
		}
		//logger.info("11 num:" + num + " minDist:" + minDist);
		if(num >= minDist) {
			return;
		}
		
		//logger.info("num:" + num + " minDist:" + minDist);
		
		minDist = num;
		bestDivision.clear();
		for(List<Integer> intList1 : intListList) {
			final List<Integer> intList2 = new ArrayList<Integer>();
			intList2.addAll(intList1);
			bestDivision.add(intList2);
		}
		
	}
	
}
