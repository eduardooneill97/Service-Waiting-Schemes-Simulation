package models;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class Customer {
	
	private int arrivalTime;
	private int serviceTime;
	private int departureTime;
	private int serviceStart;
	private int index;
	
	
	public Customer(int arrival, int service, int index) {
		this.arrivalTime = arrival;
		this.serviceTime = service;
		this.departureTime = 0;
		this.serviceStart = 0;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public int getServiceStart() {
		return serviceStart;
	}


	public void setServiceStart(int serviceStart) {
		this.serviceStart = serviceStart;
	}


	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(int serviceTime) {
		this.serviceTime = serviceTime;
	}

	public int getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(int departureTime) {
		this.departureTime = departureTime;
	}

	public String toString() {
		return this.arrivalTime+" "+this.serviceTime;
	}
	
	@Override
	public Object clone() {
		return new Customer(arrivalTime, serviceTime, index);
	}
	
}
