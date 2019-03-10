package atoLookup;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import swing.BaseFrame;
import swing.Borders;
import swing.SingletonHolder;
import utilities.ImageLibrary;

/**
 * The main frame of the ATO Lookup.
 */
public class ATOLookupFrame extends BaseFrame {

	private static final long serialVersionUID = 4569010347602817674L;

	@Override
	public void create() {
		setSize(1000, 600);

		this.setAlwaysOnTop(true);

		this.setIconImage(ImageLibrary.getImage("searchIcon"));
		this.setTitle("ATO Lookup");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(40, 40, 40, 40));

		ATOLookupTable table = ATOLookupTable.getInstance();

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(12, 205, 523, 226);
		scrollPane.setBorder(Borders.BLACK);// new EmptyBorder(20, 20, 20, 20));

		// Add the scroll pane and button panel to the content.
		contentPane.add((Component) SingletonHolder.getInstanceOf(ATOSearchPanel.class), BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		// Add the "Add to Rundown" button and associated functionality
		contentPane.add((Component) SingletonHolder.getInstanceOf(ATOLookupAddPanel.class), BorderLayout.SOUTH);

		this.add(contentPane);
	}
}
