package src.com.astadia.directv.ftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import src.com.astadia.directv.ftp.AbstractLookupFileFetcher;
import src.com.astadia.directv.util.FileNameDateComparator;

public final class FtpLookupFileFetcher extends AbstractLookupFileFetcher {

    private static Logger LOGGER = Logger.getLogger(FtpLookupFileFetcher.class);
    
    /**
     * Constructor.  Default FTPClient is used.
     */
    public FtpLookupFileFetcher() {
        setFtp(new FTPClient());
    }

    /**
     * Constructor.  
     * @param ftp FTPClient instance
     */
    public FtpLookupFileFetcher(FTPClient ftp) {
        setFtp(ftp);
    }
    
    /**
     * Downloads files and saves to disk.
     * @return number of files downloaded
     * @throws java.io.IOException if any IO error occurs
     */
    public final int downloadFilesFromServer() throws IOException {
        connect();
        FTPFile[] rawFileList = getFtp().listFiles();        
        LOGGER.debug("Found " + rawFileList.length + " files on ftp.");
        
        
        List<FTPFile> filesToGet = getFilesToDownload(rawFileList);
        
        // download files and put in a folder
        for (FTPFile file : filesToGet) {
            downloadFileFromServer(file.getName());
        }
        return filesToGet.size();
    }
    
    /**
     * Download an individual file from server and save to disk.
     * @param fileName to download
     * @throws java.io.IOException if any IO errors occur
     */
    public final void downloadFileFromServer(final String fileName) throws IOException {
        LOGGER.debug("Retreiving "+fileName);
        connect(); 
        File destinationFile = new File(getDestinationDirectory(), fileName);
        FileOutputStream fos = new FileOutputStream(destinationFile);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(fos);
            getFtp().retrieveFile(fileName, bos);
        }
        finally {
            bos.close();
        }
        LOGGER.debug("got file.");
    }

    /**
     * Builds a List of FTPFile records that, based on a number of criteria, should be downloaded 
     * and processed.
     * @param rawFileList Array of FPTFile objects.
     * @return List of FTPFile records
     */
    private List<FTPFile> getFilesToDownload(FTPFile[] rawFileList) {
        // sort in chronological order
        Arrays.sort(rawFileList, new Comparator<FTPFile>() {
            public int compare(final FTPFile a, final FTPFile b) {
                try {
                    return new FileNameDateComparator().compare(a.getName(), b.getName());
                }
                catch (IllegalArgumentException ex) {
                    return -1;
                }
            }
        });        
        // the directory can contain files other than the dodge xml files, so we filter here
        List<FTPFile> filesToGet = new ArrayList<FTPFile>();
        //Valid file should be of the greater date than the max file age set
        DateTime oldestFileDateAcceptable = new DateTime().minusDays(getMaxFileAgeInDays());
      //All the files loaded after the minimum date set by the client
        DateTime minimalFileDateAcceptable = new DateTime(getMinimalFileDate());
        LOGGER.debug("oldestFileDateAcceptable"+oldestFileDateAcceptable);
        Pattern timePattern = Pattern.compile(".*(?:^|\\D+)(\\d{2})(\\d{2})(\\d{4})\\.xml");
        for (FTPFile file : rawFileList) {
            LOGGER.debug("Considering to download: " + file.toString());
            Matcher timePatternMatcher = timePattern.matcher(file.getName());
            if (!timePatternMatcher.matches()) {
                continue;
            }
            DateTime fileDate =
                    new DateTime(Integer.parseInt(timePatternMatcher.group(3)),
                    Integer.parseInt(timePatternMatcher.group(1)),
                    Integer.parseInt(timePatternMatcher.group(2)), 0, 0, 0, 0);
            if (file.getName().matches(".*" + getRegexFileFilter() + ".*xml") 
                    && timePatternMatcher.matches()
                    && oldestFileDateAcceptable.getMillis() < fileDate.getMillis()
                    && minimalFileDateAcceptable.getMillis() <= fileDate.getMillis()
                    && !getFilesAlreadyProcessed().contains(file.getName())) {

                filesToGet.add(file);
                LOGGER.debug("Adding to queue: " + file.toString());
            }
            if (filesToGet.size() >= getMaxFilesToDownload()) {
                break;
            }
        }
        return filesToGet;
    }
}