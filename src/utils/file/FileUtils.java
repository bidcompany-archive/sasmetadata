package utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {
	
	
	public static List<File> ls(String dir, String filter, String exts){
		Path dirP = Paths.get(dir);
		List<File> ret = new ArrayList<File>();
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirP, "*" + filter + "*.{" + exts + "}")) {
		    for (Path entry: stream)
		        ret.add(entry.toFile());
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}

	
	public static String cat(String url){
		Path fileP = Paths.get(url);
		String content = "";
		try{
			content = new String( Files.readAllBytes(fileP) );
		}catch(Exception e){
			e.printStackTrace();
		}
		return content;
		
	}
	
	
	public static void write(String pathTo, String content, boolean append){
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathTo, append))) {
			if(append)
				bw.append(content);
			else
				bw.write(content);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanFolder(String dir, String filter, String exts, boolean print){
		Path dirP = Paths.get(dir);
		int i=0;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirP, "*" + filter + "*.{" + exts + "}")) {
			System.out.print(" -> [");
		    for (Path entry: stream){
		    	i++;
		        Files.delete(entry);
		        System.out.print(".");
		    }
		    System.out.print("] - Deleted (" + i + ") items\n");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
		
	
}
