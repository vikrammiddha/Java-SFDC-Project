package src.com.astadia.directv.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import src.com.astadia.directv.config.AppConfig;


/**
 * This class is used to send the Email notification as per the configuration.
 * 
 * @author jkannan
 *
 */
public class EmailUtils {

	private static Logger LOGGER = Logger.getLogger(EmailUtils.class);
	
	/**
	 * This method is used to sends a Email
	 * 
	 * @return
	 */
	public boolean sendEMail(AppConfig appConfig) {

		try {

			// Authenticator auth = null;
			/*
			 * if(_authenticate) { System.setProperty(
			 * "mail.smtp.starttls.enable", "true" ); auth = new
			 * SMTPAuthenticator(); }
			 */
			Properties props = System.getProperties();
			props.put("mail.smtp.host", appConfig.getSmtphostname());
			props.put("mail.smtp.port",appConfig.getSmtpport());
			//props.put("mail.smtp.auth", Boolean.toString(true));
			//Session session = Session.getDefaultInstance(props, null);			
			Session session = Session.getInstance(props);
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(appConfig.getAdminUserId(), appConfig.getFromAlias()));
			message.setReplyTo(new InternetAddress[] { new InternetAddress(appConfig.getReplytoaddress()) });
			message.setSubject(appConfig.getMailSubject());
			message.setContent(appConfig.getMailBody(), "text/plain");

			String[] toAddressArray = appConfig.getToAddressList().split(",");
			InternetAddress[] toInternetAddress = new InternetAddress[toAddressArray.length];
			for (int i = 0; i < toAddressArray.length; i++) {
				toInternetAddress[i] = new InternetAddress(toAddressArray[i]);
			}
			message.setRecipients(Message.RecipientType.TO, toInternetAddress);
			//message.setRecipients(Message.RecipientType.CC, toAddress);
			Transport.send(message);
			LOGGER.debug("Your email message was sent successfully..");
			return true;

		} catch (Exception e) {
			LOGGER.error("Exception while sending the email... " + e);
		}
		
		return false;
	}
	
    /**
     * To Send the Email.
     * 
     * @param subject
     * @param body
     */
    public boolean sendEMail(AppConfig appConfig, String toAddress, String subject, String body) {
    	
    	LOGGER.info("Sending email.....: " + subject);
    	
        EmailUtils email = new EmailUtils();
        appConfig.setToAddressList(toAddress);
        appConfig.setMailSubject(subject);
        appConfig.setMailBody(body);
    	boolean sentmail= email.sendEMail(appConfig);
    	
    	if(!sentmail){
    		LOGGER.error("sending the email failed.........");
    	}
    	return sentmail;
    }

}