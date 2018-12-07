package main;

import gui.ATOGeneratorFrame;
import swing.GUI;
import utilities.Configuration;
import utilities.DebugUtility;

/**
 * The main starting point of the ATO Generator.
 */
public final class ATOGenerator {

	private ATOGenerator() {
	}

	/**
	 * Starting point of ATO maker
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Configuration.setLookAndFeel(ATOGenerator.class);
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).setVisible(true);
		DebugUtility.debug(ATOGenerator.class, "Startup successful.");
	}
}
