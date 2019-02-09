package asmanager.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import asmanager.model.AirspaceTable;
import swing.Borders;
import swing.SingletonHolder;

/**
 * The main content panel for the airspace manager
 */
public class ASManagerPanel extends JPanel {

	private static final long serialVersionUID = -9008499022625165234L;

	/**
	 * Singleton implementation
	 * 
	 * @return single instance
	 */
	public static ASManagerPanel getInstance() {
		return instance;
	}

	private static ASManagerPanel instance = new ASManagerPanel();

	private ASManagerPanel() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(40, 40, 40, 40));

		AirspaceTable table = AirspaceTable.getInstance();

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 205, 523, 226);
		scrollPane.setBorder(Borders.BLACK);

		// Add the edit panel on top
		this.add((Component) SingletonHolder.getInstanceOf(ASManagerEditPanel.class), BorderLayout.NORTH);

		// Add the airspace scrollpane list in the center
		this.add(scrollPane, BorderLayout.CENTER);
	}
}
