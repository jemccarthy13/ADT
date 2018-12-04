package managers;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import atoLookup.ATOLookupForm;
import structures.Airspace;
import structures.AirspaceList;
import utilities.DebugUtility;
import utilities.Fonts;

/**
 * Puts the active airspaces button on the manager panel.
 * 
 * @author John McCarthy
 */
public class AirspaceManagerActivePanel extends JPanel {

	private static final long serialVersionUID = 6422923881297849544L;
	private static AirspaceManagerActivePanel instance = new AirspaceManagerActivePanel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static AirspaceManagerActivePanel getInstance() {
		return instance;
	}

	private ActionListener ActivateButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < AirspaceList.getInstance().size(); x++) {
				Airspace aspace = AirspaceList.getInstance().get(x);
				if (aspace.isAddToRundown()) {
					System.out.println(aspace.getName() + " being activated.");
					DebugUtility.printDebug("TODO - activate");
				}
			}
			ATOLookupForm.getInstance().repaint();
			ManagerFrame.getInstance().repaint();
		}
	};

	private AirspaceManagerActivePanel() {
		JButton addSelectedBtn = new JButton("Activate Selected");
		addSelectedBtn.setFont(Fonts.serif);
		addSelectedBtn.addActionListener(this.ActivateButtonListener);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new GridLayout(1, 3, 50, 30));
		this.add(new JLabel(""));
		this.add(addSelectedBtn);
		this.add(new JLabel(""));
	}
}
