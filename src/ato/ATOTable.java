package ato;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.border.LineBorder;

import utilities.Fonts;

/**
 * A rundown JTable to display rundown information
 * 
 * @author John McCarthy
 *
 */
public class ATOTable extends JTable {

	private static final long serialVersionUID = 1267395829859061144L;

	private static ATOTable instance = new ATOTable();

	/**
	 * the previously selected row
	 */
	int prevRow;
	/**
	 * the previously selected column
	 */
	int prevCol;

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOTable getInstance() {
		return instance;
	}

	/**
	 * @param col
	 */
	public static void setPrevCol(int col) {
		instance.prevCol = col;
	}

	/**
	 * @param row
	 */
	public static void setPrevRow(int row) {
		instance.prevRow = row;
	}

	/**
	 * @return previously selected column
	 */
	public static int getPrevCol() {
		return instance.prevCol;
	}

	/**
	 * @return previously selected row
	 */
	public static int getPrevRow() {
		return instance.prevRow;
	}

	private ATOTable() {

		// set the data
		this.setModel(ATOTableModel.getInstance());
		this.setDefaultRenderer(String.class, ATOCellRenderer.getInstance());

		// font
		this.getTableHeader().setFont(Fonts.serifBold);
		this.getTableHeader().setBorder(new LineBorder(Color.BLACK));

		// sizing
		this.setFillsViewportHeight(true);
		this.setBounds(10, 200, 500, 250);

		// reordering and column selection not allowed
		this.setColumnSelectionAllowed(false);
		this.getTableHeader().setReorderingAllowed(false);

		// this.setFont(Fonts.serif);
		this.setFont(new Font("Verdana", 1, 20));
		this.setCellEditor(new ATOTableCellEditor());

		// column header click events
		this.setSurrendersFocusOnKeystroke(true);
		this.setFocusTraversalKeysEnabled(false);

		this.setRowHeight(40 + 10);
		this.setPreferredScrollableViewportSize(new Dimension(523, 233));

		for (int x = 0; x < getModel().getColumnCount(); x++) {
			getColumnModel().getColumn(x).setCellEditor(new ATOTableCellEditor());
		}
	}
}
