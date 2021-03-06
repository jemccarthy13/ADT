package utilities;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logging wrapper around log4j
 */
public final class DebugUtility {
	/** The logger */
	private static Logger log4j = newLogger();
	// private static Logger log4j = LogManager.getLogger("ADTLogger");

	private static Logger newLogger() {
		Logger l = Logger.getLogger("ADTLogger");
		l.setLevel(Level.FINEST);
		ConsoleHandler c = new ConsoleHandler();
		c.setLevel(Level.FINEST);
		l.addHandler(c);
		return l;
	}

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
		log4j.fine(formatClassName(c) + string);
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
		log4j.severe(formatClassName(class1) + message + "\r\n" + e.getMessage());
	}

	/**
	 * Report an error with the class.
	 * 
	 * @param class1
	 * @param string
	 */
	public synchronized static void error(Class<?> class1, String string) {
		log4j.severe(formatClassName(class1) + string);
	}

	/**
	 * Trace logging
	 * 
	 * @param class1 class doing the logging
	 * @param msg    Message to log
	 */
	public synchronized static void trace(Class<?> class1, String msg) {
		log4j.finer(formatClassName(class1) + msg);
	}

	/**
	 * Change the logging level
	 * 
	 * @param newLevel - the new logging level
	 */
	public static void setLevel(Level newLevel) {
		log4j.setLevel(newLevel);
	}
}
