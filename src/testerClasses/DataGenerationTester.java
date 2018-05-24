package testerClasses;

import java.io.FileNotFoundException;
import java.util.Arrays;

import dataHandlerClasses.DataGenerator;
import dataHandlerClasses.DataReader;
import models.Customer;
import models.CustomerComparator;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class DataGenerationTester {
	
	public static void main(String[] args) throws FileNotFoundException {
		DataGenerator data = new DataGenerator("inputFiles");
		data.generateDataFiles(80);
		DataReader reader = new DataReader("inputFiles");
		Customer[] x = reader.generateCustomers("data_1.txt");
		for(Customer y: x) {
			System.out.println(y);
		}
		Arrays.sort(x,  new CustomerComparator());
		for(Customer y: x) {
			System.out.println(y);
		}
	}

}
