package sas.lineage.manager;

import json.manager.JSONReader;
import sas.lineage.rest.SASRestClient;
import sas.meta.obj.LinReport;
import xml.manager.XMLLineageWriter;

public class LineageManager {
	
	
	private XMLLineageWriter xml;

	public LineageManager(){
		xml = new XMLLineageWriter();
	}
	
	
	
	public void load(){
		JSONReader json = new JSONReader();
		System.out.println("Loading Lineage ...");
		System.out.print("-> Posting Reqs To Linage ");
		for(LinReport rep : json.readAll()){
			String xmlReq = xml.createRequest(rep);
			SASRestClient rest = new SASRestClient();
			rest.post(xmlReq);
			System.out.print(".");
		}
		System.out.println();
		System.out.println(" ... end");
		
		
		
	}
	
	public static void main(String[] args){
		LineageManager l = new LineageManager();
		l.load();
	}

}
