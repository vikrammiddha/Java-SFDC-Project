package src.com.astadia.directv.config;

import org.apache.log4j.Logger;


/**
 * This class is used to hold the Configuration Information from directv.properties file. 
 * 
 * @author jkannan
 *
 */
public final class AppConfig {

	private static Logger LOGGER = Logger.getLogger(AppConfig.class);
	
	// SFDC
    private String sfdcEndpoint;
	private String sfdcUsername;
    private String sfdcPassword;
    
    // FTP
    private String directvFtpHost;
    private String directvFtpUsername;
    private String directvFtpPassword;
    
	// Email Service
    private String smtphostname;
	private String adminUserId;
    private String adminPassword;
    private String fromAlias;
    private String replytoaddress;    
	private String mailSubject;
    private String mailBody;
    private String toAddressList;
    
    
    // MERKLE DATA FEED
    private String merkleDataFeedJobName;
    private String merkleDataFeedThresholdValue;
    private String merkleDataFeedFileName;
	private String merkleDataFeedFileType;
    private String merkleDataFeedFileControllerName;
    private String merkleDataFeedFileControllerType;
    private String merkleDataFeedFolderProcessing;
    private String merkleDataFeedFolderSuccess;
	private String merkleDataFeedFolderFail;
    private String merkleDataFeedFileDateFormat;
    private String merkleDataFeedEmailToAddressListSuccess;
    private String merkleDataFeedEmailToAddressListFail;
	private String merkleDataFeedFolderLog;
	private String merkleDataFeedFolderTargetLog;
	private String merkleDataFeedFolderTargetError;
	private String merkleDataFeedFolderTargetSuccess;
	private String merkleDataFeedFileErrorType;
	private String merkleDataFeedRecordType;
	
	
	 // MERKLE DAILY DNC FEED
    private String merkleDncFeedFileName;
	private String merkleDncFeedFileType;
    private String merkleDncFeedFileControllerName;
    private String merkleDncFeedFileControllerType;
    private String merkleDncFeedFolderProcessing;
    private String merkleDncFeedFolderSuccess;
	private String merkleDncFeedFolderError;
    private String merkleDncFeedFileDateFormat;
    private String merkleDncFeedEmailToSuccessAddressList;
    private String merkleDncFeedEmailToFailAddressList;
    private String merkleDncFeedThreshold;
    private String MerkleDncFeedFolderLog;
    
 // VENDOR DATA FEED
    private String vendorDataFeedFileName;
	private String vendorDataFeedFileType;
    private String vendorDataFeedFileControllerName;
    private String vendorDataFeedFileControllerType;
    private String vendorDataFeedFolderProcessing;
    private String vendorDataFeedFolderSuccess;
	private String vendorDataFeedFolderLog;
	private String vendorDataFeedFolderError;
    public String getVendorDataFeedFolderError() {
		return vendorDataFeedFolderError;
	}
	public void setVendorDataFeedFolderError(String vendorDataFeedFolderError) {
		this.vendorDataFeedFolderError = vendorDataFeedFolderError;
	}
	private String vendorDataFeedFileDateFormat;
    private String vendorDataFeedEmailToSuccessAddressList;
    private String vendorDataFeedEmailToFailAddressList;
    private String vendorDataFeedRecordType;
    private String vendorDataFeedThreshold;

	private String smtpport;
    
    
	public String getVendorDataFeedThreshold() {
		return vendorDataFeedThreshold;
	}
	public void setVendorDataFeedThreshold(String vendorDataFeedThreshold) {
		this.vendorDataFeedThreshold = vendorDataFeedThreshold;
	}
	public String getVendorDataFeedRecordType() {
		return vendorDataFeedRecordType;
	}
	public void setVendorDataFeedRecordType(String vendorDataFeedRecordType) {
		this.vendorDataFeedRecordType = vendorDataFeedRecordType;
	}
	
	public String getMerkleDataFeedRecordType() {
		return merkleDataFeedRecordType;
	}
	public void setMerkleDataFeedRecordType(String merkleDataFeedRecordType) {
		this.merkleDataFeedRecordType = merkleDataFeedRecordType;
	}
	public String getMerkleDataFeedFileErrorType() {
		return merkleDataFeedFileErrorType;
	}
	public void setMerkleDataFeedFileErrorType(String merkleDataFeedFileErrorType) {
		this.merkleDataFeedFileErrorType = merkleDataFeedFileErrorType;
	}
	
	public String getMerkleDataFeedFolderTargetLog() {
		return merkleDataFeedFolderTargetLog;
	}
	public void setMerkleDataFeedFolderTargetLog(
			String merkleDataFeedFolderTargetLog) {
		this.merkleDataFeedFolderTargetLog = merkleDataFeedFolderTargetLog;
	}
	public String getMerkleDataFeedFolderTargetError() {
		return merkleDataFeedFolderTargetError;
	}
	public void setMerkleDataFeedFolderTargetError(
			String merkleDataFeedFolderTargetError) {
		this.merkleDataFeedFolderTargetError = merkleDataFeedFolderTargetError;
	}
	public String getMerkleDataFeedFolderTargetSuccess() {
		return merkleDataFeedFolderTargetSuccess;
	}
	public void setMerkleDataFeedFolderTargetSuccess(
			String merkleDataFeedFolderTargetSuccess) {
		this.merkleDataFeedFolderTargetSuccess = merkleDataFeedFolderTargetSuccess;
	}
	
	public String getMerkleDataFeedFolderLog() {
		return merkleDataFeedFolderLog;
	}
	public void setMerkleDataFeedFolderLog(String merkleDataFeedFolderLog) {
		this.merkleDataFeedFolderLog = merkleDataFeedFolderLog;
	}
	 
	
	public String getMerkleDataFeedJobName() {
		return merkleDataFeedJobName;
	}
	public void setMerkleDataFeedJobName(String merkleDataFeedJobName) {
		this.merkleDataFeedJobName = merkleDataFeedJobName;
	}
	
	public String getMerkleDataFeedThresholdValue() {
		return merkleDataFeedThresholdValue;
	}
	public void setMerkleDataFeedThresholdValue(String merkleDataFeedThresholdValue) {
		this.merkleDataFeedThresholdValue = merkleDataFeedThresholdValue;
	}
	
	public String getSfdcEndpoint() {
		return sfdcEndpoint;
	}
	public void setSfdcEndpoint(String sfdcEndpoint) {
		this.sfdcEndpoint = sfdcEndpoint;
	}
	public String getSfdcUsername() {
		return sfdcUsername;
	}
	public void setSfdcUsername(String sfdcUsername) {
		this.sfdcUsername = sfdcUsername;
	}
	public String getSfdcPassword() {
		return sfdcPassword;
	}
	public void setSfdcPassword(String sfdcPassword) {
		this.sfdcPassword = sfdcPassword;
	}
	public String getDirectvFtpHost() {
		return directvFtpHost;
	}
	public void setDirectvFtpHost(String directvFtpHost) {
		this.directvFtpHost = directvFtpHost;
	}
	public String getDirectvFtpUsername() {
		return directvFtpUsername;
	}
	public void setDirectvFtpUsername(String directvFtpUsername) {
		this.directvFtpUsername = directvFtpUsername;
	}
	public String getDirectvFtpPassword() {
		return directvFtpPassword;
	}
	public void setDirectvFtpPassword(String directvFtpPassword) {
		this.directvFtpPassword = directvFtpPassword;
	}
	public String getSmtphostname() {
		return smtphostname;
	}
	public void setSmtphostname(String smtphostname) {
		this.smtphostname = smtphostname;
	}
	public String getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(String adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	public String getFromAlias() {
		return fromAlias;
	}
	public void setFromAlias(String fromAlias) {
		this.fromAlias = fromAlias;
	}
	public String getReplytoaddress() {
		return replytoaddress;
	}
	public void setReplytoaddress(String replytoaddress) {
		this.replytoaddress = replytoaddress;
	}
	public String getToAddressList() {
		return toAddressList;
	}
	public void setToAddressList(String toAddressList) {
		this.toAddressList = toAddressList;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}
	public String getMerkleDataFeedFileName() {
		return merkleDataFeedFileName;
	}
	public void setMerkleDataFeedFileName(String merkleDataFeedFileName) {
		this.merkleDataFeedFileName = merkleDataFeedFileName;
	}
	public String getMerkleDataFeedFileType() {
		return merkleDataFeedFileType;
	}
	public void setMerkleDataFeedFileType(String merkleDataFeedFileType) {
		this.merkleDataFeedFileType = merkleDataFeedFileType;
	}
	public String getMerkleDataFeedFileControllerName() {
		return merkleDataFeedFileControllerName;
	}
	public void setMerkleDataFeedFileControllerName(
			String merkleDataFeedFileControllerName) {
		this.merkleDataFeedFileControllerName = merkleDataFeedFileControllerName;
	}
	public String getMerkleDataFeedFileControllerType() {
		return merkleDataFeedFileControllerType;
	}
	public void setMerkleDataFeedFileControllerType(
			String merkleDataFeedFileControllerType) {
		this.merkleDataFeedFileControllerType = merkleDataFeedFileControllerType;
	}
	public String getMerkleDataFeedFolderProcessing() {
		return merkleDataFeedFolderProcessing;
	}
	public void setMerkleDataFeedFolderProcessing(
			String merkleDataFeedFolderProcessing) {
		this.merkleDataFeedFolderProcessing = merkleDataFeedFolderProcessing;
	}
	public String getMerkleDataFeedFolderSuccess() {
		return merkleDataFeedFolderSuccess;
	}
	public void setMerkleDataFeedFolderSuccess(String merkleDataFeedFolderSuccess) {
		this.merkleDataFeedFolderSuccess = merkleDataFeedFolderSuccess;
	}
	public String getMerkleDataFeedFolderFail() {
		return merkleDataFeedFolderFail;
	}
	public void setMerkleDataFeedFolderFail(String merkleDataFeedFolderFail) {
		this.merkleDataFeedFolderFail = merkleDataFeedFolderFail;
	}
	public String getMerkleDataFeedFileDateFormat() {
		return merkleDataFeedFileDateFormat;
	}
	public void setMerkleDataFeedFileDateFormat(String merkleDataFeedFileDateFormat) {
		this.merkleDataFeedFileDateFormat = merkleDataFeedFileDateFormat;
	}
	public String getMerkleDncFeedFileName() {
		return merkleDncFeedFileName;
	}
	public void setMerkleDncFeedFileName(String merkleDncFeedFileName) {
		this.merkleDncFeedFileName = merkleDncFeedFileName;
	}
	public String getMerkleDncFeedFileType() {
		return merkleDncFeedFileType;
	}
	public void setMerkleDncFeedFileType(String merkleDncFeedFileType) {
		this.merkleDncFeedFileType = merkleDncFeedFileType;
	}
	public String getMerkleDncFeedFileControllerName() {
		return merkleDncFeedFileControllerName;
	}
	public void setMerkleDncFeedFileControllerName(
			String merkleDncFeedFileControllerName) {
		this.merkleDncFeedFileControllerName = merkleDncFeedFileControllerName;
	}
	public String getMerkleDncFeedFileControllerType() {
		return merkleDncFeedFileControllerType;
	}
	public void setMerkleDncFeedFileControllerType(
			String merkleDncFeedFileControllerType) {
		this.merkleDncFeedFileControllerType = merkleDncFeedFileControllerType;
	}
	public String getMerkleDncFeedFolderProcessing() {
		return merkleDncFeedFolderProcessing;
	}
	public void setMerkleDncFeedFolderProcessing(
			String merkleDncFeedFolderProcessing) {
		this.merkleDncFeedFolderProcessing = merkleDncFeedFolderProcessing;
	}
	public String getMerkleDncFeedFolderSuccess() {
		return merkleDncFeedFolderSuccess;
	}
	public void setMerkleDncFeedFolderSuccess(String merkleDncFeedFolderSuccess) {
		this.merkleDncFeedFolderSuccess = merkleDncFeedFolderSuccess;
	}
	public String getMerkleDncFeedFolderLog() {
		return MerkleDncFeedFolderLog;
	}
	public void setMerkleDncFeedFolderLog(String MerkleDncFeedFolderLog) {
		this.MerkleDncFeedFolderLog = MerkleDncFeedFolderLog;
	}
	public String getMerkleDncFeedFolderError() {
		return merkleDncFeedFolderError;
	}
	public void setMerkleDncFeedFolderError(String merkleDncFeedFolderError) {
		this.merkleDncFeedFolderError = merkleDncFeedFolderError;
	}
	public String getMerkleDncFeedFileDateFormat() {
		return merkleDncFeedFileDateFormat;
	}
	public void setMerkleDncFeedFileDateFormat(String merkleDncFeedFileDateFormat) {
		this.merkleDncFeedFileDateFormat = merkleDncFeedFileDateFormat;
	}
	public String getMerkleDncFeedEmailToSuccessAddressList() {
		return merkleDncFeedEmailToSuccessAddressList;
	}
	public void setMerkleDncFeedEmailToSuccessAddressList(
			String merkleDncFeedEmailToSuccessAddressList) {
		this.merkleDncFeedEmailToSuccessAddressList = merkleDncFeedEmailToSuccessAddressList;
	}
	public String getMerkleDncFeedEmailToFailAddressList() {
		return merkleDncFeedEmailToFailAddressList;
	}
	public void setMerkleDncFeedEmailToFailAddressList(
			String merkleDncFeedEmailToFailAddressList) {
		this.merkleDncFeedEmailToFailAddressList = merkleDncFeedEmailToFailAddressList;
	}
   public String getMerkleDncFeedThreshold() {
		return merkleDncFeedThreshold;
	}
	public void setMerkleDncFeedThreshold(String merkleDncThreshold) {
		this.merkleDncFeedThreshold = merkleDncThreshold;
	}
	 
	 
	public String getMerkleDataFeedEmailToAddressListSuccess() {
		return merkleDataFeedEmailToAddressListSuccess;
	}
	public void setMerkleDataFeedEmailToAddressListSuccess(
			String merkleDataFeedEmailToAddressListSuccess) {
		this.merkleDataFeedEmailToAddressListSuccess = merkleDataFeedEmailToAddressListSuccess;
	}
	public String getMerkleDataFeedEmailToAddressListFail() {
		return merkleDataFeedEmailToAddressListFail;
	}
	public void setMerkleDataFeedEmailToAddressListFail(
			String merkleDataFeedEmailToAddressListFail) {
		this.merkleDataFeedEmailToAddressListFail = merkleDataFeedEmailToAddressListFail;
	}
	public String getVendorDataFeedFileName() {
		return vendorDataFeedFileName;
	}
	public void setVendorDataFeedFileName(String vendorDataFeedFileName) {
		this.vendorDataFeedFileName = vendorDataFeedFileName;
	}
	public String getVendorDataFeedFileType() {
		return vendorDataFeedFileType;
	}
	public void setVendorDataFeedFileType(String vendorDataFeedFileType) {
		this.vendorDataFeedFileType = vendorDataFeedFileType;
	}
	public String getVendorDataFeedFileControllerName() {
		return vendorDataFeedFileControllerName;
	}
	public void setVendorDataFeedFileControllerName(
			String vendorDataFeedFileControllerName) {
		this.vendorDataFeedFileControllerName = vendorDataFeedFileControllerName;
	}
	public String getVendorDataFeedFileControllerType() {
		return vendorDataFeedFileControllerType;
	}
	public void setVendorDataFeedFileControllerType(
			String vendorDataFeedFileControllerType) {
		this.vendorDataFeedFileControllerType = vendorDataFeedFileControllerType;
	}
	public String getVendorDataFeedFolderProcessing() {
		return vendorDataFeedFolderProcessing;
	}
	public void setVendorDataFeedFolderProcessing(
			String vendorDataFeedFolderProcessing) {
		this.vendorDataFeedFolderProcessing = vendorDataFeedFolderProcessing;
	}
	public String getVendorDataFeedFolderSuccess() {
		return vendorDataFeedFolderSuccess;
	}
	public void setVendorDataFeedFolderSuccess(String vendorDataFeedFolderSuccess) {
		this.vendorDataFeedFolderSuccess = vendorDataFeedFolderSuccess;
	}
	public String getVendorDataFeedFolderLog() {
		return vendorDataFeedFolderLog;
	}
	public void setVendorDataFeedFolderLog(String vendorDataFeedFolderLog) {
		this.vendorDataFeedFolderLog = vendorDataFeedFolderLog;
	}
	public String getVendorDataFeedFileDateFormat() {
		return vendorDataFeedFileDateFormat;
	}
	public void setVendorDataFeedFileDateFormat(String vendorDataFeedFileDateFormat) {
		this.vendorDataFeedFileDateFormat = vendorDataFeedFileDateFormat;
	}
	public String getVendorDataFeedEmailToSuccessAddressList() {
		return vendorDataFeedEmailToSuccessAddressList;
	}
	public void setVendorDataFeedEmailToSuccessAddressList(
			String vendorDataFeedEmailToSuccessAddressList) {
		this.vendorDataFeedEmailToSuccessAddressList = vendorDataFeedEmailToSuccessAddressList;
	}
	
	public String getVendorDataFeedEmailToFailAddressList() {
		return vendorDataFeedEmailToFailAddressList;
	}
	public void setVendorDataFeedEmailToFailAddressList(
			String vendorDataFeedEmailToFailAddressList) {
		this.vendorDataFeedEmailToFailAddressList = vendorDataFeedEmailToFailAddressList;
	}
	public String getSmtpport() {
		return smtpport;
		}
		public void setSmtpport(String smtpport) {
		this.smtpport = smtpport;
		}
    
}