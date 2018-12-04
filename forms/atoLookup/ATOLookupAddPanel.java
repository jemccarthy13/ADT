package atoLookup;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.RundownFrame;
import structures.ATOAssets;
import structures.Asset;
import structures.RundownAssets;
import utilities.Fonts;
import utilities.Output;

/**
 * A main panel for the ATO Lookup form
 * 
 * @author John McCarthy
 *
 */
public class ATOLookupAddPanel extends JPanel {

	private static final long serialVersionUID = 4469189062928123724L;

	private static ATOLookupAddPanel instance = new ATOLookupAddPanel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOLookupAddPanel getInstance() {
		return instance;
	}

	private ActionListener AddToRundownButtonListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			for (int x = 0; x < ATOAssets.getInstance().size(); x++) {
				Asset atoAsset = ATOAssets.getInstance().get(x);
				if (atoAsset.isAddToRundown()) {
					System.out.println(atoAsset.getVCS() + " being added");
					ATOLookupModel.getInstance().setValueAt(Boolean.FALSE, x, 0);
					if (!RundownAssets.getInstance().contains(atoAsset)) {
						RundownAssets.getInstance().add(atoAsset);
					} else {
						Output.forceInfoMessage("Duplicate", "Duplicate asset " + atoAsset.getFullCallsign() + " ("
								+ atoAsset.getMode2() + ") will not be added.");
					}
				}
			}
			ATOLookupForm.getInstance().repaint();
			RundownFrame.getInstance().repaint();
		}
	};

	private ATOLookupAddPanel() {
		JButton addSelectedBtn = new JButton("Add to Rundown");
		addSelectedBtn.setFont(Fonts.serif);
		addSelectedBtn.addActionListener(this.AddToRundownButtonListener);
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new GridLayout(1, 3, 50, 30));
		this.add(new JLabel(""));
		this.add(addSelectedBtn);
		this.add(new JLabel(""));
	}
}
