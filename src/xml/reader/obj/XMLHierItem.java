package xml.reader.obj;

public class XMLHierItem {

	private String name;
	private String label;
	private String hier;
	private String source;
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getHier() {
		return hier;
	}
	
	public void setHier(String hier) {
		this.hier = hier;
	}
	
	
	public String getSource() {
		return source;
	}
	

	public void setSource(String source) {
		this.source = source;
	}
	

	public void addLevel(String level){
		if(hier==null || "".equals(hier)){
			hier = "level";
		}
		hier = hier + " @-> " + level;
	}

	@Override
	public String toString() {
		String ret = "";
		ret += "{" ;
			ret += "\"source\"" + ":" + "\"" + source +  "\"" + ",";
			ret += "\"name\"" + ":" + "\"" + name +  "\"" + ",";
			ret += "\"label\"" + ":" + "\"" + label +  "\"" + ",";
			ret += "\"hier\"" + ":" + "\"" + hier +  "\"" + "";
		ret += "}" ;
		return ret;
	}
	
	
	
	
	
}
