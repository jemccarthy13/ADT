package asmanager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import swing.GUI;

/**
 * The main content panel for the airspace manager
 * 
 * @author John McCarthy
 *
 */
public class AirspaceManagerForm extends JPanel {

	private static final long serialVersionUID = -9008499022625165234L;

	/**
	 * Singleton implementation
	 * 
	 * @return single instance
	 */
	public static AirspaceManagerForm getInstance() {
		return instance;
	}

	private static AirspaceManagerForm instance = new AirspaceManagerForm();

	private AirspaceManagerForm() {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(40, 40, 40, 40));

		AirspaceTable table = AirspaceTable.getInstance();

		Border border = new LineBorder(Color.black);
		table.setBorder(border);
		table.setRowHeight(20 + 10);
		table.setPreferredScrollableViewportSize(new Dimension(523, 233));

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 205, 523, 226);
		scrollPane.setBorder(border);

		// Add the scroll pane and button panel to the content.
		this.add(GUI.PANELS.getInstanceOf(AirspaceManagerPanel.class), BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);

		// Add the "Add to Rundown" button and associated functionality
		this.add(GUI.PANELS.getInstanceOf(AirspaceManagerActivePanel.class), BorderLayout.SOUTH);
	}
}
