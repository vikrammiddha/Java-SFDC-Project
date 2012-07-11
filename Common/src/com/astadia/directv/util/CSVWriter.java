package src.com.astadia.directv.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

/**
 * This class is use to write data in the specified format of a file.
 * 
 * @author jkannan
 *
 */

public class CSVWriter {
	
	/**
	 * Initialise the logger
	 */
	private static Logger LOGGER = Logger.getLogger(CSVWriter.class);
		
	/**
	 * Object for dateformat for CSV file
	 */
	private SimpleDateFormat dateFormaterForcsvFile =null;
	
	/**
	 * To get today's date
	 */
	private Calendar today = Calendar.getInstance();
	
	/**
	 * taking current date and converting it into csv file dateformat
	 */
	private String todayDate =null;
	/**
	 * Constructor
	 */
	public CSVWriter(String fileDateFormat) {
		
		dateFormaterForcsvFile = new SimpleDateFormat(fileDateFormat);
		todayDate = dateFormaterForcsvFile.format(today.getTime());
	}

	/**
	 * Creating the file and writing the data into that file
	 * 
	 * @param csvFileName,
	 *            name of the CSV file
	 * @param csvFileData,
	 *            Data that needs to add in CSV file
	 * @throws IOException
	 *             when data could not add in CSV file
	 */
	public static void addDataToCSVFile(String csvFileName, String csvFileData)	throws IOException {
		
		final String METHOD_NAME = "addDataToCSVFile";
		LOGGER.info( METHOD_NAME);

		File tmpFile = new File(csvFileName);
		PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile, true)));
		printWriter.println(csvFileData);
		
		LOGGER.debug("Data is written in the csv file, data is : " + csvFileData);

		printWriter.flush();
		printWriter.close();
	}
	
	public String getTodayDate() {
		return todayDate;
	}
	/**
	 * This method return the file name based on current date 
	 * 
	 * @param csvFileName,
	 *            name of the CSV file
	 * @param csvFileData,
	 *            Data that needs to add in CSV file
	 * @throws IOException
	 *             when data could not add in CSV file
	 */
	public String getFileName(String csvFileName, String csvFileType){
		 
		//csvFileName = appConfig.getAbsolutePath()
		//+ CommonConstants.DIRECTORY_SEPERATOR
		csvFileName = csvFileName + todayDate + csvFileType;
	 	
		return csvFileName;
	}	
}
