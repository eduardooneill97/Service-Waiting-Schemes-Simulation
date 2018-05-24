package serviceClasses;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import dataHandlerClasses.DataReader;
import dataHandlerClasses.OutputGenerator;
import models.Customer;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class Main {
	
	public static void main(String[] args) throws InterruptedException, FileNotFoundException{
		String directory = "inputFiles";
		DataReader r = new DataReader(directory);
		OutputGenerator gen = new OutputGenerator(directory);
		ArrayList<String> fileNames = r.getFileNames("dataFiles.txt");
		
		for(int i = 0; i<fileNames.size(); i++) {
			try {
				Customer[] data = r.generateCustomers(fileNames.get(i));
				
				if(data == null || data.length == 0) {
					gen.generateErrorFile(fileNames.get(i), "Input file does not meet the expected format or it is empty.");
					continue;
				}
				
				Thread sim = new Thread(new ServiceSimulator(data, directory, fileNames.get(i)));
				sim.start();
				sim.join();
			}catch(FileNotFoundException e){
				gen.generateErrorFile(fileNames.get(i), "Input file not found.");
			}
			
		}
	}

}
