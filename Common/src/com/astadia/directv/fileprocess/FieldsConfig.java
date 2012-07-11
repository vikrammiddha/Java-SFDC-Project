package src.com.astadia.directv.fileprocess;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

import org.apache.log4j.Logger;

import src.com.astadia.directv.exception.DirectTVException;

/**
 * This class is used to read the Field names(Header) and its SFDC API name from the given properties file.
 * Key: Header Name
 * Value: SFDC Field API name.
 * 
 * @author jkannan
 *
 */
public class FieldsConfig {

	private static Logger LOGGER = Logger.getLogger(FieldsConfig.class);
	
	/**
	 * Private constructor.
	 */
	private FieldsConfig() {
		throw new UnsupportedOperationException("Class is not instantiable.");
	}
	
	/**
	 * Input in the file should be in format of object.field_api,header value pairs. 
	 * Key is the field API name and value is the header name that should be displayed in output file.
	 * Key can't be null. If any of the filed API value is null, this method will throw exception that its not a valid file.
	 * Header values can be null for any of the values.
	 * throws DirectTVException if files is not in proper format.
	 * 
	 * @param propFileName: the name of the properties file. eg: "directv.properties".
	 * @return
	 */
	public static Map<String, String> getFieldsConfig(String propFileName) throws DirectTVException{

		LOGGER.info("FieldsConfig:getFieldsConfig(): Reading the Fields from the properties file .........");

		Map<String, String> fieldsMap = new LinkedHashMap<String, String>();
		String strLine;
		String[] flds = new String[2];
		try {
			BufferedReader br
			   = new BufferedReader(new FileReader(propFileName));
			while ((strLine = br.readLine()) != null || !"".equals((strLine = br.readLine())))   {
				flds = strLine.split(",");
				if(strLine.indexOf(",")<0){
			    	throw new DirectTVException("Error in reading the filed from input file. Line " +strLine+"\n");
			    }
			    flds[0] = flds[0].trim();
			    flds[1] = flds[1].trim();
			    if(flds[0] == null || "".equals(flds[0])){
			    	throw new DirectTVException("Fields value in the input file can't be null. Valid format : Object.Fields API Name,Header Name\n" +strLine+"\n");
			    }
			    if(flds[0].startsWith("\\*")){
			    	flds[0] = flds[0].substring(1, flds[0].length());
			    }
			    fieldsMap.put(flds[0], flds[1]);
			}
			
		} catch (IOException e) {
			LOGGER.error("Error in reading the Fields from the properties file: " + propFileName);
			LOGGER.error("Error: " + e);
		}
		catch(Exception e){
			LOGGER.error("Error in reading the Fields from the properties file: " + propFileName);
			LOGGER.error("Error: " + e);
		}
		return fieldsMap;
	}
	
	
	/**
	 * To read the SFDC API name from the given properties file.
	 * Key: Header Name
	 * Value: SFDC Field API name.
	 * 
	 * @param propFileName: the name of the properties file. eg: "directv.properties".
	 * @return
	 * @throws DirectTVException 
	 */
	public static String getFieldsQueryString(String propFileName) throws DirectTVException {

		LOGGER.info("FieldsConfig:getFieldsQueryString(): Reading the Fields from the properties file .........");

		StringBuffer sb = new StringBuffer();
		String strLine;
		String[] flds = new String[2];
		try {
			BufferedReader br
			   = new BufferedReader(new FileReader(propFileName));
			while ((strLine = br.readLine()) != null)   {
				flds = strLine.split(",");
				if(strLine.indexOf(",")<0){
			    	throw new DirectTVException("Error in reading the filed from input file. Line " +strLine+"\n");
			    }
			    flds[0] = flds[0].trim();
			    if(flds[0] == null || "".equals(flds[0].trim())){
			    	throw new DirectTVException("Fields value in the input file can't be null. Valid format : Object.Fields API Name,Header Name. Line :-" +strLine +"\n");
			    }
			    if(flds[0].startsWith("*")){
			    	flds[0] = flds[0].substring(1, flds[0].length());
			    }
			    sb.append(flds[0] != null ? flds[0].trim() + "," : ",");
			    }
			
		} catch (IOException e) {
			LOGGER.error("Error in reading the Fields from the properties file: " + propFileName);
			LOGGER.error("Error: " + e);
		}
		
		String str = sb != null ? sb.toString() : "";
		str = str.length() > 0 ? str.substring(0, str.length() - 1) : "";
		
		return str;
	}
	
	
	/**
	 * To read the Fields names(Header) from the given properties file.
	 * Key: Header Name
	 * Value: SFDC Field API name.
	 * 
	 * @param propFileName: the name of the properties file. eg: "directv.properties".
	 * @return
	 * @throws DirectTVException 
	 */
	public static List getFieldsWidth(String propFileName, String seperator) throws DirectTVException {

		LOGGER.info("FieldsConfig:getFieldsQueryString(): Reading the Fields from the properties file .........");

		List<String> widthList = new ArrayList<String>();
		String strLine;
		String[] flds = new String[2];
		try {
			BufferedReader br
			   = new BufferedReader(new FileReader(propFileName));
			while ((strLine = br.readLine()) != null)   {
				if(strLine.indexOf(",")<0){
			    	throw new DirectTVException("Error in reading the field from input file. Line " +strLine+"\n");
			    }
				flds = strLine.split(",");
				 if(flds[0] == null || "".equals(flds[0].trim())){
				    	throw new DirectTVException("Fields value in the input file can't be null. Valid format : Object.Fields API Name,Header Name. Line :-" +strLine +"\n");
				}
				 if(flds[1] == null || "".equals(flds[1].trim())){
				    	throw new DirectTVException("Fields value in the input file can't be null. Valid format : Object.Fields API Name,Header Name. Line :-" +strLine +"\n");
				}
				flds[0] = flds[0].trim();
				flds[1] = flds[1].trim();
				widthList.add(flds[1]);
			    }
			
		} catch (IOException e) {
			LOGGER.error("Error in reading the Fields from the properties file: " + propFileName);
			LOGGER.error("Error: " + e);
			throw new DirectTVException("Error in reading the Fields from the properties file: " + propFileName);
		}
		return widthList;
	}
	
	public static void createContolFile(String fileName,String msg){
		Writer output = null;
	    File file = new File(fileName);
	    try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(msg);
			output.close();
		} catch (IOException e) {
			LOGGER.error("Error in writing data to Controller file \n" );
			LOGGER.error("Error: " + e);
		}
	}
	
	/**
	 * To read the SFDC API name from the given properties file.
	 * Key: Header Name
	 * Value: SFDC Field API name.
	 * 
	 * @param propFileName: the name of the properties file. eg: "directv.properties".
	 * @return
	 * @throws DirectTVException 
	 */
	public static String getFieldsQueryStringWithOutHeader(String propFileName) throws DirectTVException {

		LOGGER.info("FieldsConfig:getFieldsQueryString(): Reading the Fields from the properties file .........");

		StringBuffer sb = new StringBuffer();
		String strLine;
		try {
			BufferedReader br
			   = new BufferedReader(new FileReader(propFileName));
			while ((strLine = br.readLine()) != null)   {
				 if(strLine == null || "".equals(strLine.trim())){
				    	throw new DirectTVException("Fields value in the input file can't be null. Valid format : Object.Fields API Name,Header Name\n");
				    }
				if(strLine.trim().length() == 0){
					throw new DirectTVException("Error in reading the fields from the input file .Please remove blank line from file.");
				}
			    
			     if(strLine.indexOf(".") <0){
				 	throw new DirectTVException("Error in reading the fields from the input file .Fields may not be in proper format (Object.Field_API_NAME,Header Value)");
			 	}
			   
			    if(strLine.startsWith("*")){
			    	strLine = strLine.substring(1,strLine.length());
			    }
			  
			    sb.append(strLine!= null ? strLine.trim() + "|" : "|");
			    }
			
		} catch (IOException e) {
			LOGGER.error("Error in reading the Fields from the properties file: " + propFileName);
			LOGGER.error("Error: " + e);
		}
		
		String str = sb != null ? sb.toString() : "";
		str = str.length() > 0 ? str.substring(0, str.length() - 1) : "";
		System.out.println("Hello"+str);
		return str;
	}
	/**
	 * <p>
	 * This method will read the Field name file of format <<SFDC API NAME>>,Column width.
	 * Method will validate the file and return the String that contain all Column in following format
	 * <<SFDC API NAME>>,Column width|<<SFDC API NAME>>,Column width|<<SFDC API NAME>>,Column width
	 * </p>
	 * @param propFileName: the name of the properties file. eg: "directv.properties".
	 * @return String :- Out put string contains all column name sepreated  with |
	 * @throws DirectTVException : Exception thrown if the field name file is not in correct format.
	 */
	public static String getFieldsQueryStringWithOutHeaderWithLenght(String propFileName) throws DirectTVException {

		LOGGER.info("FieldsConfig:getFieldsQueryString(): Reading the Fields from the properties file .........");

		StringBuffer sb = new StringBuffer();
		String strLine;
		try {
			BufferedReader br
			   = new BufferedReader(new FileReader(propFileName));
			while ((strLine = br.readLine()) != null)   {
				 if(strLine == null || "".equals(strLine.trim())){
				    	throw new DirectTVException("Fields value in the input file can't be null. Valid format : Object.Fields API Name,Header Name\n");
				    }
				if(strLine.trim().length() == 0){
					throw new DirectTVException("Error in reading the fields from the input file .Please remove blank line from file.");
				}
 			     if(strLine.indexOf(",") <0){
					 	throw new DirectTVException("Error in reading the fields from the input file .Fields may not be in proper format (Object.Field_API_NAME,Field Length)");
				 	}
			   
			    if(strLine.startsWith("*")){
			    	strLine = strLine.substring(1,strLine.length());
			    }
			  
			    sb.append(strLine!= null ? strLine.trim() + "|" : "|");
			    }
			
		} catch (IOException e) {
			LOGGER.error("Error in reading the Fields from the properties file: " + propFileName);
			LOGGER.error("Error: " + e);
		}
		
		if(sb !=null && sb.toString().trim().length() >0){
			return sb.toString().substring(0, sb.toString().length() -1);
		}else{
			return "";
		}
		//String str = sb != null ? sb.toString() : "";
		//str = str.length() > 0 ? str.substring(0, str.length() - 1) : "";
		
		//return str;
	}
	/**
	 * To read the Fields names(Header) from the given properties file.
	 * Key: Header Name
	 * Value: SFDC Field API name.
	 * 
	 * @param propFileName: the name of the properties file. eg: "directv.properties".
	 * @return
	 * @throws DirectTVException 
	 */
	public static String getFieldsHeader(String propFileName, String seperator) throws DirectTVException {

		LOGGER.info("FieldsConfig:getFieldsQueryString(): Reading the Fields from the properties file .........");

		StringBuffer sb = new StringBuffer();
		String strLine;
		String[] flds = new String[2];
		try {
			BufferedReader br
			   = new BufferedReader(new FileReader(propFileName));
			while ((strLine = br.readLine()) != null)   {
				if(strLine.indexOf(",")<0){
			    	throw new DirectTVException("Error in reading the field from input file. Line " +strLine+"\n");
			    }
				flds = strLine.split(",");
				 if(flds[0] == null || "".equals(flds[0].trim())){
				    	throw new DirectTVException("Fields value in the input file can't be null. Valid format : Object.Fields API Name,Header Name. Line :-" +strLine +"\n");
				    }
				flds[0] = flds[0].trim();
				if(flds.length <2)sb.append(",");
				else sb.append(flds[1] != null ? flds[1] + "," : ",");
			    }
			
		} catch (IOException e) {
			LOGGER.error("Error in reading the Fields from the properties file: " + propFileName);
			LOGGER.error("Error: " + e);
			throw new DirectTVException("Error in reading the Fields from the properties file: " + propFileName);
		}
		
		String str = sb != null ? sb.toString() : "";
		str = str.length() > 0 ? str.substring(0, str.length() - 1) : "";
		
		return str;
	}
	/**
	 * To read the SFDC API name from the given properties file.
	 * Key: Header Name
	 * Value: SFDC Field API name.
	 * 
	 * @param propFileName: the name of the properties file. eg: "directv.properties".
	 * @return
	 * @throws DirectTVException 
	 */
	public static Map getFieldsQueryStringWithLenghtMultipleObject(String propFileName) throws DirectTVException {

		LOGGER.info("FieldsConfig:getFieldsQueryString(): Reading the Fields from the properties file .........");
		Map<String,String> queryMap = new HashMap<String,String>();
		StringBuffer sb = new StringBuffer();
		String strLine;
		StringBuffer callQuery = new StringBuffer();
		StringBuffer leadQuery = new StringBuffer();
		StringBuffer dncQuery= new StringBuffer();
		try {
			BufferedReader br
			   = new BufferedReader(new FileReader(propFileName));
			while ((strLine = br.readLine()) != null)   {
				 if(strLine == null || "".equals(strLine.trim())){
				    	throw new DirectTVException("Fields value in the input file can't be null. Valid format : Object.Fields API Name,Header Name\n");
				    }
				if(strLine.trim().length() == 0){
					throw new DirectTVException("Error in reading the fields from the input file .Please remove blank line from file.");
				}
			    
//			     if(strLine.indexOf(".") <0){
//				 	throw new DirectTVException("Error in reading the fields from the input file .Fields may not be in proper format (Object.Field_API_NAME,Field Length)");
//			 	}
			     if(strLine.indexOf(",") <0){
					 	throw new DirectTVException("Error in reading the fields from the input file .Fields may not be in proper format (Object.Field_API_NAME,Field Length)");
				 	}
			   
			    if(strLine.startsWith("*")){
			    	strLine = strLine.substring(1,strLine.length());
			    }
			    if(strLine.contains("Call__c")){
			    	strLine = strLine.substring(0,strLine.length());
			    	callQuery.append(strLine!= null ? strLine.trim() + "|" : "|");
			    	
			    }
			    if(strLine.contains("Lead")){
			    	strLine = strLine.substring(0,strLine.length());
			    	leadQuery.append(strLine!= null ? strLine.trim() + "|" : "|");
			    	
			    }
			    if(strLine.contains("DNC__c")){
			    	strLine = strLine.substring(0,strLine.length());
			    	dncQuery.append(strLine!= null ? strLine.trim() + "|" : "|");
			    	
			    }
			    //sb.append(strLine!= null ? strLine.trim() + "|" : "|");
			    }
			
		} catch (IOException e) {
			LOGGER.error("Error in reading the Fields from the properties file: " + propFileName);
			LOGGER.error("Error: " + e);
		}
		
		String callQueryStr = callQuery != null ? callQuery.toString() : "";
		
		if(callQueryStr.length() > 0 ){
			queryMap.put("Call__c", callQueryStr.substring(0, callQueryStr.length() - 1));
			System.out.println("callQueryStr"+callQueryStr);
		}
		
		String leadQueryStr = leadQuery != null ? leadQuery.toString() : "";
		if(leadQueryStr.length() > 0 ){
			queryMap.put("Lead", leadQueryStr.substring(0, leadQueryStr.length() - 1));
			System.out.println("leadQueryStr "+leadQueryStr);
		}
		
		String dncQueryStr = dncQuery != null ? dncQuery.toString() : "";
		if(dncQueryStr.length() > 0 ){
			queryMap.put("DNC__c", dncQueryStr.substring(0, dncQueryStr.length() - 1));
			System.out.println("dncQueryStr "+dncQueryStr);
		}
		
		 return queryMap;
	}
}
