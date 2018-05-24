package models;
/**
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 */
import java.util.ArrayList;
import java.util.Deque;

public class LineMonitor {
	
	private ArrayList<Deque<Customer>> lines;
	private ArrayList<Server> servicePosts;
	
	public LineMonitor(ArrayList<Deque<Customer>> lines, ArrayList<Server> servicePosts) {
		this.lines = lines;
		this.servicePosts = servicePosts;
	}
	/**
	 * Makes all possible transfers at the moment the method is invoked.
	 */
	public void handleTransfers() {
		while(transferPending()) {
			ArrayList<Integer> indexes = new ArrayList<Integer>();
			for(int i = 0; i<lines.size(); i++) {
				if(benefitFromTransfer(i))
					indexes.add(i);
			}
			transfer(earliestNewArrival(indexes));
		}
		
	}
	/**
	 * Transfers the last customer in line i to the shortest line from him.
	 * @param i
	 */
	private void transfer(int i) {
		lines.get(shortestLine(i)).add(lines.get(i).removeLast());
	}
	/**
	 * Returns true if and only if there are still transfers to be made.
	 * @return
	 */
	private boolean transferPending() {
		for(int i = 0; i<lines.size(); i++) {
			if(benefitFromTransfer(i))
				return true;
		}
		return false;
	}
	/**
	 * Returns true if the last customer at the specified line would benefit from a transfer.
	 * @param i
	 * @return
	 */
	private boolean benefitFromTransfer(int i) {
		for(int j = 0; j<lines.size(); j++) {
			if(lines.get(j).size() < lines.get(i).size()-1)
				return true;
		}
		return false;
	}
	/**
	 * Looks for the shortest line starting from the specified index. Returns the index of the shortest line found.
	 * @param start
	 * @return
	 */
	private int shortestLine(int start) {
		int index = 0;
		int min = lines.get(0).size();
		if(start != lines.size()-1) {
			index = start + 1;
			min = lines.get(index).size();
		}
		if(!servicePosts.get(index).isAvailable())
			min++;
		int i = index;
		while(i%lines.size() != start) {
			int sizeToCompare = lines.get(i%lines.size()).size();
			
			if(!servicePosts.get(i%lines.size()).isAvailable())
				sizeToCompare++;
			if(sizeToCompare<min) {
				min = sizeToCompare;
				index = i%lines.size();
			}
			i++;
		}
		return index;
	}
	/**
	 * Determines who amongst the last customers in each line has higher priority to be transfered. Returns the index of his line.
	 * @param x
	 * @return
	 */
	private int earliestNewArrival(ArrayList<Integer> x) {
		int index = x.get(0);
		int min = lines.get(x.get(0)).getLast().getIndex();
		for(int i = 0; i<x.size(); i++) {
			if(lines.get(x.get(i)).getLast().getIndex() < min) {
				min = lines.get(x.get(i)).getLast().getIndex();
				index = x.get(i);
			}
		}
		return index;
	}

}
