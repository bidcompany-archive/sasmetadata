package sas.meta.tag.obj;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import sas.meta.obj.LinTable;
import sas.meta.obj.LinTransformation;


public class MetaTagList {
	
	private List<LinTransformation> metaTagList;
	
	private void initList(){
		metaTagList = new ArrayList<LinTransformation>();
	}
	
	public void append(LinTransformation tr){
		if(metaTagList == null)
			initList();
		metaTagList.add(tr);
	}
	
	public Set<LinTransformation> getLinks(LinTable target){
		
		Set<LinTransformation> set = new TreeSet<LinTransformation>();
		if(metaTagList!=null)
			for(LinTransformation tr : metaTagList){
				for(LinTable tbl : tr.getTargets() ){
					if(tbl.equals(target)){
						set.add(tr);
					}
				}
			}
		return set;
	}
	
	public void printList(){
		String ret = "RES:: ";
		for(LinTransformation trl : metaTagList){
			ret += "\n SRCs:{ ";
			for(LinTable tbl : trl.getSources())
				ret += tbl + " " ;
			ret += "} ---> " + trl.getName() + "(J:" + trl.getJobName() + ":" + trl.getJobId() + ")" + " ---> TGTs:{" ;
			for(LinTable tbl : trl.getTargets())
				ret += tbl + " " ;
			ret +="}";
		}
		System.out.println(ret);
	}

	
	public int getLength(){
		if(metaTagList==null)
			return -1;
		return metaTagList.size();
	}
}
