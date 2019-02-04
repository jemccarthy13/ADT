package utilities;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * Logging wrapper around log4j
 */
public final class DebugUtility {
	/** The logger */
	public static Logger log4j = LogManager.getLogger("ADTLogger");

	/**
	 * To force log4j configuration on static instantiation
	 * 
	 * @return instance of DebugUtility
	 */
	public static DebugUtility getInstance() {
		return instance;
	}

	private static DebugUtility instance = new DebugUtility();

	private DebugUtility() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig("ADTLogger");
		loggerConfig.setLevel(Level.TRACE);
		ctx.updateLoggers();

		log4j.trace(formatClassName(DebugUtility.class) + "Log4j Set to trace");
	}

	/**
	 * To keep everything looking nice, format the class name for output.
	 * 
	 * @param c the class to format
	 * @return formatted String
	 */
	public static String formatClassName(Class<?> c) {
		return String.format("(%-15s) ", c.getSimpleName());
	}

	/**
	 * Print the given message only if we're logging (VERBOSE)
	 * 
	 * @param c
	 * 
	 * @param string - the message to log
	 */
	public synchronized static void debug(Class<?> c, String string) {
		if (log4j.isDebugEnabled()) {
			log4j.debug(formatClassName(c) + string);
		}
	}

	/**
	 * Error with exception
	 * 
	 * @param class1
	 * 
	 * @param message the message to explain the error
	 * @param e       the exception that was thrown
	 */
	public synchronized static void error(Class<?> class1, String message, Exception e) {
		log4j.error(formatClassName(class1) + message, e);
	}

	/**
	 * Fatal message
	 * 
	 * @param message the end of the world message
	 */
	public synchronized static void fatal(String message) {
		log4j.fatal(message);
	}

	/**
	 * Report an error with the class.
	 * 
	 * @param class1
	 * @param string
	 */
	public synchronized static void error(Class<?> class1, String string) {
		log4j.error(formatClassName(class1) + string);
	}

	/**
	 * Trace logging
	 * 
	 * @param class1 class doing the logging
	 * @param msg    Message to log
	 */
	public synchronized static void trace(Class<?> class1, String msg) {
		log4j.trace(formatClassName(class1) + msg);
	}
}
