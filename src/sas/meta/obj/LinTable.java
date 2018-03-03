package sas.meta.obj;

import java.util.ArrayList;
import java.util.List;

public class LinTable implements Comparable<LinTable> {
	private String id;
	private String name;
	private String path;
	
	private String libname;
	private String libId;
	private String libdesc;
	private String libpath;
	
	private String dbname = "SAS";
	private String schema;
	
	private List<LinTransformation> linkedTransfs;
	

	private List<String> xmlDItems;
	private List<String> xmlCItems;
	private List<String> xmlHItems;
	private List<String> xmlAItems;
	
	
	
	/** getHost() Return Libname HOST * */
	public String getHost(){
		String ret = "";
		if(libpath==null && "".equals(libpath))
			ret = "SBA";
		else
			ret = libpath.split("/")[0];
		return ret;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * In case missing Value => WORK
	 * @return Path
	 */
	public String getPath() {
		if(path==null)
			return "WORK";
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getFullName(){
		return getLibname().toUpperCase() + "." + this.name.toUpperCase();
	}
	
	public void setLibInfos(String libid, String libname, String libdesc, String libpath){
		this.libId=libid;
		this.libname=libname;
		this.libdesc=libdesc;
		this.libpath=libpath;
	}
	
	
	public String getLibname() {
		return libname;
	}
	

	public void setLibname(String libname) {
		this.libname = libname;
	}
	

	public String getLibId() {
		return libId;
	}
	

	public void setLibId(String libId) {
		this.libId = libId;
	}
	

	public String getLibdesc() {
		return libdesc;
	}
	

	public void setLibdesc(String libdesc) {
		this.libdesc = libdesc;
	}
	

	public String getLibpath() {
		return libpath;
	}
	

	public void setLibpath(String libpath) {
		this.libpath = libpath;
	}
	

	public void linkTransformation(LinTransformation transf){
		if(linkedTransfs==null)
			linkedTransfs=new ArrayList<LinTransformation>();
		linkedTransfs.add(transf);
		
	}
	
	
	public String getDbname() {
		if(libname!=null && !"".equals(libname)){
			try{
				return libname.split(" - ")[0];
			}catch(Exception e){
				return "<NODBNAME>";
			}
		}
		return dbname;
	}
	

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	

	public String getSchema() {
		if(libname!=null && !"".equals(libname)){
			try{
				return libname.split(" - ")[1];
			}catch(Exception e){
				return "<NOSCHEMA>";
			}
		}
		return schema;
	}
	

	public void setSchema(String schema) {
		this.schema = schema;
	}
	

	public List<LinTransformation> getLinkedTr(){
		return this.linkedTransfs;
	}
	
	
	public boolean equals(LinTable tbl){
		if(id.equals(tbl.getId()))
			return true;
		if(name.equalsIgnoreCase(tbl.getName()) && libname.equals(tbl.getLibname()))
			return true;
		return false;
	}

	@Override
	public int compareTo(LinTable arg0) {
		return arg0.getId().compareTo(id);
	}
	
	
	public String getTblInfo(){
		String ret = "";
		ret += "";
		ret += "[" + libdesc + "]" +  name + "(" + id + ")";
		return ret;
	}
	
	public String getInfo(LinTable t, int nnested, LinTransformation genTr){
		String ret = "";
		for(int i=0; i<nnested; i++)
			ret += ">";
		ret += "[" + nnested + "]";
		if(genTr!=null)
			ret += genTr.getJobInfo() + "::" + genTr.getName(); 
		ret += " -> " + t.getTblInfo();
		System.out.println(ret);
		
		if(t.getLinkedTr()!=null)
			for(LinTransformation tr : t.getLinkedTr()){
				if(tr.getSources()!=null)
					for(LinTable tbl : tr.getSources()){
						getInfo(tbl, nnested+1, tr);
					}
			}
		return "";
	}
	
	public String getInfo(int nnested){
		String ret = "";
		for(int i=0; i<nnested; i++)
			ret += ">";
		ret += "[" + nnested + "]";
		ret += " L-> Tbl[" + getTblInfo() + "]";
		System.out.println(ret);
		if(linkedTransfs!=null)
			for(LinTransformation tr : linkedTransfs)
				tr.getInfo(nnested+1);
			
		return "";
	}
	
	
	public LinTable clone(){
		LinTable obj = new LinTable();
		obj.setId(id);
		obj.setName(name);
		obj.setPath(path);
		obj.setLibdesc(libdesc);
		obj.setLibname(libname);
		obj.setLibpath(libpath);
		obj.setDbname(dbname);
		obj.setSchema(schema);
		/*
		if(linkedTransfs!=null)
			for(LinTransformation lt : linkedTransfs)
				obj.linkTransformation(lt.clone());
		*/
		return obj;
	}
	

	public void toPrint(){
		getInfo(0);
	}
	
	
	
	
	@Override
	public String toString(){
		String ret = "";
		ret += this.libname + "." + this.name;
		
		return ret;
	}
	
	
	public List<String> getXmlDItems() {
		if(xmlDItems==null)
			xmlDItems=new ArrayList<String>();
		return xmlDItems;
	}
	
	public void setXmlDItems(List<String> xmlDItems) {
		this.xmlDItems = xmlDItems;
	}
	
	public void addDItem(String ditem){
		if(xmlDItems==null)
			xmlDItems=new ArrayList<String>();
		xmlDItems.add(ditem);
	}
	
	public List<String> getXmlCItems() {
		if(xmlCItems==null)
			xmlCItems=new ArrayList<String>();
		return xmlCItems;
	}
	
	public void setXmlCItems(List<String> xmlCItems) {
		this.xmlCItems = xmlCItems;
	}
	
	public void addCItem(String citem){
		if(xmlCItems==null)
			xmlCItems=new ArrayList<String>();
		xmlCItems.add(citem);
	}
	
	public List<String> getXmlHItems() {
		if(xmlHItems==null)
			xmlHItems=new ArrayList<String>();
		return xmlHItems;
	}
	
	public void setXmlHItems(List<String> xmlHItems) {
		this.xmlHItems = xmlHItems;
	}
	
	public void addHItem(String hitem){
		if(xmlHItems==null)
			xmlHItems=new ArrayList<String>();
		xmlHItems.add(hitem);
	}
	
	public List<String> getXmlAItems() {
		if(xmlAItems==null)
			xmlAItems=new ArrayList<String>();
		return xmlAItems;
	}
	
	public void setXmlAItems(List<String> xmlAItems) {
		this.xmlAItems = xmlAItems;
	}
	
	public void addAItem(String aitem){
		if(xmlAItems==null)
			xmlAItems=new ArrayList<String>();
		xmlAItems.add(aitem);
	}
	
	
	
	public boolean hasXML(){
		if(xmlDItems!=null  && xmlDItems.size()>0)
			return true; 
		if(xmlCItems!=null  && xmlCItems.size()>0)
			return true;
		return false;
	}
	
	
}
