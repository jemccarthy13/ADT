package asmanager;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import rundown.gui.RundownFrame;
import rundown.model.RundownTable;
import swing.ADTLabel;
import swing.ActionButton;
import swing.BasePanel;
import swing.SingletonHolder;

/**
 * Puts the active airspaces button on the manager panel.
 */
public class AirspaceManagerActivePanel extends BasePanel {

	private static final long serialVersionUID = 6422923881297849544L;

	/** The button to active selected airspaces */
	public JButton addSelectedBtn;

	@Override
	public void create() {
		this.addSelectedBtn = new ActionButton("Activate Selected", new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RundownTable.getInstance().getCellListener().processEditingStopped();

				((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
				// ((Component) SingletonHolder.getInstanceOf(ASManagerFrame.class)).repaint();
			}
		});
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new GridLayout(1, 3, 50, 30));
		this.add(new ADTLabel(""));
		this.add(this.addSelectedBtn);
		this.add(new ADTLabel(""));
	}
}
