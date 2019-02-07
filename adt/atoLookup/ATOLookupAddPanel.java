package atoLookup;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;

import rundown.gui.RundownMenuBar;
import structures.ATOAssets;
import structures.Asset;
import structures.RundownAssets;
import swing.ADTLabel;
import swing.ActionButton;
import swing.BasePanel;
import swing.SingletonHolder;
import utilities.ADTTableModel;
import utilities.DebugUtility;
import utilities.Output;

/**
 * A main panel for the ATO Lookup form
 */
public class ATOLookupAddPanel extends BasePanel {

	private static final long serialVersionUID = 4469189062928123724L;

	/** Button for adding from ATO Lookup to rundown */
	ActionButton addToRundown;

	/**
	 * Handle the "Add to Rundown" button click
	 */
	public class ATOLookupButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			DebugUtility.trace(this.getClass(), "Add to rundown button pressed");
			for (int x = 0; x < ATOAssets.getInstance().size(); x++) {
				Asset atoAsset = ATOAssets.getInstance().get(x);
				if (atoAsset.isAddToRundown()) {
					DebugUtility.debug(ATOLookupAddPanel.class, atoAsset.getVCS() + " being added from ATO Lookup");
					((ADTTableModel<?>) SingletonHolder.getInstanceOf(ATOLookupModel.class)).setValueAt(Boolean.FALSE,
							x, 0);

					if (RundownAssets.getInstance().contains(atoAsset)) {
						Output.forceInfoMessage("Duplicate", "Duplicate asset " + atoAsset.getFullCallsign() + " ("
								+ atoAsset.getMode2() + ") will not be added.");
					} else if (RundownAssets.getInstance().forceAdd()) {
						DebugUtility.debug(this.getClass(), "Should be overwritten.");
						RundownAssets.force(atoAsset);
						RundownAssets.setForcedRow(-1);
					} else {
						RundownAssets.getInstance().add(atoAsset);
					}
				}
			}
			RundownMenuBar.getInstance().refresh.doClick();
			((Component) SingletonHolder.getInstanceOf(ATOLookupFrame.class)).repaint();
		}
	}

	private ActionListener AddToRundownButtonListener;

	@Override
	public void create() {
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new GridLayout(1, 3, 50, 30));

		this.AddToRundownButtonListener = new ATOLookupButtonListener();
		this.addToRundown = new ActionButton("Add to Rundown", this.AddToRundownButtonListener);

		add(new ADTLabel(""));
		add(this.addToRundown);
		add(new ADTLabel(""));
	}
}
