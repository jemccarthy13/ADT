
package ato;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import panels.ADTLabel;
import panels.ActionButton;
import panels.BasePanel;

/**
 * The panel containing all of the ATO functions.
 *
 */
public class ATOButtonPanel extends BasePanel {

	private static final long serialVersionUID = 6980336047696920906L;

	/** button to load ATO project */
	JButton loadBtn;
	/** button to generate the ATO from current data */
	JButton genBtn;
	/** button to load ATO project */
	JButton saveBtn;

	/**
	 * 
	 */
	ActionListener atoButtonListener;

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

				try {
					ATOData.getInstance().save();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (e.getSource().equals(this.pnl.loadBtn)) {
				ATOData.loadAssets();
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
