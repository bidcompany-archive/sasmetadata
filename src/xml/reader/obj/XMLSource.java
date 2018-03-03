package xml.reader.obj;

import java.util.ArrayList;
import java.util.List;

public class XMLSource {
	
	private String reportID;
	
	private String name;
	private String ref;
	private String label;
	private String fullName;
	
	private List<XMLDataItem> dItems = new ArrayList<XMLDataItem>();
	private List<XMLCalcItem> cItems = new ArrayList<XMLCalcItem>();
	private List<XMLPredItem> pItems = new ArrayList<XMLPredItem>();
	private List<XMLAggrItem> aItems = new ArrayList<XMLAggrItem>();
	private List<XMLHierItem> hItems = new ArrayList<XMLHierItem>();
	private List<XMLGeneItem> gItems = new ArrayList<XMLGeneItem>();
	
	
	
	
	public String getReportID() {
		return reportID;
	}
	

	public void setReportID(String reportID) {
		this.reportID = reportID;
	}
	

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRef() {
		return ref;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	
	public String getFullName() {
		return fullName;
	}
	


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	


	public void fillInfos(String name, String ref, String label){
		this.name = name;
		this.ref = ref;
		this.label = label;
	}
	
	
	public List<XMLDataItem> getDItems(){
		if(null==dItems)
			dItems=new ArrayList<XMLDataItem>();
		return dItems;
	}
	
	public void addDItem(XMLDataItem item){
		if(dItems==null)
			dItems=new ArrayList<XMLDataItem>();
		dItems.add(item);
	}
	
	public List<XMLCalcItem> getCItems(){
		if(null==cItems)
			cItems=new ArrayList<XMLCalcItem>();
		return cItems;
	}
	
	public void addCItem(XMLCalcItem item){
		if(cItems==null)
			cItems=new ArrayList<XMLCalcItem>();
		cItems.add(item);
	}
	
	public List<XMLPredItem> getPItems(){
		if(null==pItems)
			pItems=new ArrayList<XMLPredItem>();
		return pItems;
	}
	
	public void addPItem(XMLPredItem item){
		if(pItems==null)
			pItems=new ArrayList<XMLPredItem>();
		pItems.add(item);
	}
	
	public List<XMLAggrItem> getAItems(){
		if(null==aItems)
			aItems=new ArrayList<XMLAggrItem>();
		return aItems;
	}
	
	public void addAItem(XMLAggrItem item){
		if(aItems==null)
			aItems=new ArrayList<XMLAggrItem>();
		aItems.add(item);
	}
	
	public List<XMLHierItem> getHItems(){
		if(null==hItems)
			hItems=new ArrayList<XMLHierItem>();
		return hItems;
	}
	
	public void addHItem(XMLHierItem item){
		if(hItems==null)
			hItems=new ArrayList<XMLHierItem>();
		hItems.add(item);
	}
	
	public List<XMLGeneItem> getGItems(){
		if(null==gItems)
			gItems=new ArrayList<XMLGeneItem>();
		return gItems;
	}
	
	public void addGItem(XMLGeneItem item){
		if(gItems==null)
			gItems=new ArrayList<XMLGeneItem>();
		gItems.add(item);
	}
	
	
	@Override
	public String toString() {
		return "XMLSource [name=" + name + ", ref=" + ref + ", label=" + label + "]";
	}

	public void print(){
		String ret = "";
		ret += toString();
		if(dItems!=null){
			for(XMLDataItem item : dItems)
				ret += "\n ->" + item;
		}
		if(cItems!=null){
			for(XMLCalcItem item : cItems)
				ret += "\n ->" + item;
		}
		if(pItems!=null){
			for(XMLPredItem item : pItems)
				ret += "\n ->" + item;
		}
		if(aItems!=null){
			for(XMLAggrItem item : aItems)
				ret += "\n ->" + item;
		}
		if(hItems!=null){
			for(XMLHierItem item : hItems)
				ret += "\n ->" + item;
		}
		if(gItems!=null){
			for(XMLGeneItem item : gItems)
				ret += "\n ->" + item;
		}
		System.out.println(ret);
	}
	
	
	
	private String fixSimple(String pre, String expr, String post){
		String ret;
		
		ret = expr.replaceAll("\\#\\{pr\\d.+\\}", "<PROMPT>");
		
		/* 1 Replace Data Item */
		for(XMLDataItem ditem : dItems){
			if(ret.contains(pre + ditem.getName() + post)){
				ret = ret.replaceAll(ditem.getName(), "<D::" + label.toUpperCase() + "." + ditem.getXref() +  ">");
			}
		}
		/* 2 Replace Pred Item */
		for(XMLPredItem pitem : pItems){
			if(ret.contains(pre + pitem.getName() + post)){
				ret = ret.replaceAll(pitem.getName(), "<P::" + label.toUpperCase() + "." + pitem.getLabel().toUpperCase() +  ">");
			}
		}
		/* 3 Replace Grouped Item */
		if(gItems!=null)
			for(XMLGeneItem gitem : gItems){
				if(ret.contains(pre + gitem.getName() + post)){
					ret = ret.replaceAll(gitem.getName(), "<G::" + label.toUpperCase() + "." + gitem.getLabel().toUpperCase() +  ">");
				}
			}
		return ret;
	}
	
	
	private String fixComplex(String pre, String expr, String post){
		String ret = expr;
		/* 1.2.a Fix other Calc */
		for(XMLCalcItem citem : cItems){
			if(ret.contains("${" + citem.getName() + ",")){
				ret = ret.replaceAll(citem.getName(), "<C::" + label.toUpperCase() + "." + citem.getLabel().toUpperCase() +  "::" + citem.extractDataItem() + ">");
			}
		}
		return ret;
	}
	
	
	public void fixDependancy() throws InterruptedException{
	
		
		/* 1. FIX EASY */
		/* 1.1 Fix Calculated */
		if(cItems!=null)
			for(XMLCalcItem calc : cItems){
				String expr = calc.getExpr();
				calc.setExpr(fixSimple("${", expr , ","));
			}
		/* 1.2 Fix Aggregated */
		if(aItems!=null)
			for(XMLAggrItem calc : aItems){
				String expr = calc.getExpr();
				calc.setExpr(fixSimple("${", expr , ","));
			}
		/* 1.3 Fix Hierarchy */
		if(hItems!=null)
			for(XMLHierItem calc : hItems){
				String expr = calc.getHier();
				calc.setHier(fixSimple("${", expr , "}"));
			}
		
		
		/* 2. FIX COMPLEX */
		/* 2.1 Fix Calculated */
		if(cItems!=null)
			for(XMLCalcItem calc : cItems){
				String expr = calc.getExpr();
				calc.setExpr(fixComplex("${", expr , ","));
			}
		/* 2.2 Fix Aggregated */
		if(aItems!=null)
			for(XMLAggrItem calc : aItems){
				String expr = calc.getExpr();
				calc.setExpr(fixSimple("${", expr , ","));
			}
	}
	
	public List<String> getDItemsStr(){
		List<String> ret = new ArrayList<String>();
		for(XMLDataItem ditem : dItems){
			ret.add(ditem.toString());
		}
		return ret;
	}
	public List<String> getCItemsStr(){
		List<String> ret = new ArrayList<String>();
		for(XMLCalcItem citem : cItems){
			ret.add(citem.toString());
		}
		return ret;
	}
	public List<String> getPItemsStr(){
		List<String> ret = new ArrayList<String>();
		for(XMLPredItem pitem : pItems){
			ret.add(pitem.toString());
		}
		return ret;
	}
	public List<String> getAItemsStr(){
		List<String> ret = new ArrayList<String>();
		for(XMLAggrItem aitem : aItems){
			ret.add(aitem.toString());
		}
		return ret;
	}
	public List<String> getHItemsStr(){
		List<String> ret = new ArrayList<String>();
		for(XMLHierItem hitem : hItems){
			ret.add(hitem.toString());
		}
		return ret;
	}
	public List<String> getGItemsStr(){
		List<String> ret = new ArrayList<String>();
		for(XMLGeneItem gitem : gItems){
			ret.add(gitem.toString());
		}
		return ret;
	}
	
	

}
