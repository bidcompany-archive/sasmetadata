package sas.meta.obj;

import java.util.ArrayList;
import java.util.List;


public class LinReport {
	
	private String id;
	private String name;
	private String path; 
		
	private List<LinTable> sources;
	

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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public void addSource(LinTable src){
		if(sources==null)
			sources = new ArrayList<LinTable>();
		sources.add(src);
	}
	
	public int getSourceSize(){
		if(sources==null)
			return -1;
		return sources.size();
	}
	
	public List<LinTable> getSources(){
		if(sources==null)
			sources=new ArrayList<LinTable>();
		return sources;
	}
	
	
	
	@Override
	public String toString() {
		String ret = "";
		ret += "LinReport [id=" + id + ", name=" + name + ", path=" + path + "]";
		return ret;
	}	
		
	public void toPrint(){
		System.out.println(toString());
		for(LinTable src : sources)
			src.toPrint();
	}
	
	
	
	
	
	
	
	

}
