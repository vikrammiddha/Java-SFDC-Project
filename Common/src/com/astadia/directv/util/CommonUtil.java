package src.com.astadia.directv.util;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;

/**
 * This class has utility methods for the application.
 * 
 * @author jkannan
 *
 */

public class CommonUtil {

	public static boolean isValidDate(String inDate, String dateType) {

		if (inDate == null)
			return false;

		// set the format to use as a constructor argument
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);

		if (inDate.trim().length() != dateFormat.toPattern().length())
			return false;

		dateFormat.setLenient(false);

		try {
			// parse the inDate parameter
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}
	
	public static String getCurrentTime(){
		SimpleDateFormat dateFormaterForcsvFile = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar today = Calendar.getInstance();
		String currentDate = dateFormaterForcsvFile.format(today.getTime());
		return currentDate;
	}

	public static void main(String[] args) {

		System.out.println(CommonUtil.isValidDate("2004-02-29", "yyyy-MM-dd"));
		System.out.println(CommonUtil.isValidDate("2005-02-29", "yyyy-MM-dd"));
		System.out.println(CommonUtil.isValidDate("2010-06-17", "yyyy-MM-dd"));
	}
}
