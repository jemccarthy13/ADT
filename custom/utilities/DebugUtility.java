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
	protected static DebugUtility getInstance() {
		return instance;
	}

	private static DebugUtility instance = new DebugUtility();

	private DebugUtility() {
		LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
		Configuration config = ctx.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig("ADTLogger");
		loggerConfig.setLevel(Level.TRACE);
		ctx.updateLoggers();

		log4j.trace("Log4j Set to trace");
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
			log4j.debug("(" + c.getSimpleName() + ") " + string);
		}
	}

	/**
	 * Print an error without exiting
	 * 
	 * @param string - the message to print
	 */
	public synchronized static void error(String string) {
		log4j.error(string);
	}

	/**
	 * Error with exception
	 * 
	 * @param message the message to explain the error
	 * @param e       the exception that was thrown
	 */
	public synchronized static void error(String message, Exception e) {
		log4j.error(message, e);
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
		log4j.error("(" + class1.getSimpleName() + ") " + string);
	}

	/**
	 * Trace logging
	 * 
	 * @param class1 class doing the logging
	 * @param msg    Message to log
	 */
	public synchronized static void trace(Class<?> class1, String msg) {
		log4j.trace("(" + class1.getSimpleName() + ") " + msg);
	}
}
