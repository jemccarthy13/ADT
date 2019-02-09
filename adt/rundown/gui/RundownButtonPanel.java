
package rundown.gui;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;

import asmanager.ASManagerFrame;
import atoLookup.ATOLookupFrame;
import stacksmanager.StacksFrame;
import swing.ActionButton;
import swing.BaseFrame;
import swing.BasePanel;
import swing.SingletonHolder;
import utilities.Configuration;

/**
 * The panel containing all of the rundown buttons (i.e. a form header of
 * sorts).
 */
public class RundownButtonPanel extends BasePanel {

	private static final long serialVersionUID = 6980336047696920906L;
	/** do ato lookup */
	public ActionButton atoLookupBtn;

	/** manage airspaces */
	public ActionButton asMgrBtn;
	/** publicize compact mode for unit test */
	public JCheckBox compactCheck;

	/** Button to open the MILDECON assist (modified AS Manager frame) */
	ActionButton mildeconBtn;

	private ActionListener rdBtnListener;

	/** generate stacks on stacks on stacks */
	public ActionButton stacksBtn;

	private ActionButton metricsBtn;

	private ActionButton lowdownMgrBtn;

	private ActionButton getLowdownBtn;

	/**
	 * Listen to the rundown buttons
	 */
	public class RundownButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Class<?> c = null;
			if (e.getSource().equals(RundownButtonPanel.this.atoLookupBtn)) {
				c = ATOLookupFrame.class;
			} else if (e.getSource().equals(RundownButtonPanel.this.asMgrBtn)) {
				c = ASManagerFrame.class;
			} else if (e.getSource().equals(RundownButtonPanel.this.stacksBtn)) {
				c = StacksFrame.class;
			} else if (e.getSource().equals(RundownButtonPanel.this.mildeconBtn)) {
				c = ASManagerFrame.class;
				ASManagerFrame asFrame = (ASManagerFrame) SingletonHolder.getInstanceOf(c);
				asFrame.setMildeon();
			}

			BaseFrame frame = (BaseFrame) SingletonHolder.getInstanceOf(c);
			frame.setLocationRelativeTo((Component) SingletonHolder.getInstanceOf(RundownFrame.class));
			frame.setVisible(true);
		}
	}

	/**
	 * Create a new rundown button panel
	 */
	@Override
	public void create() {
		this.setLayout(new GridLayout(3, 3, 20, 20));
		this.setBorder(new EmptyBorder(20, 20, 20, 20));

		this.rdBtnListener = new RundownButtonListener();

		this.atoLookupBtn = new ActionButton("ATO Lookup", this.rdBtnListener);
		this.asMgrBtn = new ActionButton("AS Manager", this.rdBtnListener);
		this.mildeconBtn = new ActionButton("MILDECON", this.rdBtnListener);
		this.stacksBtn = new ActionButton("Stacks", this.rdBtnListener);
		this.metricsBtn = new ActionButton("Metrics", this.rdBtnListener);
		this.lowdownMgrBtn = new ActionButton("Lowdown Manager", this.rdBtnListener);
		this.getLowdownBtn = new ActionButton("Get Lowdown", this.rdBtnListener);

		this.compactCheck = new JCheckBox("Compact Mode");
		this.compactCheck.setSelected(true);
		this.compactCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Configuration.switchCompact();
				((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).handleCompact();
			}
		});

		add(this.atoLookupBtn);
		add(this.stacksBtn);
		add(this.lowdownMgrBtn);
		add(this.asMgrBtn);
		add(this.metricsBtn);
		add(this.getLowdownBtn);
		add(this.mildeconBtn);
		add(this.compactCheck);
	}
}
