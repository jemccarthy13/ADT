package rundown;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * The main panel for rundown table and information
 * 
 * @author John McCarthy
 *
 */
public class RundownPanel extends JPanel {

	private static final long serialVersionUID = 8713537272012192261L;

	private static RundownPanel instance = new RundownPanel();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static RundownPanel getInstance() {
		return instance;
	}

	private RundownPanel() {
		// establish the main content pane (layout the way we want)
		this.setBorder(new EmptyBorder(20, 20, 20, 20));
		this.setLayout(new BorderLayout());

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(RundownTable.getInstance());
		scrollPane.setBounds(12, 205, 523, 226);

		// Add the scroll pane and button panel to the content.
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(RundownButtonPanel.getInstance(), BorderLayout.NORTH);
	}
}
