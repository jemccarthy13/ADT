package rundown.model;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashSet;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import rundown.gui.RundownCellListener;
import rundown.gui.RundownCellRenderer;
import rundown.gui.RundownContextMenu;
import swing.Borders;
import swing.MyTableCellEditor;
import swing.SingletonHolder;
import utilities.Configuration;
import utilities.DebugUtility;
import utilities.Fonts;

/**
 * A rundown JTable to display rundown information
 */
public class RundownTable extends JTable {

	private static final long serialVersionUID = 1267395829859061144L;

	private static RundownTable instance = new RundownTable();

	/**
	 * list of current recognized conflicts
	 */
	public HashSet<String> listOfConflicts = new HashSet<String>();

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static RundownTable getInstance() {
		return instance;
	}

	/**
	 * Will select / delete text on edit
	 * 
	 * @param row    - row to be edited
	 * @param column - column to be edited
	 * @param e      - event of edit
	 * @return true if cell at was successful
	 */
	@Override
	public boolean editCellAt(int row, final int column, EventObject e) {
		boolean result = super.editCellAt(row, column, e);
		final Component editor = getEditorComponent();
		if (editor == null || !(editor instanceof JTextComponent)) {
			return result;
		}
		if (e instanceof MouseEvent) {
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					if (column == 3 || column == 4)
						((JTextComponent) editor).setText("");
				}
			});
		} else {
			((JTextComponent) editor).selectAll();
		}
		return result;
	}

	private RundownTable() {

		// set the data
		this.setModel((TableModel) SingletonHolder.getInstanceOf(RundownTableModel.class));

		this.setDefaultRenderer(String.class,
				(RundownCellRenderer) SingletonHolder.getInstanceOf(RundownCellRenderer.class));

		// font
		this.getTableHeader().setFont(Fonts.serifBold);
		this.getTableHeader().setBorder(Borders.BLACK);

		// sizing
		this.setFillsViewportHeight(true);
		this.setBounds(10, 200, 500, 250);

		// reordering and column selection not allowed
		this.setColumnSelectionAllowed(false);
		this.getTableHeader().setReorderingAllowed(false);

		this.setFont(Fonts.serif);

		// column header click events
		this.setAutoCreateRowSorter(true);

		this.setRowHeight(35);
		this.setPreferredScrollableViewportSize(new Dimension(523, 233));

		Configuration.setCompact(3);

		for (int x = 0; x < getModel().getColumnCount(); x++) {
			getColumnModel().getColumn(x).setCellEditor(new MyTableCellEditor());
		}
		this.addPropertyChangeListener((RundownCellListener) SingletonHolder.getInstanceOf(RundownCellListener.class));

		this.setComponentPopupMenu(new RundownContextMenu());
	}

	/**
	 * Resize the rundown columns based on compact mode or not
	 */
	public void resizeColumns() {
		DebugUtility.trace(RundownTable.class, "Resizing...");
		int[] minWidths = { 60, 60, 140, 60, 60, 90, 60, 60, 100, 0, 0 };
		int[] widths = { 60, 60, 140, 60, 60, 90, 60, 60, 100, 0, 0 };
		int[] maxWidths = { 60, 60, 400, 60, 60, 300, 100, 100, 100, 0, 0 };

		// size the columns
		TableColumnModel cModel = this.getColumnModel();
		int columnCount = ((TableModel) SingletonHolder.getInstanceOf(RundownTableModel.class)).getColumnCount();
		DebugUtility.trace(RundownTable.class, "Columns: " + columnCount);
		for (int x = 0; x < columnCount; x++) {
			cModel.getColumn(x).setMinWidth(minWidths[x]);
			cModel.getColumn(x).setWidth(widths[x]);
			cModel.getColumn(x).setMaxWidth(maxWidths[x]);

			if (x > 5) {
				if (Configuration.getCompact() != 0) {
					cModel.getColumn(x).setMinWidth(5);
					cModel.getColumn(x).setWidth(5);
					cModel.getColumn(x).setMaxWidth(5);
				} else {
					cModel.getColumn(x).setMinWidth(minWidths[x]);
					cModel.getColumn(x).setWidth(widths[x]);
					cModel.getColumn(x).setMaxWidth(minWidths[x]);
				}
			}
			if (x == 2 && Configuration.getCompact() != 0) {
				cModel.getColumn(x).setMaxWidth(5000);
			}
		}
	}
}
