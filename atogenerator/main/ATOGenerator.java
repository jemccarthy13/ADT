package main;

import gui.ATOGeneratorFrame;
import swing.GUI;

/**
 * The main starting point of the ATO Generator.
 */
public class ATOGenerator {

	/**
	 * Starting point of ATO maker
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).setVisible(true);
		System.out.println("Started.");
	}
}
