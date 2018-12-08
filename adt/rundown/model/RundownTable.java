package rundown.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import rundown.gui.RundownCellListener;
import structures.LockedCells;
import swing.GUI;
import swing.MyTableCellEditor;
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
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static RundownTable getInstance() {
		return instance;
	}

	private RundownTable() {

		// set the data
		this.setModel(GUI.MODELS.getInstanceOf(RundownTableModel.class));
		this.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 8107150085372915277L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				RundownTable.getInstance().getModel();
				if (LockedCells.isLocked(row, column)) {
					c.setBackground(Color.gray.darker());
				} else if (!isSelected) {
					c.setBackground(RundownTable.getInstance().getBackground());
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

		this.setFont(Fonts.serif);

		// column header click events
		this.setAutoCreateRowSorter(true);

		this.setRowHeight(25 + 10);
		this.setPreferredScrollableViewportSize(new Dimension(523, 233));

		Configuration.setCompact(3);

		for (int x = 0; x < getModel().getColumnCount(); x++) {
			getColumnModel().getColumn(x).setCellEditor(new MyTableCellEditor() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -5861885526072691837L;

				@Override
				public Object getCellEditorValue() {
					return ((JTextField) this.component).getText();
				}
			});
		}
		this.addPropertyChangeListener(new RundownCellListener());
	}

	/**
	 * Resize the rundown columns based on compact mode or not
	 */
	public void resizeColumns() {
		DebugUtility.trace(RundownTable.class, "Resizing...");
		int[] minWidths = { 60, 60, 140, 60, 60, 90, 60, 60, 60 };
		int[] widths = { 60, 60, 140, 60, 60, 60, 100, 100, 100 };
		int[] maxWidths = { 60, 60, 400, 60, 60, 60, 100, 100, 100 };

		// size the columns
		/** TODO - reduce the amount of code? */
		TableColumnModel cModel = this.getColumnModel();
		int columnCount = GUI.MODELS.getInstanceOf(RundownTableModel.class).getColumnCount();
		DebugUtility.trace(RundownTable.class, "Columns: " + columnCount);
		for (int x = 0; x < columnCount; x++) {
			cModel.getColumn(x).setMinWidth(minWidths[x]);
			cModel.getColumn(x).setWidth(widths[x]);
			cModel.getColumn(x).setMaxWidth(maxWidths[x]);
			if (Configuration.getCompact() != 0 && x > 5) {
				cModel.getColumn(x).setMinWidth(5);
				cModel.getColumn(x).setWidth(5);
				cModel.getColumn(x).setMaxWidth(5);
			}
		}
	}
}
