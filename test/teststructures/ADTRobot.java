package teststructures;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.Assert;

/**
 * Wrapper around JRobot to provide enhanced capability.
 */
public class ADTRobot {

	/**
	 * Static instance of robot
	 */
	public Robot r;

	private static ADTRobot instance = new ADTRobot();

	private ADTRobot() {
		try {
			this.r = new Robot();
		} catch (AWTException e) {
			Assert.fail("Couldn't create test robot.");
		}
	}

	/**
	 * Sleep for button input
	 */
	public static void sleep() {
		sleep(100);
	}

	/**
	 * Behaves like a human was pressing the keyboard buttons
	 * 
	 * @param e
	 */
	public static void simulatePressKey(int e) {
		instance.r.keyPress(e);
		sleep();
		instance.r.keyRelease(e);
		sleep();
	}

	/**
	 * @param message
	 */
	public static void type(String message) {
		char[] letters = message.toCharArray();
		for (char ch : letters) {
			simulatePressKey((byte) ch);
		}
	}

	/**
	 * Tab with a simulated key press
	 */
	public static void tab() {
		simulatePressKey(KeyEvent.VK_TAB);
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

	/**
	 * Press the backspace key
	 */
	public static void backspace() {
		simulatePressKey(KeyEvent.VK_BACK_SPACE);
	}
}
