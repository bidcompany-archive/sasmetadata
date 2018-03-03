package utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sas.meta.obj.LinReport;
import utils.props.PropsGeneral;
import utils.props.PropsTag;

public class Dav {
	
	private String wgetCmd = "\"\"";
	private PropsGeneral prop;
	private PropsTag jTags;
	
	public Dav(){
		prop = new PropsGeneral();
		jTags = new PropsTag();
		wgetCmd = prop.getWgetPath();
	}
	
	public void download(List<LinReport> reportList){
		
		System.out.print("Retrieving XML ... ");
		System.out.println("... found: [" + reportList.size() + "]");
		System.out.print(" -> Downloading XML: ");
		for(LinReport report:reportList){
			System.out.print(".");
			try{ 
				String workinDir = System.getProperty("user.dir") ;
				
				String urlStr = prop.getVaServerAddr() + report.getPath() + "(Report)";
				URL url = new URL(urlStr);
				if(wgetCmd.contains(" "))
					wgetCmd="\"" + wgetCmd + "\"";
				
				//String cmd = wgetCmd + " --no-check-certificate -U Mozilla --user=" + prop.getMetaServerUser() + " --password=" + prop.getMetaServerPass() + " \"" + url + "\" -O \"" + workinDir + "/" + prop.getDavXMLPath() + "/" + report.getId() + ".xml\"";
				String[] cmd = { wgetCmd, "--no-check-certificate", "-U", "Mozilla", "--user=" + prop.getLinServerUser(), "--password=" + prop.getMetaServerPass(), url + "", "-O", workinDir + "/xml/" + report.getId() + ".xml" };
				if(prop.isDavLogUrl())
					System.out.println("\nURL: [" + Arrays.toString(cmd) + "]");
				executeCommand(cmd);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println(" done!");
	}
	
	public void download(LinReport report){
		try{ 
			String workinDir = System.getProperty("user.dir") ;
			
			String urlStr = prop.getVaServerAddr() + report.getPath() + "(Report)";
			URL url = new URL(urlStr);
			//String cmd = wgetCmd + " --no-check-certificate -U Mozilla --user=" + prop.getLinServerUser() + " --password=" + prop.getMetaServerPass() + " \"" + url + "\" -O \"" + workinDir + "/xml/" + report.getId() + ".xml\"";
			String[] cmd = { wgetCmd, "--no-check-certificate", "-U", "Mozilla", "--user=" + prop.getLinServerUser(), "--password=" + prop.getMetaServerPass(), url + "", "-O", workinDir + "/xml/" + report.getId() + ".xml" };
			executeCommand(cmd);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	public String executeCommand(String[] cmd) {
		String oline = "";
		try {
			// Use a ProcessBuilder
			ProcessBuilder pb = new ProcessBuilder(cmd);
			
			Process p = pb.start();
			InputStream is = p.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line = null;
			while ((line = br.readLine()) != null) {
				oline += "\n" + line;
			}
			int r = p.waitFor(); // Let the process finish.
			if (r == 0) { // No error
				System.out.println("Completed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(prop.isDavLogUrl()) {
			System.out.println("CMD: " + Arrays.toString(cmd));
			System.out.println("RES: " + oline);
		}
		return oline;
	}
	
		
	/**
	 * getAvailableReports 
	 * extract parsed created Json
	 * => "report" is fixed because only report search is allowed
	 * @return ID List
	 */
	public List<String> getAvailableReports(){
		List<String> ret = new ArrayList<String>();
		for(File f : FileUtils.ls(prop.getJsonOutPath(), jTags.getTagJsonLookupReport(), jTags.getJsonExtension())) {
			String id = f.getName().split("_")[f.getName().split("_").length-1].replaceAll("." + jTags.getJsonExtension(), "");
			ret.add(id);
		}
		return ret;
	}
	

}
