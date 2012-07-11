package src.com.astadia.directv.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;


/**
 * This class is used to initialize and get the Configuration detail from directv.properties file.
 * 
 * @author jkannan
 *
 */
public class Configurator {

	private static Logger LOGGER = Logger.getLogger(Configurator.class);

	/**
	 * Private constructor.
	 */
	private Configurator() {
		throw new UnsupportedOperationException("Class is not instantiable.");
	}

	/**
	 * initialize and get the Configuration
	 * 
	 * @return
	 */
	public static AppConfig getAppConfig() {

		//LOGGER.info("Configurator:getAppConfig(): Configuring the Application credentials .........................");
		
		Properties props = new Properties();
		AppConfig appConfig = new AppConfig();

		try {
			props.load(new FileInputStream("directv.properties"));

			// SFDC
			appConfig.setSfdcEndpoint(props.getProperty("sfdc.sfdcEndpoint"));
			appConfig.setSfdcUsername(props.getProperty("sfdc.sfdcUsername"));
			appConfig.setSfdcPassword(props.getProperty("sfdc.sfdcPassword"));
									
			//FTP
			appConfig.setDirectvFtpHost(props.getProperty("ftp.directvFtpHost"));
			appConfig.setDirectvFtpPassword(props.getProperty("ftp.directvFtpPassword"));
			appConfig.setDirectvFtpUsername(props.getProperty("ftp.directvFtpUsername"));
			
			// Email
			appConfig.setAdminPassword(props.getProperty("email.adminPassword"));
			appConfig.setAdminUserId(props.getProperty("email.adminUserId"));
			appConfig.setFromAlias(props.getProperty("email.fromAlias"));
			appConfig.setMailBody(props.getProperty("email.mailBody"));
			appConfig.setMailSubject(props.getProperty("email.mailSubject"));			
			appConfig.setReplytoaddress(props.getProperty("email.replyToAddress"));
			appConfig.setSmtphostname(props.getProperty("email.smtpHostName"));
			appConfig.setToAddressList(props.getProperty("email.toAddressList"));
			appConfig.setSmtpport(props.getProperty("email.smtpPort"));
			
			
			// MERKLE DATA FEED			
			appConfig.setMerkleDataFeedFileName(props.getProperty("merkle.dataFeed.file.name"));
			appConfig.setMerkleDataFeedFileType(props.getProperty("merkle.dataFeed.file.type"));
			appConfig.setMerkleDataFeedFileControllerName(props.getProperty("merkle.dataFeed.file.controllerName"));
			appConfig.setMerkleDataFeedFileControllerType(props.getProperty("merkle.dataFeed.file.controllerType"));
			appConfig.setMerkleDataFeedFolderProcessing(props.getProperty("merkle.dataFeed.folder.processing"));
			appConfig.setMerkleDataFeedFolderSuccess(props.getProperty("merkle.dataFeed.folder.success"));
			appConfig.setMerkleDataFeedFolderFail(props.getProperty("merkle.dataFeed.folder.error"));
			appConfig.setMerkleDataFeedFileDateFormat(props.getProperty("merkle.dataFeed.file.dateFormat"));
			appConfig.setMerkleDataFeedEmailToAddressListSuccess(props.getProperty("merkle.dataFeed.email.toAddressListSuccess"));
			appConfig.setMerkleDataFeedEmailToAddressListFail(props.getProperty("merkle.dataFeed.email.toAddressListFail"));
			appConfig.setMerkleDataFeedThresholdValue(props.getProperty("merkle.dataFeed.threshold"));
			appConfig.setMerkleDataFeedJobName(props.getProperty("merkle.dataFeed.jobName"));
			appConfig.setMerkleDataFeedFolderLog(props.getProperty("merkle.dataFeed.folder.log"));
			appConfig.setMerkleDataFeedFolderTargetError(props.getProperty("merkle.dataFeed.folder.target.error"));
			appConfig.setMerkleDataFeedFolderTargetLog(props.getProperty("merkle.dataFeed.folder.target.log"));
			appConfig.setMerkleDataFeedFolderTargetSuccess(props.getProperty("merkle.dataFeed.folder.target.success"));
			appConfig.setMerkleDataFeedFileErrorType(props.getProperty("merkle.dataFeed.file.errorType"));
			appConfig.setMerkleDataFeedRecordType(props.getProperty("merkle.dataFeed.recordType"));
			// MERKLE DAILY DNC FEED
			// MERKLE DAILY DNC FEED
			appConfig.setMerkleDncFeedFileName(props.getProperty("merkle.dncFeed.file.name"));
			appConfig.setMerkleDncFeedFileType(props.getProperty("merkle.dncFeed.file.type"));
			appConfig.setMerkleDncFeedFileControllerName(props.getProperty("merkle.dncFeed.file.controllerName"));
			appConfig.setMerkleDncFeedFileControllerType(props.getProperty("merkle.dncFeed.file.controllerType"));
			appConfig.setMerkleDncFeedFolderProcessing(props.getProperty("merkle.dncFeed.folder.processing"));
			appConfig.setMerkleDncFeedFolderSuccess(props.getProperty("merkle.dncFeed.folder.success"));
			appConfig.setMerkleDncFeedFolderLog(props.getProperty("merkle.dncFeed.folder.log"));
			appConfig.setMerkleDncFeedFolderError(props.getProperty("merkle.dncFeed.folder.error"));
			appConfig.setMerkleDncFeedFileDateFormat(props.getProperty("merkle.dncFeed.file.dateFormat"));
			appConfig.setMerkleDncFeedEmailToSuccessAddressList(props.getProperty("merkle.dncFeed.email.toAddressListSuccess"));
			appConfig.setMerkleDncFeedEmailToFailAddressList(props.getProperty("merkle.dncFeed.email.toAddressListFail"));
			appConfig.setMerkleDncFeedThreshold(props.getProperty("merkle.dncFeed.threshold"));
			 // VENDOR DATA FEED 
			appConfig.setVendorDataFeedFileName(props.getProperty("vendor.dataFeed.file.name"));
			appConfig.setVendorDataFeedFileType(props.getProperty("vendor.dataFeed.file.type"));
			appConfig.setVendorDataFeedFileControllerName(props.getProperty("vendor.dataFeed.file.controllerName"));
			appConfig.setVendorDataFeedFileControllerType(props.getProperty("vendor.dataFeed.file.controllerType"));
			appConfig.setVendorDataFeedFolderProcessing(props.getProperty("vendor.dataFeed.folder.processing"));
			appConfig.setVendorDataFeedFolderSuccess(props.getProperty("vendor.dataFeed.folder.success"));
			appConfig.setVendorDataFeedFolderLog(props.getProperty("vendor.dataFeed.folder.log"));
			appConfig.setVendorDataFeedFileDateFormat(props.getProperty("vendor.dataFeed.file.dateFormat"));
			appConfig.setVendorDataFeedEmailToSuccessAddressList(props.getProperty("vendor.dataFeed.email.toAddressListSuccess"));
			appConfig.setVendorDataFeedEmailToFailAddressList(props.getProperty("vendor.dataFeed.email.toAddressListFail"));
			appConfig.setVendorDataFeedRecordType((props.getProperty("vendor.dataFeed.recordType")));
			appConfig.setVendorDataFeedThreshold((props.getProperty("vendor.dataFeed.threshold")));
			appConfig.setVendorDataFeedFolderError((props.getProperty("vendor.dataFeed.folder.error")));
		    
		} catch (IOException e) {
			LOGGER.error("Exception while configuring the Application credentials ..." + e);
		}
		return appConfig;
	}

}
