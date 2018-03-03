package ibm.obj;

import java.util.ArrayList;
import java.util.List;

import utils.props.PropsIBM;


public class BIObject {
	

	private final PropsIBM ibm = new PropsIBM();
	
	private List<String> lines;
	private String name;
	private int num;
	private String colName;
	private String prefix;
	private String suffixBegin;
	private String suffixEnd;
	
	
	private void setUp() {
		prefix=ibm.getStructurePrefix();
		suffixBegin = ibm.getStructureSuffixBegin();
		suffixEnd = ibm.getStructureSuffixEnd();
	}
	
	

	public BIObject(String name, String colName, int num, String line){
		lines = new ArrayList<String>();
		lines.add(line);
		this.name = name;
		this.num = num;
		this.colName = colName;
		setUp();
	}
	
	public BIObject(String name, String colName, int num, List<String> lines){
		this.lines=lines;
		this.name=name;
		this.num = num;
		this.colName = colName;
		setUp();
	}
	
	
	public List<String> getLines() {
		return lines;
	}
	public void setLines(List<String> lines) {
		this.lines = lines;
	}
	


	private boolean check(String val){
		int num = val.split(",").length;
		if(num == this.num)
			return true;
		return false;
	}
	
	
	public String toFile(List<String> lines){
		String ret = "";
		ret += prefix + name + " " + suffixBegin;
		ret += "\n" + colName;
		for(String l : lines){
			if(check(l))
				ret += "\n" + l;
		}
		ret += "\n" + prefix + name + " " + suffixEnd;
		ret += "\n\n";
		
		return ret;
	}
	
	public String toFile(){
		String ret = "";
		ret += prefix + name + " " +  suffixBegin;
		ret += "\n" + colName;
		for(String l : lines){
			ret += "\n" + l;
		}
		
		ret += "\n" + prefix + name + " " +  suffixEnd;
		ret += "\n\n";
		
		
		return ret;
	}
	
	

}
