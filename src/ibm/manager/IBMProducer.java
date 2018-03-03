package ibm.manager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ibm.obj.BIObject;
import json.manager.JSONReader;
import sas.meta.obj.LinReport;
import sas.meta.obj.LinTable;
import sas.meta.obj.LinTransformation;
import utils.file.FileUtils;
import utils.props.PropsGeneral;
import utils.props.PropsIBM;

public class IBMProducer {
	
	private int counter = 0;
	private LinReport head;
	private String phyPath;
	private String logPath;
	private String now;
	
	private int position=0;
	
	private DateFormat dateFormat;
	private String ibmHostInfo;
	private String ibmServerInfo;
	
	private IBMFormatter ibmFormat;
	
	private final PropsGeneral props = new PropsGeneral();;
	private final PropsIBM ibm = new PropsIBM();
	
	
	public IBMProducer(){
		dateFormat = new SimpleDateFormat(ibm.getStructureDateFormat());
		ibmHostInfo = ibm.getInfoHost();
		ibmServerInfo = ibm.getInfoServer();
		ibmFormat = new IBMFormatter(ibmHostInfo, ibmServerInfo);
	}
	
	
	/**
	 * Create:
	 * - logical: 
	 * 	- query
	 * 	- collection
	 * - physical:
	 * 	- db (libname)
	 * 	- schema (libname)
	 * 	- table (metadata table)
	 * @param tbl
	 */
	private void updateTableCSV(LinTable tbl){
		
	
		
		/** Table: Logical */
		BIObject queryIBM = new BIObject( 	
					ibm.getQueryName(), 		
					ibm.getQueryColName(), 
					ibm.getQueryColNum(),
					ibmFormat.toIbm(tbl, ibm.getQueryName())
					);
		BIObject collIBM = new BIObject(	
					ibm.getCollectionName(), 
					ibm.getCollectionColName(), 
					ibm.getCollectionColNum(),
					ibmFormat.toIbm(tbl, ibm.getCollectionName())
					);
		BIObject modelIBM = new BIObject(
					ibm.getModelName(),
					ibm.getModelColName(),
					ibm.getModelColNum(),
					ibmFormat.toIbm(tbl, ibm.getModelName())
				);
		
		appendLogical(queryIBM.toFile());
		appendLogical(collIBM.toFile());
		appendLogical(modelIBM.toFile());
		
		/** Table: Physical */
		BIObject dbIBM = new BIObject(
				ibm.getDatabaseName(),
				ibm.getDatabaseColName(),
				ibm.getDatabaseColNum(),
				ibmFormat.toIbm(tbl, ibm.getDatabaseName())
				);
		
		BIObject schemaIBM = new BIObject( 	
				ibm.getSchemaName(),
				ibm.getSchemaColName(),
				ibm.getSchemaColNum(),
				ibmFormat.toIbm(tbl, ibm.getSchemaName())
				);
		
		BIObject tableIBM = new BIObject(	
				ibm.getTableName(),
				ibm.getTableColName(),
				ibm.getTableColNum(),
				ibmFormat.toIbm(tbl, ibm.getTableName())
				);
		
		
		appendPhysical(dbIBM.toFile());
		appendPhysical(schemaIBM.toFile());
		appendPhysical(tableIBM.toFile());
		
		
		if(tbl.hasXML()){
			
		}
		else {
			
		}
		
		boolean hasXML = false;
		
		/** Column: Logical */
		if(ibmFormat.toIbmLST(tbl, ibm.getQueryMemName(), hasXML).size()>0) {
			BIObject queryMemIBM = new BIObject(	
					ibm.getQueryMemName(),
					ibm.getQueryMemColName(),
					ibm.getQueryMemColNum(),
					ibmFormat.toIbmLST(tbl, ibm.getQueryMemName(), hasXML)
					);
			appendLogical(queryMemIBM.toFile());
		}
		
		if(ibmFormat.toIbmLST(tbl, ibm.getCollectionMemName(), hasXML).size()>0) {
			BIObject collMemIBM = new BIObject(		
					ibm.getCollectionMemName(),
					ibm.getCollectionMemColName(),
					ibm.getCollectionMemColNum(),
					ibmFormat.toIbmLST(tbl, ibm.getCollectionMemName(), hasXML)
					);
			appendLogical(collMemIBM.toFile());
		}
		
		if(ibmFormat.toIbmLST(tbl, ibm.getRelationsName(), hasXML).size()>0) {
			BIObject relIBM = new BIObject(
					ibm.getRelationsName(),
					ibm.getRelationsColName(),
					ibm.getRelationsColNum(),
					ibmFormat.toIbmLST(tbl, ibm.getRelationsName(), hasXML)
				);
			appendLogical(relIBM.toFile());
		}
		
		
		
		
		

		/** Column: Physical */
		BIObject tableColsIBM = new BIObject(
				ibm.getTableColsName(),
				ibm.getTableColsColName(),
				ibm.getTableColsColNum(),
				ibmFormat.toIbmLST(tbl, ibm.getTableColsName(), hasXML)
				);
		
		
		appendPhysical(tableColsIBM.toFile());
	}
	
	
	/**
	 * Parse all Table
	 * @param tbl
	 */
	private void parseTable(LinTable tbl){
		
		updateTableCSV(tbl);
		if(tbl.getLinkedTr()!=null)
			for(LinTransformation ltr : tbl.getLinkedTr()){
				parseTransformation(ltr, tbl);
			}	
	}
	
	
	private void updateTransfCSV(LinTransformation ltr, LinTable tgtTbl, List<LinTable> srcs){
		String line = ibm.getExtmappingColName();
		line += "\n";
		/* Name */
		line += "\"" + ltr.getName() + "\"" + ",";
		
		/* Source */
		if(srcs!=null && srcs.size()>0){
			line += "\"" ;
			boolean first = true;
			for(LinTable tbl : srcs){
				if(!first)
					line += ", " ;
				line += ibmServerInfo + "." + tbl.getDbname() + "." + tbl.getSchema() + "." + tbl.getName() + "" ;
				first = false;
			}
			line += "\"" + ",";
		}else 
			line += " " + ",";
		
		/* Target */
		line += "\"" + ibmServerInfo + "." + tgtTbl.getDbname() + "." + tgtTbl.getSchema() + "." + tgtTbl.getName() + "\"";
		
		/* Padding */
		line += ",,,,,,,,,,,";
		
		createMapping(line, ltr.getName().trim() + "_" + ltr.getId(), position);
		position++;
	}
	
	
	
	private void parseTransformation(LinTransformation ltr, LinTable srcTbl){
		updateTransfCSV(ltr, srcTbl, ltr.getSources() );
		
		if(ltr.getSources()!=null)
			for(LinTable tbl: ltr.getSources()){
				parseTable(tbl);
			}
		
	}
	
	
	/**
	 * Create reportIBM object
	 * @param rep
	 */
	private void parseReport(LinReport rep){
		BIObject repIbm = new BIObject(
				ibm.getReportName(),
				ibm.getReportColName(),
				ibm.getReportColNum(),
				ibmFormat.toIbm(rep));
		appendLogical(repIbm.toFile());
		
		for(LinTable tab: rep.getSources() ){
			parseTable(tab);
		}
		
	}
	
	
	private void createMapping(String content, String fname, int position){
		String fpath = props.getIbmCSVPath() + "/" + now + "_" + head.getName().trim() + "_" + head.getId() + "_c_map" + "_" + String.format("%03d", position) + "_" + fname + "." + "csv";
		FileUtils.write(fpath, content, false);
		counter+=1;
	}
	
	
	private void appendPhysical(String content){
		FileUtils.write(phyPath, content, true );
	}
	
	private void appendLogical(String content){
		FileUtils.write(logPath, content, true );
	}
	
	/**
	 * Define PhysicalPath and LogicalPath where output file are stored
	 * IBM Objects created:
	 * a. server
	 * b. host 
	 * 
	 * @param id
	 * @param name
	 */
	private void setUp(String id, String name, String path){
		position = 0;
		now = dateFormat.format(new Date());
		
		phyPath = props.getIbmCSVPath() + "/" + now + "_" + name + "_" + id + "_b_phy" + "." + "csv";
		logPath = props.getIbmCSVPath() + "/" + now + "_" + name + "_" + id + "_a_log" + "." + "csv";
	
		counter+=2;
		
		BIObject server = new BIObject(
				ibm.getServerName(),
				ibm.getServerColName(),
				ibm.getServerColNum(),
				ibmServerInfo + ",BI Reporting Server");
		
		
		BIObject host 	= new BIObject(
				ibm.getHostName(),
				ibm.getHostColName(),
				ibm.getHostColNum(),
				ibmServerInfo + ",BI Reporting Host");
		
		
		ibmFormat.setRepName(name);
		ibmFormat.setRepPath(path);
		
		/** Set Up Logical CSV */
		FileUtils.write(logPath, server.toFile(), false);
		
		/** Set Up Physical CSV */
		FileUtils.write(phyPath, host.toFile(), false);
		
		
	}
	
	
	
	/**
	 * For each Report it assign:
	 * 1. Head as the starting Report
	 * 2. SetUp define Server and Host
	 * 3. ParseReport go through linked Table and Transformation to create 
	 * 3.1 Physical and Logical Objects for Table
	 * 3.2 Extension Mapping for Transformations
	 * 
	 * @param rep
	 */
	public void parse(LinReport rep){
		if(rep!=null)
			head=rep;
		
		if(head!=null){
			this.head=rep;
			setUp(rep.getId(), rep.getName().trim(), rep.getPath());
			
			parseReport(rep);
		}
	}
	
	/**
	 * Parse all JSONs and create a LinReport list
	 * @param list
	 */
	public void parse(List<LinReport> list){
		if(list!=null){
			for(LinReport rep : list){
				parse(rep);
			}
		}
	}
	
	
	/**
	 * Parse all JSONs and create a LinReport list
	 */
	public void parse(){
		JSONReader json = new JSONReader();
		System.out.println("Creating IBM CSV ...");
		System.out.print(" -> csvs ");
		List<LinReport> reportList = json.readAll();
		for(LinReport rep : reportList){
			System.out.print(".");
			parse(rep);
		}
		System.out.println(" created: [" + counter + "] IBM CSVs for [" + reportList.size() + "] reports!");
	}
	
	

}
