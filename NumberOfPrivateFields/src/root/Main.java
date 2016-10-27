
package root;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author marius - Counts the number of (private+protected) fields from a java
 *         project
 */
public class Main {
	

	public static void main(String args[]) {
		System.out.println("Number of (private+protected) fields: " + countPrivateFieldsForFolder(new File("src")));
	}
	
	public static int countPrivateFieldsForFolder(final File folder) {
		int count = 0;
		
		ArrayList<File> files = getFilesForFolder(folder);
		// System.out.println(files.toString());
		

		for ( File file : files ) {
			count += countPrivateFieldsForFile(file);
		}
		
		return count;
	}
	
	public static int countPrivateFieldsForFile(final File file) {
		int count = 0;
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				line = line.trim();
				
				if ( line.contains("private class ") ) {
					continue;
				}
				
				if ( line.contains("private ") && (!line.contains("(")) ) {
					if ( line.indexOf("private ") == 0 )
						count++;
				}
				
				if ( line.contains("protected") && (line.indexOf("protected") == 0) && (!line.contains("(")) ) {
					// System.out.println(line);
					count++;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public static ArrayList<File> getFilesForFolder(final File folder) {
		ArrayList<File> files = new ArrayList<>();
		for ( final File fileEntry : folder.listFiles() ) {
			if ( fileEntry.isDirectory() ) {
				files.addAll(getFilesForFolder(fileEntry));
			} else if ( fileEntry.getName().endsWith(".java") ) {
				files.add(fileEntry);
			}
		}
		return files;
	}
	
}
