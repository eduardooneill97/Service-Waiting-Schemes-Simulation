package dataHandlerClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import models.Customer;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class OutputGenerator {
	
	private String directory;
	
	public OutputGenerator(String directory) {
		this.directory = directory;
	}
	/**
	 * Generates an error file for the specified data file with the given error message.
	 * @param fileName
	 * @param error
	 * @throws FileNotFoundException
	 */
	public void generateErrorFile(String fileName, String error) throws FileNotFoundException {
		PrintWriter wtr = new PrintWriter(new File(directory, fileName.substring(0, fileName.length()-4)+"_OUT.txt"));
		
		wtr.println(error);
		
		wtr.close();
	}
	/**
	 * Generates the output file for the specified input file using the results from the input.
	 * @param fileName
	 * @param listOfResults
	 * @throws FileNotFoundException
	 */
	public void generateOutputFile(String fileName, ArrayList<Customer>[] listOfResults) throws FileNotFoundException {
		PrintWriter wtr = new PrintWriter(new File(directory, fileName.substring(0, fileName.length()-4)+"_OUT.txt"));
		wtr.println("Number of customers is: "+listOfResults[0].size());
		
		int t = 1;
		for(int i = 0; i<3; i++) {
			
			int serviceCompletion = timeOfServiceCompletion(listOfResults[i]);
			double avgWT = avgWaitingTime(listOfResults[i]);
			double avgEarlierCust = avgEarlierCustomers(listOfResults[i]);
			
			wtr.printf("SLMS %d: %-8d %-8.2f %-8.2f\n", t,serviceCompletion, avgWT, avgEarlierCust);
			t +=2;
		}
		t = 1;
		for(int i = 3; i<6; i++) {
			
			int serviceCompletion = timeOfServiceCompletion(listOfResults[i]);
			double avgWT = avgWaitingTime(listOfResults[i]);
			double avgEarlierCust = avgEarlierCustomers(listOfResults[i]);
			
			wtr.printf("MLMS %d: %-8d %-8.2f %-8.2f\n", t,serviceCompletion, avgWT, avgEarlierCust);
			t +=2;
		}
		
		t = 1;
		for(int i = 6; i<9; i++) {
			
			int serviceCompletion = timeOfServiceCompletion(listOfResults[i]);
			double avgWT = avgWaitingTime(listOfResults[i]);
			double avgEarlierCust = avgEarlierCustomers(listOfResults[i]);
			
			wtr.printf("MLMSBLL %d: %-8d %-8.2f %-8.2f\n", t,serviceCompletion, avgWT, avgEarlierCust);
			t +=2;
		}
		
		t = 1;
		for(int i = 9; i<12; i++) {
			
			int serviceCompletion = timeOfServiceCompletion(listOfResults[i]);
			double avgWT = avgWaitingTime(listOfResults[i]);
			double avgEarlierCust = avgEarlierCustomers(listOfResults[i]);
			
			wtr.printf("MLMSBWT %d: %-8d %-8.2f %-8.2f\n", t,serviceCompletion, avgWT, avgEarlierCust);
			t +=2;
		}
		
		wtr.close();
		
	}
	
	private int timeOfServiceCompletion(ArrayList<Customer> arr) {
		return arr.get(arr.size()-1).getDepartureTime();
	}
	
	private double avgWaitingTime(ArrayList<Customer> arr) {
		int sum = 0;
		
		for(int i = 0; i<arr.size(); i++) {
			sum += arr.get(i).getServiceStart() - arr.get(i).getArrivalTime();
		}
		
		double avg = (double)sum/arr.size();
		
		return avg;
	}
	
	private double avgEarlierCustomers(ArrayList<Customer> arr) {
		int sum = 0;
		
		for(int i = 0; i<arr.size(); i++) {
			for(Customer cust: arr) {
				if(arr.get(i).getArrivalTime() < cust.getArrivalTime() && arr.get(i).getServiceStart() > cust.getServiceStart())
					sum++;
			}
		}
		
		double avg = (double)sum/arr.size();
		
		return avg;
		
	}

}
