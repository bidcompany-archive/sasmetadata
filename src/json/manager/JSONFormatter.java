package json.manager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import utils.props.PropsTag;
import xml.reader.obj.XMLAggrItem;
import xml.reader.obj.XMLCalcItem;
import xml.reader.obj.XMLDataItem;
import xml.reader.obj.XMLHierItem;

public class JSONFormatter {
	
	private JSONParser parser;
	private PropsTag jTags = new PropsTag();
	
	
	private String source;
	private String name;
	private String xref;
	private String label;
	private String format;
	
	private String expr;
	private String type;
	private String usage;
	
	
	public JSONFormatter() {
		parser = new JSONParser();
		source = jTags.getTagJsonSource();
		name = jTags.getTagJsonName();
		xref = jTags.getTagJsonXref();
		label = jTags.getTagJsonLabel();
		format = jTags.getTagJsonFormat();
		expr = jTags.getTagJsonExpr();
		type = jTags.getTagJsonType();
		usage = jTags.getTagJsonUsage();
	}
	
	
	public JSONObject format(XMLDataItem dItem) {
		
		String strFmt = "";
		strFmt += "{" ;
			strFmt += "\"" + source + "\"" + ":" + "\"" + dItem.getSource() +  "\"" + ",";
			strFmt += "\"" + name + "\"" + ":" + "\"" + dItem.getName() +  "\"" + ",";
			strFmt += "\"" + xref + "\"" + ":" + "\"" + dItem.getXref() +  "\"" + "";
			strFmt += "\"" + label + "\"" + ":" + "\"" + dItem.getLabel() +  "\"" + "";
			strFmt += "\"" + format + "\"" + ":" + "\"" + dItem.getFormat()  +  "\"" + "";
		strFmt += "}" ;
		
		JSONObject ret = new JSONObject();
		try {
			ret = (JSONObject) parser.parse(strFmt);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public JSONObject format(XMLCalcItem cItem) {
		String strFmt = "";
		strFmt += "{" ;
			strFmt += "\"" + source + "\"" + ":" + "\"" + cItem.getSource() +  "\"" + ",";
			strFmt += "\"" + name + "\"" + ":" + "\"" + cItem.getName()  +  "\"" + ",";
			strFmt += "\"" + type + "\"" + ":" + "\"" + cItem.getType()  +  "\"" + ",";
			strFmt += "\"" + usage + "\"" + ":" + "\"" + cItem.getUsage() +  "\"" + ",";
			strFmt += "\"" + format + "\"" + ":" + "\"" + cItem.getFormat() +  "\"" + ",";
			strFmt += "\"" + label + "\"" + ":" + "\"" + cItem.getLabel() +  "\"" + ",";
			strFmt += "\"" + expr + "\"" + ":" + "\"" + cItem.getExpr() +  "\"" + "";
		strFmt += "}" ;
		
		JSONObject ret = new JSONObject();
		try {
			ret = (JSONObject) parser.parse(strFmt);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}


	public JSONObject format(XMLAggrItem aItem) {
		String strFmt = "";
		strFmt += "{" ;
			strFmt += "\"source\"" + ":" + "\"" + aItem.getSource() +  "\"" + ",";
			strFmt += "\"name\"" + ":" + "\"" + aItem.getName() +  "\"" + ",";
			strFmt += "\"format\"" + ":" + "\"" + aItem.getFormat() +  "\"" + ",";
			strFmt += "\"label\"" + ":" + "\"" + aItem.getLabel() +  "\"" + ",";
			strFmt += "\"expr\"" + ":" + "\"" + aItem.getExpr() +  "\"" + ",";
		strFmt += "}" ;
		
		JSONObject ret = new JSONObject();
		try {
			ret = (JSONObject) parser.parse(strFmt);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	
	public JSONObject format(XMLHierItem hItem) {
		String strFmt = "";
		strFmt += "{" ;
			strFmt += "\"source\"" + ":" + "\"" + hItem.getSource() +  "\"" + ",";
			strFmt += "\"name\"" + ":" + "\"" + hItem.getName() +  "\"" + ",";
			strFmt += "\"label\"" + ":" + "\"" + hItem.getLabel() +  "\"" + ",";
			strFmt += "\"hier\"" + ":" + "\"" + hItem.getHier() +  "\"" + ",";
		strFmt += "}" ;
		
		JSONObject ret = new JSONObject();
		try {
			ret = (JSONObject) parser.parse(strFmt);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	

}
