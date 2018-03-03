package utils.props;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropsIBM implements Props {
	
	private String queryName;
	private String queryColName;
	private int queryColNum;
	private String queryMemName;
	private String queryMemColName;
	private int queryMemColNum;
	private String reportName;
	private String reportColName;
	private int reportColNum;
	private String collectionName;
	private String collectionColName;
	private int modelColNum;
	private String modelName;
	private String modelColName;
	private int collectionColNum;
	private String collectionMemName;
	private String collectionMemColName;
	private int collectionMemColNum;
	private String relationsName;
	private String relationsColName;
	private int relationsColNum;
	private String tableName;
	private String tableColName;
	private int tableColNum;
	private String tableColsName;
	private String tableColsColName;
	private int tableColsColNum;
	private String serverName;
	private String serverColName;
	private int serverColNum;
	private String hostName;
	private String hostColName;
	private int hostColNum;
	private String databaseName;
	private String databaseColName;
	private int databaseColNum;
	private String schemaName;
	private String schemaColName;
	private int schemaColNum;
	private String structurePrefix;
	private String structureSuffixBegin;
	private String structureSuffixEnd;
	
	private String extmappingColName;
	private int extmappingColNum;

	private String structureDateFormat;
	
	private String infoServer;
	private String infoHost;
	
	
	private final Properties prop = new Properties();
	private final String _PROPS_FOLDER = "props";
	private final String _PROPS_CONFIG_NAME = "ibm.properties";
	
	
	private boolean configFound;
	

	@Override
	public void loadProps() {
		
		InputStream input = null;
		try{
			input = new FileInputStream(_PROPS_FOLDER + "/" + _PROPS_CONFIG_NAME);
			prop.load(input);
			
			queryName = prop.getProperty("ibm.query.name");
			queryColName = prop.getProperty("ibm.query.columns.name");
			queryColNum = Integer.parseInt(prop.getProperty("ibm.query.columns.num"));
			queryMemName = prop.getProperty("ibm.queryMem.name");
			queryMemColName = prop.getProperty("ibm.queryMem.columns.name");
			queryMemColNum = Integer.parseInt(prop.getProperty("ibm.queryMem.columns.num"));
			reportName = prop.getProperty("ibm.report.name");
			reportColName = prop.getProperty("ibm.report.columns.name");
			reportColNum = Integer.parseInt(prop.getProperty("ibm.report.columns.num"));
			collectionName = prop.getProperty("ibm.collection.name");
			collectionColName = prop.getProperty("ibm.collection.columns.name");
			collectionColNum = Integer.parseInt(prop.getProperty("ibm.collection.columns.num"));
			modelName = prop.getProperty("ibm.model.name");
			modelColName = prop.getProperty("ibm.model.columns.name");
			modelColNum = Integer.parseInt(prop.getProperty("ibm.model.columns.num"));
			collectionMemName = prop.getProperty("ibm.collectionMem.name");
			collectionMemColName = prop.getProperty("ibm.collectionMem.columns.name");
			collectionMemColNum = Integer.parseInt(prop.getProperty("ibm.collectionMem.columns.num"));
			relationsName = prop.getProperty("ibm.relations.name");
			relationsColName = prop.getProperty("ibm.relations.columns.name");
			relationsColNum = Integer.parseInt(prop.getProperty("ibm.relations.columns.num"));
			tableName = prop.getProperty("ibm.table.name");
			tableColName = prop.getProperty("ibm.table.columns.name");
			tableColNum = Integer.parseInt(prop.getProperty("ibm.table.columns.num"));
			tableColsName = prop.getProperty("ibm.tableCols.name");
			tableColsColName = prop.getProperty("ibm.tableCols.columns.name");
			tableColsColNum = Integer.parseInt(prop.getProperty("ibm.tableCols.columns.num"));
			serverName = prop.getProperty("ibm.server.name");
			serverColName = prop.getProperty("ibm.server.columns.name");
			serverColNum = Integer.parseInt(prop.getProperty("ibm.server.columns.num"));
			hostName = prop.getProperty("ibm.host.name");
			hostColName = prop.getProperty("ibm.host.columns.name");
			hostColNum = Integer.parseInt(prop.getProperty("ibm.host.columns.num"));
			databaseName = prop.getProperty("ibm.database.name");
			databaseColName = prop.getProperty("ibm.database.columns.name");
			databaseColNum = Integer.parseInt(prop.getProperty("ibm.database.columns.num"));
			schemaName = prop.getProperty("ibm.schema.name");
			schemaColName = prop.getProperty("ibm.schema.columns.name");
			schemaColNum = Integer.parseInt(prop.getProperty("ibm.schema.columns.num"));
			
			extmappingColName = prop.getProperty("ibm.extmapping.columns.name");
			extmappingColNum = Integer.parseInt(prop.getProperty("ibm.extmapping.columns.num"));
			
			structurePrefix = prop.getProperty("ibm.structure.prefix");
			structureSuffixBegin = prop.getProperty("ibm.structure.suffix.begin");
			structureSuffixEnd = prop.getProperty("ibm.structure.suffix.end");
			
			structureDateFormat = prop.getProperty("ibm.structure.dateformat");
			
			infoHost = prop.getProperty("ibm.info.host");
			infoServer = prop.getProperty("ibm.info.server");
			
			configFound = true;
			
		}catch(Exception e){
			
			System.err.println("SAS Metadata Lineage use a <ibm.properties> file for all settings.");
			System.err.println("Please, provide a proper <ibm.properties> file.");
			
			configFound = false;
		}

	}
	
	public int getModelColNum() {
		return modelColNum;
	}

	public void setModelColNum(int modelColNum) {
		this.modelColNum = modelColNum;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelColName() {
		return modelColName;
	}

	public void setModelColName(String modelColName) {
		this.modelColName = modelColName;
	}

	public String getInfoServer() {
		return infoServer;
	}

	public void setInfoServer(String infoServer) {
		this.infoServer = infoServer;
	}

	public String getInfoHost() {
		return infoHost;
	}

	public void setInfoHost(String infoHost) {
		this.infoHost = infoHost;
	}

	public String getStructureDateFormat() {
		return structureDateFormat;
	}

	public void setStructureDateFormat(String structureDateFormat) {
		this.structureDateFormat = structureDateFormat;
	}

	public PropsIBM() {
		loadProps();
	}
	


	public String getQueryName() {
		return queryName;
	}


	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}


	public String getQueryColName() {
		return queryColName;
	}


	public void setQueryColName(String queryColName) {
		this.queryColName = queryColName;
	}


	public int getQueryColNum() {
		return queryColNum;
	}


	public void setQueryColNum(int queryColNum) {
		this.queryColNum = queryColNum;
	}


	public String getQueryMemName() {
		return queryMemName;
	}


	public void setQueryMemName(String queryMemName) {
		this.queryMemName = queryMemName;
	}


	public String getQueryMemColName() {
		return queryMemColName;
	}


	public void setQueryMemColName(String queryMemColName) {
		this.queryMemColName = queryMemColName;
	}


	public int getQueryMemColNum() {
		return queryMemColNum;
	}


	public void setQueryMemColNum(int queryMemColNum) {
		this.queryMemColNum = queryMemColNum;
	}


	public String getReportName() {
		return reportName;
	}


	public void setReportName(String reportName) {
		this.reportName = reportName;
	}


	public String getReportColName() {
		return reportColName;
	}


	public void setReportColName(String reportColName) {
		this.reportColName = reportColName;
	}


	public int getReportColNum() {
		return reportColNum;
	}


	public void setReportColNum(int reportColNum) {
		this.reportColNum = reportColNum;
	}


	public String getCollectionName() {
		return collectionName;
	}


	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}


	public String getCollectionColName() {
		return collectionColName;
	}


	public void setCollectionColName(String collectionColName) {
		this.collectionColName = collectionColName;
	}


	public int getCollectionColNum() {
		return collectionColNum;
	}


	public void setCollectionColNum(int collectionColNum) {
		this.collectionColNum = collectionColNum;
	}


	public String getCollectionMemName() {
		return collectionMemName;
	}


	public void setCollectionMemName(String collectionMemName) {
		this.collectionMemName = collectionMemName;
	}


	public String getCollectionMemColName() {
		return collectionMemColName;
	}


	public void setCollectionMemColName(String collectionMemColName) {
		this.collectionMemColName = collectionMemColName;
	}


	public int getCollectionMemColNum() {
		return collectionMemColNum;
	}


	public void setCollectionMemColNum(int collectionMemColNum) {
		this.collectionMemColNum = collectionMemColNum;
	}


	public String getRelationsName() {
		return relationsName;
	}


	public void setRelationsName(String relationsName) {
		this.relationsName = relationsName;
	}


	public String getRelationsColName() {
		return relationsColName;
	}


	public void setRelationsColName(String relationsColName) {
		this.relationsColName = relationsColName;
	}


	public int getRelationsColNum() {
		return relationsColNum;
	}


	public void setRelationsColNum(int relationsColNum) {
		this.relationsColNum = relationsColNum;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getTableColName() {
		return tableColName;
	}


	public void setTableColName(String tableColName) {
		this.tableColName = tableColName;
	}


	public int getTableColNum() {
		return tableColNum;
	}


	public void setTableColNum(int tableColNum) {
		this.tableColNum = tableColNum;
	}


	public String getTableColsName() {
		return tableColsName;
	}


	public void setTableColsName(String tableColsName) {
		this.tableColsName = tableColsName;
	}


	public String getTableColsColName() {
		return tableColsColName;
	}


	public void setTableColsColName(String tableColsColName) {
		this.tableColsColName = tableColsColName;
	}


	public int getTableColsColNum() {
		return tableColsColNum;
	}


	public void setTableColsColNum(int tableColsColNum) {
		this.tableColsColNum = tableColsColNum;
	}


	public String getServerName() {
		return serverName;
	}


	public void setServerName(String serverName) {
		this.serverName = serverName;
	}


	public String getServerColName() {
		return serverColName;
	}


	public void setServerColName(String serverColName) {
		this.serverColName = serverColName;
	}


	public int getServerColNum() {
		return serverColNum;
	}


	public void setServerColNum(int serverColNum) {
		this.serverColNum = serverColNum;
	}


	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}


	public String getHostColName() {
		return hostColName;
	}


	public void setHostColName(String hostColName) {
		this.hostColName = hostColName;
	}


	public int getHostColNum() {
		return hostColNum;
	}


	public void setHostColNum(int hostColNum) {
		this.hostColNum = hostColNum;
	}


	public String getDatabaseName() {
		return databaseName;
	}


	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}


	public String getDatabaseColName() {
		return databaseColName;
	}


	public void setDatabaseColName(String databaseColName) {
		this.databaseColName = databaseColName;
	}


	public int getDatabaseColNum() {
		return databaseColNum;
	}


	public void setDatabaseColNum(int databaseColNum) {
		this.databaseColNum = databaseColNum;
	}


	public String getSchemaName() {
		return schemaName;
	}


	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}


	public String getSchemaColName() {
		return schemaColName;
	}


	public void setSchemaColName(String schemaColName) {
		this.schemaColName = schemaColName;
	}


	public int getSchemaColNum() {
		return schemaColNum;
	}


	public void setSchemaColNum(int schemaColNum) {
		this.schemaColNum = schemaColNum;
	}


	public String getStructurePrefix() {
		return structurePrefix;
	}


	public void setStructurePrefix(String structurePrefix) {
		this.structurePrefix = structurePrefix;
	}


	public String getStructureSuffixBegin() {
		return structureSuffixBegin;
	}


	public void setStructureSuffixBegin(String structureSuffixBegin) {
		this.structureSuffixBegin = structureSuffixBegin;
	}


	public String getStructureSuffixEnd() {
		return structureSuffixEnd;
	}


	public void setStructureSuffixEnd(String structureSuffixEnd) {
		this.structureSuffixEnd = structureSuffixEnd;
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

	public String getExtmappingColName() {
		return extmappingColName;
	}

	public void setExtmappingColName(String extmappingColName) {
		this.extmappingColName = extmappingColName;
	}

	public int getExtmappingColNum() {
		return extmappingColNum;
	}

	public void setExtmappingColNum(int extmappingColNum) {
		this.extmappingColNum = extmappingColNum;
	}

	
	
	
	
	
}
