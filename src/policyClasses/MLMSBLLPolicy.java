package policyClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import javax.management.monitor.Monitor;

import models.Customer;
import models.CustomerComparator;
import models.LineMonitor;
import models.Server;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class MLMSBLLPolicy extends MLMSPolicy{
	
	private String policyName;
	
	public MLMSBLLPolicy() {
		this.policyName = "MLMSBLL";
	}
	
	@Override
	public ArrayList<Customer> proccess(Customer[] customers, int serverCount) {
		
		arrivals = arrivalEvents(customers);
		lines = new ArrayList<Deque<Customer>>();
		servicePosts = new ArrayList<Server>();
		customersServedInFull = new ArrayList<Customer>();
		LineMonitor monitor = new LineMonitor(lines, servicePosts);
		
		for(int i = 0; i<serverCount; i++) {
			servicePosts.add(new Server());
			lines.add(new ConcurrentLinkedDeque<Customer>());
		}
		
		int time = 0;
		while( stillCurrentServices(servicePosts) || !areLinesEmpty(lines) || !arrivals.isEmpty() ) {
			checkForServiceCompletion(time);
			monitor.handleTransfers();
			checkForServiceStart(time);
			checkForArrivals(time);
			monitor.handleTransfers();
			checkForServiceStart(time);
			time = updateTime();
		}
		
		return customersServedInFull;
	}

	@Override
	public String toString() {
		return "MLMSBWT";
	}

	public String getPolicyName() {
		return policyName;
	}


}
