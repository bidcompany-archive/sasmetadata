package xml.reader.obj;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLCalcItem {
	
	private String name;
	private String expr;
	private String type;
	private String usage;
	private String format;
	private String label;
	private String source;
	
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getExpr() {
		return expr;
	}
	
	public void setExpr(String expr) {
		this.expr = expr;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUsage() {
		return usage;
	}
	
	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	
	public String getLabel() {
		return label;
	}
	

	public void setLabel(String label) {
		this.label = label;
	}
	
	

	public String getSource() {
		return source;
	}
	

	public void setSource(String source) {
		this.source = source;
	}
	

	/*
	@Override
	public String toString() {
		String ret = "";
		ret += "{" ;
			ret += "\"source\"" + ":" + "\"" + source +  "\"" + ",";
			ret += "\"name\"" + ":" + "\"" + name +  "\"" + ",";
			ret += "\"type\"" + ":" + "\"" + type +  "\"" + ",";
			ret += "\"usage\"" + ":" + "\"" + usage +  "\"" + ",";
			ret += "\"format\"" + ":" + "\"" + format +  "\"" + ",";
			ret += "\"label\"" + ":" + "\"" + label +  "\"" + ",";
			ret += "\"expr\"" + ":" + "\"" + expr +  "\"" + "";
		ret += "}" ;
		return ret;
	}
	*/
	
	
	/**
	 * extractDataItem
	 * extract PhysicalTable from JSON
	 * @return
	 */
	public String extractDataItem(){
		String ret = "<L:: ";
		Pattern pattern = Pattern.compile("(<D\\:\\:\\w+\\.\\w+>)");
		Matcher matcher = pattern.matcher(expr);
		int n=0;
		while(matcher.find()){
			if(matcher.groupCount()>n) {
				ret += matcher.group(n);
				n++;
			}
		}
		ret += ">";
		return ret;
	}
	
	

	
	
}
