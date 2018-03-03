package sas.meta.obj;

import java.util.ArrayList;
import java.util.List;

public class LinTransformation implements Comparable<LinTransformation>, LinObj{
	
	private String id;
	private String name;
	private String jobId;
	private String jobName;
	private String jobPath;
	private String type;
	
	private List<LinTable> sourceList = new ArrayList<LinTable>();
	private List<LinTable> targetList = new ArrayList<LinTable>();
	
	private List<String> tgtColList = new ArrayList<String>();
	private List<String> srcColList = new ArrayList<String>();
	
	
	public void setJobInfos(String id, String name, String path){
		this.jobId=id;
		this.jobName=name;
		this.jobPath=path;
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
	public String getJobId() {
		return jobId;
	}
	
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getJobName() {
		return jobName;
	}
	
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	
	public String getJobPath() {
		return jobPath;
	}
	
	public void setJobPath(String jobPath) {
		this.jobPath = jobPath;
	}
	
	public void addSource(LinTable source){
		if(sourceList == null )
			sourceList = new ArrayList<LinTable>();
		this.sourceList.add(source);
	}
	
	public void addTarget(LinTable target){
		if(targetList == null )
			targetList = new ArrayList<LinTable>();
		this.targetList.add(target);
	}
	
	public String getJobInfo(){
		String ret = "";
		ret += "<J:" + jobName + "(" + jobId + ")" + ">";
		return ret;
	}
	
	
	public LinTransformation clone(){
		LinTransformation obj = new LinTransformation();
		obj.setId(id);
		obj.setName(name);
		obj.setJobId(jobId);
		obj.setJobName(jobName);
		obj.setJobPath(jobPath);
		/*
		if(sourceList!=null)
			for(LinTable tbl : sourceList)
				obj.addSource(tbl.clone());
		
		if(targetList!=null)
			for(LinTable tbl : targetList)
				obj.addTarget(tbl.clone());
		*/
		
		return obj;
	}
	
	

	public String getInfo(int nnested){
		String ret = "";
		for(int i=0; i<nnested; i++)
			ret += ">";
		ret += "[" + nnested + "]";
		ret += " -> " + "TR:[" + name + "::" + id +"]";
		if(sourceList!=null){
			ret+= " #Sources[" + sourceList.size() + "]";
		}
		System.out.println(ret);
		
		if(sourceList!=null){
			for(LinTable t: sourceList)
				t.getInfo(nnested);
		}
		
		return "";
	}
	
	
	
	
	public List<LinTable> getSources(){
		return this.sourceList;
	}
	
	public List<LinTable> getTargets(){
		return this.targetList;
	}
	
	public int countSources(){
		return sourceList.size();
	}
	public int countTargets(){
		return targetList.size();
	}
	@Override
	public int compareTo(LinTransformation o) {
		return id.compareTo(o.getId());
	}
	
	public void addColTarget(String obj){
		if(tgtColList==null)
			tgtColList=new ArrayList<String>();
		tgtColList.add(obj);
	}
	public void addColSource(String obj){
		if(srcColList==null)
			srcColList=new ArrayList<String>();
		srcColList.add(obj);
	}
	
	
	
	public List<String> getTgtColList(){
		return tgtColList;
	}
	public List<String> getSrcColList(){
		return srcColList;
	}
	
	
	public void setTgtColList(List<String> lst){
		this.tgtColList = lst;
	}
	public void setSrcColList(List<String> lst){
		this.srcColList = lst;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getMapping(){
		String impact = "";
		String srcs = "";
		String tgts = "";
		if(null!=srcColList){
			for(String colSrc: srcColList){
				if("".equals(srcs))
					srcs += "<I::";
				else
					srcs += ", <I::";
				srcs += colSrc ;
				srcs += ">" ;
			}
		}
		if("".equals(srcs))
			srcs += "<NO_MAPPING>";
		
		
		if(null!=tgtColList){
			for(String colTgt: tgtColList){
				if("".equals(tgts))
					tgts += "<O::";
				else
					tgts += ", <O::" ;
				tgts += colTgt ;
				tgts += ">" ;
			}
		}
		if("".equals(tgts))
			tgts += "<NO_MAPPING>";
		
		if(tgts.equals(srcs) && tgts.equals("<NO_MAPPING>"))
			impact += "<NO_MAPPING>";
		else
			impact += srcs + " @-> " + tgts ;
		return impact;
	}

}
