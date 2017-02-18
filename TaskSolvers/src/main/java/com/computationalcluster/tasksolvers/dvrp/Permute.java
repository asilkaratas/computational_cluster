package com.computationalcluster.tasksolvers.dvrp;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;



public class Permute {
	//private static final Logger logger = Logger.getLogger(Permute.class);
	
	private long timeMax = 0;
	private double minDist = Double.MAX_VALUE;
	private double[][] distances = null;
	private int[] availableTimes = null;
	private int[] loadingTimes = null;
	private double[] requests = null;
	private long permutations = 0;
	private int numVehicle = 0;
	private List<Integer> bestPermutation = null;
	
	public void setTimeMax(long timeMax) {
		this.timeMax = timeMax;
	}
	
	public void setPermutation(int[] list) {
		int m = list.length - 1;
		solve(list, 0, m);
	}
	
	public void setAvailableTimes(int[] availableTimes) {
		this.availableTimes = availableTimes;
	}
	
	public void setDistances(double[][] distances) {
		this.distances = distances;
	}
	
	public void setLoadingTimes(int[] loadingTimes) {
		this.loadingTimes = loadingTimes;
	}
	
	public void setRequests(double[] requests) {
		this.requests = requests;
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
	
	public List<Integer> getBestPermutation() {
		return bestPermutation;
	}
	
	private void swap(int[] list, int indexA, int indexB) {
		if(list[indexA] == list[indexB]) 
			return;
		
		list[indexA] ^= list[indexB];
		list[indexB] ^= list[indexA];
		list[indexA] ^= list[indexB];
	}
	
	private void solve(int[] list, int k, int m) {
		if(k == m) {
			double lastTime = 0;
			double lastDistance = 0;
			double requestTime = 0;
			++permutations;
			
			for(int index = 0; index < list.length; ++index) {
				double availableTime = availableTimes[list[index]] > timeMax/2.0 ? 0.0 : (double)availableTimes[list[index]];
				double startTime = Math.max(lastTime, availableTime);
				double time = 0;
				
				if(index == 0) {
					time = startTime + distances[list[index]][numVehicle];
					lastDistance += distances[list[index]][numVehicle];
					requestTime = 100.0;
				} else if(requests[list[index]] > requestTime) {
					requestTime = 100.0;
					time = startTime + distances[list[index - 1]][numVehicle] + distances[list[index]][numVehicle];
					lastDistance += distances[list[index - 1]][numVehicle] + distances[list[index]][numVehicle];
				} else {
					time = startTime +  distances[list[index - 1]][list[index]];
					lastDistance += distances[list[index - 1]][list[index]];
				}
				
				requestTime -= requests[list[index]];
				lastTime = time + (double)loadingTimes[list[index]];
			} 
			
			double maxTime = lastTime + distances[list[list.length - 1]][numVehicle];
			double maxDistance = lastDistance + distances[list[list.length - 1]][numVehicle];
			if(maxTime > (double)timeMax || maxDistance >= minDist) {
				return;
			}
			
			bestPermutation = Arrays.asList(ArrayUtils.toObject(list));
			
			//logger.info("bestPermutation:" + bestPermutation.size() + " list:" + list.length);
			//logger.info("bestPermutation2:" + bestPermutation.get(0) + " list:" + list[0]);
			
			//logger.info("num6:" + distances[list[list.length - 1]][N]);
			
			minDist = maxDistance;
		} else {
			for(int index = k; index <= m; ++index) {
				swap(list, k, index);
				solve(list, k + 1, m);
				swap(list, k, index);
			}
		}
	}
	
}
