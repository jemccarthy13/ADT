package atoLookup;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import utilities.Fonts;

/**
 * A panel for the ATO lookup that holds the functionality to search the ATO
 * assets
 * 
 * @author John McCarthy
 *
 */
public class ATOSearchPanel extends JPanel {

	private static final long serialVersionUID = 4495220896320668705L;

	private static ATOSearchPanel instance = new ATOSearchPanel(ATOLookupTable.getInstance());

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOSearchPanel getInstance() {
		return instance;
	}

	/**
	 * Construct a new panel
	 * 
	 * @param table
	 *            the table to use in the search
	 */
	private ATOSearchPanel(final ATOLookupTable table) {
		setLayout(new GridLayout(4, 4, 5, 5));

		final TableRowSorter<ATOLookupModel> sorter = new TableRowSorter<ATOLookupModel>(ATOLookupModel.getInstance());
		table.setRowSorter(sorter);

		final JTextField callsignBox = new JTextField();
		callsignBox.setFont(Fonts.serif);
		JLabel csLbl = new JLabel("Callsign: ");
		csLbl.setFont(Fonts.serif);

		final JTextField vcsBox = new JTextField();
		vcsBox.setFont(Fonts.serif);
		JLabel vcsLbl = new JLabel("VCS: ");
		vcsLbl.setFont(Fonts.serif);

		final JTextField mode2Box = new JTextField();
		mode2Box.setFont(Fonts.serif);
		JLabel mode2Lbl = new JLabel("Mode 2: ");
		mode2Lbl.setFont(Fonts.serif);

		JButton searchBtn = new JButton("Search");
		searchBtn.setFont(Fonts.serif);

		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>();
				// If current expression doesn't parse, don't update.
				try {
					filters.add(RowFilter.regexFilter(callsignBox.getText(), 1));
					filters.add(RowFilter.regexFilter(vcsBox.getText(), 2));
					filters.add(RowFilter.regexFilter(mode2Box.getText(), 3));

				} catch (java.util.regex.PatternSyntaxException e1) {
					return;
				}
				sorter.setRowFilter(RowFilter.andFilter(filters));
			}
		});

		add(csLbl);
		add(callsignBox);
		add(new JLabel());
		add(vcsLbl);
		add(vcsBox);
		add(searchBtn);
		add(mode2Lbl);
		add(mode2Box);

		add(new JLabel());
		add(new JLabel());
	}

}
