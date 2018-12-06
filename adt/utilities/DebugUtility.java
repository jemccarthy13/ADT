package utilities;

import java.io.PrintStream;

/**
 * Logging wrapper around Syso / syserr
 */
public class DebugUtility {

	private static PrintStream stream = System.out;

	/**
	 * Set the DEBUG_LEVEL
	 */
	private static DEBUG_LEVEL level = DEBUG_LEVEL.DEBUG;

	/**
	 * The max length of debug messages
	 * 
	 * @NOTE: not strictly enforced except for debug header
	 */
	static int MESSAGE_LENGTH = 80;

	/**
	 * A description of possible logging levels
	 */
	public enum DEBUG_LEVEL {
		/**
		 * Debug logging
		 */
		DEBUG,
		/**
		 * Verbose logging
		 */
		VERBOSE,
		/**
		 * Error logging
		 */
		ERROR,
		/**
		 * No logging
		 */
		OFF
	}

	/**
	 * Set the current debug level
	 * 
	 * @param lvl
	 *            - the level to set to
	 */
	public static void setLevel(DEBUG_LEVEL lvl) {
		level = lvl;
	}

	/**
	 * Set the current output stream
	 * 
	 * @param ps
	 *            - the printstream to use
	 */
	public static void setPrintStream(PrintStream ps) {
		stream = ps;
	}

	/**
	 * Print a formatted debug header section
	 * 
	 * @param header
	 *            - the message to print
	 */
	public static void printHeader(String header) {
		if (level != DEBUG_LEVEL.OFF && level != DEBUG_LEVEL.ERROR) {
			int banner = (MESSAGE_LENGTH - (header.length() + 2)) / 2;

			StringBuilder b = new StringBuilder();
			for (int x = 0; x < banner; x++) {
				b.append("=");
			}
			b.append(" " + header + " ");

			for (int x = 0; x < banner; x++) {
				b.append("=");
			}

			stream.print(b.toString());
			for (int x = 0; x < (MESSAGE_LENGTH - b.toString().length()); x++) {
				stream.print("=");
			}
			stream.println();
		}
	}

	/**
	 * Print the given message only if we're logging (VERBOSE)
	 * 
	 * @param string
	 *            - the message to log
	 */
	public static void logMessage(String string) {
		if (level == DEBUG_LEVEL.VERBOSE) {
			stream.println("* " + string);
		}
	}

	/**
	 * Print a string if debugging or verbose
	 * 
	 * @param string
	 *            - the message to print
	 */
	public static void printDebug(String string) {
		if (level != DEBUG_LEVEL.OFF && level != DEBUG_LEVEL.ERROR) {
			stream.println("* " + string);
		}
	}

	/**
	 * Print an error without exiting
	 * 
	 * @param string
	 *            - the message to print
	 */
	public static void printError(String string) {
		if (level != DEBUG_LEVEL.OFF) {
			System.err.println("* " + string);
		}
	}

	/**
	 * Wrapper around quitting the JVM
	 */
	public static void quit() {
		System.exit(0);
	}
}
