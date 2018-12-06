package atoLookup;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import structures.ADTTableModel;
import swing.GUI;
import utilities.Fonts;

/**
 * An ATO Lookup JTable.
 * 
 * @author John McCarthy
 *
 */
public class ATOLookupTable extends JTable {

	private static final long serialVersionUID = 1267395829859061144L;

	private ADTTableModel<?> model;

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

		this.model = GUI.MODELS.getInstanceOf(ATOLookupModel.class);
		this.setFont(Fonts.serif);

		// set the data
		this.setModel(this.model);

		// font
		this.getTableHeader().setFont(Fonts.serifBold);

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
	}

	/**
	 * Size the columns of the ATO lookup form.
	 */
	public void resizeColumns() {

		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		// size the columns
		int[] minSizes = { 40, 120, 60, 60, 60, 60, 120, 120 };
		int[] widths = { 40, 120, 60, 60, 60, 60, 120, 120 };
		int[] maxSizes = { 40, 120, 60, 60, 60, 60, 120, 120 };

		TableColumnModel colModel = this.getColumnModel();
		for (int x = 0; x < colModel.getColumnCount(); x++) {
			colModel.getColumn(x).setMinWidth(minSizes[x]);
			colModel.getColumn(x).setWidth(widths[x]);
			colModel.getColumn(x).setMaxWidth(maxSizes[x]);
		}
	}
}
