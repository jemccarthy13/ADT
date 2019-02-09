package asmanager.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;

import rundown.gui.RundownFrame;
import swing.BaseFrame;
import swing.SingletonHolder;
import utilities.DebugUtility;
import utilities.ImageLibrary;

/**
 * Main frame for the Airspace Manager, MILDECON assist, and Lowdown Managers.
 */
public class ManagerFrame extends BaseFrame {

	private static final long serialVersionUID = 311927586878557417L;

	private JPanel content = null;

	@Override
	public void create() {
		setSize(800, 900);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setIconImage(ImageLibrary.getImage("searchIcon"));
		this.setTitle("Airspace Manager");
		this.setLocationRelativeTo((JFrame) (SingletonHolder.getInstanceOf(RundownFrame.class)));

		this.content = ASManagerPanel.getInstance();
		this.add(this.content);
	}

	/**
	 * Set the content panel to contain just the add form and shorten the user
	 * action chain
	 */
	public void setMildeon() {
		DebugUtility.error(ManagerFrame.class, "SETUP AS MANAGER FRAME FOR MILDECON");
	}

	/**
	 * Set the content panel to show a table of SAMs instead
	 */
	public void setLowdown() {
		DebugUtility.error(ManagerFrame.class, "SETUP AS MANAGER FRAME FOR LOWDOWN");
	}
}
