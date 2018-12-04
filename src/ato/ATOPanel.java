package ato;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * The main panel for ATO table and information
 * 
 * @author John McCarthy
 *
 */
public class ATOPanel extends JPanel {

	private static final long serialVersionUID = -6930365855924400678L;
	private static ATOPanel instance = new ATOPanel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOPanel getInstance() {
		return instance;
	}

	private ATOPanel() {
		// establish the main content pane (layout the way we want)
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new BorderLayout());

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(ATOTable.getInstance());
		scrollPane.setBounds(12, 205, 523, 226);

		// Add the scroll pane and button panel to the content.
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(ATOButtonPanel.getInstance(), BorderLayout.NORTH);
	}
}
