package utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logging wrapper around log4j
 */
public class DebugUtility {

	/** The logger */
	private static Logger log4j = LogManager.getLogger("ADTLogger");

	private final static int MESSAGE_LENGTH = 40;

	/**
	 * Print a formatted debug header section
	 * 
	 * @param header - the message to print
	 */
	public static void printHeader(String header) {

		int banner = (MESSAGE_LENGTH - (header.length() + 2)) / 2;

		StringBuilder b = new StringBuilder();
		for (int x = 0; x < banner; x++) {
			b.append("=");
		}
		b.append(" " + header + " ");

		for (int x = 0; x < banner; x++) {
			b.append("=");
		}

		log4j.debug(b.toString());
	}

	/**
	 * Print the given message only if we're logging (VERBOSE)
	 * 
	 * @param c
	 * 
	 * @param string - the message to log
	 */
	public static void debug(Class<?> c, String string) {
		if (log4j.isDebugEnabled()) {
			log4j.debug("(" + c.getSimpleName() + ") " + string);
		}
	}

	/**
	 * Print an error without exiting
	 * 
	 * @param string - the message to print
	 */
	public static void error(String string) {
		log4j.error(string);
	}

	/**
	 * Error with exception
	 * 
	 * @param message the message to explain the error
	 * @param e       the exception that was thrown
	 */
	public static void error(String message, Exception e) {
		log4j.error(message, e);
	}

	/**
	 * Fatal message
	 * 
	 * @param message the end of the world message
	 */
	public static void fatal(String message) {
		log4j.fatal(message);
	}

	/**
	 * Wrapper around quitting the JVM
	 */
	public static void quit() {
		System.exit(0);
	}

	/**
	 * Report an error with the class.
	 * 
	 * @param class1
	 * @param string
	 */
	public static void error(Class<?> class1, String string) {
		log4j.error("(" + class1.getSimpleName() + ") " + string);
	}
}
