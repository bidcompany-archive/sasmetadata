package json.manager;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import sas.meta.obj.LinReport;
import sas.meta.obj.LinTable;
import sas.meta.obj.LinTransformation;
import utils.file.FileUtils;
import utils.props.PropsGeneral;
import utils.props.PropsTag;

/**
 * Read Impact JSON 
 * @author itacsq
 *
 */
public class JSONReader {
	
	private final String json_folder;
	
	private final PropsGeneral prop = new PropsGeneral();
	private final PropsTag jTags = new PropsTag();
	
	public JSONReader(){
		json_folder = prop.getJsonOutPath();
	}
	
	
	
	public void print(){

		for(File f : FileUtils.ls(json_folder, jTags.getTagJsonLookupReport(), jTags.getJsonExtension() ) ){
			JSONObject reportJ = retrieve(f);
			LinReport report = fillReport(reportJ);
			report.toPrint();
		}
	}
	
	public LinReport read(String nm){
		for(File f : FileUtils.ls(json_folder, jTags.getTagJsonLookupReport() , jTags.getJsonExtension()) ){
			if(f.getName().toLowerCase().contains("report") && f.getName().toLowerCase().contains(nm.toLowerCase())){
				JSONObject reportJ = retrieve(f);
				LinReport report = fillReport(reportJ);
				return report;
			}
		}
		return null;
	}
	
	public List<LinReport> readAll(){
		List<LinReport> ret = new ArrayList<LinReport>();
		for(File f : FileUtils.ls(json_folder, jTags.getTagJsonLookupReport() , jTags.getJsonExtension() ) ){
			JSONObject reportJ = retrieve(f);
			LinReport report = fillReport(reportJ);
			ret.add(report);
		}
		return ret;
		
	}
	
	
	 
	private LinTable getNestedTable(JSONObject table){
		LinTable ret = new LinTable();
		
		
		/** TABLE: setup Infos */
		ret.setId((String) table.get(jTags.getTagJsonAttrSeparator() + jTags.getTagJsonId() ));
		ret.setName((String) table.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonName() ));
		ret.setPath((String) table.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonPath() ));
		
		
		ret.setLibId((String) table.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonLibid() ));
		ret.setLibname((String) table.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonLibname() ));
		ret.setLibdesc((String) table.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonLibdesc() ));
		ret.setLibpath((String) table.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonLibpath() ));
		ret.setDbname((String) table.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonDbname() ));
		ret.setSchema((String) table.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonSchema() ));
		
		
		JSONArray dItems = (JSONArray)table.get( jTags.getTagJsonXmlditems() );
		if(dItems!=null && dItems.size()>0){
			for(int i=0; i<dItems.size(); i++){
				String dItem = (String)dItems.get(i).toString();
				//System.out.println("\n dItem=" + dItem);
				ret.addDItem(dItem);
			}
		}
		
		
		JSONArray cItems = (JSONArray)table.get( jTags.getTagJsonXmlcitems() );
		if(cItems!=null && cItems.size()>0){
			for(int i=0; i<cItems.size(); i++){
				String cItem = (String)cItems.get(i).toString();
				//System.out.println("\n cItem=" + cItem);
				ret.addCItem(cItem);
			}
		}
		
		JSONArray aItems = (JSONArray)table.get( jTags.getTagJsonXMLaitems() );
		if(aItems!=null && aItems.size()>0){
			for(int i=0; i<aItems.size(); i++){
				String aItem = (String)aItems.get(i).toString();
				//System.out.println("\n aItem=" + aItem);
				ret.addAItem(aItem);
			}
		}
		
		JSONArray hItems = (JSONArray)table.get( jTags.getTagJsonXMLhitems() );
		if(hItems!=null && hItems.size()>0){
			for(int i=0; i<hItems.size(); i++){
				String hItem = (String)hItems.get(i).toString();
				//System.out.println("\n hItem=" + hItem);
				ret.addHItem(hItem);
			}
		}
		
			
		
		
		
		
		JSONArray links = (JSONArray) table.get( jTags.getTagJsonLinks() );
		if(links!=null){
			for(int i=0; i<links.size(); i++){
				JSONObject trJ = (JSONObject)( (JSONObject) links.get(i) ).get( jTags.getTagJsonTransf() );
				LinTransformation tr = new LinTransformation();
				
				/** TRANSFORMATIONS: get Infos */
				tr.setId( (String) trJ.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonId() ) );
				tr.setName( (String) trJ.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonName() ) );
				// TODO: Manage Reverse Mapping 
				tr.setJobId((String) trJ.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonJobid() ));
				tr.setJobName((String) trJ.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonJobname() ));
				tr.setJobPath((String) trJ.get( jTags.getTagJsonAttrSeparator() + jTags.getTagJsonJobpath() ));
				
				JSONArray sources = (JSONArray) trJ.get( jTags.getTagJsonSources() );
				if(sources!=null){
					for( int j=0; j<sources.size(); j++){
						JSONObject srcJ = (JSONObject)( (JSONObject)sources.get(j) ).get( jTags.getTagJsonTable() );
						if(srcJ!=null) {
							LinTable src = getNestedTable(srcJ);
							tr.addSource(src);
						}
					}
				}
				ret.linkTransformation(tr);
			}
		}
		return ret;
	}
	
	
	
	private LinReport fillReport(JSONObject reportJ){
		LinReport report = new LinReport();
		report.setId((String)reportJ.get(jTags.getTagJsonAttrSeparator() + jTags.getTagJsonId() ));
		report.setName((String) reportJ.get(jTags.getTagJsonAttrSeparator() + jTags.getTagJsonName() ));
		report.setPath((String) reportJ.get(jTags.getTagJsonAttrSeparator() + jTags.getTagJsonPath() ));
		
		
		JSONArray sources = (JSONArray) reportJ.get( jTags.getTagJsonSources() );
		if(sources!=null)
			for(int i=0; i<sources.size(); i++){
				JSONObject tableJ = (JSONObject)((JSONObject)sources.get(i)).get( jTags.getTagJsonTable() );
				LinTable table = getNestedTable(tableJ);
				report.addSource(table);
			}
		
		
		return report;
	}
	
	
	private JSONObject retrieve(File f){
		try{
			String content = new String(Files.readAllBytes(f.toPath()), StandardCharsets.UTF_8);
			JSONParser jsonParser=new JSONParser();
			JSONObject result = (JSONObject)jsonParser.parse(content);
			JSONObject ret = (JSONObject) result.get( jTags.getTagJsonResult() );
			return ret;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	

}
