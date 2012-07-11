package src.com.astadia.directv.util;

import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.joda.time.DateTime;

/**
 * Comparator implementation for the date encoded in the CSV file names. File names must match this regex: .+(\\d{2})(\\d{2})(\\d{4})\.csv
 * 
 * @author jkannan
 *
 */

public final class FileNameDateComparator implements Comparator<String> {

    private final Pattern timePattern = Pattern.compile(".+(\\d{2})(\\d{2})(\\d{4})\\.csv");
    
    /**
     * Compare the files for sorting
     * 
     */
    public int compare(final String arg0, final String arg1) {
        DateTime arg0Date = getDateTime(arg0);
        DateTime arg1Date = getDateTime(arg1);
        return arg0Date.compareTo(arg1Date);       
    }
    
    /**
     * get the Date/Time from the file name.
     * 
     * @param str
     * @return
     */
    private DateTime getDateTime(final String str) {
        Matcher matcher = timePattern.matcher(str);
        DateTime dateTime = null;
        if (matcher.matches()) {
            dateTime = new DateTime(Integer.parseInt(matcher.group(3)), 
                    Integer.parseInt(matcher.group(1)), 
                    Integer.parseInt(matcher.group(2)),
                    0, 0, 0, 0);
        }
        else {
            throw new IllegalArgumentException("Incorrect date format in file name: " + str);
        }
        return dateTime;
    }
    
}