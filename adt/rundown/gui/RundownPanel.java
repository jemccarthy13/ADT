package rundown.gui;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import rundown.model.RundownTable;
import swing.BasePanel;
import swing.GUI;

/**
 * The main panel for rundown table and information
 */
public class RundownPanel extends BasePanel {

	private static final long serialVersionUID = 8713537272012192261L;

	@Override
	public void create() {
		// establish the main content pane (layout the way we want)
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new BorderLayout());

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(RundownTable.getInstance());
		scrollPane.setBounds(12, 205, 523, 226);

		// Add the scroll pane and button panel to the content.
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(GUI.PANELS.getInstanceOf(RundownButtonPanel.class), BorderLayout.NORTH);
	}
}
