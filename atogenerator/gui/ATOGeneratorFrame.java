package gui;

import java.awt.Component;

import swing.BaseFrame;
import swing.SingletonHolder;
import utilities.DebugUtility;
import utilities.ImageLibrary;

/**
 * A class to generate valid, formatted ATOs in USMTF00.txt format.
 *
 */
public class ATOGeneratorFrame extends BaseFrame {

	private static final long serialVersionUID = 6718631382663500003L;

	@Override
	public void create() {
		this.setSize(1600, 600);
		this.setLocationRelativeTo(null);
		this.setTitle("ATO Generator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		DebugUtility.trace(ATOGeneratorFrame.class, "Frame loaded");

		this.setIconImage(ImageLibrary.getImage("./AF-Roundel"));
		DebugUtility.trace(ATOGeneratorFrame.class, "IconImage loaded/set");

		this.add((Component) SingletonHolder.getInstanceOf(ATOPanel.class));
		DebugUtility.trace(ATOGeneratorFrame.class, "MainPanel added");
	}
}
