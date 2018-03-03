package utils.props;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

public final class PropsGeneral implements Props{
	
	private final String _PROPS_FOLDER = "props";
	private final String _PROPS_CONFIG_NAME = "config.properties";
	
	private boolean configFound;
	
	private Properties prop = new Properties();
	
	private String linServerName;
	

	private String linServerPort;
	private String linServerUser;
	private String linServerPass;
	
	private String linRestURL;
	private String jsonOutPath;
	
	private String vaServerAddr;
	private String davXMLPath;
	private boolean davLogUrl;

	private String metaServerName;
	private String metaServerPort;
	private String metaServerUser;
	private String metaServerPass;
	
	private String linPathRequest;
	private String ibmCSVPath;
	
	private String omrNm;
	private String omrTp;
	
	private String omrPath;
	private String omrFilter;
	
	
	private boolean actionMetascan;
	private boolean actionMetaUseTag;
	private boolean actionXMLDownload;
	private boolean actionXMLParse;
	private boolean actionLinUpload;
	private boolean actionIBMProduce;
	
	private boolean cleanXML;
	private boolean cleanJSON;
	private boolean cleanIBM;
	private boolean cleanLIN;
	
	private boolean versionXML;
	private boolean versionJSON;
	private boolean versionIBM;
	private boolean versionLIN;
	
	private String versionStyle;
	
	private String wgetPath;
	

	public void loadProps(){
		/**
		 * Load Project Properties
		 */
		
		InputStream input = null;
		try{
			input = new FileInputStream(_PROPS_FOLDER + "/" + _PROPS_CONFIG_NAME);
			prop.load(input);
			
			linServerName = prop.getProperty("lineage.env.addr");
			linServerPort = prop.getProperty("lineage.env.port");
			linServerUser = prop.getProperty("lineage.user.name");
			linServerPass = prop.getProperty("lineage.user.pwd");
			linPathRequest = prop.getProperty("lineage.req.path");
			
			vaServerAddr = prop.getProperty("va.env.addr");
			davXMLPath = prop.getProperty("dav.xml.path");
			davLogUrl = Boolean.parseBoolean(prop.getProperty("dav.log.url"));
			
			
			metaServerName = prop.getProperty("meta.env.addr");
			metaServerPort = prop.getProperty("meta.env.port");
			metaServerUser = prop.getProperty("meta.user.name");
			metaServerPass = prop.getProperty("meta.user.pwd");
			
			jsonOutPath = prop.getProperty("json.out.path");
			ibmCSVPath = prop.getProperty("ibm.csv.path");
	
			linRestURL = "http://" + linServerName + ":" + linServerPort +"/SASWIPClientAccess/rest/relsvc/relationships";
			
			omrNm = prop.getProperty("meta.omr.obj.name");
			omrTp = prop.getProperty("meta.omr.obj.type");
			omrPath = prop.getProperty("meta.omr.obj.folder.path");
			omrFilter = prop.getProperty("meta.omr.obj.folder.filter");
			
			
			actionMetascan 		= Boolean.parseBoolean(prop.getProperty("action.meta.scan"));   
			actionMetaUseTag	= Boolean.parseBoolean(prop.getProperty("action.meta.usetag"));
			actionXMLDownload 	= Boolean.parseBoolean(prop.getProperty("action.xml.download"));
			actionXMLParse 		= Boolean.parseBoolean(prop.getProperty("action.xml.parse"));   
			actionLinUpload 	= Boolean.parseBoolean(prop.getProperty("action.lin.upload"));  
			actionIBMProduce 	= Boolean.parseBoolean(prop.getProperty("action.ibm.produce")); 
			
			cleanXML	= Boolean.parseBoolean(prop.getProperty("clean.xml"));
			cleanJSON	= Boolean.parseBoolean(prop.getProperty("clean.json")); 
			cleanIBM	= Boolean.parseBoolean(prop.getProperty("clean.ibm")); 
			cleanLIN 	= Boolean.parseBoolean(prop.getProperty("clean.lineage"));
			
			versionXML 	= Boolean.parseBoolean(prop.getProperty("version.xml"));
			versionJSON = Boolean.parseBoolean(prop.getProperty("version.json"));
			versionIBM 	= Boolean.parseBoolean(prop.getProperty("version.ibm"));
			versionLIN 	= Boolean.parseBoolean(prop.getProperty("version.lin"));
			
			versionStyle = prop.getProperty("version.style");
			
			wgetPath = prop.getProperty("dav.wget.path");
			
			configFound = true;
			
		}catch(Exception e){
			
			actionMetascan 		= true;
			actionXMLDownload 	= false;
			actionXMLParse 		= false;
			actionLinUpload 	= false;
			actionIBMProduce 	= false;
			
			System.err.println("SAS Metadata Lineage use a <config.properties> file for all settings.");
			System.err.println("Please, provide a proper <config.properties> file.");
			
			configFound = false;
		}
	}
	
	public boolean isVersionXML() {
		return versionXML;
	}

	public void setVersionXML(boolean versionXML) {
		this.versionXML = versionXML;
	}

	public boolean isVersionJSON() {
		return versionJSON;
	}

	public void setVersionJSON(boolean versionJSON) {
		this.versionJSON = versionJSON;
	}

	public boolean isVersionIBM() {
		return versionIBM;
	}

	public void setVersionIBM(boolean versionIBM) {
		this.versionIBM = versionIBM;
	}

	public boolean isVersionLIN() {
		return versionLIN;
	}

	public void setVersionLIN(boolean versionLIN) {
		this.versionLIN = versionLIN;
	}

	public String getVersionStyle() {
		return versionStyle;
	}

	public void setVersionStyle(String versionStyle) {
		this.versionStyle = versionStyle;
	}

	public boolean isActionMetaUseTag() {
		return actionMetaUseTag;
	}

	public void setActionMetaUseTag(boolean actionMetaUseTag) {
		this.actionMetaUseTag = actionMetaUseTag;
	}

	public boolean isCleanLIN() {
		return cleanLIN;
	}
	

	public void setCleanLIN(boolean cleanLIN) {
		this.cleanLIN = cleanLIN;
	}
	

	public String getDavXMLPath() {
		return davXMLPath;
	}
	

	public void setDavXMLPath(String davXMLPath) {
		this.davXMLPath = davXMLPath;
	}
	

	public void printProps(){
		String print = "";
		
		print += "------------------------------" ;
		print += "\n" + "Lineage URL	: " + linRestURL;
		print += "\n" + "Metadata Server: " + metaServerName + ":" + metaServerPort;

		print += "\n" + "Ling Credential: " + linServerUser + "::Base64(" + Base64.getEncoder().encode(linServerPass.getBytes()) + ")";
		print += "\n" + "Meta Credential: " + metaServerUser + "::Base64(" + Base64.getEncoder().encode(metaServerPass.getBytes()) + ")";
		
		print += "\n" + "Actions?: " ;
		print += "\n" + "- MetaScan: 		" + actionMetascan + " - with Tag: " + actionMetaUseTag;
		print += "\n" + "- XMLDown: 		" + actionXMLDownload ;
		print += "\n" + "- XMLParse: 		" + actionXMLParse ;
		print += "\n" + "- Lineage Upload: 	" + actionLinUpload ;
		print += "\n" + "- IBM Produce: 	" + actionIBMProduce ;
		
		print += "\n" + "------------------------------" ;
		
		System.out.println(print);
	}
	
	public PropsGeneral (){
		loadProps();
	}

	public String getLinServerName() {
		return linServerName;
	}
	

	public void setLinServerName(String linServerName) {
		this.linServerName = linServerName;
	}
	

	public String getLinServerPort() {
		return linServerPort;
	}
	

	public void setLinServerPort(String linServerPort) {
		this.linServerPort = linServerPort;
	}
	

	public String getLinServerUser() {
		return linServerUser;
	}
	

	public void setLinServerUser(String linServerUser) {
		this.linServerUser = linServerUser;
	}
	

	public String getLinServerPass() {
		return linServerPass;
	}
	

	public void setLinServerPass(String linServerPass) {
		this.linServerPass = linServerPass;
	}
	

	public String getMetaServerName() {
		return metaServerName;
	}
	

	public void setMetaServerName(String metaServerName) {
		this.metaServerName = metaServerName;
	}
	

	public String getMetaServerPort() {
		return metaServerPort;
	}
	

	public void setMetaServerPort(String metaServerPort) {
		this.metaServerPort = metaServerPort;
	}
	

	public String getMetaServerUser() {
		return metaServerUser;
	}
	

	public void setMetaServerUser(String metaServerUser) {
		this.metaServerUser = metaServerUser;
	}
	

	public String getMetaServerPass() {
		return metaServerPass;
	}
	

	public void setMetaServerPass(String metaServerPass) {
		this.metaServerPass = metaServerPass;
	}
	

	public String getLinPathRequest() {
		return linPathRequest;
	}
	

	public void setLinPathRequest(String linPathRequest) {
		this.linPathRequest = linPathRequest;
	}
	
	

	public String getJsonOutPath() {
		return jsonOutPath;
	}
	

	public void setJsonOutPath(String jsonOutPath) {
		this.jsonOutPath = jsonOutPath;
	}
	

	public String getLinRestURL() {
		return "http://" + linServerName + ":" + linServerPort +"/SASWIPClientAccess/rest/relsvc/relationships";
	}

	public String getVaServerAddr() {
		return vaServerAddr;
	}
	

	public void setVaServerAddr(String vaServerAddr) {
		this.vaServerAddr = vaServerAddr;
	}

	public String getOmrNm() {
		return omrNm;
	}
	

	public void setOmrNm(String omrNm) {
		this.omrNm = omrNm;
	}
	

	public String getOmrTp() {
		return omrTp;
	}
	

	public void setOmrTp(String omrTp) {
		this.omrTp = omrTp;
	}

	public String getIbmCSVPath() {
		return ibmCSVPath;
	}
	

	public void setIbmCSVPath(String ibmCSVPath) {
		this.ibmCSVPath = ibmCSVPath;
	}

	public boolean isActionMetascan() {
		return actionMetascan;
	}
	

	public void setActionMetascan(boolean actionMetascan) {
		this.actionMetascan = actionMetascan;
	}
	

	public boolean isActionXMLDownload() {
		return actionXMLDownload;
	}
	

	public void setActionXMLDownload(boolean actionXMLDownload) {
		this.actionXMLDownload = actionXMLDownload;
	}
	

	public boolean isActionXMLParse() {
		return actionXMLParse;
	}
	

	public void setActionXMLParse(boolean actionXMLParse) {
		this.actionXMLParse = actionXMLParse;
	}
	

	public boolean isActionLinUpload() {
		return actionLinUpload;
	}
	

	public void setActionLinUpload(boolean actionLinUpload) {
		this.actionLinUpload = actionLinUpload;
	}
	

	public boolean isActionIBMProduce() {
		return actionIBMProduce;
	}
	

	public void setActionIBMProduce(boolean actionIBMProduce) {
		this.actionIBMProduce = actionIBMProduce;
	}

	public String getOmrPath() {
		return omrPath;
	}
	

	public void setOmrPath(String omrPath) {
		this.omrPath = omrPath;
	}
	

	public String getOmrFilter() {
		return omrFilter;
	}
	

	public void setOmrFilter(String omrFilter) {
		this.omrFilter = omrFilter;
	}

	public boolean isCleanXML() {
		return cleanXML;
	}
	

	public void setCleanXML(boolean cleanXML) {
		this.cleanXML = cleanXML;
	}
	

	public boolean isCleanJSON() {
		return cleanJSON;
	}
	

	public void setCleanJSON(boolean cleanJSON) {
		this.cleanJSON = cleanJSON;
	}
	

	public boolean isCleanIBM() {
		return cleanIBM;
	}
	

	public void setCleanIBM(boolean cleanIBM) {
		this.cleanIBM = cleanIBM;
	}
	

	public String getWgetPath() {
		return wgetPath;
	}

	public void setWgetPath(String wgetPath) {
		this.wgetPath = wgetPath;
	}
	

	public boolean isConfigFound() {
		return configFound;
	}

	public void setConfigFound(boolean configFound) {
		this.configFound = configFound;
	}

	public boolean isDavLogUrl() {
		return davLogUrl;
	}

	public void setDavLogUrl(boolean davLogUrl) {
		this.davLogUrl = davLogUrl;
	}
	
	
	
	
	
	
}
