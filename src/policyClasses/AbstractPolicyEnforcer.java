package policyClasses;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import models.Customer;
import models.Server;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public abstract class AbstractPolicyEnforcer implements PolicyEnforcer{
	/**
	 * Takes an array of ordered customers and puts them in a Queue of customers awaiting arrival.
	 * @param x
	 * @return
	 */
	protected Queue<Customer> arrivalEvents(Customer[] x){
		Queue<Customer> queue = new ConcurrentLinkedQueue<Customer>();
		for(Customer cust: x) {
			queue.add(cust);
		}
		return queue;
	}
	/**
	 * Returns true if there is at least one server who is still attending to a customer.
	 * @param servicePosts
	 * @return
	 */
	protected boolean stillCurrentServices(ArrayList<Server> servicePosts) {
		boolean x = false;
		for(Server server: servicePosts) {
			if(!server.isAvailable()) {
				x = true;
				break;
			}
		}
		return x;
	}
	/**
	 * Handles all the service completion events, if any.
	 * @param time
	 */
	protected abstract void checkForServiceCompletion(int time);
	/**
	 * Handles all the service start events, if any.
	 * @param time
	 */
	protected abstract void checkForServiceStart(int time);
	/**
	 * Handles all the arrival events, if any.
	 * @param time
	 */
	protected abstract void checkForArrivals(int time);
	/**
	 * Returns an integer value representing the next time an event will happen.
	 * @return
	 */
	protected abstract int updateTime();

}
