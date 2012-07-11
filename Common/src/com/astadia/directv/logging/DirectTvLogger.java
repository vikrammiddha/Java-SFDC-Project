package src.com.astadia.directv.logging;
import org.apache.commons.logging.Log;

/**
* DirectTvLogger is a wrapper over the logging framework used for Direct TV application.
* 
* <p>
* Every class should have a static final instance variable (conventionally)
* called log that is intialized to the DirectTvLogger attached to that class. The
* code for that should look like:
* <p>
* 
* private static final Log log = LogFactory.getLog(ClassName.class);
* 
* <p>
* The debug, warn, error and fatal methods are used to log messages into the
* log files. Besides that the start and end methods are used to mark the entry
* and exit of ALL methods.
* 
* For more details on logging see the log4j framework at
* http://jakarta.apache.org/log4j
* <p>
 * 
 */
public class DirectTvLogger {

	/**
	 * <p>
	 * Produces a log message designed to be used within a method whose start
	 * should be logged. These logs will only be produced if the log level is
	 * set to trace. This method should be used in conjuction with
	 * 
	 * @param log
	 *            The log configuration to be used
	 * @param methodName
	 *            The name of the method whose start should be logged
	 * @see com.be.bss.logging.BeLogger#end
	 *      </p>
	 */
	public static void start(Log log, String methodName) {
		if (log.isTraceEnabled()) {
			StringBuffer startMethodLog = new StringBuffer(25);
			startMethodLog.append("START: ");
			startMethodLog.append(methodName);
			log.trace(startMethodLog);
		}
	}

	/**
	 * <p>
	 * Produces a log message designed to be used within a method whose
	 * completion should be logged. These logs will only be produced if the log
	 * level is set to trace. This method should be used in conjuction with
	 * 
	 * @param log
	 *            The log configuration to be used
	 * @param methodName
	 *            The name of the method whose end should be logged
	 * @see com.be.bss.logging.BeLogger#start
	 *      </p>
	 */
	public static void end(Log log, String methodName) {
		if (log.isTraceEnabled()) {
			StringBuffer endMethodLog = new StringBuffer(25);
			endMethodLog.append("END: ");
			endMethodLog.append(methodName);
			log.trace(endMethodLog);
		}
	}

	 

	/**
	 * <p>
	 * Provides a standard process for logging debug messages. Debug messages
	 * should be used to display variable values and additional data throughout
	 * execution of methods to aid in debugging.
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param message
	 *            The message to be logged
	 */
	public static void debug(Log log, String methodName, String message) {

		if (log.isDebugEnabled()) {
			StringBuffer debugMethodLog = new StringBuffer(25);
			debugMethodLog.append("DEBUG: ");
			debugMethodLog.append(methodName);
			debugMethodLog.append(":");
			debugMethodLog.append(message);
			log.debug(debugMethodLog);
		}
	}

	/**
	 * <p>
	 * Provides a standard process for logging info messages. Info messages
	 * should be used to indicate important milestones within program execution
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param message
	 *            The message to be logged
	 */
	public static void info(Log log, String methodName, String message) {

		if (log.isInfoEnabled()) {
			StringBuffer infoMethodLog = new StringBuffer(25);
			infoMethodLog.append("INFO: ");
			infoMethodLog.append(methodName);
			infoMethodLog.append(":");
			infoMethodLog.append(message);
			log.info(infoMethodLog);
		}
	}

	 
	/**
	 * <p>
	 * Provides a standard process for logging warning messages. Warning
	 * messages should be used for non-critical warnings that do not constitute
	 * an error message
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param message
	 *            The message to be logged
	 */
	public static void warn(Log log, String methodName, String message) {

		if (log.isWarnEnabled()) {
			StringBuffer warnMethodLog = new StringBuffer(25);
			warnMethodLog.append("WARNING: ");
			warnMethodLog.append(methodName);
			warnMethodLog.append(":");
			warnMethodLog.append(message);
			log.warn(warnMethodLog);
		}
	}

	/**
	 * <p>
	 * Provides a standard process for logging error messages. Error messages
	 * should be used when an operation fails. All <code>Exceptions</code> are
	 * logged automatically, so errors should only be used where there is
	 * additional information to provide additional clarity as to why the
	 * exception was thrown
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param message
	 *            The message to be logged
	 */
	public static void error(Log log, String methodName, String message) {

		if (log.isErrorEnabled()) {
			StringBuffer errorMethodLog = new StringBuffer(25);
			errorMethodLog.append("ERROR: ");
			errorMethodLog.append(methodName);
			errorMethodLog.append(":");
			errorMethodLog.append(message);
			log.error(errorMethodLog);
		}
	}

	/**
	 * <p>
	 * Provides a standard process for logging error messages. Error messages
	 * should be used when an operation fails. All <code>Exceptions</code> are
	 * logged automatically, so errors should only be used where there is
	 * additional information to provide additional clarity as to why the
	 * exception was thrown
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param message
	 *            The message to be logged
	 * @param throwable
	 */
	public static void error(Log log, String methodName, String message,
			Throwable throwable) {

		if (log.isErrorEnabled()) {
			StringBuffer errorMethodLog = new StringBuffer(25);
			errorMethodLog.append("ERROR: ");
			errorMethodLog.append(methodName);
			errorMethodLog.append(":");
			errorMethodLog.append(message);
			log.error(errorMethodLog, throwable);
		}
	}

	/**
	 * <p>
	 * Provides a standard process for logging error messages. Error messages
	 * should be used when an operation fails. All <code>Exceptions</code> are
	 * logged automatically, so errors should only be used where there is
	 * additional information to provide additional clarity as to why the
	 * exception was thrown
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param errorCode
	 *            The error code corresponding to the error
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param extraMessage
	 *            Message passed with error code to be logged
	 */

	public static void error(Log log, String methodName, int errorCode,
			String extraMessage) {

		// System.out.println("Error
		// Message....."+errorConfig.getErrorLabel(String.valueOf(errorCode)));
		if (log.isErrorEnabled()) {
			StringBuffer errorMethodLog = new StringBuffer(25);
			errorMethodLog.append("ERROR ");
			errorMethodLog.append(errorCode);
			errorMethodLog.append(":");
			 

			errorMethodLog.append(":");
			errorMethodLog.append(methodName);
			errorMethodLog.append(":");
			errorMethodLog.append(extraMessage);

			log.error(errorMethodLog);
		}
	}

	public static void error(Log log, String methodName, int errorCategoryCode,
			int errorCode, String extraMessage) {

		 
		if (log.isErrorEnabled()) {
			StringBuffer errorMethodLog = new StringBuffer(25);
			errorMethodLog.append("Error type: ");
			errorMethodLog.append(errorCategoryCode);
			errorMethodLog.append(" | Error code: ");
			errorMethodLog.append(errorCode);
			errorMethodLog.append(" | ");
			 

			errorMethodLog.append(":");
			errorMethodLog.append(methodName);
			errorMethodLog.append(":");
			errorMethodLog.append(extraMessage);

			log.error(errorMethodLog);
		}
	}

	/**
	 * <p>
	 * Provides a standard process for logging error messages. Error messages
	 * should be used when an operation fails. All <code>Exceptions</code> are
	 * logged automatically, so errors should only be used where there is
	 * additional information to provide additional clarity as to why the
	 * exception was thrown
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param errorCode
	 *            The error code corresponding to the error
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param extraMessage
	 *            Message passed with error code to be logged
	 */

	public static void error(Log log, int errorCode, String methodName,
			String extraMessage) {
		error(log, methodName, errorCode, extraMessage);

	}

	public static void error(Log log, int errorCategoryCode, int errorCode,
			String methodName, String extraMessage) {
		error(log, methodName, errorCategoryCode, errorCode, extraMessage);

	}

	/**
	 * <p>
	 * Provides a standard process for logging error messages. Error messages
	 * should be used when an operation fails. All <code>Exceptions</code> are
	 * logged automatically, so errors should only be used where there is
	 * additional information to provide additional clarity as to why the
	 * exception was thrown
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param errorCode
	 *            The error code corresponding to the error
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param extraMessage
	 *            The message to be logged
	 * @param throwable
	 */
	public static void error(Log log, String methodName, int errorCode,
			String extraMessage, Throwable throwable) {

		if (log.isErrorEnabled()) {
			StringBuffer errorMethodLog = new StringBuffer(25);
			errorMethodLog.append("ERROR ");
			errorMethodLog.append(errorCode);
			errorMethodLog.append(":");
			errorMethodLog.append(methodName);
			errorMethodLog.append(":");
		 
			errorMethodLog.append(":");
			errorMethodLog.append(extraMessage);
			log.error(errorMethodLog, throwable);
		}
	}

	public static void error(Log log, String methodName, int errorCategoryCode,
			int errorCode, String extraMessage, Throwable throwable) {

		if (log.isErrorEnabled()) {
			StringBuffer errorMethodLog = new StringBuffer(25);
			errorMethodLog.append("Error type: ");
			errorMethodLog.append(errorCategoryCode);
			errorMethodLog.append(" | Error code: ");
			errorMethodLog.append(errorCode);
			errorMethodLog.append(" | ");
			errorMethodLog.append(methodName);
			errorMethodLog.append(":");
		 
			errorMethodLog.append(":");
			errorMethodLog.append(extraMessage);
			log.error(errorMethodLog, throwable);
		}
	}

	public static void error(Log log, int errorCode, String methodName,
			String extraMessage, Throwable throwable) {

		error(log, methodName, errorCode, extraMessage, throwable);
	}

	public static void error(Log log, int errorCategoryCode, int errorCode,
			String methodName, String extraMessage, Throwable throwable) {

		error(log, methodName, errorCategoryCode, errorCode, extraMessage,
				throwable);
	}

	/**
	 * <p>
	 * Provides a standard process for logging fatal messages. Fatal messages
	 * should be used when an error occurs that will cause the system to stop
	 * operating
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param message
	 *            The message to be logged
	 */
	public static void fatal(Log log, String methodName, String message) {

		if (log.isFatalEnabled()) {
			StringBuffer fatalMethodLog = new StringBuffer(25);
			fatalMethodLog.append("FATAL: ");
			fatalMethodLog.append(methodName);
			fatalMethodLog.append(":");
			fatalMethodLog.append(message);
			log.fatal(fatalMethodLog);
		}
	}

	/**
	 * <p>
	 * Provides a standard process for logging fatal messages. Fatal messages
	 * should be used when an error occurs that will cause the system to stop
	 * operating
	 * </p>
	 * 
	 * @param log
	 *            The configuration of the current log to be used to write the
	 *            message to
	 * @param methodName
	 *            The name of the method the message originated from
	 * @param message
	 *            The message to be logged
	 * @param throwable
	 */
	public static void fatal(Log log, String methodName, String message,
			Throwable throwable) {

		if (log.isFatalEnabled()) {
			StringBuffer fatalMethodLog = new StringBuffer(25);
			fatalMethodLog.append("FATAL: ");
			fatalMethodLog.append(methodName);
			fatalMethodLog.append(":");
			fatalMethodLog.append(message);
			log.fatal(fatalMethodLog, throwable);
		}
	}
	
  
	 
}
