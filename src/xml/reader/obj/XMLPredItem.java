package xml.reader.obj;

public class XMLPredItem {
	
	private String name;
	private String calculation;
	private String label;
	private String format;
	private String source;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCalculation() {
		return calculation;
	}
	
	public void setCalculation(String calculation) {
		this.calculation = calculation;
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
	

	public String getSource() {
		return source;
	}
	

	public void setSource(String source) {
		this.source = source;
	}
	

	@Override
	public String toString() {
		String ret = "";
		ret += "{" ;
			ret += "\"source\"" + ":" + "\"" + source +  "\"" + ",";
			ret += "\"name\"" + ":" + "\"" + name +  "\"" + ",";
			ret += "\"format\"" + ":" + "\"" + format +  "\"" + ",";
			ret += "\"label\"" + ":" + "\"" + label +  "\"" + ",";
			ret += "\"calculation\"" + ":" + "\"" + calculation +  "\"" + ",";
		ret += "}" ;
		return ret;
	}
	
	
	

}
