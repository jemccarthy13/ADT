package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

/**
 * Wrapper around JRobot to provide enhanced capability.
 */
public class ADTRobot extends Robot {

	/**
	 * @throws AWTException ?
	 */
	public ADTRobot() throws AWTException {
		super();
	}

	/**
	 * Sleep for button input
	 */
	public static void sleep() {
		sleep(50);
	}

	/**
	 * @param message
	 */
	public void type(String message) {
		char[] letters = message.toCharArray();
		for (char ch : letters) {
			this.keyPress((byte) ch);
			sleep();
			this.keyRelease((byte) ch);
			sleep();
		}
	}

	/**
	 * @param message
	 */
	public void tab() {
		this.keyPress(KeyEvent.VK_TAB);
		sleep();
		this.keyRelease(KeyEvent.VK_TAB);
		sleep();
	}

	/**
	 * @param i how long to sleep
	 */
	public static void sleep(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			// do nothing
		}
	}
}
