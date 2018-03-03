package ibm.manager;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sas.meta.obj.LinReport;
import sas.meta.obj.LinTable;
import utils.props.PropsIBM;
import utils.props.PropsTag;

public class IBMFormatter {
	
	private PropsIBM ibm = new PropsIBM();
	private PropsTag jtag = new PropsTag();
	private String repName;
	private String repPath;
	private String host;
	private String server;
	
	
	private String getPathWithoutSlash(String path) {
		if(path!=null && path.startsWith("/") && path.length()>1)
			path = path.substring(1);
		return path;
		
	}
	private String getPathWithSlash(String path) {
		if(path!=null && !path.startsWith("/"))
			return "/" + path;
		if(path==null || "null".equalsIgnoreCase(path))
			return "";
		return path;
	}

	
	
	public IBMFormatter(String host, String server) {
		this.host = host;
		this.server = server;
	}



	public String getServer() {
		return server;
	}



	public void setServer(String server) {
		this.server = server;
	}
	

	public String toIbm(LinReport report){
		
		
		String ret = "";
		ret += report.getName() + "," + server + "," + getPathWithoutSlash(report.getPath()) + "," + report.getName() + "[" + report.getId() + "]";
	
		return ret;
	}
	
	

	public List<String> toIbmLST(LinTable source, String type, boolean fake){
		List<String> retLst = null;
		JSONParser parser = new JSONParser();
		
		if(fake) {
			retLst = new ArrayList<String>();
			String ret = "";
			String nm = source.getName();
			String src = "\"Source: " + source.getLibdesc().toUpperCase() + "." + source.getName().toUpperCase() + "\"";
			
			
			switch(type){
			case "BI Query Member":
				ret = "";
				ret += nm + ", " 
						+ "DATASOURCE_" + source.getName() + "," 
						+ repName + "<" + server + repPath + ">" + "," 
						+ nm + "," 
						+ src;
				
				retLst.add(ret);
				break;
				
			case "BI Collection Member":
				
				ret += nm + ", " 
						+ source.getName() + "," 
						+ source.getLibdesc() +"<" + server + source.getLibpath() + ">" + ","
						+ /* Label */ "" + "," 
						+ /* Format */ "" + "," 
						+ nm;
				
				retLst.add(ret);
				break;
				
			case "Database Column":
				
				ret += nm + ", " 
						+ host + "," 
						+ source.getDbname() + "<" + jtag.getTagLinDbms() + "=" + jtag.getTagLinDbmsSASValue() + "::" + jtag.getTagLinInstance() + "=" + jtag.getTagLinInstanceSASValue() + ">" + "," 
						+ source.getSchema() + "," 
						+ source.getName() + "," 
						+ /* Label */ "" + "," 
						+ " , , , , , , , , , ";
				
				retLst.add(ret);
				break;
				
			case "BI Relationship":
				
				ret += repName + "<" + server + repPath + ">." + "DATASOURCE_" + source.getName() + "." + nm +"," 
						+ source.getLibdesc() + "<" + server + source.getLibpath() + ">." + source.getName() + "." + nm + "," 
						+ host + "." + source.getDbname() + "<" + jtag.getTagLinDbms() + "=" + jtag.getTagLinDbmsSASValue() + "::" + jtag.getTagLinInstance() + "=" + jtag.getTagLinInstanceSASValue() + ">" + "." + source.getSchema() + "." + source.getName() + "." + nm  ; 
				
				retLst.add(ret);
				break;
				
				
			}
			
			return retLst;
			
		}
		
		
		switch(type){
		/* 	Name,Report,Description					*/
		case "BI Query Member":
			retLst = new ArrayList<String>();
			
			if(source.getXmlDItems()!=null){
				for(String dItem : source.getXmlDItems()){
					try{
						String ret = "";
						JSONObject dItemJ = (JSONObject)parser.parse(dItem);
						String nm = (String)dItemJ.get(jtag.getTagJsonXref());
						/** Source on XML referes to libname TAG which is could not be the same value as what you find within libname*/
						String src = /* (String)dItemJ.get("source") */ "\"Source: " + source.getLibdesc().toUpperCase() + "." + source.getName().toUpperCase() + "\"";
						/* Name,Query,Report,Expression,Description  */
						ret += nm + ", " 
								+ "DATASOURCE_" + source.getName() + "," 
								+ repName + "<" + server + repPath + ">" + "," 
								+ nm + "," 
								+ src;
						
						retLst.add(ret);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			
			if(source.getXmlCItems() !=null){
				for(String cItem : source.getXmlCItems()){
					try{
						String ret = "";
						JSONObject cItemJ = (JSONObject)parser.parse(cItem);
						
						String nm = (String)cItemJ.get(jtag.getTagJsonLabel());
						/** Source on XML referes to libname TAG which is could not be the same value as what you find within libname*/
						String src = /* (String)cItemJ.get("source") */ "\"Source: " + source.getLibdesc().toUpperCase() + "." + source.getName().toUpperCase() + "\"";
						String expr = (String)cItemJ.get(jtag.getTagJsonExpr());
						/* Name,Query,Report,Expression,Description  */
						ret += nm + ", " ;
						ret += "DATASOURCE_" + source.getName() + "," ; 
						ret += repName + "<" + server + repPath + ">" + "," ;
						ret += "\"" + expr + "\"" ;
						ret += "," + src;
						
						retLst.add(ret);
					
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}// TODO
			if(source.getXmlHItems() !=null){
				for(String hItem : source.getXmlHItems()){
					try{
						String ret = "";
						JSONObject hItemJ = (JSONObject)parser.parse(hItem);
						
						String nm = (String)hItemJ.get(jtag.getTagJsonLabel());
						/** Source on XML referes to libname TAG which is could not be the same value as what you find within libname*/
						String src = /* (String)cItemJ.get("source") */ "\"Source: " + source.getLibdesc().toUpperCase() + "." + source.getName().toUpperCase() + "\"";
						String expr = (String)hItemJ.get(jtag.getTagJsonHier());
						
						/* Name,Query,Report,Expression,Description  */
						ret += nm + ", " ;
						ret += "DATASOURCE_" + source.getName() + "," ; 
						ret += repName + "<" + server + repPath + ">" + "," ;
						ret += "\"" + expr + "\"" ;
						ret += "," + src;
						
						retLst.add(ret);
					
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
			
			break;
		case "BI Collection Member":
			retLst = new ArrayList<String>();
			if(source.getXmlDItems()!=null){
				for(String dItem : source.getXmlDItems()){
					try{
						String ret = "";
						JSONObject dItemJ = (JSONObject)parser.parse(dItem);
						String nm = (String)dItemJ.get(jtag.getTagJsonXref());
						String label = (String)dItemJ.get(jtag.getTagJsonLabel());
						String format = (String)dItemJ.get(jtag.getTagJsonFormat());
						
						
						/* Name,Collection,Model,Description,Data Type,Expression  */
						ret += nm + ", " 
								+ source.getName() + "," 
								+ source.getLibdesc() +"<" + server + source.getLibpath() + source.getLibname() + ">" + "," 
								+ label + "," 
								+ format + "," 
								+ nm;
						
						retLst.add(ret);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			
			
			if(source.getXmlCItems() !=null){
				for(String cItem : source.getXmlCItems()){
					try{
						String ret = "";
						JSONObject cItemJ = (JSONObject)parser.parse(cItem);
						
						String nm = (String)cItemJ.get(jtag.getTagJsonLabel());
						/** Source on XML referes to libname TAG which is could not be the same value as what you find within libname*/
						String label = (String)cItemJ.get(jtag.getTagJsonLabel());
						String format = (String)cItemJ.get(jtag.getTagJsonFormat());
						String expr = (String)cItemJ.get(jtag.getTagJsonExpr());
						
						/* Name,Collection,Model,Description,Data Type,Expression  */
						ret += nm + ", " 
								+ source.getName() + "," 
								+ source.getLibdesc() +"<" + server + source.getLibpath() + source.getLibname() + ">" + "," 
								+ label + "," 
								+ format + "," 
								+ expr;
						
						retLst.add(ret);
					
					}catch(Exception e){
						e.printStackTrace();
					}
				}
				
			}
			
			
			break;
			
		case "Database Column":
			retLst = new ArrayList<String>();
			
			if(source.getXmlDItems()!=null){
				for(String dItem : source.getXmlDItems()){
					try{
						String ret = "";
						JSONObject dItemJ = (JSONObject)parser.parse(dItem);
						String nm = (String)dItemJ.get(jtag.getTagJsonXref());
						String label = (String)dItemJ.get(jtag.getTagJsonLabel());
						
						/* Name,Host,Database,Schema,Table,Description,ODBC Type,Data Type,Native Type,Length,Minimum Length,Unique,Nullability,Fraction,Position,Level Number  */
						ret += nm + ", " 
								+ host + "," 
								+ source.getDbname() + "<" + jtag.getTagLinDbms() + "=" + jtag.getTagLinDbmsSASValue() + "::" + jtag.getTagLinInstance() + "=" + jtag.getTagLinInstanceSASValue() + ">" + "," 
								+ source.getSchema() + "," 
								+ source.getName() + "," 
								+ label + "," 
								+ " , , , , , , , , , ";
						
						retLst.add(ret);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			
			break;
			
		case "BI Relationship":
			retLst = new ArrayList<String>();
			
			if(source.getXmlDItems()!=null){
				for(String dItem : source.getXmlDItems()){
					try{
						String ret = "";
						JSONObject dItemJ = (JSONObject)parser.parse(dItem);
						String nm = (String)dItemJ.get(jtag.getTagJsonXref());
						
						/* Query Member,Collection Member,Database Field  */
						
						ret += repName + "<" + server + repPath + ">." + "DATASOURCE_" + source.getName() + "." + nm +"," 
								+ source.getLibdesc() + "<" + server + source.getLibpath() + ">." + source.getName() + "." + nm + "," 
								+ host + "." + source.getDbname() + "<" + jtag.getTagLinDbms() + "=" + jtag.getTagLinDbmsSASValue() + "::" + jtag.getTagLinInstance() + "=" + jtag.getTagLinInstanceSASValue() + ">" + "." + source.getSchema() + "." + source.getName() + "." + nm  ; 
						
						retLst.add(ret);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
			break;
			
		default:
			break;
		}
		
		return retLst;
	}
	
	public String toIbm(LinTable source, String type){
		String ret = "";
		switch(type){
		/* 	Name,Report,Description					*/
		case "BI Query":
			ret += "DATASOURCE_" + source.getName() + "," + repName + "<" + server + repPath + ">" + "," + source.getName();
			break;
		/* Name,Model,Description 					*/
		case "BI Collection":
			ret += source.getName() + "," + source.getLibdesc() + "<" + server + getPathWithSlash(source.getLibpath()) + ">" + "," +  source.getName();
			break;
			
		/* Name,Server,Folder,Description 					*/
		case "BI Model":
            ret += source.getLibdesc() + "," + repName + "<" + server + repPath + ">" + "," + source.getName();
            break;

			
		/* Name,Host,Database,Schema,Description 	*/	
		case "Database Table":
			ret += source.getName() + "," + host + "," + source.getLibname() + "," + source.getSchema() + "," + source.getName() ; 
			break;
			
		//TODO: check istance on tables that are not SAS 
		/* Name,Host,Description,Vendor,Version,Instance,Location,DBMS */
		case "Database":
			ret += source.getDbname() + "," + host + "," + source.getDbname() + "," + /*VENDOR*/ "," + /*VERSION*/ "," + jtag.getTagLinInstanceSASValue() + "," + "Lab" + "," + "Relational" ;
			break;
		/* Name,Host,Database,Description */
		case "Schema":
			ret += source.getSchema() + "," + host + "," + source.getDbname() + "<" + jtag.getTagLinDbms() +  "=" + jtag.getTagLinDbmsSASValue() + "::" + jtag.getTagLinInstance() +  "=" + jtag.getTagLinInstanceSASValue() + ">" + "," + source.getSchema();
			break;
		
			
			
		default:
			break;
		}
		return ret;
	}


	public PropsIBM getIbm() {
		return ibm;
	}


	public void setIbm(PropsIBM ibm) {
		this.ibm = ibm;
	}


	public String getRepName() {
		return repName;
	}


	public void setRepName(String repName) {
		this.repName = repName;
	}


	public String getRepPath() {
		return repPath;
	}


	public void setRepPath(String repPath) {
		this.repPath = repPath;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}
	

	
	

}
