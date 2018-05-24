package policyClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import models.Customer;
import models.CustomerComparator;
import models.Server;
import models.WTMonitor;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class MLMSBWTPolicy extends MLMSPolicy{
	
	private String policyName;
	private int[] expectedWaitingTime;
	private WTMonitor monitor;
	
	public MLMSBWTPolicy() {
		this.policyName = "MLMSBWT";
	}

	@Override
	public ArrayList<Customer> proccess(Customer[] customers, int serverCount) {
		expectedWaitingTime = new int[serverCount];

		arrivals = arrivalEvents(customers);
		lines = new ArrayList<Deque<Customer>>();
		servicePosts = new ArrayList<Server>();
		customersServedInFull = new ArrayList<Customer>();
		monitor = new WTMonitor(servicePosts);
		
		for(int i = 0; i<serverCount; i++) {
			servicePosts.add(new Server());
			lines.add(new ConcurrentLinkedDeque<Customer>());
		}
		
		int time = 0;
		while( stillCurrentServices(servicePosts) || !areLinesEmpty(lines) || !arrivals.isEmpty() ) {
			checkForServiceCompletion(time);
			checkForServiceStart(time);
			checkForArrivals(time);
			checkForServiceStart(time);
			time = updateTime();
		}
		
		return customersServedInFull;
	}

	@Override
	protected void checkForServiceStart(int time) {
		for(int i = 0; i<servicePosts.size(); i++) {
			if(servicePosts.get(i).isAvailable()) {
				if(!lines.get(i).isEmpty()) {
					Customer cust = lines.get(i).remove();
					cust.setServiceStart(time);
					expectedWaitingTime[i] -= cust.getServiceTime();
					servicePosts.get(i).setCurrentCustomer(cust);
				}
			}
		}
		
	}

	@Override
	protected void checkForArrivals(int time) {
		if(!arrivals.isEmpty()) {
			Queue<Customer> assignLine = new ConcurrentLinkedQueue<Customer>();
			
			boolean next = true;
			while(next) {
				if(!arrivals.isEmpty()) {
					if(arrivals.peek().getArrivalTime() == time)
						assignLine.add(arrivals.remove());
					else
						next = false;
				}
				else next = false;
			}
			
			while(!assignLine.isEmpty()) {
				int index = monitor.lowestExpectedWTLine(expectedWaitingTime, time);
				Customer cust = assignLine.remove();
				lines.get(index).add(cust);
				expectedWaitingTime[index] += cust.getServiceTime();
			}
		}	
	}

	@Override
	public String toString() {
		return "MLMSBWT";
	}

	public String getPolicyName() {
		return policyName;
	}
}
