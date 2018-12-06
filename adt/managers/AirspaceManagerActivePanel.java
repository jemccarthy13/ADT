package managers;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import atoLookup.ATOLookupForm;
import structures.Airspace;
import structures.AirspaceList;
import swing.ADTLabel;
import swing.ActionButton;
import swing.BasePanel;
import swing.GUI;
import utilities.DebugUtility;

/**
 * Puts the active airspaces button on the manager panel.
 */
public class AirspaceManagerActivePanel extends BasePanel {

	private static final long serialVersionUID = 6422923881297849544L;

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

			GUI.FRAMES.getInstanceOf(ATOLookupForm.class).repaint();
			ManagerFrame.getInstance().repaint();
		}
	};

	@Override
	public void create() {
		JButton addSelectedBtn = new ActionButton("Activate Selected", this.ActivateButtonListener);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new GridLayout(1, 3, 50, 30));
		this.add(new ADTLabel(""));
		this.add(addSelectedBtn);
		this.add(new ADTLabel(""));
	}
}
