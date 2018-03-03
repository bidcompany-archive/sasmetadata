package xml.reader.obj;

public class XMLDataItem {
	
	private String source;
	private String name;
	private String xref;
	private String label;
	private String format;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getXref() {
		return xref;
	}
	public void setXref(String xref) {
		this.xref = xref;
	}
	


	public String getSource() {
		return source;
	}
	

	public void setSource(String source) {
		this.source = source;
	}
	
	
	

	public String getLabel() {
		return label;
	}
	

	public void setLabel(String label) {
		this.label = label;
	}
	

	public String getFormat() {
		return format;
	}
	

	public void setFormat(String format) {
		this.format = format;
	}
	
	/*
	@Override
	public String toString() {
		String ret = "";
		ret += "{" ;
			ret += "\"source\"" + ":" + "\"" + source +  "\"" + ",";
			ret += "\"name\"" + ":" + "\"" + name +  "\"" + ",";
			ret += "\"xref\"" + ":" + "\"" + xref +  "\"" + "";
			ret += "\"label\"" + ":" + "\"" + label +  "\"" + "";
			ret += "\"format\"" + ":" + "\"" + format +  "\"" + "";
		ret += "}" ;
		return ret;
	}
	*/
	
	
	

}
