package asmanager.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import rundown.model.RundownTable;
import swing.Borders;
import utilities.Fonts;

/**
 * An ATO Lookup JTable.
 */
public class AirspaceTable extends JTable {

	private static final long serialVersionUID = 1267395829859061144L;

	/**
	 * The table model for this table
	 */
	AirspaceTableModel model;

	/**
	 * Airspace Table Instance
	 */
	static AirspaceTable instance = new AirspaceTable();

	private class AirspaceColorSelector implements TableCellEditor {

		JButton delegate = new JButton();

		Color savedColor;
		int savedRow;
		int savedColumn;

		public void changeColor(Color c) {
			if (c != null) {
				this.savedColor = c;
				AirspaceTable.this.setValueAt(c, this.savedRow, this.savedColumn);
				RundownTable.getInstance().getCellListener().checkAirspaceHighlights();
			}
			List<SortKey> keys = new ArrayList<SortKey>();
			keys.addAll(AirspaceTable.this.getRowSorter().getSortKeys());
			RundownTable.getInstance().setRowSorter(null);

			AirspaceTableModel m = ((AirspaceTableModel) (AirspaceTable.this.getModel()));
			m.fireTableStructureChanged();

			AirspaceTable.this.setup(keys);

		}

		public AirspaceColorSelector() {
			ActionListener actionListener = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Color c = JColorChooser.showDialog(AirspaceColorSelector.this.delegate, "Color Chooser",
							AirspaceColorSelector.this.savedColor);
					AirspaceColorSelector.this.changeColor(c);
				}

			};
			this.delegate.addActionListener(actionListener);
			this.delegate.setVisible(false);
		}

		@Override
		public Object getCellEditorValue() {
			return this.savedColor;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			this.savedRow = row;
			this.savedColumn = column;
			return this.delegate;
		}

		@Override
		public boolean isCellEditable(EventObject anEvent) {
			return true;
		}

		@Override
		public boolean shouldSelectCell(EventObject anEvent) {
			return true;
		}

		@Override
		public boolean stopCellEditing() {
			return true;
		}

		@Override
		public void cancelCellEditing() {
			// nothing
		}

		@Override
		public void addCellEditorListener(CellEditorListener l) {
			// nothing
		}

		@Override
		public void removeCellEditorListener(CellEditorListener l) {
			// nothing
		}

	}

	private class AirspaceCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 8107150085372915277L;

		public AirspaceCellRenderer() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			if (column == 1) {
				c.setBackground((Color) (table.getValueAt(row, column)));
				c.setForeground((Color) (table.getValueAt(row, column)));
			}
			return c;
		}

	}

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static AirspaceTable getInstance() {
		return instance;
	}

	/**
	 * After table structure change, reaccomplish table setup
	 * 
	 * @param keys
	 */
	public void setup(List<? extends SortKey> keys) {

		this.model = AirspaceTableModel.getInstance();
		this.addPropertyChangeListener(new AirspaceTableCellListener());

		TableRowSorter<TableModel> sorter;
		sorter = new TableRowSorter<TableModel>(this.getModel());
		sorter.setSortKeys(keys);
		sorter.setRowFilter(null);
		this.setRowSorter(sorter);

		this.repaint();

		// this.setCellSelectionEnabled(false);
		this.setDefaultRenderer(Color.class, new AirspaceCellRenderer());

		this.setBorder(Borders.BLACK);
		this.setRowHeight(20 + 10);
		this.setPreferredScrollableViewportSize(new Dimension(523, 233));

		this.setFont(Fonts.serif);

		// set the data
		this.setModel(this.model);
		this.getColumnModel().getColumn(1).setCellEditor(new AirspaceColorSelector());

		// font
		this.getTableHeader().setFont(Fonts.serifBold);

		// sizing

		// reordering and column selection not allowed
		this.setColumnSelectionAllowed(false);
		this.getTableHeader().setReorderingAllowed(false);

		// set initital widths
		this.resizeColumns();

		// column header click events
		this.setAutoCreateRowSorter(true);
	}

	private AirspaceTable() {
		this.setFillsViewportHeight(true);
		this.setBounds(10, 200, 500, 250);

		this.getModel();
		this.setAutoCreateRowSorter(true);

		List<? extends SortKey> keys = this.getRowSorter().getSortKeys();
		this.setRowSorter(null);

		setup(keys);
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
		this.getColumnModel().getColumn(1).setMinWidth(100);
		this.getColumnModel().getColumn(1).setWidth(100);
		this.getColumnModel().getColumn(1).setMaxWidth(100);
		this.getColumnModel().getColumn(2).setMinWidth(120);
		this.getColumnModel().getColumn(2).setWidth(120);
		this.getColumnModel().getColumn(2).setMaxWidth(120);
		this.getColumnModel().getColumn(3).setMinWidth(250);
		this.getColumnModel().getColumn(3).setWidth(250);
		this.getColumnModel().getColumn(3).setMaxWidth(250);
		this.getColumnModel().getColumn(4).setMinWidth(60);
		this.getColumnModel().getColumn(4).setWidth(60);
		this.getColumnModel().getColumn(4).setMaxWidth(60);
		this.getColumnModel().getColumn(5).setMinWidth(60);
	}

}
