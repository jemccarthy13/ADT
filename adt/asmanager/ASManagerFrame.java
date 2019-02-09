package asmanager;

import javax.swing.JFrame;

import rundown.gui.RundownFrame;
import swing.BaseFrame;
import swing.SingletonHolder;
import utilities.DebugUtility;
import utilities.ImageLibrary;

/**
 * Main frame for the Airspace Manager, MILDECON assist, and Lowdown Managers.
 * 
 * TODO - massive implementation needed
 *
 */
public class ASManagerFrame extends BaseFrame {

	private static final long serialVersionUID = 311927586878557417L;

	@Override
	public void create() {
		setSize(800, 900);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setIconImage(ImageLibrary.getImage("searchIcon"));
		this.setTitle("Airspace Manager");
		this.setLocationRelativeTo((JFrame) (SingletonHolder.getInstanceOf(RundownFrame.class)));

		this.add(AirspaceManagerForm.getInstance());
	}

	/**
	 * Set the content panel to nothing and shorten the user action chain
	 */
	public void setMildeon() {
		DebugUtility.error(ASManagerFrame.class, "SETUP AS MANAGER FRAME FOR MILDECON");
	}
}
