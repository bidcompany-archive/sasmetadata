package sas.meta.tag.manager;

import java.io.StringReader;
import java.util.Properties;

import utils.props.PropsMetaTag;


/**
 * 
 * @author itacsq
 *
 */
public class TagReader {
	
	private final PropsMetaTag metaTag = new PropsMetaTag();
	
	
	private Properties properties;
	
	
	public TagReader(String text){
		properties = new Properties();
		try{
			properties.load(new StringReader(text));
		}catch(Exception e){e.printStackTrace();}
	}
	
	
	public String getDB(String env){
		if(metaTag.getDbTypeTeradata().equalsIgnoreCase(env))
			return metaTag.getTeradataDBName();
		
		return env.toUpperCase();
	}
	
	
	public String getSourceName(int pos){
		String ret = "";
		try{
			String key = metaTag.getPrefixInputTable() + pos + metaTag.getValueName();
			ret = properties.getProperty(key);
		}catch(Exception e){
			ret = metaTag.getNoValue();
		}
		return ret;
	}
	public String getTargetName(int pos){
		String ret = "";
		try{
			String key = metaTag.getPrefixOutputTable() + pos + metaTag.getValueName();
			ret = properties.getProperty(key);
		}catch(Exception e){
			ret = metaTag.getNoValue();
		}
		return ret;
	}
	
	
	
	public String getSourceLib(int pos){
		String ret = "";
		try{
			String key = metaTag.getPrefixInputTable() + pos + metaTag.getValueLib();
			ret = properties.getProperty(key);
		}catch(Exception e){
			ret = metaTag.getNoValue();
		}
		return ret;
	}
	public String getTargetLib(int pos){
		String ret = "";
		try{
			String key = metaTag.getPrefixOutputTable() + pos + metaTag.getValueLib();
			ret = properties.getProperty(key);
		}catch(Exception e){
			ret = metaTag.getNoValue();
		}
		return ret;
	}
	
	public String getSourceSchema(int pos){
		String ret = "";
		try{
			String key = metaTag.getPrefixInputTable() + pos + metaTag.getValueLib();
			ret = properties.getProperty(key);
		}catch(Exception e){
			ret = metaTag.getNoValue();
		}
		return ret;
	}
	
	public String getSourceDB(int pos){
		String ret = "";
		try{
			String key = metaTag.getPrefixInputTable() + pos + metaTag.getValueEnv();
			ret = getDB(properties.getProperty(key));
		}catch(Exception e){
			ret = metaTag.getNoValue();
		}
		return ret;
	}
	
	
	public int getInputCount(){
		String ret = "";
		try{
			ret = properties.getProperty(metaTag.getInputTableCount())==null?"0":properties.getProperty(metaTag.getInputTableCount());
			return Integer.parseInt(ret);
		}catch(Exception e){e.printStackTrace();}
		return -1;
	}
	
	public int getOutputCount(){
		String ret = "";
		try{
			ret = properties.getProperty(metaTag.getOutputTableCount())==null?"0":properties.getProperty(metaTag.getOutputTableCount());
			return Integer.parseInt(ret);
		}catch(Exception e){e.printStackTrace();}
		return -1;
	}
	
	
}
