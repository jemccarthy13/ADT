package managers;

import javax.swing.JPanel;

import rundown.gui.RundownFrame;
import swing.BaseFrame;
import swing.GUI;
import utilities.ImageLibrary;

/**
 * Main frame for the Airspace Manager, MILDECON assist, and Lowdown Managers.
 * 
 * TODO - massive implementation needed
 *
 */
public class ManagerFrame extends BaseFrame {

	private static final long serialVersionUID = 311927586878557417L;

	private JPanel content;

	@Override
	public void create() {
		setSize(800, 900);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		this.setIconImage(ImageLibrary.getImage("searchIcon"));
		this.setTitle("Airspace Manager");
		this.setLocationRelativeTo(GUI.FRAMES.getInstanceOf(RundownFrame.class));
		this.setAirspacePanel();
		this.add(this.content);
	}

	/**
	 * Set the content panel to be the airspace manager.
	 */
	public void setAirspacePanel() {
		this.content = AirspaceManagerForm.getInstance();
	}
}
