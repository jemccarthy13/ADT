package main;

import swing.GUI;
import utilities.ShutdownThread;

/**
 * Main starting point of the ADT.
 */
public class ADTApp {
	/**
	 * Launch the application.
	 * 
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		GUI.FRAMES.getInstanceOf(RundownFrame.class).setVisible(true);
		Runtime.getRuntime().addShutdownHook(new ShutdownThread());
	}
}
