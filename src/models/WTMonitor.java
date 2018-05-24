package models;

import java.util.ArrayList;
import java.util.Deque;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class WTMonitor {
	
	ArrayList<Server> servicePosts;
	
	public WTMonitor(ArrayList<Server> servicePosts) {
		this.servicePosts = servicePosts;
	}
	
	public void handleArrivals(ArrayList<Deque<Customer>> lines, int[] wt,int time, Customer cust) {
		lines.get(lowestExpectedWTLine(wt, time)).add(cust);
	}
	/**
	 * Determines which line has lowest expected waiting time. Returns the index of the specified line.
	 * @param wt
	 * @param t
	 * @return
	 */
	public int lowestExpectedWTLine(int[] wt, int t) {
		int[] serviceRemainingTime = serviceRemainingTime(t);
		int[] totalTime = new int[wt.length];
		for(int i = 0; i<totalTime.length; i++) {
			totalTime[i] = serviceRemainingTime[i] + wt[i];
		}
		
		return lowestWaitingTimeLine(totalTime);
	}
	/**
	 * Returns the lowest element in the specified array of integers.
	 * @param times
	 * @return
	 */
	private int lowestWaitingTimeLine(int[] times) {
		int min = times[0];
		int index = 0;
		for(int i = 0; i<times.length; i++) {
			if(min>times[i]) {
				min = times[i];
				index = i;
			}
		}
		return index;
	}
	/**
	 * Returns an array with the remaining service time of all the occupied servers.
	 * @param t
	 * @return
	 */
	private int[] serviceRemainingTime(int t){
		int[] time = new int[servicePosts.size()];
		
		for(int i = 0; i<time.length; i++) {
			if(!servicePosts.get(i).isAvailable())
				time[i] = servicePosts.get(i).getNextAvailableTime() - t;
			else 
				time[i] = 0;
		}
		
		return time;
	}

}
