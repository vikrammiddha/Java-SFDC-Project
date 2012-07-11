package src.com.astadia.directv.ftp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public abstract class AbstractLookupFileFetcher {

    private static final Logger LOGGER = Logger.getLogger(AbstractLookupFileFetcher.class);
    
    private List<String> filesAlreadyProcessed = new ArrayList<String>();
    private File destinationDirectory;
    private int maxFilesToDownload = 1;
    protected FTPClient ftp;
    private String server = "";
    private String username = "";
    private String password= "";
    private String regexFileFilter = "";
    private int maxFileAgeInDays;
    private Date minimalFileDate ;

    
    public Date getMinimalFileDate() {
		return minimalFileDate;
	}

	public void setMinimalFileDate(Date minimalFileDate) {
		this.minimalFileDate = minimalFileDate;
	}

    /**
     * FTPClient instance.
     * @return ftpclient
     */
    public FTPClient getFtp() {
        return ftp;
    }
    
    /**
     * Set FTPClient instance 
     * @param ftp FTPClient instance
     */
    public void setFtp(FTPClient ftp) {
        this.ftp = ftp;
    }

    /**
     * Maximum age acceptable for Dodge files.
     * @return max age in days
     */
    public final int getMaxFileAgeInDays() {
        return maxFileAgeInDays;
    }

    /**
     * Maximum age acceptable for Dodge files.
     * @param maxFileAgeInDays Number of days
     */
    public final void setMaxFileAgeInDays(final int maxFileAgeInDays) {
        this.maxFileAgeInDays = maxFileAgeInDays;
    }

    /**
     * File names must match this regex in order to be downloaded.
     * @return regex
     */
    public final String getRegexFileFilter() {
        return regexFileFilter;
    }

    /**
     * File names must match this regex in order to be downloaded.
     * @param regexFileFilter regex
     */
    public final void setRegexFileFilter(final String regexFileFilter) {
        this.regexFileFilter = regexFileFilter;
    }
    
    /**
     * Downloads Dodge xml files meeting criteria specified in instance.
     * @return number of files downloaded
     * @throws java.io.IOException if can't download
     */
    public abstract int downloadFilesFromServer() throws IOException;
    
    /**
     * Downloads a file.
     * @param fileName file name
     * @throws java.io.IOException if can't download
     */
    public abstract void downloadFileFromServer(String fileName) throws IOException;
    
    /**
     * Directory to which files will be downloaded.
     * @return File
     */
    public final File getDestinationDirectory() {
        return destinationDirectory;
    }

    /**
     * Directory to which files will be downloaded.
     * @param destinationDirectory directory
     */
    public final void setDestinationDirectory(final File destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    /**
     * List of file names that have been processed for this client.  
     * Files on the remote server matching these names will not be downloaded again.
     * @return List of files
     */
    public final List<String> getFilesAlreadyProcessed() {
        return filesAlreadyProcessed;
    }

    /**
     * List of file names that have been processed for this client.  
     * Files on the remote server matching these names will not be downloaded again.
     * @param filesAlreadyProcessed List of file names
     */
    public final void setFilesAlreadyProcessed(final List<String> filesAlreadyProcessed) {
        this.filesAlreadyProcessed = filesAlreadyProcessed;
    }

    /**
     * Maximum number of files to download.
     * @return number of files.
     */
    public final int getMaxFilesToDownload() {
        return maxFilesToDownload;
    }

    /**
     * Maximum number of files to download.
     * @param maxFiles # files
     */
    public final void setMaxFilesToDownload(final int maxFiles) {
        this.maxFilesToDownload = maxFiles;
    }

    /**
     * Password on remote server.
     * @return password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * Password on remote server.
     * @param password password
     */
    public final void setPassword(final String password) {
        this.password = password;
    }

    /**
     * Remote server host name.
     * @return server name
     */
    public final String getServer() {
        return server;
    }

    /**
     * Remote server host name.
     * @param server server
     */
    public final void setServer(final String server) {
        this.server = server;
    }

    /**
     * Username on remote server.
     * @return username
     */
    public final String getUsername() {
        return username;
    }

    /**
     * Username on remote server.
     * @param username username
     */
    public final void setUsername(final String username) {
        this.username = username;
    }
    
    protected final void connect() throws IOException {
        
        LOGGER.debug("connecting to ftp...");
        
        ftp.connect(server);
        ftp.login(username, password);
        
        int reply = ftp.getReplyCode();
        
        LOGGER.debug("Connected, reply code: " + reply);

        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new IOException("FTP refused connection");
        }
        
        LOGGER.debug("Requesting passive mode");
        ftp.enterLocalPassiveMode();
    }
}