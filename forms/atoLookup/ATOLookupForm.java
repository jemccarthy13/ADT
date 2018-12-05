package atoLookup;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import panels.Panels;
import utilities.ImageLibrary;

/**
 * The main frame of the ATO Lookup.
 * 
 * @author John McCarthy
 *
 */
public class ATOLookupForm extends JFrame {

	private static final long serialVersionUID = 4569010347602817674L;

	private static ATOLookupForm instance = new ATOLookupForm();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOLookupForm getInstance() {
		return instance;
	}

	private ATOLookupForm() {
		setSize(725, 600);

		this.setIconImage(ImageLibrary.getImage("searchIcon"));
		this.setTitle("ATO Lookup");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(40, 40, 40, 40));

		ATOLookupTable table = ATOLookupTable.getInstance();

		Border border = new LineBorder(Color.black);
		table.setBorder(border);
		table.setRowHeight(20 + 10);
		table.setPreferredScrollableViewportSize(new Dimension(523, 233));

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 205, 523, 226);
		scrollPane.setBorder(border);// new EmptyBorder(20, 20, 20, 20));

		// Add the scroll pane and button panel to the content.
		contentPane.add(Panels.getInstanceOf(ATOSearchPanel.class), BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// Add the "Add to Rundown" button and associated functionality
		contentPane.add(Panels.getInstanceOf(ATOLookupAddPanel.class), BorderLayout.SOUTH);

		this.add(contentPane);
	}
}
