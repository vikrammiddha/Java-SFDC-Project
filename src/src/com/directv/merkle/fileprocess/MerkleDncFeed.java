package src.com.directv.merkle.fileprocess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import src.com.astadia.directv.config.AppConfig;
import src.com.astadia.directv.config.Configurator;
import src.com.astadia.directv.exception.DirectTVException;
import src.com.astadia.directv.logging.CustomLoggerDnc;
import src.com.astadia.directv.logging.DirectTvLogger;
import src.com.astadia.directv.logging.LogInitialiser;
import src.com.astadia.directv.sfdc.EnterpriseSession;
import src.com.astadia.directv.util.CSVWriter;
import src.com.astadia.directv.util.CommonUtil;
import src.com.astadia.directv.util.EmailUtils;
import src.com.astadia.directv.util.SalesforceUtils;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.SObject;
import src.com.astadia.directv.fileprocess.FieldsConfig;

/*########################################################################### 
# Project Name..........: DirecTV Merkle Data Feed
# File..................: MerkleDataFeed.java
# Version...............: 1.0
# Created by............: Vikram Middha
# Created Date..........: 31-July-2010
# Last Modified by......: 
# Last Modified Date....: 
# Description...........: This class is used to Export the Merkle DNC date . 
# Change Request History: 				   							 
########################################################################### 
*/

public class MerkleDncFeed {
	
	/** Captures id of the Data Feed Job Object created . Used when updating  DFJ.**/
	
	private String retId="";
	
	/** New Line character.**/
	
	private String newLine = System.getProperty("line.separator");
	
	/** Session object that stores SalesForce session info.**/
	
	private EnterpriseSession eSession = null;
	
	/** Logger declaration.**/
	
	private static Log log = LogFactory.getLog(MerkleDncFeed.class);
	
	/** Logger declaration.**/
	
	private static Logger LOGGER = Logger.getLogger(MerkleDncFeed.class);
	
	/** Application Config object. Stores application level config info.**/
	
	private static AppConfig appConfig = null;
	
	/** QuerySFDC object. Used to call methods to interact Salesforce.**/
	
	private static QuerySFDC querySfdc = null;
	
	/** Contains error data to be stored in error file.**/
	
	private static StringBuffer errorBody = new StringBuffer();
	
	/** Contains data to be sent in email alerts.**/
	
	private static StringBuffer emailBody = new StringBuffer();
	
	/** Contains data about Feed process. eg start time,end time etc.**/
	
	private static StringBuffer controllerStr = new StringBuffer();
	
	/** Total number of records fetched from Salesforce..**/
	
	private static Double totalRecords = 0.0;
	
	/** Total number of records successfully processed.**/
	
	private static Double recordsProcessed = 0.0;
	
	/** Total number of records failed.**/
	
	private static Double failureRecords = 0.0;
	
	/** Name of success file.**/
	
	private static String succFile="";
	
	/** Name of Error file.**/
	
	private static String failFile="";
	
	/** CSVWrited object used for file operations.**/
	
	private static CSVWriter csvWriObj = null;
	
	/** Feed start time.**/
	
	private static String startTime ="";
	
	/**
	 * Constructor to initialize AppConfig and EnterpriseSession.
	 * @throws DirectTVException
	*/
	
	public MerkleDncFeed() throws DirectTVException {
		
		DirectTvLogger.start(log,"MerkleDataFeed()");
		LogInitialiser.initializeLogger("DirecTv");
		BasicConfigurator.configure();
		appConfig = Configurator.getAppConfig();
		csvWriObj = new CSVWriter(appConfig.getMerkleDncFeedFileDateFormat());
		startTime = CommonUtil.getCurrentTime();
		
		// connecting to salesforce and initializing session object with session id.
		
		try{
			eSession = SalesforceUtils.initMasterSession(appConfig);
			
		}catch(DirectTVException de) {
			LOGGER.error("Exception while initializing SFDC Login Session...: " + de);
			throw de;
		}
		querySfdc = new QuerySFDC(eSession);
		DirectTvLogger.end(log,"MerkleDataFeed()");
	}
	
	/**
	 * The main class.
	 * 
	 */
	public static void main(String []s) {
	    
		final String METHOD_NAME="main";
		
		DirectTvLogger.start(log,METHOD_NAME);
		MerkleDncFeed v = null;
		
		try {
			v = new MerkleDncFeed();
			
			// Insert entry in Data Feed Job and mark the status = 'Started'
			
			v.InsertUpdateJobTracking("INSERT");
			DirectTvLogger.info(log, METHOD_NAME,"Calling Extract_DncObjectData() method from main class." );
			
			//Extract the data from salesforce.
			
			v.Extract_DncObjectData();
		} catch (Exception e){
			LOGGER.error("Exception occured...: " + e );
			if(!emailBody.toString().contains(e.getMessage())){
				emailBody.append("Exception occured :" + e + System.getProperty("line.separator"));
			}
		}
		finally{
			DirectTvLogger.info(log, METHOD_NAME,"Moved file from Processing folder to Success and Error folder."); 

			// Finally send the email notification to mail group with summary of errors if any
			try{
				DirectTvLogger.info(log, METHOD_NAME,"Creating controllfile." );
				if(v != null){
					
					// Update Data Feed Job object and mark the status = 'Completed'
					
					v.InsertUpdateJobTracking("UPDATE");
					
					// Create controle file that will indicate that the process has been completed.
					
					FieldsConfig.createContolFile(appConfig.getMerkleDncFeedFolderSuccess()+ 
							csvWriObj.getFileName(appConfig.getMerkleDncFeedFileControllerName(), appConfig.getMerkleDncFeedFileControllerType()),"Total Number of records Succesfully processed :-"+recordsProcessed);
				}
				try {
					csvWriObj.addDataToCSVFile(failFile,errorBody.toString());
				} catch (IOException e) {
					DirectTvLogger.error(log, METHOD_NAME,"Not able to write exceptions detail in error file : " + failFile); 
				}
				}
			catch(Exception e){
				DirectTvLogger.error(log, METHOD_NAME,"Exception occured : " + failFile);
				emailBody.append("Exception occured :" + e + System.getProperty("line.separator"));
			}
			finally{
				JobCompletionAlert();
			}
		}
		DirectTvLogger.info(log, METHOD_NAME,"Job Summary : " + controllerStr);
		DirectTvLogger.start(log,"end");
	}
	
	/*
     * This function inserts an entry in Data_Feed_Job__c object when job starts and updates the status
     * to 'Completed' when job finishes
     * @param op
     */
	
	public void InsertUpdateJobTracking(String op){
	
		final String METHOD_NAME="InsertUpdateJobTracking";
		
		DirectTvLogger.start(log,METHOD_NAME);
		try{
			/*Finally insert the Data Feed Job entry in SFDC weather the job is success or failure .*/
			if(op.equals("INSERT")){
				DirectTvLogger.info(log, METHOD_NAME,"Inserting entry in Data_Feed_Job__c object with the job summary." );
				HashMap<String,Object>[] dncArray = new HashMap[1];
				HashMap<String,Object> dncMap = new HashMap<String,Object>(); 
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis());
				dncMap.put("Start_Time__c", cal.getTime());
				dncMap.put("Job_Name__c", "Merkle_DNC_Feed_"+csvWriObj.getTodayDate());
				dncMap.put("Job_Status__c", "Started");
				dncMap.put("Name", "Merkle_DNC_Feed_"+csvWriObj.getTodayDate());
				dncMap.put("ObjectType", "Data_Feed_Job__c");
				dncArray[0]=dncMap;
				retId = querySfdc.createDataFeedJob(dncArray);
				DirectTvLogger.info(log, METHOD_NAME,"Inserted entry in Data_Feed_Job__c object successfully with the job summary." );
			}
			else{
				DirectTvLogger.info(log, METHOD_NAME,"Updating entry in Data_Feed_Job__c object with the job summary." );
				String[] ids = new String[1];
				ids[0]=retId;
				HashMap<String,Object>[] dncArray = new HashMap[1];
				HashMap<String,Object> dncMap = new HashMap<String,Object>(); 
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis());
				dncMap.put("End_Time__c", cal.getTime());
				dncMap.put("ObjectType", "Data_Feed_Job__c");
				dncMap.put("Total_Records_Attempted__c", totalRecords);
				dncMap.put("Total_Failures__c", failureRecords);
				dncMap.put("Job_Status__c", "Completed");
				dncMap.put("Total_Records_Successfully_Processed__c", recordsProcessed);
				dncMap.put("Output_Filename__c", new File(succFile).getName());
				dncMap.put("Errors_Filename__c", new File(failFile).getName());
							
				String logFileName = CustomLoggerDnc.logFile;
				if(logFileName.indexOf("\\") > 0){
					logFileName=logFileName.substring(logFileName.lastIndexOf("\\")+1,logFileName.length());
				}else if(logFileName.indexOf("/") > 0){
					logFileName=logFileName.substring(logFileName.lastIndexOf("/")+1,logFileName.length());
				}
				
				dncMap.put("Log_Filename__c", logFileName);
				dncArray[0]=dncMap;
				querySfdc.updateDataFeedJob(ids,dncArray);
				DirectTvLogger.info(log, METHOD_NAME,"Updated entry in Data_Feed_Job__c object successfully with the job summary." );
			}
			
			
		}
		catch(Exception e){
			DirectTvLogger.error(log, METHOD_NAME,"Not able to insert Data Feed Job object in salesforce due to exception :" + e );
			emailBody.append(newLine +"Not able to insert Data Feed Job object in salesforce due to exception :" + e + newLine);
		}
		DirectTvLogger.end(log,METHOD_NAME);
	}
	
	/**
     * This function send the success and error emails alerts to the users mentioned in directtv.properties
     * 
     */
	
	public static void JobCompletionAlert(){
		
		final String METHOD_NAME="JobCompletionAlert";
		
		DirectTvLogger.start(log,METHOD_NAME);
		DirectTvLogger.info(log, METHOD_NAME,"Sending mail with job summary to email users specified in config file." );
		String emailSubject = "";
		
		// preparing the information to be sent in the email alerts.
		
		if(controllerStr.length()<=0){
			
			controllerStr.append("Job Name:Merkle_DNC_Feed_"+csvWriObj.getTodayDate()+System.getProperty("line.separator"));
			controllerStr.append("Job status :- Completed\n");
			controllerStr.append("Start time :- " + startTime + System.getProperty("line.separator"));
			controllerStr.append("End Time :- " + CommonUtil.getCurrentTime() + System.getProperty("line.separator"));
			controllerStr.append("Total Records Attempted :- " + totalRecords + System.getProperty("line.separator"));
			controllerStr.append("Total Records Succesfully processed :- " + recordsProcessed + System.getProperty("line.separator"));
			controllerStr.append("Total Failures :- " + failureRecords + System.getProperty("line.separator"));
			
		}
		
		//if emailBody is not null, send error alert mail
		
		if(emailBody != null && emailBody.length() != 0) {
			emailSubject = "Merkle DNC Export: Error Summary";
			sendEMail(emailSubject,controllerStr.toString() +  System.getProperty("line.separator") + "Error Message :" + emailBody.toString() + System.getProperty("line.separator"),appConfig.getMerkleDncFeedEmailToFailAddressList());
			
		}
		emailSubject = "Job Completed - Merkle DNC Feed";
		
		sendEMail(emailSubject,controllerStr.toString() + System.getProperty("line.separator"),appConfig.getMerkleDncFeedEmailToSuccessAddressList());
		DirectTvLogger.end(log,METHOD_NAME);
	}
	
	 /**
     * This function exports the dnc__c object data in output files .
     * @throws IOException,DirectTVException 
     */
	
	public void Extract_DncObjectData() throws IOException, DirectTVException{
		
		// local variables declarations
		
		final String METHOD_NAME="Extract_DncObjectData";
		
		DirectTvLogger.start(log,METHOD_NAME);
		
		int thresholdCnt = Integer.parseInt(appConfig.getMerkleDncFeedThreshold());
		
		DirectTvLogger.debug(log, METHOD_NAME,"Threshold limit given in config file : "+ thresholdCnt );
		
		StringBuffer csvDataWithoutHeader = new StringBuffer();
		
		HashMap<String,Object>[] dncMap = null;
		
		List<String> errorList = new ArrayList<String>();
		
		StringBuffer queryString = new StringBuffer();
		
		StringBuffer uniqueQueryString = new StringBuffer();
		
		Map<String,String> succRecords = new HashMap<String,String>();
		
		String tmp = "";
		
		boolean areaCode = false;
		
		List<String> widthList = new ArrayList<String>();
		
		File fp = null ;
		
		boolean success=true;
		
		// creating success and failure file names.
		
		succFile=appConfig.getMerkleDncFeedFolderSuccess() + csvWriObj.getFileName("INC_MERKLE_DNC_", appConfig.getMerkleDncFeedFileType());
		failFile=appConfig.getMerkleDncFeedFolderError() + csvWriObj.getFileName("INC_MERKLE_DNC_FAILED_RECORDS_",".log");
		
		//checking if Log file folder exists or not. If not, creating it.
		
		fp = new File(appConfig.getMerkleDncFeedFolderLog()) ;
		success=true;
		if(!fp.exists()){
			success = fp.mkdirs();
		}
		if(!success){
			emailBody.append("Not able to create Log directory :" + appConfig.getMerkleDncFeedFolderLog() + newLine);
			throw new DirectTVException("Not able to create Log directory :" + appConfig.getMerkleDncFeedFolderLog());
		}
		
		// checking if Success file folder exists or not. If not, creating it.
		
		fp = new File(appConfig.getMerkleDncFeedFolderSuccess()) ;
		success=true;
		if(!fp.exists()){
			success = fp.mkdirs();
		}
		if(!success){
			emailBody.append("Not able to create Success directory :" + appConfig.getMerkleDncFeedFolderSuccess() + newLine);
			throw new DirectTVException("Not able to create Processing directory :" + appConfig.getMerkleDncFeedFolderSuccess());
		}
		
		// checking if Error file folder exists or not. If not, creating it.
		
		fp = new File(appConfig.getMerkleDncFeedFolderError()) ;
		success=true;
		if(!fp.exists()){
			success = fp.mkdirs();
		}
		if(!success){
			emailBody.append("Not able to create Error directory :" + appConfig.getMerkleDncFeedFolderError() + newLine);
			throw new DirectTVException("Not able to create Error directory :" + appConfig.getMerkleDncFeedFolderError());
		}
		
		// creating success file.
		
		fp = new File(succFile);
		success=true;
		success = fp.createNewFile();
		
		if(!success){
			emailBody.append("Not able to create output file  :" + succFile + newLine);
			throw new DirectTVException("Not able to create output file  :" + succFile);
		}
		
		// creating error file.
		
		fp = new File(failFile) ;
		success=true;
		success = fp.createNewFile();
		
		if(!success){
			emailBody.append("Not able to create Error log file  :" + succFile + newLine);
			throw new DirectTVException("Not able to create Error log file  :" + failFile);
		}
		
		// Creating log file. If not able to create it, throw exception and exits from the process.
		
		fp = new File(CustomLoggerDnc.logFile);
		if(!fp.exists()){
			emailBody.append("Not able to create log file  :" + CustomLoggerDnc.logFile + newLine);
			throw new DirectTVException("Not able to create log file  :" + CustomLoggerDnc.logFile);
		}
		
		widthList = FieldsConfig.getFieldsWidth("INC_MERKLE_DNC_FIELDNAMES.txt",",");
		StringTokenizer stk = new StringTokenizer(FieldsConfig.getFieldsQueryString("INC_MERKLE_DNC_FIELDNAMES.txt"),",");
		
		
		//Read the fields API from input file and prepare the query string .
		
		while (stk.hasMoreElements())
		{
			tmp = stk.nextElement().toString();
			if(tmp.split("\\.").length > 3){
				queryString.append(tmp.toString().split("\\.")[1] + "." + tmp.toString().split("\\.")[2] + "." + tmp.toString().split("\\.")[3] + ",");
			}else if(tmp.split("\\.").length > 2){
				queryString.append(tmp.toString().split("\\.")[1] + "." + tmp.toString().split("\\.")[2] + ",");
			}else if(tmp.split("\\.").length > 1){
				queryString.append(tmp.toString().split("\\.")[1] + ",");
			}else{
				queryString.append(tmp.toString().split("\\.")[0] + ",");
			}
			
		}
		List<String> idList = new ArrayList<String>();
		queryString=queryString.replace(queryString.length()-1,queryString.length(),"");
		
		
		/* This query prepared from the input file may contain same filed_api more then once. 
		 * need to eliminate duplicate field names.	*/
		
		Set<String> uniqueElmnts = new TreeSet<String>();
		StringTokenizer unq = new StringTokenizer(queryString.toString(),",");
		while (unq.hasMoreElements())
		{			
			uniqueElmnts.add(unq.nextElement().toString().toUpperCase());
		}
		
		/*removing '<FILLER>' from the query string. This is used in input file for fields,
		 *  whose values need to be left blank.
		 */
		
		for(String s: uniqueElmnts){
			if(!s.toUpperCase().equals("<FILLER>")){
				uniqueQueryString.append(s+",");
			}
		}
		
		//Truncate last "," from the string.
		
		uniqueQueryString=uniqueQueryString.replace(uniqueQueryString.length()-1,uniqueQueryString.length(),"");
	    List st = Arrays.asList(queryString.toString().split(","));
		
	    // making call to salesforce and getting array of hashmap that will contain Dnc object records.
	    
		try{
			dncMap = querySfdc.loadCallObjects(uniqueQueryString.toString());
		}catch(DirectTVException e){
			emailBody.append(newLine  + e + newLine);
			throw e;
		}
		
		DirectTvLogger.debug(log, METHOD_NAME,"total records fetched from sfdc : "+ dncMap.length);
		SimpleDateFormat sdfSource ;
		if(dncMap.length > 0){
			Object value = null;
			totalRecords = (double)dncMap.length;
			try {
				
				// iterate through all the DNC__c objects to create success and failure maps
				
				DirectTvLogger.info(log, METHOD_NAME,"Preparing fieldMap of all the fields using reflections" );
				
				int ii=0;
				DirectTvLogger.info(log, METHOD_NAME,"Going to iterate all fetched objects" );
				for(HashMap<String,Object> hm : dncMap){
					DirectTvLogger.debug(log, METHOD_NAME,"Processing records with id :"+ hm.get("ID"));
					
					// throw the exception and come out of this loop if error record count exceeds the threshold limit defined in conf file.
					
					if (thresholdCnt >0 && failureRecords >= thresholdCnt){
						DirectTvLogger.error(log, METHOD_NAME,"Failure count has reached the threshold limit of count : "+thresholdCnt);
						emailBody.append("Failure count has reached the threshold limit of count : "+thresholdCnt + newLine);
						break;
					}
					try {
						ii=0;
						
						/* iterating through array of header. Fetching value for each header value and 
						preparing output string. */
						
						for (Object s : st){
							
							value = hm.get(s.toString().toUpperCase());
							
							// Transforming few fields as per business requirement.
							
							if(s.toString().toUpperCase().equals("CALL__R.CALL_START_TIME__C")){
								sdfSource = new SimpleDateFormat("yyyyMMdd");
								DateTime dt = new DateTime(value);
								value = sdfSource.format(dt.toDate());
								
							}
							if(s.toString().toUpperCase().equals("VIOLATION__C") ){
								value=(value.toString().toUpperCase().equals("TRUE"))?'Y':'N';
							}
							if(s.toString().toUpperCase().equals("RECORD_NUMBER__C") ){
								value="0";
							}
							if(areaCode==false && s.toString().toUpperCase().equals("DNC_PHONE_NUMBER__C") ){
								value = value!=null ? value.toString().substring(0, value.toString().length() >=3 ? 3 : value.toString().length()):"";
								areaCode=true;
							} 
							else if(areaCode==true && s.toString().toUpperCase().equals("DNC_PHONE_NUMBER__C")){
									if(value!=null && (value.toString().trim().length() >=3 && value.toString().trim().length() <=10)){
										value = value!=null ? value.toString().substring(3, value.toString().length()):"";
									}
									else if(value!=null && (value.toString().trim().length() >10)){
										value = value!=null ? value.toString().substring(3, 10):"";
									}
									else{
										value="";
									}
								
							}else if(value !=null && isDateValue(value) ){
								DateTime dt = new DateTime(value.toString());
								sdfSource = new SimpleDateFormat("yyyyMMddhh:mm:ss");
								value =sdfSource.format(dt.toDate());
							}
							value = value!=null ? value:"";
							if(value == null){ value = "";}
							
							// writing output string to a string buffer.
							
							MerkleDataFeedHelper.format(value.toString(),csvDataWithoutHeader,Integer.parseInt(widthList.get(ii)));
							ii++;
						
						}
						
						//putting records in succRecords map after processing all the fields.
						
						succRecords.put(hm.get("ID").toString(), csvDataWithoutHeader.toString());
						recordsProcessed++;
						
						/*since the above prepared string has been put into map, resetting the 
						 * string buffer for next record.*/
						
						csvDataWithoutHeader.replace(0,csvDataWithoutHeader.length(),"");
						
					} catch (Exception exception) {
						errorList.add(hm.get("ID").toString());
						csvDataWithoutHeader.replace(0,csvDataWithoutHeader.length(),"");
					
					//increase the failure counter by 1 and capture the id of failure record to be displayed in mail and error log file 
					
					failureRecords++;
					DirectTvLogger.error(log, METHOD_NAME,exception.getMessage());
					errorBody.append("Extract_CallObjectData: Error while processing the record with id :" + hm.get("ID").toString()+" ." + exception.getMessage() + newLine);
					//emailBody.append("Extract_CallObjectData: Error while processing the record with id :" + hm.get("ID").toString()+" ." + exception.getMessage() + newLine);
					} 
				}
				
				
				//create SObject array for all the call__C objects to make update call.This is the set SENT_TO_Merkle=true;
				//Update only those records that got successfully processed in the above loop. ignore rest
				
				int size = dncMap.length- errorList.size();
				SObject[] s = new SObject[size];
				List<String> ids = new ArrayList<String>();
				List<HashMap<String,Object>> ml= new ArrayList<HashMap<String,Object>>(); 
				int jj=0;
				for(int i=0;i< (recordsProcessed + failureRecords); i++){
					
					if(!errorList.contains(dncMap[i].get("ID"))){
						HashMap<String,Object> updHashMap = new HashMap<String,Object>();
						updHashMap.put("Is_Processed__c","true");
						updHashMap.put("ObjectType","Dnc__c");
						ids.add(dncMap[i].get("ID").toString());
						ml.add(updHashMap);
						jj++;
					}
				}
				String[] newIds = new String[ids.size()];
				HashMap<String,Object>[] newHM = new HashMap[ml.size()];
				
				for(int i=0;i<ids.size();i++){
					newIds[i] = ids.get(i);
				}
				
				for(int i=0;i<ml.size();i++){
					newHM[i] = ml.get(i);
				}
				
				//updating call__c objects
				SaveResult[] sResults = querySfdc.update(newIds,newHM ) ;
				int cnt=0;		
				
				/*save ids of all the records which did not get updated properly. 
				 * These records have to be moved to error File.*/
								
				DirectTvLogger.info(log, METHOD_NAME,"Updating IS_Processed__c=true for all the successfully processed records." );
				String err="";
				for(SaveResult sr : sResults){
					if( !sr.isSuccess()){
						String tmpId = "";
						if(sr.getId()==null ){
							tmpId=newIds[cnt++];
							idList.add(tmpId);
						}
						else{
							tmpId=sr.getId();
							idList.add(tmpId);
						}
						if(recordsProcessed > 0){recordsProcessed--;}
						failureRecords++;
						err=sr.getErrors(0).getMessage();
						errorBody.append("Not able to update DNC object in SalesForce ID:- "+ tmpId + "    ." + sr.getErrors(0).getMessage() + newLine);
						//emailBody.append("Not able to update DNC object in SalesForce ID:- "+ tmpId + "    ." + sr.getErrors(0).getMessage() + newLine);
						DirectTvLogger.error(log, METHOD_NAME,"Not able to update Call object in SalesForce ID:- "+ tmpId + "    ." + sr.getErrors(0).getMessage());
					}
				}
				if(!"".equals(err)){
					emailBody.append(err + newLine);
				}
				DirectTvLogger.debug(log, METHOD_NAME,"Success records count : "+ recordsProcessed);
				DirectTvLogger.debug(log, METHOD_NAME,"Failure records count : "+ failureRecords);
				DirectTvLogger.debug(log, METHOD_NAME,"Number of records got updated in SFDC : "+ (sResults.length-cnt));
			}
			catch (Exception e) {
				DirectTvLogger.error(log, METHOD_NAME,"Exception occured while processing or updating records in SFDC." + e.getMessage()+newLine);
				emailBody.append("Exception occured while processing or updating records in SFDC." + e.getMessage()+newLine);
				errorBody.append("Exception occured while processing or updating records in SFDC." + e.getMessage()+newLine);
			}
		}
		
		// All the records are processed now and are there in succRecords . Writing them to files now.
		
		writeData(succRecords,idList,csvWriObj);
		
		//Preparing controllerStr to create Controller.done file after completion of the job
		
		controllerStr.append("Job Name:Merkle_DNC_Feed_"+csvWriObj.getTodayDate()+newLine);
		controllerStr.append("Job status :- Completed\n");
		controllerStr.append("Start time :- " + startTime + newLine);
		controllerStr.append("End Time :- " + CommonUtil.getCurrentTime() + newLine);
		controllerStr.append("Total Records Attempted :- " + totalRecords + newLine);
		controllerStr.append("Total Records Succesfully processed :- " + recordsProcessed + newLine);
		controllerStr.append("Total Failures :- " + failureRecords + newLine);
		
		DirectTvLogger.end(log,METHOD_NAME);
	}
	
	 /**
     * This function checks if the given input value is a valid date field or not.
     * @param value      
     */
	
	private boolean isDateValue(Object  value){
		if(value == null) return false;
		return value.toString().matches("\\d\\d\\d\\d-\\d\\d-\\d\\dT\\d\\d:\\d\\d:\\d\\d.\\d\\d\\d\\D");
	}
	
	 /**
     * This function iterate through succRecords  hashmap and write the records in output files..
     * each records with id present in idList will be moved to error file. 
     * @param succRecords
     * @param recordType
     * @param idList
     * @param csvWriObj
	 * @throws DirectTVException 
     */
	
	private void writeData(Map<String,String> succRecords, List<String> idList, CSVWriter csvWriObj) throws DirectTVException {
		
		final String METHOD_NAME="writeData";
		
		DirectTvLogger.start(log,METHOD_NAME);
		
		String strLine="";
		
		DirectTvLogger.info(log, METHOD_NAME,"Writing records from maps into the file.");    	
		try {
			// Write all records in succRecords in success output file
			for(String key : succRecords.keySet()){
				strLine = succRecords.get(key);
				if(idList.contains(key)){
					//csvWriObj.addDataToCSVFile(failFile, strLine);
				}
				else{
					csvWriObj.addDataToCSVFile(succFile, strLine);
				}
			}
			
						
		}catch (FileNotFoundException e) {
			DirectTvLogger.error(log, METHOD_NAME,e.getMessage());
		}catch(IOException e){
			DirectTvLogger.error(log, METHOD_NAME,e.getMessage());
		}catch(Exception e){
			DirectTvLogger.error(log, METHOD_NAME,e.getMessage());
		}
		
		DirectTvLogger.end(log,METHOD_NAME);
	}
    
	/**
     * To Send the Email.
     * 
     * @param subject
     * @param body
     */
	private static void sendEMail(String subject, String body, String emailAddress) {
		DirectTvLogger.debug(log, "sendEMail", "Sending email.....: " + subject); 
		EmailUtils email = new EmailUtils();
		email.sendEMail(appConfig, emailAddress, subject, body);
	    	
	}
}
