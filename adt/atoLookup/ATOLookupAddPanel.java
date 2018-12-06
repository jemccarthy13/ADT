package atoLookup;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import main.RundownFrame;
import structures.ATOAssets;
import structures.Asset;
import structures.RundownAssets;
import swing.ADTLabel;
import swing.ActionButton;
import swing.BasePanel;
import swing.GUI;
import utilities.DebugUtility;
import utilities.Output;

/**
 * A main panel for the ATO Lookup form
 */
public class ATOLookupAddPanel extends BasePanel {

	private static final long serialVersionUID = 4469189062928123724L;

	private ActionListener AddToRundownButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < ATOAssets.staticInstance().size(); x++) {
				Asset atoAsset = ATOAssets.staticInstance().get(x);
				if (atoAsset.isAddToRundown()) {
					DebugUtility.debug(ATOLookupAddPanel.class, atoAsset.getVCS() + " being added from ATO Lookup");
					GUI.MODELS.getInstanceOf(ATOLookupModel.class).setValueAt(Boolean.FALSE, x, 0);
					if (!RundownAssets.getInstance().contains(atoAsset)) {
						RundownAssets.getInstance().add(atoAsset);
					} else {
						Output.forceInfoMessage("Duplicate", "Duplicate asset " + atoAsset.getFullCallsign() + " ("
								+ atoAsset.getMode2() + ") will not be added.");
					}
				}
			}
			GUI.FRAMES.getInstanceOf(ATOLookupForm.class).repaint();
			RundownFrame.getInstance().repaint();
		}
	};

	@Override
	public void create() {
		JButton addSelectedBtn = new ActionButton("Add to Rundown", this.AddToRundownButtonListener);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new GridLayout(1, 3, 50, 30));
		this.add(new ADTLabel(""));
		this.add(addSelectedBtn);
		this.add(new ADTLabel(""));
	}
}
