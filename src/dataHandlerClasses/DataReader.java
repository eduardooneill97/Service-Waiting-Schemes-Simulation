package dataHandlerClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

import models.Customer;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class DataReader {
	
	private String directory;
	
	public DataReader(String directory) {
		this.directory = directory;
	}
	/**
	 * Returns an array of customers from the data at the specified file
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public Customer[] generateCustomers(String fileName) throws FileNotFoundException {
		Scanner file = new Scanner(new File(directory, fileName));
		Queue<Customer> list = new ConcurrentLinkedQueue<Customer>();
		int j = 0;
		while(file.hasNext()) {
			int arrivalTime;
			int serviceTime;
			try {
				arrivalTime = file.nextInt();
				serviceTime = file.nextInt();
			}catch(NoSuchElementException e) {
				file.close();
				return null;
			}
			Customer current = new Customer(arrivalTime, serviceTime, j);
			j++;
			list.add(current);
		}
		
		file.close();
		
		Customer[] arr = new Customer[list.size()];
		for(int i = 0; i<arr.length; i++) {
			arr[i] = list.remove();
		}
		return arr;
	}
	/**
	 * Returns a list of Strings with the names retrieved from the specified file.
	 * @param dataFile
	 * @return
	 * @throws FileNotFoundException
	 */
	public ArrayList<String> getFileNames(String dataFile) throws FileNotFoundException {
		Scanner scan = new Scanner(new File(directory, dataFile));
		ArrayList<String> names = new ArrayList<String>();
		
		while(scan.hasNext()) {
			names.add(scan.nextLine());
		}
		scan.close();
		
		return names;	
	}
}
