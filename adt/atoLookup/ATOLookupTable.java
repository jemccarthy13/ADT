package atoLookup;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import swing.Borders;
import swing.SingletonHolder;
import utilities.ADTTableModel;
import utilities.Fonts;

/**
 * An ATO Lookup JTable.
 */
public class ATOLookupTable extends JTable {

	private static final long serialVersionUID = 1267395829859061144L;

	private ADTTableModel model;

	private static ATOLookupTable instance = new ATOLookupTable();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOLookupTable getInstance() {
		return instance;
	}

	private ATOLookupTable() {
		this.setFont(Fonts.serif);

		this.model = (ADTTableModel) SingletonHolder.getInstanceOf(ATOLookupModel.class);

		// set the data
		this.setModel(this.model);

		// font
		this.getTableHeader().setFont(Fonts.serifBold);
		this.setBorder(Borders.BLACK);
		this.setRowHeight(20 + 10);
		this.setPreferredScrollableViewportSize(new Dimension(523, 233));

		// sizing
		this.setFillsViewportHeight(true);
		this.setBounds(10, 200, 500, 250);

		// reordering and column selection not allowed
		this.setColumnSelectionAllowed(false);
		this.getTableHeader().setReorderingAllowed(false);

		// set initital widths
		this.resizeColumns();

		// column header click events
		this.setAutoCreateRowSorter(true);

		this.getColumnModel().getColumn(8)
				.setCellRenderer((ATOLookupCellRenderer) SingletonHolder.getInstanceOf(ATOLookupCellRenderer.class));
	}

	/**
	 * Size the columns of the ATO lookup form.
	 */
	public void resizeColumns() {

		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		// size the columns
		int[] minSizes = { 40, 120, 60, 60, 60, 60, 120, 120, 90 };
		int[] widths = { 40, 120, 60, 60, 60, 60, 120, 120, 90 };
		int[] maxSizes = { 40, 120, 60, 60, 60, 60, 120, 120, 600 };

		TableColumnModel colModel = this.getColumnModel();
		for (int x = 0; x < colModel.getColumnCount(); x++) {
			colModel.getColumn(x).setMinWidth(minSizes[x]);
			colModel.getColumn(x).setWidth(widths[x]);

			// let the last two columns (on/off station) resize freely
			if (x < maxSizes.length - 3)
				colModel.getColumn(x).setMaxWidth(maxSizes[x]);
		}
	}
}
