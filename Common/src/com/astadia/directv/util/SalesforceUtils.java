package src.com.astadia.directv.util;

import org.apache.log4j.Logger;

import src.com.astadia.directv.config.AppConfig;
import src.com.astadia.directv.exception.DirectTVException;
import src.com.astadia.directv.sfdc.EnterpriseSession;

/**
 * This class is used to connect to SFDC and get the Session.
 * 
 * @author jkannan
 *
 */
public final class SalesforceUtils {
	
	private static Logger LOGGER = Logger.getLogger(SalesforceUtils.class);
	
    /**
     * Private constructor.
     */
    private SalesforceUtils() {
        throw new UnsupportedOperationException("Class is not instantiable.");
    }
    
    /**
     * Connects to Master SFDC.
     * 
     * @param appConfig AppConfig instance
     * @return a populated PartnerSession for the Master session.
     * @throws DirectTVException 
     */
    public static EnterpriseSession initMasterSession(final AppConfig appConfig) throws DirectTVException {
    	LOGGER.info("SalesforceUtils: initMasterSession(): Connecting to SFDC...........");
    	EnterpriseSession session = new EnterpriseSession();
        boolean connected = session.connect(appConfig);
        if(connected) {
        	return session;
        }
        return null;
    }
    
}
