package utils.file;

import utils.props.PropsGeneral;

public class CleanManager {
	
	private PropsGeneral props;
	
	
	public CleanManager() {
		props = new PropsGeneral();
	}


	public void clean(){
		if(props.isCleanXML()){
			
			String folder = props.getDavXMLPath();
			String filter = "*";
			String extension = "xml";
			
			System.out.print("# Cleaning: " + folder + " [Filter=" + filter + " Extension=" + extension + "]");
			FileUtils.cleanFolder(folder, filter, extension, true);
		}
		if(props.isCleanJSON()){
			String folder = props.getJsonOutPath();
			String filter = "*";
			String extension = "json";
			
			System.out.print("# Cleaning: " + folder + " [Filter=" + filter + " Extension=" + extension + "]");
			FileUtils.cleanFolder(folder, filter, extension, true);
		}
		if(props.isCleanIBM()){
			String folder = props.getIbmCSVPath();
			String filter = "*";
			String extension = "csv";
			
			System.out.print("# Cleaning: " + folder + " [Filter=" + filter + " Extension=" + extension + "]");
			FileUtils.cleanFolder(folder, filter, extension, true);
		}
		if(props.isCleanLIN()){
			String folder = props.getLinPathRequest();
			String filter = "*";
			String extension = "xml";
			
			System.out.print("# Cleaning: " + folder + " [Filter=" + filter + " Extension=" + extension + "]");
			FileUtils.cleanFolder(folder, filter, extension, true);
		}
	}

}
