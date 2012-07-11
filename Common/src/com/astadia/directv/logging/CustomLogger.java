package src.com.astadia.directv.logging;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

import src.com.astadia.directv.config.AppConfig;
import src.com.astadia.directv.config.Configurator;


public class CustomLogger extends org.apache.log4j.FileAppender
{
	
	public CustomLogger() {
    }

    public CustomLogger(Layout pLayout, String filename) throws IOException {
            super(pLayout, filename);
    }

    public CustomLogger(Layout pLayout, String filename, boolean append) throws IOException {
            super(pLayout, filename, append);
    }

    public CustomLogger(Layout pLayout, String filename, boolean append, boolean pBufferedIO,
                                                              int pBufferSize) throws IOException {
            super(pLayout, filename, append, pBufferedIO, pBufferSize);
    }

     
    public void activateOptions() {
    	 
    	BasicConfigurator.configure();
    	AppConfig	appConfig = Configurator.getAppConfig();
    	String logFileName=getFile();
    	SimpleDateFormat dateFormater=new SimpleDateFormat();
    	dateFormater.applyLocalizedPattern(appConfig.getMerkleDataFeedFileDateFormat());

    	String todayDate = dateFormater.format(Calendar.getInstance().getTime());
    	logFileName=logFileName.replaceFirst(".log","_"+todayDate +".log");
	 
	try {
		setFile(logFileName, false, bufferedIO, bufferSize);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 
	}
	 
}
