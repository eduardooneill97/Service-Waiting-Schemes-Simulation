package policyClasses;

import java.util.ArrayList;

import models.Customer;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public interface PolicyEnforcer {
	/**
	 * Takes an array of customers and starts the simulation according to the particular policy's specifications and with the specified amount of servers.
	 * @param customers
	 * @param serverCount
	 * @return
	 */
	ArrayList<Customer> proccess(Customer[] customers, int serverCount);
	String getPolicyName();

}
