package sas.lineage.rest;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import utils.file.FileUtils;
import utils.props.PropsGeneral;

public class SASRestClient {
	
	
	
	private PropsGeneral prop = new PropsGeneral();
	
	
	public int post(String xmlNm){
		int ret = -1;
		try{
			URL url = new URL(prop.getLinRestURL());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.setRequestProperty("Accept", "application/xml");
			
			String authString = prop.getLinServerUser() + ":" + prop.getLinServerPass();
	        String authStringEnc = new String(Base64.getEncoder().encode(authString.getBytes()));
	        conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
			
	        String input = FileUtils.cat(prop.getLinPathRequest() + "/" + xmlNm + ".xml");
	        
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
	        
			if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}
			
			ret = conn.getResponseCode();
			conn.disconnect();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
}
