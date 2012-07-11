package src.com.directv.merkle.fileprocess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;
import src.com.astadia.directv.exception.DirectTVException;
import src.com.astadia.directv.sfdc.EnterpriseSession;

import com.sforce.soap.partner.*;
import com.sforce.soap.partner.sobject.SObject;

import edu.emory.mathcs.backport.java.util.Collections;


/* 
########################################################################### 
# Project Name..........: DirecTV
# File..................: QuerySFDC.java
# Version...............: 1.0
# Created by............: Vikram Middha
# Created Date..........: 20-Jul-2010
# Last Modified by......: 
# Last Modified Date....: 
# Description...........: This class is used to query SFDC Objects. 
# Change Request History: 				   							 
########################################################################### 
*/

public class QuerySFDC {
	
	private static Logger LOGGER = Logger.getLogger(QuerySFDC.class);
	
	private List<String> fieldList = new ArrayList<String>();
	
	private HashMap<String,Object> results = null;
	
	private EnterpriseSession eSession = null;
	
	public QuerySFDC(EnterpriseSession eSession) {
		this.eSession = eSession;
	}
	
	 /**
     * This function returns an array of hashmap where each hashmap is a pair of field-value pair of DNC object
     * @param fields
     * @param appConfig
     * @return HashMap<String,Object>[]
     * @throws DirectTVException
     */
	
    public HashMap<String,Object>[] loadCallObjects(String fields) throws DirectTVException {
    	
    	QueryResult qr = null;
 	    String queryStr = "";	
 	    fieldList = Arrays.asList(fields.split(","));
 	    Collections.sort(fieldList);
 	    HashMap<String,Object>[] rows = null;	    
	    try {
	    	queryStr = "SELECT " + fields + " from dnc__c where Is_Processed__c = false ";
	    	LOGGER.info("Query string prepared with duplicate eliments eliminated : "+ queryStr );	    	
	        qr = eSession.query(queryStr);
	        rows = new HashMap[qr.getSize()];
	        
	        int counter=0;
			boolean stopLooping=false;
	        if (qr != null && qr.getSize() > 0)
			{
				while(!stopLooping)
				{
					for (int i = 0; i < qr.getRecords().length; i++)
					{	results =  new HashMap<String,Object>();
						rows[counter++] = getValuesFromMessageElement(qr.getRecords()[i]);
						
					}
					if (qr.isDone()) 
					{
						stopLooping = true;
					} else 
					{
						try {
							qr = eSession.queryMore(qr.getQueryLocator());
						} catch (DirectTVException e) {
								// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
	        
	    } catch (DirectTVException ex) {
	    	LOGGER.error("Failed to get DNC__c from SFDC succesfully, the error message was: " + ex.getMessage());
	    	throw ex;
	    }  
	
		return rows;    	
    }

    
    /**
     * This function returns  hashmap where hashmap is a pair of field-value pair of call object
     * @param object
     * @return HashMap<String,Object>
     */
    
    private HashMap<String,Object> getValuesFromMessageElement(SObject object)
	{
		if(object==null) {
			return null;
		}
		int fieldNo = 0;
		MessageElement[] fields = object.get_any();
		
		
		if (fields != null)
		{
			
			while (fieldNo < fields.length)
			{
				if(fields[fieldNo].getLength()==1 && fields[fieldNo].getObjectValue()==null)
				{
					
					results.put(getFieldName(fields[fieldNo]), fields[fieldNo].getValue());
				}
				else 
				{
					getValuesFromSubObjects((SObject)fields[fieldNo].getObjectValue());
					
				}
				fieldNo++;
			
			}
		}
		if (object.getId() != null)
		{
			results.put("ID", object.getId().toString());
		}
		return results;
	}
	
    /**
     * This function returns value from object where parent-child relationship exists. eg Call__c.Prospect__r.Name
     * @param object
     * @return String
     */
    
	private String getValuesFromSubObjects(SObject object)
	{
		if(object==null) {
			return null;
		}
		int fieldNo = 0;
		String val = null;
		MessageElement[] fields = object.get_any();
		
		if (fields != null)
		{
			while (fieldNo < fields.length){
				if(fields[fieldNo].getLength()==1 && fields[fieldNo].getObjectValue()==null)
				{
					results.put(getFieldName(fields[fieldNo]), fields[fieldNo].getValue());
				}
				else if(fields[fieldNo].getObjectValue() instanceof SObject) 
				{
					getValuesFromSubObjects((SObject)fields[fieldNo].getObjectValue());
				}
				fieldNo++;
			}
				
		}
	
		return val;
	}
    
	/**
     * This function returns the API name of the field
     * @param object
     * @return String
     */
	
	public String getFieldName(MessageElement me){
		me.getParentElement().setPrefix("");
		SOAPElement  se = me.getParentElement();
		se.setPrefix("");
		String name = "";
		if(se.getNodeName().equals("records")){
			return me.getName().toUpperCase();
		}
		else{
			name = me.getParentElement().getNodeName() + "." + me.getName();
		}
		
		while(se.getParentElement().getNodeName().toLowerCase() != "records"){
			se = se.getParentElement();
			se.setPrefix("");
			name = se.getNodeName() + "." + name;
		}
		return name.toUpperCase();
	}
  
	/**
     * This function creates an entry in Data Feed Job and updates is state to 'Started'
     * @param fields
     * @return String
     * @throws DirectTVException
     */
	
	public String createDataFeedJob(HashMap<String,Object>[] fields) throws DirectTVException{
	   
	    String retId = "";
	    HashMap<String,Object>[] IDs = null;
		SObject[] sObjects = new SObject[fields.length];
		try {
			for(int i=0;i<fields.length;i++) 
			{
				HashMap<String,Object> hm = fields[i]; 
				//hm = UtilNew.removeNull(hm);
				String[] fieldNames = new String[hm.size()];
				fieldNames = getKeysInArray(hm, fieldNames);
				MessageElement[] elements = new MessageElement[hm.size()-1]; 
				int elementNo = 0;
				int elementCounter=0;
				while(elementNo<hm.size()) 
				{
					if(!fieldNames[elementNo].equalsIgnoreCase("ObjectType"))
						{
							elements[elementCounter++] = new MessageElement(new QName(fieldNames[elementNo]),hm.get(fieldNames[elementNo]));
						}
					elementNo++;
				}
				String objectType = "";
				if(hm.get("ObjectType")!=null)objectType = (String)hm.get("ObjectType");
				sObjects[i] = new SObject(); 
				sObjects[i].setId(new String(""));
				sObjects[i].setType(objectType); 
				sObjects[i].set_any(elements);
				
			}
			
			SaveResult[] sr = eSession.create(sObjects);
			for(SaveResult s : sr){
				if(!s.isSuccess()){
					throw new DirectTVException("Data Feed Job Exception");
				}
				else{
					retId = s.getId();
				}
			}
		}
		catch (DirectTVException e) {
			LOGGER.error("Failed to insert records for Data Feed Job in SalesForce" + e.getMessage());
			throw new DirectTVException("Failed to insert records for Data Feed Job in SalesForce" + e.getMessage());
		}
		return retId;
	}
   
	/**
     * This function is a utility helper function that returns array of keys in a hashmap.
     * @param map
     * @param array
     * @return String[]
     */
	
	private String[] getKeysInArray(HashMap<String,Object> map, String[] array)
	{
	
		Object keys[] = map.keySet().toArray(array);
		
		for (int i = 0; i < map.size(); i++)
		{
			array[i] = (String) keys[i];
		}
		
		return array;
	}
  
	/**
     * This function is used to update salesforce object. all field-value pairs are stored
     * 	in hashmap. A record with key "ObjectType" tell which object type to update.
     * @param ids
     * @param fields
     * @return SaveResult[]
     * @throws DirectTVException
     */
	
	public SaveResult[] update(String[] ids, HashMap<String,Object>[] fields) throws DirectTVException{
		
		SObject sObjects[] = new SObject[fields.length];
		
		try{
		for(int i=0;i<fields.length;i++) 
		{
			
			String[] fieldNames = new String[fields[i].size()];
			fieldNames = getKeysInArray(fields[i], fieldNames);
			String objectType = "";
			if(fields[i].get("ObjectType")!=null)objectType = (String)fields[i].get("ObjectType");
			SObject sfObject = new SObject();
			sfObject.setType(objectType);
			sfObject.setId(new String(ids[i]));
			
			
			int fieldNo = 0;
			int elemno = 0;
			int elemSize=0;
			while(fieldNo<fields[i].size()) 
			{
				if(!fieldNames[fieldNo].equalsIgnoreCase("ObjectType"))
				{
					Object value = fields[i].get(fieldNames[fieldNo]);
					if(value!=null && !(value.toString().trim().equals(""))) {
						elemSize++;
					}
				}	
						fieldNo++;
				
			
			}
			fieldNo = 0;
			MessageElement[] elements = new MessageElement[elemSize];
			ArrayList<String> nullFields = new ArrayList<String>();
			while(fieldNo<fields[i].size()) 
			{
				String fld = fieldNames[fieldNo];
				if(!fld.equalsIgnoreCase("ObjectType"))
				{
					Object value = fields[i].get(fieldNames[fieldNo]);
					if(value==null)
						{
							nullFields.add(fld);
						}
					else
						{
							elements[elemno] = new MessageElement(new QName(fieldNames[fieldNo]), fields[i].get(fieldNames[fieldNo]));  
							elemno++;
						}
				 }	
				fieldNo++;
			}
			if(nullFields!=null && nullFields.size()>0)
				{
					String[] nullArray = new String[nullFields.size()];
					for(int j=0;j<nullFields.size();j++) {
						nullArray[j] = (String)nullFields.get(j);
					}
					sfObject.setFieldsToNull(nullArray);
				}
			
			
			sfObject.set_any(elements);
			sObjects[i] = sfObject;
			
		}
		
		SaveResult[] sr = eSession.updateMore(sObjects,sObjects.length);
		return sr;
		}
		catch (DirectTVException e) {
			LOGGER.error("Failed to update records  in SalesForce" + e.getMessage());
			throw new DirectTVException("Failed to update records  in SalesForce" + e.getMessage());
		}
	}
	
	/**
     * This function is used to update salesforce object. all field-value pairs are stored
     * 	in hashmap. A record with key "ObjectType" tell which object type to update.
     * @param ids
     * @param fields
     * @throws DirectTVException
     */
	
	public void updateDataFeedJob(String[] ids, HashMap<String,Object>[] fields) throws DirectTVException {
		try {
			SaveResult[] sr = update(ids,fields);
			for(SaveResult s : sr){
				if(!s.isSuccess()){
					throw new DirectTVException("Data Feed Job Exception");
				}
			}
			
		} catch (DirectTVException e) {
			LOGGER.error("Failed to insert records for Data Feed Job in SalesForce" + e.getMessage());
			throw new DirectTVException("Failed to insert records for Data Feed Job in SalesForce" + e.getMessage());
		}
		
	}


}
