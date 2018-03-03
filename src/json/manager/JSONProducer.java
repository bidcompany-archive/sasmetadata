package json.manager;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.cedarsoftware.util.io.JsonWriter;

import sas.meta.obj.LinReport;
import sas.meta.obj.LinTable;
import sas.meta.obj.LinTransformation;
import utils.props.PropsGeneral;
import utils.props.PropsTag;
import xml.reader.obj.XMLAggrItem;
import xml.reader.obj.XMLCalcItem;
import xml.reader.obj.XMLDataItem;
import xml.reader.obj.XMLHierItem;
import xml.reader.obj.XMLSource;

@SuppressWarnings("unchecked")
public class JSONProducer {
	
	private String jsonAttrSep;
	
	private String outPrefix;
	private String jsonExt;
	
	private JSONFormatter jsonFormat;
	
	
	
	private final String json_folder;
	
	
	private final PropsGeneral prop = new PropsGeneral();
	private final PropsTag jTags = new PropsTag();
	
	public JSONProducer(){
		json_folder = prop.getJsonOutPath();
		jsonExt = jTags.getJsonExtension() ;
		outPrefix = jTags.getJsonImpactPrefix() ;
		jsonAttrSep = jTags.getTagJsonAttrSeparator();
		jsonFormat = new JSONFormatter();
	}
	
	
	public void publish(LinTable object){
		JSONObject result = new JSONObject();
		JSONObject table = getTable(object);
		result.put(jTags.getTagJsonResult(), table);
		Path file = Paths.get(json_folder + "/" + jTags.getTagJsonLookupTable() + "_" + outPrefix + "_" + object.getId() + "." + jsonExt);
		try (BufferedWriter writer = Files.newBufferedWriter(file)) {
			writer.append(JsonWriter.formatJson(result.toString()));
			writer.close();
		}catch(Exception e){}
	}
	
	public void publish(LinReport object){
		JSONObject result = new JSONObject();
		JSONObject reportJ = getReport(object);
		result.put(jTags.getTagJsonResult(), reportJ);
		Path file = Paths.get(json_folder + "/" + jTags.getTagJsonLookupReport() + "_" + outPrefix + "_" + object.getId() + "." + jsonExt);
		try (BufferedWriter writer = Files.newBufferedWriter(file)) {
			writer.append(JsonWriter.formatJson(result.toString()));
			writer.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/** 
	 * update (METADATA_ID , XML_SOURCE) 
	 * locate json via metadata id and update this file including two JSONArray:
	 * 1. Data Item
	 * 2. Calculated Item
	 * 
	 * 
	 * @param metaId
	 * @param source
	 */
	public void update(String metaId, XMLSource source){
		/** Rebuild ReportJ object */
		JSONObject reportJ = retrieve(metaId);
		if(reportJ!=null){
			
			JSONArray sources = (JSONArray) reportJ.get( jTags.getTagJsonSources() );
			
			/** Locate the pointing source */
			if(sources!=null){
				for( int j=0; j<sources.size(); j++){
					
					JSONObject srcJ = (JSONObject)( (JSONObject)sources.get(j) ).get( jTags.getTagJsonTable() );
					if(source.getLabel().equals(srcJ.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonName() ))){
						
						
						JSONArray dItems = new JSONArray();
						for(XMLDataItem dItem : source.getDItems()){
							try{
								dItems.add(jsonFormat.format(dItem));
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						/** Update the XML_DATA_ITEMS even if void */
						srcJ.remove(jTags.getTagJsonXmlditems());
						srcJ.put(jTags.getTagJsonXmlditems(), dItems);
						
						
						
						JSONArray cItems = new JSONArray();
						for(XMLCalcItem cItem : source.getCItems()){
							try{
								cItems.add(jsonFormat.format(cItem));
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						/** Update the XML_CALC_ITEMS even if void */
						srcJ.remove(jTags.getTagJsonXmlcitems());
						srcJ.put(jTags.getTagJsonXmlcitems(), cItems);
						
						
						
						JSONArray aItems = new JSONArray();
						for(XMLAggrItem aItem : source.getAItems()){
							try{
								aItems.add(jsonFormat.format(aItem));
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						/** Update the XML_AGGR_ITEMS even if void */
						srcJ.remove(jTags.getTagJsonXMLaitems());
						srcJ.put(jTags.getTagJsonXMLaitems(), aItems);
						
						JSONArray hItems = new JSONArray();
						for(XMLHierItem hItem : source.getHItems()){
							try{
								hItems.add(jsonFormat.format(hItem));
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						/** Update the XML_HIER_ITEMS even if void */
						srcJ.remove(jTags.getTagJsonXMLhitems());
						srcJ.put(jTags.getTagJsonXMLhitems(), hItems);
						
					}
					
					
				}
			}
			
			
			JSONObject result = new JSONObject();
			result.put(jTags.getTagJsonResult(), reportJ);
			
			Path file = Paths.get(json_folder + "/" + jTags.getTagJsonLookupReport() + "_" + outPrefix +"_" + metaId + "." + jsonExt);;
			try (BufferedWriter writer = Files.newBufferedWriter(file)) {
				writer.append(JsonWriter.formatJson(result.toString()));
				writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	private JSONObject getReport(LinReport report){
		JSONObject reportInfo = new JSONObject();
		reportInfo.put(jsonAttrSep + jTags.getTagJsonId(), report.getId());
		reportInfo.put(jsonAttrSep + jTags.getTagJsonName(), report.getName());
		reportInfo.put(jsonAttrSep + jTags.getTagJsonPath(), report.getPath());
		
		if(report.getSourceSize()>0){
			JSONArray sources = new JSONArray();
			for(LinTable src : report.getSources()){
				JSONObject srcJ = getTable(src);
				sources.add(srcJ);
			}
			reportInfo.put(jTags.getTagJsonSources(), sources);
		}
		return reportInfo;
	}
	
	
	
	private JSONObject getTable(LinTable table){
		JSONObject tblinfo = new JSONObject();
		
		tblinfo.put(jsonAttrSep + jTags.getTagJsonId(), table.getId());
		tblinfo.put(jsonAttrSep + jTags.getTagJsonName(), table.getName());
		tblinfo.put(jsonAttrSep + jTags.getTagJsonPath(), table.getPath());
		tblinfo.put(jsonAttrSep + jTags.getTagJsonLibid(), table.getLibId());
		tblinfo.put(jsonAttrSep + jTags.getTagJsonLibdesc(), table.getLibdesc() ); // TODO: fix lib desc - libname
		tblinfo.put(jsonAttrSep + jTags.getTagJsonLibname(), table.getLibname() ); // TODO: fix libname - lib desc
		tblinfo.put(jsonAttrSep + jTags.getTagJsonLibpath(), table.getLibpath() );
		tblinfo.put(jsonAttrSep + jTags.getTagJsonDbname(), table.getDbname() );
		tblinfo.put(jsonAttrSep + jTags.getTagJsonSchema(), table.getSchema() );
		tblinfo.put(jsonAttrSep + jTags.getTagJsonFullName(), table.getFullName() );

		
		if(null!=table.getLinkedTr() && table.getLinkedTr().size()>0){
			JSONArray trList = new JSONArray();
			for(LinTransformation ltr : table.getLinkedTr()){
				
				JSONObject trinfo = new JSONObject();
				trinfo.put(jsonAttrSep + jTags.getTagJsonId(), ltr.getId());
				trinfo.put(jsonAttrSep + jTags.getTagJsonName(), ltr.getName());
				trinfo.put(jsonAttrSep + jTags.getTagJsonMapping(), ltr.getMapping());
				trinfo.put(jsonAttrSep + jTags.getTagJsonJobid(), ltr.getJobId());
				trinfo.put(jsonAttrSep + jTags.getTagJsonJobname(), ltr.getJobName());
				trinfo.put(jsonAttrSep + jTags.getTagJsonJobpath(), ltr.getJobPath());
				
				
				if(null!=ltr.getSources() && ltr.getSources().size()>0){
					JSONArray srcJ = new JSONArray();
					for(LinTable lstbl : ltr.getSources()){
						JSONObject src = new JSONObject();
						src = getTable(lstbl);
						srcJ.add(src);
					}
					trinfo.put(jTags.getTagJsonSources(), srcJ);
				}
				JSONObject trJ = new JSONObject();
				trJ.put(jTags.getTagJsonTransf(), trinfo);
				trList.add(trJ);
				
			}
			tblinfo.put(jTags.getTagJsonLinks(), trList);
		}
		
		JSONObject ret = new JSONObject();
		ret.put( jTags.getTagJsonTable() , tblinfo);
		return ret;
	}
	
	
	private JSONObject retrieve(String metaId){
		Path dir = Paths.get(json_folder);
		List<File> files = new ArrayList<>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*" + metaId + "*.{" + jsonExt + "}")) {
		    for (Path entry: stream) {
		        files.add(entry.toFile());
		        if(prop.isDavLogUrl())
					System.out.println("\nJSON: " + entry.getFileName() );
				
		    }
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			for(File f: files){
				String content = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
				JSONParser jsonParser=new JSONParser();
				JSONObject result = (JSONObject)jsonParser.parse(content);
				JSONObject ret = (JSONObject) result.get(jTags.getTagJsonResult());
				return ret;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	
	
	

}
