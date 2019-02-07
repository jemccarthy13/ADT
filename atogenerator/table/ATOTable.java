package table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import swing.SingletonHolder;
import utilities.Fonts;

/**
 * A rundown JTable to display rundown information
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
		this.setModel((TableModel) SingletonHolder.getInstanceOf(ATOTableModel.class));
		this.setDefaultRenderer(JButton.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				return (Component) value;
			}
		});
		this.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

			/**
			 * Serialization info
			 */
			private static final long serialVersionUID = 15484447;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (!isSelected) {
					c.setBackground(getBackground());
				}
				return c;
			}
		});

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

		// column header click events
		this.setSurrendersFocusOnKeystroke(true);
		this.setFocusTraversalKeysEnabled(false);

		this.setRowHeight(40 + 10);
		this.setPreferredScrollableViewportSize(new Dimension(523, 233));

		ATOTableCellEditor editor = new ATOTableCellEditor();
		for (int x = 0; x < getModel().getColumnCount() - 1; x++) {
			getColumnModel().getColumn(x).setCellEditor(editor);
		}
	}
}
