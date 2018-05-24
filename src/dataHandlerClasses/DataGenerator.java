package dataHandlerClasses;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */

public class DataGenerator {
	private String directory;
	
	public DataGenerator(String directory) {
		this.directory = directory;
	}
	
	public void generateDataFiles(int n) throws FileNotFoundException {
		
		PrintWriter dataFiles = new PrintWriter(new File(directory, "dataFiles.txt"));
		
		for(int i = 1; i<=n; i++) {
			dataFiles.println("data_"+i+".txt");
		}
		
		dataFiles.close();
		
		generateData();
		
	}
	
	private void generateData() throws FileNotFoundException {
		Scanner dataFiles = new Scanner(new File(directory, "dataFiles.txt"));
		Random customerAmount = new Random(); 
		
		while(dataFiles.hasNextLine()) {
			String fileName = dataFiles.nextLine();
			int x = customerAmount.nextInt(70); //has maximum amount of customers;
			createDataFile(fileName, x);
		}
		
		dataFiles.close();
	}
	
	private void createDataFile(String fileName, int customers) throws FileNotFoundException {
		Random time = new Random();
		
		PrintWriter file = new PrintWriter(new File(directory, fileName));
		int arrivalTime = 0;
		for(int i = 0; i<customers; i++) {
			arrivalTime += time.nextInt(3); //random incremental arrival time per customer
			int serviceTime = time.nextInt(100); //Has maximum service time
			
			file.printf("%-10d %-10d", arrivalTime, serviceTime);
			file.println("");
			
		}
		file.close();
	}

}
