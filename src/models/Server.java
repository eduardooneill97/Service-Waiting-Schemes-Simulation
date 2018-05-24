package models;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class Server {
	
	private Customer currentCustomer;
	private int nextAvailableTime;
	
	public Server() {
		currentCustomer = null;
	}

	public Customer getCurrentCustomer() {
		return currentCustomer;
	}

	public void setCurrentCustomer(Customer currentCustomer) {
		this.currentCustomer = currentCustomer;
		nextAvailableTime = currentCustomer.getServiceStart() + currentCustomer.getServiceTime();
	}
	
	public boolean isServiceCompleted(int currentTime) {
		return nextAvailableTime == currentTime;
	}
	
	public boolean isAvailable() {
		return currentCustomer == null;
	}
	
	public Customer becomeAvailable() {
		Customer ctr = currentCustomer;
		currentCustomer = null;
		nextAvailableTime = 0;
		return ctr;
	}

	public int getNextAvailableTime() {
		return nextAvailableTime;
	}

}
