package atoLookup;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableRowSorter;

import swing.ADTLabel;
import swing.ActionButton;
import swing.BasePanel;
import swing.GUI;
import utilities.ADTTableModel;

/**
 * A panel for the ATO lookup that holds the functionality to search the ATO
 * assets
 */
public class ATOSearchPanel extends BasePanel {

	private static final long serialVersionUID = 4495220896320668705L;

	@Override
	public void create() {
		setLayout(new GridLayout(4, 4, 5, 5));

		final TableRowSorter<ADTTableModel<?>> sorter = new TableRowSorter<ADTTableModel<?>>(
				GUI.MODELS.getInstanceOf(ATOLookupModel.class));
		ATOLookupTable.getInstance().setRowSorter(sorter);

		final JTextField callsignBox = new JTextField();
		ADTLabel csLbl = new ADTLabel("Callsign: ");

		final JTextField vcsBox = new JTextField();
		ADTLabel vcsLbl = new ADTLabel("VCS: ");

		final JTextField mode2Box = new JTextField();
		ADTLabel mode2Lbl = new ADTLabel("Mode 2: ");

		ActionButton searchBtn = new ActionButton("Search", new ActionListener() {
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
		add(new ADTLabel());
		add(vcsLbl);
		add(vcsBox);
		add(searchBtn);
		add(mode2Lbl);
		add(mode2Box);

		add(new ADTLabel());
		add(new ADTLabel());
	}

}
