package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import datastructures.ATOData;
import datastructures.ATOProjectImporter;
import swing.ADTLabel;
import swing.ActionButton;
import swing.BasePanel;
import utilities.Configuration;
import utilities.FileChooser;

/**
 * The panel containing all of the ATO functional buttons.
 */
public class ATOButtonPanel extends BasePanel {

	private static final long serialVersionUID = 6980336047696920906L;

	/** button to load ATO project */
	public ActionButton loadBtn;
	/** button to generate the ATO from current data */
	public ActionButton genBtn;
	/** button to load ATO project */
	public ActionButton saveBtn;

	/**
	 * ActionListener to be added to the buttons
	 */
	public ActionListener atoButtonListener;

	/**
	 * Listen to button events on the ATOButtonPanel
	 */
	class ATOButtonListener implements ActionListener {

		/** save the panel for button sources */
		ATOButtonPanel pnl;

		/**
		 * Constructor
		 * 
		 * @param panel the panel to listen to
		 */
		ATOButtonListener(ATOButtonPanel panel) {
			this.pnl = panel;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(this.pnl.saveBtn)) {
				ATOData.getInstance().save();
			} else if (e.getSource().equals(this.pnl.loadBtn)) {
				FileChooser.selectAndLoadFile("Select an ATO Proj", new FileNameExtensionFilter("ATO Proj", "proj"),
						Configuration.getInstance().getATOProjFileLoc(), new ATOProjectImporter());
			} else if (e.getSource().equals(this.pnl.genBtn)) {
				ATOData.output();
			}
		}
	}

	@Override
	public void create() {
		this.atoButtonListener = new ATOButtonListener(this);

		setLayout(new GridLayout(1, 5, 20, 20));
		setBorder(new EmptyBorder(20, 20, 20, 20));

		this.saveBtn = new ActionButton("Save", this.atoButtonListener);
		this.loadBtn = new ActionButton("Load", this.atoButtonListener);
		this.genBtn = new ActionButton("Generate", this.atoButtonListener);

		add(new ADTLabel());
		add(this.saveBtn);
		add(this.loadBtn);
		add(this.genBtn);
		add(new ADTLabel());
	}

}