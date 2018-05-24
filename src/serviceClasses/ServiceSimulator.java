package serviceClasses;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import dataHandlerClasses.OutputGenerator;
import models.Customer;
import policyClasses.MLMSBLLPolicy;
import policyClasses.MLMSBWTPolicy;
import policyClasses.MLMSPolicy;
import policyClasses.PolicyEnforcer;
import policyClasses.SLMSPolicy;
/**
 * 
 * @author Eduardo O'Neill 801-15-5476 CIIC4020-050
 *
 */
public class ServiceSimulator implements Runnable{
	
	private Customer[] customers;
	private String directory;
	private String fileName;
	private PolicyEnforcer policy1;
	private PolicyEnforcer policy2;
	private PolicyEnforcer policy3;
	private PolicyEnforcer policy4;
	
	public ServiceSimulator(Customer[] data,String directory, String fileName) {
		this.customers = data;
		this.directory = directory;
		this.fileName = fileName;
		policy1 = new SLMSPolicy();
		policy2 = new MLMSPolicy();
		policy3 = new MLMSBLLPolicy();
		policy4 = new MLMSBWTPolicy();
	}

	@Override
	public void run() {
		ArrayList<Customer>[] results = new ArrayList[12];
		
		//Policy1 results for each server count.
		results[0]=policy1.proccess(cloneOfData(customers), 1);
		results[1]=policy1.proccess(cloneOfData(customers), 3);
		results[2]=policy1.proccess(cloneOfData(customers), 5);
		
		//Policy2 results for each server count.
		results[3] = policy2.proccess(cloneOfData(customers), 1);
		results[4] = policy2.proccess(cloneOfData(customers), 3);
		results[5] = policy2.proccess(cloneOfData(customers), 5);
		
		//Policy3 results for each server count.
		results[6] = policy3.proccess(cloneOfData(customers), 1);
		results[7] = policy3.proccess(cloneOfData(customers), 3);
		results[8] = policy3.proccess(cloneOfData(customers), 5);
		
		//Policy4 results for each server count.
		results[9] = policy4.proccess(cloneOfData(customers), 1);
		results[10] = policy4.proccess(cloneOfData(customers), 3);
		results[11] = policy4.proccess(cloneOfData(customers), 5);
		
		OutputGenerator og = new OutputGenerator(directory);
		
		try {
			og.generateOutputFile(fileName, results);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creates a clone of the specified array of customers.
	 * @param x
	 * @return
	 */
	private Customer[] cloneOfData(Customer[] x) {
		Customer[] data = new Customer[x.length];
		for(int i = 0; i<data.length; i++) {
			data[i] = (Customer)x[i].clone();
		}
		return data;
	}

}
