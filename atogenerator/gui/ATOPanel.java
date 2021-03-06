package gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import swing.BasePanel;
import swing.SingletonHolder;
import table.ATOTable;

/**
 * The main panel for ATO table and information
 */
public class ATOPanel extends BasePanel {

	private static final long serialVersionUID = -6930365855924400678L;

	@Override
	public void create() {
		// establish the main content pane (layout the way we want)
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new BorderLayout());

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(ATOTable.getInstance());
		scrollPane.setBounds(12, 205, 523, 226);

		// Add the scroll pane and button panel to the content.
		this.add(scrollPane, BorderLayout.CENTER);
		this.add((Component) SingletonHolder.getInstanceOf(ATOButtonPanel.class), BorderLayout.NORTH);
	}
}
