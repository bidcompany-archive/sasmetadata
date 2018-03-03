package xml.reader.obj;

public class XMLAggrItem {
	
	private String source;
	private String name;
	private String label;
	private String format;
	private String expr;
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
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getExpr() {
		return expr;
	}
	
	public void setExpr(String expr) {
		this.expr = expr;
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
			ret += "\"expr\"" + ":" + "\"" + expr +  "\"" + ",";
		ret += "}" ;
		return ret;
	}
	
	
	
	
	

}
