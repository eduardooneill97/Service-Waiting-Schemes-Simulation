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
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class MLMSPolicy extends AbstractPolicyEnforcer{
	
	private String policyName;
	protected Queue<Customer> arrivals;
	protected ArrayList<Deque<Customer>> lines;
	protected ArrayList<Server> servicePosts;
	protected ArrayList<Customer> customersServedInFull;
	
	public MLMSPolicy() {
		this.policyName = "MLMS";
	}

	@Override
	public ArrayList<Customer> proccess(Customer[] customers, int serverCount) {
		
		arrivals = arrivalEvents(customers);
		lines = new ArrayList<Deque<Customer>>();
		servicePosts = new ArrayList<Server>();
		customersServedInFull = new ArrayList<Customer>();
		
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
	/**
	 * Returns true if all lines specified are empty.
	 * @param lines
	 * @return
	 */
	protected boolean areLinesEmpty(ArrayList<Deque<Customer>> lines) {
		for(Queue<Customer> q: lines) {
			if(!q.isEmpty())
				return false;
		}
		return true;
	}

	@Override
	protected void checkForServiceCompletion(int time) {
		for(Server server: servicePosts) {
			if(!server.isAvailable()) {
				if(server.isServiceCompleted(time)) {
					Customer cust = server.becomeAvailable();
					cust.setDepartureTime(time);
					customersServedInFull.add(cust);
				}
			}
		}	
	}

	@Override
	protected void checkForServiceStart(int time) {
		for(int i = 0; i<servicePosts.size(); i++) {
			if(servicePosts.get(i).isAvailable()) {
				if(!lines.get(i).isEmpty()) {
					Customer cust = lines.get(i).remove();
					cust.setServiceStart(time);
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
				lines.get(shortestLine()).add(assignLine.remove());
				
			}
		}	
	}
	/**
	 * Returns the index corresponding to the shortest line.
	 * @return
	 */
	protected int shortestLine() {
		int index = 0;
		int min = lines.get(0).size();
		if(!servicePosts.get(0).isAvailable())
			min++;
		for(int i = 0; i<lines.size(); i++) {
			int sizeToCompare = lines.get(i).size();
			if(!servicePosts.get(i).isAvailable())
				sizeToCompare++;
			if(sizeToCompare<min) {
				min = sizeToCompare;
				index = i;
			}
		}
		return index;
	}

	@Override
	protected int updateTime() {
		int time = 0;
		
		if(arrivals.isEmpty()) {
			for(Server server: servicePosts) {
				if(!server.isAvailable()) {
					if(time == 0)
						time = server.getNextAvailableTime();
					else time = Math.min(time, server.getNextAvailableTime());
				}
			}
			return time;
		}
		
		else {
			time = arrivals.peek().getArrivalTime();
			for(Server server: servicePosts) {
				if(!server.isAvailable())
					time = Math.min(time, server.getNextAvailableTime());
			}
			return time;
		}
	}

	@Override
	public String toString() {
		return "MLMS";
	}

	public String getPolicyName() {
		return policyName;
	}

}
