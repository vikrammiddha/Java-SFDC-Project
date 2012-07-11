package src.com.astadia.directv.logging;

import org.apache.log4j.xml.DOMConfigurator;


/**
 * The purpose of this class is to initalize the logging sub system based on the configuration file path passed.
 * 
 *
 */

public class LogInitialiser {
    
    /**
     * Initalize the logging system with the property file path passed as parameter .
     * @param logFileConfigurationPath
     */
    public static void initializeLogger(String logFileConfigurationPath) {
    	
    	if (null !=  logFileConfigurationPath || "".equals(logFileConfigurationPath)) {
    		//  PropertyConfigurator.configure(logFileConfigurationPath);
    	          DOMConfigurator.configure("log4j.xml");
	    }
    	else {
	       System.err.println("log 4 j property not found.logging will not be enabled for this module" );
    	}
	   
    }
    
}
