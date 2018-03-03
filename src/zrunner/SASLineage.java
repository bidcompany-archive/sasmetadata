package zrunner;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ibm.manager.IBMProducer;
import sas.lineage.manager.LineageManager;
import sas.meta.manager.MDManager;
import utils.file.CleanManager;
import utils.file.Dav;
import utils.props.PropsGeneral;
import xml.manager.XMLReportReader;

public class SASLineage {
	
	private final String _PROPS_FOLDER = "props";
	private final String _LOG4J_FILE_NAME = "log4j.properties";
	
	
	Logger logger = Logger.getLogger(this.getClass());
	private Properties p4j = new Properties();
	
	private long startTime;
	
	private PropsGeneral prop;
	private CleanManager cm;
	
	private MDManager md;
	private XMLReportReader xml;
	
	private Dav dav;
	
	private LineageManager lm;
	private IBMProducer ibm;
	
	
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
			dav = new Dav();
			cm = new CleanManager();
			xml = new XMLReportReader();
			lm = new LineageManager();
			ibm = new IBMProducer();
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
		SASLineage sas = new SASLineage();
		System.out.println(sas);
	}
	
	
	
	public SASLineage(){
		/** Set start Time */
		startTimer();
		System.out.println("*** Begin ***");
		System.out.println();
		
		
		
		
		/** Load Properties */
		if(loadProps()) {
			

			/** Connect 2 MetadataServer and Setup XML */
			boolean initObjs = initObjs();
			
			/** Clean Directories */
			cm.clean();
			
			if(initObjs){
				
				if(prop.isActionMetascan()){
					/** Connect to MetadataServer */
					if(md.connectToServer()) {
						
					
						/** 1. Metadata TAG Analysis 
						 		MDManager -> getMetaTagLineage (print::true|false)
									MDParser -> getMetaTagList :: fill Meta Tag ArrayList with MetaTag transformations 
						 * 
						 * */
						if(prop.isActionMetaUseTag())
							md.fillMetaTagLineage(false);
						
						
						/** 2. Metadata Impact Analysis 
						 		MDManager -> getImpact ( report|table , obj_name ): if obj_name = [ALL] it impact all object
								  -> getImpactTable (obj_name) | -> getImpact Report (obj_name)
									 MDParser -> getReports (obj_name) :: List<Meta Transformation> 
									  for each Transf: MDParser -> getTableLineage: 
										 1. setUP parsedTransformation Set + parsedTable Set + SetUp headNode as starting point
										 2. -> recursiveLinker ( Table ) >> recursive! 
										  if !parsed table :: 
											--> 2.1 search within TAGs > get Linked Transf > get Tagged Sources >> for each source recursiveLinker ( source )
											--> 2.2 if !Tagged search within Meta > get Linked Transf > get Meta Sources >> for each source recursiveLinker ( source )
									 JSONProducer -> publish :: stored all object within JSON one for each impact analysis
						 * */
						md.getImpact(prop.getOmrFilter(), prop.getOmrPath(), prop.getOmrNm());
						//md.close();
						
					}
				}
				
				
				/** 3. Download Report XML 
		 		Dav -> download ( MDManager -> getSimmpleReport ( obj_name ) ) ::
		 		 -> get a report path in order to find it within Dav repos 
		 		 MDManager -> getSimpleReeport ( obj_name )
		 		 	MDParser -> getReport ( obj_name )
		 		 Dav -> download ( REPORT ) : 
		 		 	wgetCmd reportURL :: NOTE: in order to work it needs wget binaries
		 		 	http://gnuwin32.sourceforge.net/packages/wget.htm
				 * */
				if(prop.isActionXMLDownload())
					if(md.connectToServer()) {
						dav.download( md.getSimpleReportList( dav.getAvailableReports() ) );
						//md.close();
						
					}
						
				
				/** 4. Update Json with XML infos  
				 	XMLReportReader -> parseXML :: 
				 		1. loop though all XML dowanloded by Dav and 
				 		2. -> extractItems :: for each DataSource it extract
						-> DataItems | CalcItems | PredItems | AggrItems | HierItems | GroupedItems creating an XMLSource Object 
						3. JSONProducecer -> update (metaID, XMLSource ) update the relative json (it searches within fld by MetaID) with
				 			-> all Sources of Report are updated with:
				 				- calcItem | dataItem
				 * */
				if(prop.isActionXMLParse())
					xml.parseXML();
				
				
				/** 5. Load to Lineage 
				 	LineageManager -> load :: 
						1. JSONReader -> readAll :: List<LinReport> : read all JSON and produce Java Object that can be recursively parsed
						2. SASRestClient -> post (xmlRequest :: String ) : post the JSON in an XML that can be seen on Lineage
				 * */
				if(prop.isActionLinUpload())
					lm.load();
				
				
				/** 6. Export to IBM 
				 	IBMProducer -> parse :: 
				 		1. JSONReader -> readAll :: List<LinReport> : read all JSON and produce Java Object that can be recursively parsed
				 		2. for each report (JSON) Create three types of CSV:
				 			a. Logical: containing all report connections (report + query + collection)
				 			b. Physical: containing all physical entries (db + schema + table)
				 			c. Mapping: containing transformation and table connections
				 			
				 			-> parseReport -> parseTable -> parseTransformation
				 			
				 			NOTE: all names | string are embedded within IBMInfo enumerator
				 			
				 * */
				if(prop.isActionIBMProduce())
					ibm.parse();
			}
		}
	}
	

}
