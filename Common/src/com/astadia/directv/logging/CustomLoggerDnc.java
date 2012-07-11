package src.com.astadia.directv.logging;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;

import org.apache.log4j.Layout;
import org.apache.log4j.Logger;

import src.com.astadia.directv.config.AppConfig;
import src.com.astadia.directv.config.Configurator;


public class CustomLoggerDnc extends org.apache.log4j.FileAppender
{
	public static String logFile;
	public CustomLoggerDnc() {
    }

    public CustomLoggerDnc(Layout pLayout, String filename) throws IOException {
            super(pLayout, filename);
    }

    public CustomLoggerDnc(Layout pLayout, String filename, boolean append) throws IOException {
            super(pLayout, filename, append);
    }

    public CustomLoggerDnc(Layout pLayout, String filename, boolean append, boolean pBufferedIO,
                                                              int pBufferSize) throws IOException {
            super(pLayout, filename, append, pBufferedIO, pBufferSize);
    }

	
    public void activateOptions() {
    	AppConfig appConfig = Configurator.getAppConfig();
    	String logFileName=getFile();
    	SimpleDateFormat dateFormater=new SimpleDateFormat();
    	dateFormater.applyLocalizedPattern(appConfig.getMerkleDncFeedFileDateFormat());

    	String todayDate = dateFormater.format(Calendar.getInstance().getTime());
    	logFileName=logFileName.replaceFirst(".log","_"+todayDate +".log");
    	logFile = logFileName;
	try {
		
		setFile(logFileName, false, bufferedIO, bufferSize);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		//e.printStackTrace();
	}
 
	}
	 
}
