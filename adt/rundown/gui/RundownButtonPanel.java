
package rundown.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.border.EmptyBorder;

import atoLookup.ATOLookupFrame;
import main.RundownFrame;
import managers.ManagerFrame;
import swing.ActionButton;
import swing.BaseFrame;
import swing.BasePanel;
import swing.GUI;
import utilities.Configuration;
import utilities.DebugUtility;

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
	private ActionButton mildeconBtn;

	private ActionListener rdBtnListener;

	private ActionButton stacksBtn;

	private ActionButton metricsBtn;

	private ActionButton lowdownMgrBtn;

	private ActionButton getLowdownBtn;

	/**
	 * Listen to the rundown buttons
	 */
	public class RundownButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(RundownButtonPanel.this.atoLookupBtn)) {
				BaseFrame atoLookup = GUI.FRAMES.getInstanceOf(ATOLookupFrame.class);
				atoLookup.setLocationRelativeTo(GUI.FRAMES.getInstanceOf(RundownFrame.class));
				atoLookup.setVisible(true);
			} else if (e.getSource().equals(RundownButtonPanel.this.asMgrBtn)) {
				ManagerFrame mgrForm = (ManagerFrame) GUI.FRAMES.getInstanceOf(ManagerFrame.class);
				mgrForm.setLocationRelativeTo(GUI.FRAMES.getInstanceOf(RundownFrame.class));
				mgrForm.setVisible(true);
			}
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

		JCheckBox compactCheck = new JCheckBox("Compact Mode");
		compactCheck.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Configuration.setCompact(((JCheckBox) (e.getSource())).isSelected());
				DebugUtility.debug(getClass(), "Setting compact: " + Configuration.isCompactMode());
				((RundownFrame) GUI.FRAMES.getInstanceOf(RundownFrame.class)).handleCompact();
			}
		});
		compactCheck.setSelected(true);

		add(this.atoLookupBtn);
		add(this.stacksBtn);
		add(this.lowdownMgrBtn);
		add(this.asMgrBtn);
		add(this.metricsBtn);
		add(this.getLowdownBtn);
		add(this.mildeconBtn);
		add(compactCheck);
	}
}
