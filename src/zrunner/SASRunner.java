package zrunner;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import sas.meta.manager.MDManager;
import utils.props.PropsGeneral;

public class SASRunner {
	
	private final String _PROPS_FOLDER = "props";
	private final String _LOG4J_FILE_NAME = "log4j.properties";
	
	
	Logger logger = Logger.getLogger(this.getClass());
	private Properties p4j = new Properties();
	
	private long startTime;
	
	private PropsGeneral prop;	
	private MDManager md;

	
	/**
	 * Set start time
	 */
	private void startTimer(){
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Get elapsed time
	 * @param print: decide whatever print or not
	 * @return
	 */
	private long getTime(boolean print){
		long estimatedTime = System.currentTimeMillis() - startTime;
		if(print)
			System.out.println("Elapsed (s): " + estimatedTime/1000);
		return estimatedTime/1000;
	}
	
	
	/**
	 * loadProps :: load properties from config file
	 */
	private boolean loadProps(){
		boolean ret = true;
		/**
		 * Load Log4J Properties
		 */
		BasicConfigurator.configure();
		try {
			p4j.load(new FileInputStream(_PROPS_FOLDER + "/" + _LOG4J_FILE_NAME));
		} catch (Exception e) {
			ret = false;
		} 
		PropertyConfigurator.configure(p4j);
		/**
		 * Load Config Properties
		 */
		try {
			prop = new PropsGeneral();
			if(!prop.isConfigFound())
				ret = false;
		}catch(Exception e) {
			ret = false;
		}
		return ret;
	}
	
	
	
	/**
	 * Connect 2 Metadataserver
	 * @return connect
	 */
	private boolean initObjs(){
		boolean ret = true;
		try {
			md = new MDManager();
		}catch(Exception e) {
			ret = false;
		}
		return ret;
	}
	
	/**
	 * To String
	 */
	public String toString(){
		return "Elapsed: " + getTime(false) + "s";
	}
	
	
	/**
	 * Sleep for [sec]
	 * @param sec
	 */
	public void sleep(int sec){
		try{
			Thread.sleep(sec*1000);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Void Main
	 * @param args
	 */
	public static void main(String args[]){
		SASRunner sas = new SASRunner();
		System.out.println(sas);
	}
	
	

	public SASRunner(){
		/** Set start Time */
		startTimer();
		System.out.println("*** Begin ***");
		System.out.println();
		
		
		
		
		/** Load Properties */
		if(loadProps()) {
			

			/** Connect 2 MetadataServer and Setup XML */
			boolean initObjs = initObjs();
			
			if(initObjs){
				for(String str : md.getInfoJob()) {
					System.out.println(str);
				}
			}
		}
	}
	

}
