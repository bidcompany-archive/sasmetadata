package utils.props;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropsTag implements Props{
	
	private final String _PROPS_FOLDER = "props";
	private final String _PROPS_CONFIG_NAME = "engine.properties";
	
	private boolean configFound;
	
	private Properties prop = new Properties();
	
	
	private String tagJsonId;
	private String tagJsonName;
	private String tagJsonPath;
	
	private String tagJsonTable;
	private String tagJsonSources;
	private String tagJsonLinks;
	private String tagJsonFullName;
	
	private String tagJsonLibname;
	private String tagJsonLibdesc;
	private String tagJsonLibid;
	private String tagJsonLibpath;
	private String tagJsonDbname;
	private String tagJsonSchema;
	
	private String tagJsonTransf;
	private String tagJsonJobid;
	private String tagJsonJobname;
	private String tagJsonJobpath;
	private String tagJsonMapping;
	private String tagJsonResult;
	private String tagJsonXmlditems;
	private String tagJsonXmlcitems;
	private String tagJsonXMLaitems;
	private String tagJsonXMLhitems;
	
	private String tagJsonSource;
	private String tagJsonXref;
	private String tagJsonFormat;
	private String tagJsonExpr;
	private String tagJsonType;
	private String tagJsonUsage;
	private String tagJsonLabel;
	private String tagJsonHier;
	
	private String tagJsonAttrSeparator;
	private String jsonExtension;
	
	private String tagJsonLookupReport;
	private String tagJsonLookupTable;
	
	private String jsonImpactPrefix;
	
	
	private String tagLinLookupTable;
	private String tagLinProperties;
	private String tagLinLookupReport;
	private String tagLinPath;
	private String tagLinRelationships;
	private String tagLinRelationship;
	private String tagLinRelationshipType;
	private String tagLinMapping;
	private String tagLinLabel;
	private String tagLinDirection;
	private String tagLinDirectionTo;
	private String tagLinProperty;
	private String tagLinName;
	private String tagLinValue;
	private String tagLinResource;
	private String tagLinVersion;
	private int tagLinVersionNum;
	private String tagLinId;
	private String tagLinObjectType;
	private String tagLinRelationshipModels;
	private String tagLinRelationshipModel;
	private String tagLinDbms;
	private String tagLinDbmsSASValue;
	private String tagLinInstance;
	private String tagLinInstanceSASValue;
	
	private String extLin;


	private String linRelTypeA;
	private String linRelTypeI;

	
	
	public PropsTag() {
		loadProps();
	}

	public void loadProps(){
		/**
		 * Load Project Properties
		 */
		
		InputStream input = null;
		try{
			input = new FileInputStream(_PROPS_FOLDER + "/" + _PROPS_CONFIG_NAME);
			prop.load(input);
			
			tagJsonId 			= prop.getProperty("json.tag.id");
			tagJsonName			= prop.getProperty("json.tag.name");
			tagJsonPath			= prop.getProperty("json.tag.path");
			tagJsonTable		= prop.getProperty("json.tag.table");
			tagJsonSources		= prop.getProperty("json.tag.sources");
			tagJsonLinks		= prop.getProperty("json.tag.links");
			tagJsonFullName		= prop.getProperty("json.tag.fullname");
			tagJsonLibname		= prop.getProperty("json.tag.libname");
			tagJsonLibdesc		= prop.getProperty("json.tag.libdesc");
			tagJsonLibid		= prop.getProperty("json.tag.libid");
			tagJsonLibpath		= prop.getProperty("json.tag.libpath");
			tagJsonDbname		= prop.getProperty("json.tag.dbname");
			tagJsonSchema		= prop.getProperty("json.tag.schema");
			tagJsonTransf		= prop.getProperty("json.tag.transformation");
			tagJsonJobid		= prop.getProperty("json.tag.jobid");
			tagJsonJobname		= prop.getProperty("json.tag.jobname");
			tagJsonJobpath		= prop.getProperty("json.tag.jobpath");
			tagJsonMapping		= prop.getProperty("json.tag.mapping");
			tagJsonResult		= prop.getProperty("json.tag.result");
			tagJsonXmlditems	= prop.getProperty("json.tag.xmlditems");
			tagJsonXmlcitems	= prop.getProperty("json.tag.xmlcitems");
			tagJsonXMLaitems	= prop.getProperty("json.tag.xmlaitems");
			tagJsonXMLhitems	= prop.getProperty("json.tag.xmlhitems");
			
			tagJsonAttrSeparator	= prop.getProperty("json.tag.attr.separator");
			jsonExtension	= prop.getProperty("json.ext");
			tagJsonLookupReport	= prop.getProperty("json.tag.lookup.report");
			tagJsonLookupTable	= prop.getProperty("json.tag.lookup.table");
			
			tagJsonSource		= prop.getProperty("json.tag.source");
			tagJsonXref			= prop.getProperty("json.tag.xref");
			tagJsonFormat		= prop.getProperty("json.tag.format");
			tagJsonExpr			= prop.getProperty("json.tag.expr");
			tagJsonType			= prop.getProperty("json.tag.type");
			tagJsonUsage		= prop.getProperty("json.tag.usage");
			tagJsonLabel 		= prop.getProperty("json.tag.label");
			tagJsonHier			= prop.getProperty("json.tag.hier");
			
			
			jsonImpactPrefix 	= prop.getProperty("json.out.impact.prefix");
			
			tagLinLookupTable 			= prop.getProperty("lin.tag.lookup.table");
			tagLinLookupReport 			= prop.getProperty("lin.tag.lookup.report");
			tagLinProperties 			= prop.getProperty("lin.tag.properties");
			tagLinPath 					= prop.getProperty("lin.tag.path");
			tagLinRelationships 		= prop.getProperty("lin.tag.relationships");
			tagLinRelationship			= prop.getProperty("lin.tag.relationship");
			tagLinRelationshipType		= prop.getProperty("lin.tag.relationshipType");
			tagLinMapping 				= prop.getProperty("lin.tag.mapping");
			tagLinLabel 				= prop.getProperty("lin.tag.label");
			tagLinDirection 			= prop.getProperty("lin.tag.direction");
			tagLinDirectionTo 			= prop.getProperty("lin.tag.direction.to");
			tagLinProperty 				= prop.getProperty("lin.tag.property");
			tagLinName 					= prop.getProperty("lin.tag.name");
			tagLinValue 				= prop.getProperty("lin.tag.value");
			tagLinResource 				= prop.getProperty("lin.tag.resource");
			tagLinVersion 				= prop.getProperty("lin.tag.version");
			tagLinVersionNum 			= Integer.parseInt(prop.getProperty("lin.tag.version.num"));
			tagLinId 					= prop.getProperty("lin.tag.id");
			tagLinObjectType 			= prop.getProperty("lin.tag.objectType");
			tagLinRelationshipModels 	= prop.getProperty("lin.tag.relationshipModels");
			tagLinRelationshipModel 	= prop.getProperty("lin.tag.relationshipModel");
			tagLinDbms					= prop.getProperty("lin.tag.dbms");
			tagLinDbmsSASValue			= prop.getProperty("lin.tag.dbms.sas.value");
			tagLinInstance				= prop.getProperty("lin.tag.instance");
			tagLinInstanceSASValue		= prop.getProperty("lin.tag.instance.sas.value");
			
			
			extLin 						= prop.getProperty("lin.ext");
			
			linRelTypeA					= prop.getProperty("lin.rel.type.a");
			linRelTypeI					= prop.getProperty("lin.rel.type.i");
			
			
			
			configFound = true;
			
			
			
		}catch(Exception e){
			
			
			
			System.err.println("SAS Metadata Lineage use a <engine.properties> file for all settings.");
			System.err.println("Please, provide a proper <engine.properties> file.");
			
			configFound = false;
		}
	}
	
	

	public String getTagLinDbms() {
		return tagLinDbms;
	}

	public void setTagLinDbms(String tagLinDbms) {
		this.tagLinDbms = tagLinDbms;
	}

	public String getTagLinDbmsSASValue() {
		return tagLinDbmsSASValue;
	}

	public void setTagLinDbmsSASValue(String tagLinDbmsSASValue) {
		this.tagLinDbmsSASValue = tagLinDbmsSASValue;
	}

	public String getTagLinInstance() {
		return tagLinInstance;
	}

	public void setTagLinInstance(String tagLinInstance) {
		this.tagLinInstance = tagLinInstance;
	}

	public String getTagLinInstanceSASValue() {
		return tagLinInstanceSASValue;
	}

	public void setTagLinInstanceSASValue(String tagLinInstanceSASValue) {
		this.tagLinInstanceSASValue = tagLinInstanceSASValue;
	}

	public String getTagJsonHier() {
		return tagJsonHier;
	}

	public void setTagJsonHier(String tagJsonHier) {
		this.tagJsonHier = tagJsonHier;
	}

	public String getTagJsonXMLhitems() {
		return tagJsonXMLhitems;
	}

	public void setTagJsonXMLhitems(String tagJsonXMLhitems) {
		this.tagJsonXMLhitems = tagJsonXMLhitems;
	}

	public String getTagJsonXMLaitems() {
		return tagJsonXMLaitems;
	}

	public void setTagJsonXMLaitems(String tagJsonXMLaitems) {
		this.tagJsonXMLaitems = tagJsonXMLaitems;
	}

	public String getLinRelTypeA() {
		return linRelTypeA;
	}

	public void setLinRelTypeA(String linRelTypeA) {
		this.linRelTypeA = linRelTypeA;
	}

	public String getLinRelTypeI() {
		return linRelTypeI;
	}

	public void setLinRelTypeI(String linRelTypeI) {
		this.linRelTypeI = linRelTypeI;
	}

	public String getTagLinRelationshipModel() {
		return tagLinRelationshipModel;
	}

	public void setTagLinRelationshipModel(String tagLinRelationshipModel) {
		this.tagLinRelationshipModel = tagLinRelationshipModel;
	}

	public String getTagLinRelationshipType() {
		return tagLinRelationshipType;
	}

	public void setTagLinRelationshipType(String tagLinRelationshipType) {
		this.tagLinRelationshipType = tagLinRelationshipType;
	}

	public String getTagLinRelationship() {
		return tagLinRelationship;
	}

	public void setTagLinRelationship(String tagLinRelationship) {
		this.tagLinRelationship = tagLinRelationship;
	}

	public String getTagLinLookupTable() {
		return tagLinLookupTable;
	}

	public void setTagLinLookupTable(String tagLinLookupTable) {
		this.tagLinLookupTable = tagLinLookupTable;
	}

	public String getTagLinProperties() {
		return tagLinProperties;
	}

	public void setTagLinProperties(String tagLinProperties) {
		this.tagLinProperties = tagLinProperties;
	}

	public String getTagLinLookupReport() {
		return tagLinLookupReport;
	}

	public void setTagLinLookupReport(String tagLinLookupReport) {
		this.tagLinLookupReport = tagLinLookupReport;
	}

	public String getTagLinPath() {
		return tagLinPath;
	}

	public void setTagLinPath(String tagLinPath) {
		this.tagLinPath = tagLinPath;
	}

	public String getTagLinRelationships() {
		return tagLinRelationships;
	}

	public void setTagLinRelationships(String tagLinRelationships) {
		this.tagLinRelationships = tagLinRelationships;
	}

	public String getTagLinMapping() {
		return tagLinMapping;
	}

	public void setTagLinMapping(String tagLinMapping) {
		this.tagLinMapping = tagLinMapping;
	}

	public String getTagLinLabel() {
		return tagLinLabel;
	}

	public void setTagLinLabel(String tagLinLabel) {
		this.tagLinLabel = tagLinLabel;
	}

	public String getTagLinDirection() {
		return tagLinDirection;
	}

	public void setTagLinDirection(String tagLinDirection) {
		this.tagLinDirection = tagLinDirection;
	}

	public String getTagLinDirectionTo() {
		return tagLinDirectionTo;
	}

	public void setTagLinDirectionTo(String tagLinDirectionTo) {
		this.tagLinDirectionTo = tagLinDirectionTo;
	}

	public String getTagLinProperty() {
		return tagLinProperty;
	}

	public void setTagLinProperty(String tagLinProperty) {
		this.tagLinProperty = tagLinProperty;
	}

	public String getTagLinName() {
		return tagLinName;
	}

	public void setTagLinName(String tagLinName) {
		this.tagLinName = tagLinName;
	}

	public String getTagLinValue() {
		return tagLinValue;
	}

	public void setTagLinValue(String tagLinValue) {
		this.tagLinValue = tagLinValue;
	}

	public String getTagLinResource() {
		return tagLinResource;
	}

	public void setTagLinResource(String tagLinResource) {
		this.tagLinResource = tagLinResource;
	}

	public String getTagLinVersion() {
		return tagLinVersion;
	}

	public void setTagLinVersion(String tagLinVersion) {
		this.tagLinVersion = tagLinVersion;
	}

	public int getTagLinVersionNum() {
		return tagLinVersionNum;
	}

	public void setTagLinVersionNum(int tagLinVersionNum) {
		this.tagLinVersionNum = tagLinVersionNum;
	}

	public String getTagLinId() {
		return tagLinId;
	}

	public void setTagLinId(String tagLinId) {
		this.tagLinId = tagLinId;
	}

	public String getTagLinObjectType() {
		return tagLinObjectType;
	}

	public void setTagLinObjectType(String tagLinObjectType) {
		this.tagLinObjectType = tagLinObjectType;
	}

	public String getTagLinRelationshipModels() {
		return tagLinRelationshipModels;
	}

	public void setTagLinRelationshipModels(String tagLinRelationshipModels) {
		this.tagLinRelationshipModels = tagLinRelationshipModels;
	}

	public String getExtLin() {
		return extLin;
	}

	public void setExtLin(String extLin) {
		this.extLin = extLin;
	}

	public String getTagJsonLabel() {
		return tagJsonLabel;
	}

	public void setTagJsonLabel(String label) {
		this.tagJsonLabel = label;
	}

	public String getTagJsonSource() {
		return tagJsonSource;
	}

	public void setTagJsonSource(String source) {
		this.tagJsonSource = source;
	}

	public String getTagJsonXref() {
		return tagJsonXref;
	}

	public void setTagJsonXref(String xref) {
		this.tagJsonXref = xref;
	}

	public String getTagJsonFormat() {
		return tagJsonFormat;
	}

	public void setTagJsonFormat(String format) {
		this.tagJsonFormat = format;
	}

	public String getTagJsonExpr() {
		return tagJsonExpr;
	}

	public void setTagJsonExpr(String expr) {
		this.tagJsonExpr = expr;
	}

	public String getTagJsonType() {
		return tagJsonType;
	}

	public void setTagJsonType(String type) {
		this.tagJsonType = type;
	}

	public String getTagJsonUsage() {
		return tagJsonUsage;
	}

	public void setTagJsonUsage(String usage) {
		this.tagJsonUsage = usage;
	}

	public String getJsonImpactPrefix() {
		return jsonImpactPrefix;
	}

	public void setJsonImpactPrefix(String impactPrefix) {
		this.jsonImpactPrefix = impactPrefix;
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

	public void setProp(Properties prop) {
		this.prop = prop;
	}

	public String getTagJsonId() {
		return tagJsonId;
	}

	public void setTagJsonId(String id) {
		this.tagJsonId = id;
	}

	public String getTagJsonName() {
		return tagJsonName;
	}

	public void setTagJsonName(String name) {
		this.tagJsonName = name;
	}

	public String getTagJsonPath() {
		return tagJsonPath;
	}

	public void setTagJsonPath(String path) {
		this.tagJsonPath = path;
	}

	public String getTagJsonTable() {
		return tagJsonTable;
	}

	public void setTagJsonTable(String table) {
		this.tagJsonTable = table;
	}

	public String getTagJsonSources() {
		return tagJsonSources;
	}

	public void setTagJsonSources(String sources) {
		this.tagJsonSources = sources;
	}

	public String getTagJsonLinks() {
		return tagJsonLinks;
	}

	public void setTagJsonLinks(String links) {
		this.tagJsonLinks = links;
	}

	public String getTagJsonFullName() {
		return tagJsonFullName;
	}

	public void setTagJsonFullName(String fullName) {
		this.tagJsonFullName = fullName;
	}

	public String getTagJsonLibname() {
		return tagJsonLibname;
	}

	public void setTagJsonLibname(String libname) {
		this.tagJsonLibname = libname;
	}

	public String getTagJsonLibdesc() {
		return tagJsonLibdesc;
	}

	public void setTagJsonLibdesc(String libdesc) {
		this.tagJsonLibdesc = libdesc;
	}

	public String getTagJsonLibid() {
		return tagJsonLibid;
	}

	public void setTagJsonLibid(String libid) {
		this.tagJsonLibid = libid;
	}

	public String getTagJsonLibpath() {
		return tagJsonLibpath;
	}

	public void setTagJsonLibpath(String libpath) {
		this.tagJsonLibpath = libpath;
	}

	public String getTagJsonDbname() {
		return tagJsonDbname;
	}

	public void setTagJsonDbname(String dbname) {
		this.tagJsonDbname = dbname;
	}

	public String getTagJsonSchema() {
		return tagJsonSchema;
	}

	public void setTagJsonSchema(String schema) {
		this.tagJsonSchema = schema;
	}

	public String getTagJsonTransf() {
		return tagJsonTransf;
	}

	public void setTagJsonTransf(String transformation) {
		this.tagJsonTransf = transformation;
	}

	public String getTagJsonJobid() {
		return tagJsonJobid;
	}

	public void setTagJsonJobid(String jobid) {
		this.tagJsonJobid = jobid;
	}

	public String getTagJsonJobname() {
		return tagJsonJobname;
	}

	public void setTagJsonJobname(String jobname) {
		this.tagJsonJobname = jobname;
	}

	public String getTagJsonJobpath() {
		return tagJsonJobpath;
	}

	public void setTagJsonJobpath(String jobpath) {
		this.tagJsonJobpath = jobpath;
	}

	public String getTagJsonMapping() {
		return tagJsonMapping;
	}

	public void setTagJsonMapping(String mapping) {
		this.tagJsonMapping = mapping;
	}

	public String getTagJsonResult() {
		return tagJsonResult;
	}

	public void setTagJsonResult(String result) {
		this.tagJsonResult = result;
	}

	public String getTagJsonXmlditems() {
		return tagJsonXmlditems;
	}

	public void setTagJsonXmlditems(String xmlditems) {
		this.tagJsonXmlditems = xmlditems;
	}

	public String getTagJsonXmlcitems() {
		return tagJsonXmlcitems;
	}

	public void setTagJsonXmlcitems(String xmlcitems) {
		this.tagJsonXmlcitems = xmlcitems;
	}

	public String getTagJsonAttrSeparator() {
		return tagJsonAttrSeparator;
	}

	public void setTagJsonAttrSeparator(String attrSeparator) {
		this.tagJsonAttrSeparator = attrSeparator;
	}

	public String getJsonExtension() {
		return jsonExtension;
	}

	public void setJsonExtension(String extension) {
		this.jsonExtension = extension;
	}

	public String getTagJsonLookupReport() {
		return tagJsonLookupReport;
	}

	public void setTagJsonLookupReport(String lookupReport) {
		this.tagJsonLookupReport = lookupReport;
	}

	public String getTagJsonLookupTable() {
		return tagJsonLookupTable;
	}

	public void setTagJsonLookupTable(String lookupTable) {
		this.tagJsonLookupTable = lookupTable;
	}
	
	
	
	
	
	

}
