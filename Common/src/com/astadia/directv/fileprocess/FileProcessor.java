package src.com.astadia.directv.fileprocess;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.log4j.Logger;

import src.com.astadia.directv.config.AppConfig;
import src.com.astadia.directv.util.EmailUtils;

/**
 * This class is used to read the CSV files from the local directory.
 * 
 * @author jkannan
 *
 */
public class FileProcessor {

	private static Logger LOGGER = Logger.getLogger(FileProcessor.class);


	/**
	 * Get all the available files from the local directory.
	 * 
	 * @param appConfig
	 * @return
	 */
	public File[] getFilesFormFolder(final String sourceFolder, final String fileType, final AppConfig appConfig) {

		File directory = new File(sourceFolder);
		File[] files = null;
		
		try {
			
            files = directory.listFiles(new FileFilter() {
                public boolean accept(final File file0) {
                    return file0.getName().endsWith(fileType);
                }
            });
            
            /*
            Arrays.sort(files, new Comparator<File>() {
                public int compare(final File file0, final File file1) {
                    return new FileNameDateComparator().compare(file0.getName(), 
                            file1.getName());
                }
            });
			*/
            
            if (files.length == 0) {
            	LOGGER.info("No files to process.");
                EmailUtils email = new EmailUtils();
                String subject = "No File available to process.";
                String body = "There is no File available to process in the folder: " + sourceFolder;
            	email.sendEMail(appConfig, appConfig.getToAddressList(), subject, body);          	
            	System.exit(0);
            }

		}catch (Exception e) {
			LOGGER.error("Exception while reading the files ..." + e);
		}
		
		return files;
	}
	/**
	 * 
	 * @param sourceDir
	 * 			 Source location from where file will be moved
	 * @param destinationDir
	 * 			 Destination location where file need to be moved
	 * @return Status 
	 * 			 Status of file move
	 */
	public boolean moveFile(String sourceDir, String destinationDir){
		LOGGER.debug("Moving file from :- "+sourceDir + " to :-"+destinationDir);
		// File (or directory) to be moved
	    File srcFile = new File(sourceDir);
	    
	    // Destination directory
	    File destDir = new File(destinationDir);
	    
	    // Move file to new directory
	    boolean success = srcFile.renameTo(new File(destDir, srcFile.getName()));
		
	    if (!success) {
	    	LOGGER.debug("Not able to move file");
	}else{
		LOGGER.debug(" File moved sucessfully");
		srcFile.delete();
	}
		
		return success;
	}
	/**
	 * 
	 * @param sourceDir
	 * @param extenstion
	 * @return
	 */
	public boolean changeFileExtension(String sourceDir, String extenstion){
		
		LOGGER.debug("Moving file from :- "+ sourceDir + " to extenstion:-" + extenstion);
		
		// File (or directory) to be moved
	    File srcFile = new File(sourceDir);
	    
	    String srcFileName=srcFile.toString();
	    int dotPos = srcFileName.lastIndexOf(".");
	    
	    String strFilename = srcFileName.substring(0, dotPos);
	    String strNewFileName = strFilename + extenstion;
	    
	    // Move file to new directory
	    boolean success = srcFile.renameTo(new File(strNewFileName));
		
	    if (!success) {
	    	LOGGER.debug("Not able to move file");
		}else{
			LOGGER.debug(" File moved sucessfully");
			srcFile.delete();
		}
		
		return success;
				 
	}
	
	public static void copyfileContent(String srFile, String dtFile)  {
		
			LOGGER.debug("Copying file content from :- " + srFile + " to extenstion: " + dtFile);
			
			try {
				File detFile = new File(dtFile);
				PrintWriter printWriter = new PrintWriter(new BufferedWriter(
						new FileWriter(detFile, true)));
				
				FileWriter fileWritter = new FileWriter(dtFile,true);
    	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
				
				File file = new File(srFile);  
				  Scanner scanner = new Scanner(file);  
				//  scanner.useDelimiter("\\Z");

				   while (scanner.hasNext()) {  
					   bufferWritter.write(scanner.next());
					  // printWriter.println(scanner.next());
					   System.out.println("data -"+scanner.next());
				   }  
				   scanner.close();  
				   printWriter.flush();
				   printWriter.close();		 		 
				}
				catch(Exception e){
					LOGGER.error("Copying file content from :- " + srFile + " to extenstion: " + dtFile);
				} 
	        
	    }
	public void copy(String fromFileName, String toFileName) {
		File fromFile = new File(fromFileName);
		File toFile = new File(toFileName);

		FileInputStream from = null;
		FileOutputStream to = null;

		try {
		from = new FileInputStream(fromFile);
		to = new FileOutputStream(toFile);

		byte[] buffer = new byte[4096];
		int bytesRead;

		while ((bytesRead = from.read(buffer)) != -1)
		to.write(buffer, 0, bytesRead); // write
		} catch (FileNotFoundException e) {
		LOGGER.debug("Error in copying the file "+e.getMessage());
		} catch (IOException e) {
		LOGGER.debug("Error in copying the file "+e.getMessage());
		} finally {
		if (from != null) {
		try {
		from.close();
		} catch (IOException e) {
		LOGGER.debug("Error in copying the file "+e.getMessage());
		}
		}

		if (to != null) {
		try {
		to.close();
		} catch (IOException e) {
		LOGGER.debug("Error in copying the file "+e.getMessage());
		}
		}
		}
		}
	
}
