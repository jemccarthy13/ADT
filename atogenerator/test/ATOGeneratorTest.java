package test;

import org.junit.Test;

import gui.ATOGeneratorFrame;
import main.ATOGenerator;
import swing.GUI;

/**
 * JUnit test cases
 */
public class ATOGeneratorTest extends BaseTest {

	/**
	 * Test load
	 */
	@Test
	public void test() {
		ATOGenerator.main(null);
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).dispose();
	}
}
