package utils.props;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropsMetaTag implements Props {
	
	
	private String prefixInputTable;
	private String prefixOutputTable;
	private String inputTableCount;
	private String outputTableCount;
	
	private String valueName;
	private String valueLib;
	private String valueEnv;
	
	private String dbTypeTeradata;
	
	private String teradataDBName;
	
	private String noValue;
	
	
	private int linTransfNum;
	private int linLibNum;
	private int linReportNum;
	private int linTableNum;
	private int linColumnNum;
	private int linJobNum;
	
	
	private String linTransfName;
	private String linReportName;
	private String linTableName;
	
	
	private final Properties prop = new Properties();
	private final String _PROPS_FOLDER = "props";
	private final String _PROPS_CONFIG_NAME = "metatag.properties";
	
	private boolean configFound;
	
	
	

	@Override
	public void loadProps() {
		
		InputStream input = null;
		try{
			input = new FileInputStream(_PROPS_FOLDER + "/" + _PROPS_CONFIG_NAME);
			prop.load(input);
			
			prefixInputTable 		= prop.getProperty("meta.tag.prefix.input.table");
			prefixOutputTable		= prop.getProperty("meta.tag.prefix.output.table");
			inputTableCount			= prop.getProperty("meta.tag.input.table.count");
			outputTableCount		= prop.getProperty("meta.tag.output.table.count");
			
			valueName		= prop.getProperty("meta.tag.value.name");
			valueLib 		= prop.getProperty("meta.tag.value.lib");
			valueEnv 		= prop.getProperty("meta.tag.value.env");
			
			teradataDBName		= prop.getProperty("engine.db.name.teradata");
			dbTypeTeradata		= prop.getProperty("engine.db.type.teradata");
			
			noValue				= prop.getProperty("meta.tag.novalue");
			
			linTransfNum	= Integer.parseInt(prop.getProperty("lin.obj.transf.num"));
			linLibNum		= Integer.parseInt(prop.getProperty("lin.obj.lib.num"));
			linReportNum	= Integer.parseInt(prop.getProperty("lin.obj.report.num"));
			linTableNum		= Integer.parseInt(prop.getProperty("lin.obj.table.num"));
			linColumnNum	= Integer.parseInt(prop.getProperty("lin.obj.column.num"));
			linJobNum		= Integer.parseInt(prop.getProperty("lin.obj.job.num"));
			
			linTableName	= prop.getProperty("lin.obj.table.name");
			linReportName	= prop.getProperty("lin.obj.report.name");
			linTransfName	= prop.getProperty("lin.obj.transf.name");
			
			
			configFound = true;
			
		}catch(Exception e){
			
			System.err.println("SAS Metadata Lineage use a <metatag.properties> file for all settings.");
			System.err.println("Please, provide a proper <metatag.properties> file.");
			
			configFound = false;
		}	

	}


	
	

	public int getLinTransfNum() {
		return linTransfNum;
	}





	public void setLinTransfNum(int linTransfNum) {
		this.linTransfNum = linTransfNum;
	}





	public int getLinLibNum() {
		return linLibNum;
	}





	public void setLinLibNum(int linLibNum) {
		this.linLibNum = linLibNum;
	}





	public int getLinReportNum() {
		return linReportNum;
	}





	public void setLinReportNum(int linReportNum) {
		this.linReportNum = linReportNum;
	}





	public int getLinTableNum() {
		return linTableNum;
	}





	public void setLinTableNum(int linTableNum) {
		this.linTableNum = linTableNum;
	}





	public int getLinColumnNum() {
		return linColumnNum;
	}





	public void setLinColumnNum(int linColumnNum) {
		this.linColumnNum = linColumnNum;
	}





	public int getLinJobNum() {
		return linJobNum;
	}





	public void setLinJobNum(int linJobNum) {
		this.linJobNum = linJobNum;
	}





	public String getLinTransfName() {
		return linTransfName;
	}





	public void setLinTransfName(String linTransfName) {
		this.linTransfName = linTransfName;
	}





	public String getLinReportName() {
		return linReportName;
	}





	public void setLinReportName(String linReportName) {
		this.linReportName = linReportName;
	}





	public String getLinTableName() {
		return linTableName;
	}





	public void setLinTableName(String linTableName) {
		this.linTableName = linTableName;
	}





	public String getDbTypeTeradata() {
		return dbTypeTeradata;
	}

	public void setDbTypeTeradata(String dbTypeTeradata) {
		this.dbTypeTeradata = dbTypeTeradata;
	}

	public String getTeradataDBName() {
		return teradataDBName;
	}

	public void setTeradataDBName(String teradataDBName) {
		this.teradataDBName = teradataDBName;
	}

	public PropsMetaTag() {
		loadProps();
	}


	public String getPrefixInputTable() {
		return prefixInputTable;
	}




	public void setPrefixInputTable(String prefixInputTable) {
		this.prefixInputTable = prefixInputTable;
	}




	public String getPrefixOutputTable() {
		return prefixOutputTable;
	}




	public void setPrefixOutputTable(String prefixOutputTable) {
		this.prefixOutputTable = prefixOutputTable;
	}




	public String getInputTableCount() {
		return inputTableCount;
	}




	public void setInputTableCount(String inputTableCount) {
		this.inputTableCount = inputTableCount;
	}




	public String getOutputTableCount() {
		return outputTableCount;
	}




	public void setOutputTableCount(String outputTableCount) {
		this.outputTableCount = outputTableCount;
	}




	public String getValueName() {
		return valueName;
	}




	public void setValueName(String valueName) {
		this.valueName = valueName;
	}




	public String getValueLib() {
		return valueLib;
	}




	public void setValueLib(String valueLib) {
		this.valueLib = valueLib;
	}




	public String getValueEnv() {
		return valueEnv;
	}




	public void setValueEnv(String valueEnv) {
		this.valueEnv = valueEnv;
	}




	public boolean isConfigFound() {
		return configFound;
	}




	public void setConfigFound(boolean configFound) {
		this.configFound = configFound;
	}




	public Properties getProp() {
		return prop;
	}

	public String getNoValue() {
		return noValue;
	}

	public void setNoValue(String noValue) {
		this.noValue = noValue;
	}
	
	
	
	

}
