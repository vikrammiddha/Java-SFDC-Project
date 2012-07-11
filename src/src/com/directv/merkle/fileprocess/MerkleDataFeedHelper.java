package src.com.directv.merkle.fileprocess;

import java.io.IOException;

import src.com.astadia.directv.exception.DirectTVException;

/*
###########################################################################
# Project Name..........: DirecTV
# File..................: MerkleDataFeedHelper.java
# Version...............: 1.0
# Created by............: Vikram Middha
# Created Date..........: 30-July-2010
# Last Modified by......:
# Last Modified Date....:
# Description...........: This class is a helper class of Merkle and contain supporting method require for Merkle feed Job.
# Change Request History:
###########################################################################
*/

public class MerkleDataFeedHelper {

	  
	/**
     * This method is use to insert fixed width variable in String Buffer 
     *
     * @param args used for capturing command line parameters
	 * @param args used for capturing command line parameters
	 * @throws DirectTVException 
     */
	  public static StringBuffer format(String obj, StringBuffer where,int maxChars) {

	    String wanted = obj.substring(0, Math.min(obj.length(), maxChars));
	
		if (wanted.length() == maxChars) {
	      where.append(wanted);
	    }

	    // Else get the spaces in the right place.
	    else{
	      
	    	where.append(wanted);
	        pad(where, maxChars - wanted.length());
	    }  
	    return where;
	  }

	  private static void pad(StringBuffer to, int howMany) {
	    for (int i = 0; i < howMany; i++)
	      to.append(' ');
	  }
}
