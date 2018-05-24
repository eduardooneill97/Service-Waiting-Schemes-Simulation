package models;
/**
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 */
import java.util.Comparator;

public class CustomerComparator implements Comparator<Customer>{

	@Override
	public int compare(Customer arg0, Customer arg1) {
		if(arg0.getArrivalTime()>arg1.getArrivalTime())return 1;
		if(arg0.getArrivalTime()<arg1.getArrivalTime())return -1;
		return 0;
	}

}
