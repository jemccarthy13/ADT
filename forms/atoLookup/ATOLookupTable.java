package atoLookup;

import javax.swing.JTable;

import utilities.Fonts;

/**
 * An ATO Lookup JTable.
 * 
 * @author John McCarthy
 *
 */
public class ATOLookupTable extends JTable {

	private static final long serialVersionUID = 1267395829859061144L;

	private ATOLookupModel model;

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

		this.model = ATOLookupModel.getInstance();
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
		// size the columns
		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		this.getColumnModel().getColumn(0).setMinWidth(40);
		this.getColumnModel().getColumn(0).setWidth(40);
		this.getColumnModel().getColumn(0).setMaxWidth(40);
		this.getColumnModel().getColumn(1).setMinWidth(120);
		this.getColumnModel().getColumn(1).setWidth(120);
		this.getColumnModel().getColumn(1).setMaxWidth(120);
		this.getColumnModel().getColumn(2).setMinWidth(60);
		this.getColumnModel().getColumn(2).setWidth(60);
		this.getColumnModel().getColumn(2).setMaxWidth(60);
		this.getColumnModel().getColumn(3).setMinWidth(60);
		this.getColumnModel().getColumn(3).setWidth(60);
		this.getColumnModel().getColumn(3).setMaxWidth(60);
		this.getColumnModel().getColumn(4).setMinWidth(60);
		this.getColumnModel().getColumn(4).setWidth(60);
		this.getColumnModel().getColumn(4).setMaxWidth(60);
		this.getColumnModel().getColumn(5).setMinWidth(60);
		this.getColumnModel().getColumn(5).setWidth(60);
		this.getColumnModel().getColumn(5).setMaxWidth(60);
		this.getColumnModel().getColumn(6).setMinWidth(120);
		this.getColumnModel().getColumn(7).setMinWidth(120);
	}
}
