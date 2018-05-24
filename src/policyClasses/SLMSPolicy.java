package policyClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import models.Customer;
import models.CustomerComparator;
import models.Server;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class SLMSPolicy extends AbstractPolicyEnforcer{
	
	private String policyName;
	protected Queue<Customer> arrivals;
	protected Queue<Customer> line;
	protected ArrayList<Server> servicePosts;
	protected ArrayList<Customer> customersServedInFull;
	
	public SLMSPolicy() {
		this.policyName = "SLMS";
	}

	@Override
	public ArrayList<Customer> proccess(Customer[] customers, int serverCount) {
		arrivals = arrivalEvents(customers);
		line = new ConcurrentLinkedQueue<>();
		servicePosts = new ArrayList<Server>();
		customersServedInFull = new ArrayList<Customer>();
		
		for(int i = 0; i<serverCount; i++) {
			servicePosts.add(new Server());
		}
		
		int time = 0;
		while( stillCurrentServices(servicePosts) || !line.isEmpty() || !arrivals.isEmpty() ) {
			checkForServiceCompletion(time);
			checkForServiceStart(time);
			checkForArrivals(time);
			checkForServiceStart(time);
			time = updateTime();
		}
		
		return customersServedInFull;
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
		if(line.isEmpty())
			return;
		for(Server server: servicePosts) {
			if(server.isAvailable()) {
				if(!line.isEmpty()) {
					Customer cust = line.remove();
					cust.setServiceStart(time);
					server.setCurrentCustomer(cust);
				}
			}
		}
	}
	
	@Override
	protected void checkForArrivals(int time) {
		if(!arrivals.isEmpty()) {
			boolean next = true;
			while(next) {
				if(!arrivals.isEmpty()) {
					if(arrivals.peek().getArrivalTime() == time)
						line.add(arrivals.remove());
					else
						next = false;
				}
				else next = false;
			}
		}
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
		return policyName;
	}

	public String getPolicyName() {
		return policyName;
	}

}
