package gui;

import swing.BaseFrame;
import swing.GUI;
import utilities.ImageLibrary;

/**
 * A class to generate valid, formatted ATOs in USMTF00.txt format.
 *
 */
public class ATOGeneratorFrame extends BaseFrame {

	private static final long serialVersionUID = 6718631382663500003L;

	@Override
	public void create() {
		this.add(GUI.PANELS.getInstanceOf(ATOPanel.class));
		this.setSize(1600, 600);
		this.setLocationRelativeTo(null);
		this.setIconImage(ImageLibrary.getImage("AF-Roundel"));
		this.setTitle("ATO Generator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
