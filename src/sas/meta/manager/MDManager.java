package sas.meta.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.sas.metadata.remote.CMetadata;
import com.sas.metadata.remote.CustomAssociation;
import com.sas.metadata.remote.MdException;
import com.sas.metadata.remote.MdFactory;
import com.sas.metadata.remote.MdFactoryImpl;
import com.sas.metadata.remote.MdOMIUtil;
import com.sas.metadata.remote.MdOMRConnection;
import com.sas.metadata.remote.MdObjectBase;
import com.sas.metadata.remote.MdObjectStore;

import com.sas.metadata.remote.PhysicalTable;
import com.sas.metadata.remote.Transformation;

import json.manager.JSONProducer;
import sas.meta.obj.LinReport;
import sas.meta.obj.LinTable;
import sas.meta.tag.obj.MetaTagList;
import utils.props.PropsGeneral;




@SuppressWarnings("rawtypes")
public class MDManager {
	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getRootLogger();
	/**
	* The following statements instantiate the object factory.
	*/
	private MdFactory _factory = null;
	private MdObjectStore store = null;
	
	
	private MDParser mdchain;
	private MetaTagList metaTagList;
	
	private PropsGeneral prop;
	
	public void close() {
		try {
			_factory.dispose();
			store.dispose();
			mdchain.close();
		}catch(Exception e) {}
	}
	
	
	/**
	 * Connection parameters
	 * This are the default pars, used for SASBAP environment
	 */
	private String serverName = "sasserver";
	private String serverPort = "8561";
	private String serverUser = "sasdemo";
	private String serverPass = "Orion123";
	

	/**
	 *  Structure Table Lineage
	 *  Used to create Lists
	 */
	
	
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerPort() {
		return serverPort;
	}

	public void setServerPort(String serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerUser() {
		return serverUser;
	}

	public void setServerUser(String serverUser) {
		this.serverUser = serverUser;
	}

	public String getServerPass() {
		return serverPass;
	}

	public void setServerPass(String serverPass) {
		this.serverPass = serverPass;
	}

	
	/**
	* Pointing constructor
	*/
	public MDManager(){
		prop = new PropsGeneral();
		
		this.serverName = prop.getMetaServerName();
		this.serverPort = prop.getMetaServerPort();
		this.serverUser = prop.getMetaServerUser();
		this.serverPass = prop.getMetaServerPass();
		// Calls the factory’s constructor
		initializeFactory();
	}
	
	
	/**
	 * Init _factory
	 */
	private void initializeFactory(){
		try{
			//	Initializes the factory. The Boolean parameter is used to
			// determine if the application is running in a remote or local
			// environment. If the data does not need to be accessible across
			// remote JVMs, then "false" can be used, as shown here.
			_factory = new MdFactoryImpl(false);
			// Defines debug logging, but does not turn it on.
			boolean debug = false;
			if (debug){
				_factory.setDebug(false);
				_factory.setLoggingEnabled(false);
				//	Sets the output streams for logging. The logging output can be
				// directed to any OutputStream, including a file.
				_factory.getUtil().setOutputStream(System.out);
				_factory.getUtil().setLogStream(System.out);
			}
		}
		catch (Exception e){e.printStackTrace();}
	}
	
	/**
	* The following statements define variables for SAS Metadata Server
	* connection properties, instantiate a connection factory, issue
	* the makeOMRConnection method, and check exceptions for error conditions.
	*
	*/
	public boolean connectToServer(){
		
		try{
			MdOMRConnection connection = _factory.getConnection();
			/*connection.makeISecurityConnection(serverName,
											serverPort,
											serverUser,
											serverPass);
			*/
			// This statement makes the connection to the server.
			connection.makeOMRConnection(
											serverName,
											serverPort,
											serverUser,
											serverPass
										);
			// The following statements define error handling and error
			// reporting messages.
			
		}
		catch (MdException e){
			Throwable t = e.getCause();
			if (t != null){
				String ErrorType = e.getSASMessageSeverity();
				String ErrorMsg = e.getSASMessage();
				if (ErrorType == null){
					// If there is no SAS server message, write a Java/CORBA message.
				}
				else{
					//Using the SAS Java Metadata Interface Getting Information About Repositories 29
					// If there is a message from the server:
					System.out.println(ErrorType + ": " + ErrorMsg);
				}
				if (t instanceof org.omg.CORBA.COMM_FAILURE){
					// If there is an invalid port number or host name:
					System.out.println(e.getLocalizedMessage());
				}
				else if (t instanceof org.omg.CORBA.NO_PERMISSION){
					// If there is an invalid user ID or password:
					System.out.println(e.getLocalizedMessage());
				}
			}
			else{
				// If we cannot find a nested exception, get message and print.
				System.out.println(e.getLocalizedMessage());
			}
			// If there is an error, print the entire stack trace.
			e.printStackTrace();
			return false;
		} catch (java.rmi.RemoteException e) {
			e.printStackTrace();
			return false;
		}
		// If no errors occur, then a connection is made.
		mdchain = new MDParser( _factory );
		
	return true;
	}
	
	
	/**
	 * getReportSources( REPORT_VA )
	 * Extract tables from reports. 
	 * 
	 * @param report
	 * @return
	 */
	private List<PhysicalTable> getReportSources(Transformation report){
		List<PhysicalTable> ret = new ArrayList<PhysicalTable>();
		try{
			Iterator assocItr = report.getCustomAssociations().iterator();
			while(assocItr.hasNext()){
				CustomAssociation assocs = (CustomAssociation)assocItr.next();
				Iterator assocObjs = assocs.getAssociatedObjects().iterator();
				while(assocObjs.hasNext()){
					/**
					 * NOTE: Method to discover metadata object type 
					 */
					MdObjectBase genObj = (MdObjectBase)assocObjs.next();
					if("PhysicalTable".equals(genObj.getCMetadataType()))
						ret.add((PhysicalTable)genObj);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		
		return ret;
	}
	
	
	private LinReport getLinReport(Transformation rep){
		LinReport ret = new LinReport();
		try{
			ret.setId(rep.getId());
			ret.setName(rep.getName());
			ret.setPath(_factory.getOMIUtil().getObjectPath(store, rep, false));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	public void getImpact(String type, String omrPath, String omrNm){
		if("table".equals(type)){
			getImpactTable(omrPath);
		}
		if("report".equals(type)){
			getImpactReport(omrPath, omrNm);
		}
	}
	
	


	private List<LinReport> getImpactTable(String omrNm){
		List<LinReport> ret = new ArrayList<LinReport>();
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			
			System.out.print("Retrieving Tbls ... ");
			List<PhysicalTable> metaTbls = mdchain.getTables(omrNm);
			System.out.println("... found: [" + metaTbls.size() + "]");
			
			
			System.out.print(" -> Getting MetaImpact: ");
			for(PhysicalTable metaSrc : metaTbls){
				System.out.print(".");
				LinTable src = mdchain.getTableLineage(metaSrc);
				JSONProducer jm = new JSONProducer();
				jm.publish(src);
			}
			System.out.println(" done!");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	
	

	private List<LinReport> getImpactReport(String omrPath, String omrNm){
		List<LinReport> ret = new ArrayList<LinReport>();
		/** To connect to metadata */
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}

			/** Set UP Common Objects */
			mdchain.setUp(foundation, store);
			
			
			/** Logicss */ 
			System.out.print("Retrieving Reports ... ");
			List<Transformation> metaReports = mdchain.getReports(omrPath, omrNm);
			System.out.println("... found: [" + metaReports.size() + "]");
			
			
			System.out.print(" -> Getting MetaImpact: ");
			for(Transformation metaRep : metaReports){
				System.out.print(".");
				LinReport lrp = getLinReport(metaRep);
				for(PhysicalTable metasrc : getReportSources(metaRep)){
					LinTable src = mdchain.getTableLineage(metasrc);
					lrp.addSource(src);
				}
				ret.add(lrp);
				JSONProducer jm = new JSONProducer();
				jm.publish(lrp);
			}
			System.out.println(" done!");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

	
	/**
	 * Fill a MetaTag List of Transformation that it is used by Impact Methods to attach tags
	 * instead of Meta Transformations
	 * 
	 * @param print
	 */
	public void fillMetaTagLineage(boolean print){
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			System.out.print("Retrieving MetaTags ... ");
			metaTagList = mdchain.getMetaTagList();
			System.out.println(" ... found: [" + metaTagList.getLength() + "]");
			if(print)
				metaTagList.printList();
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public LinReport getSimpleReport(String omrNm){
		LinReport ret = null;
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			return mdchain.getReport(omrNm);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	/**
	 * 
	 * @param omrPath
	 * @return
	 */
	public List<LinReport> getSimpleReportList(List<String> metaIds){
		List<LinReport> ret = new ArrayList<LinReport>();
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			return mdchain.getReportListById(metaIds);
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	
	public MetaTagList getMetaTagList(){
		return metaTagList;
	}
	
	
	public void updatePassword(String username, String pswd) {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			mdchain.updateLoginPassword(username, pswd);
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void getUserInfos() {
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			mdchain.getAuthInfos();
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public List<String> getInfoJob() {
		List<String> ret = null;
		try{
			store = _factory.createObjectStore();
			MdOMIUtil omiUtil = _factory.getOMIUtil();
			List reposList = omiUtil.getRepositories();
			Iterator iter = reposList.iterator();
			CMetadata foundation = null;
			// Create only in Foundation
			while (iter.hasNext()){
				CMetadata tmp = (CMetadata) iter.next();
				if(tmp.getName().equals("Foundation"))
					foundation = tmp;
			}
			if(foundation == null){
				store.dispose();
			}
			
			mdchain.setUp(foundation, store);
			
			ret = mdchain.getInfoJob();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	
	
}
