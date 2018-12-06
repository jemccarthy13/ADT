package rundown.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

import rundown.gui.RundownCellListener;
import swing.MyTableCellEditor;
import utilities.Fonts;

/**
 * A rundown JTable to display rundown information
 * 
 * @author John McCarthy
 *
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
		this.setModel(RundownTableModel.getInstance());
		this.setDefaultRenderer(String.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 8107150085372915277L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				RundownTable.getInstance().getModel();
				if (RundownTableModel.getInstance().isLocked(row, column)) {
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

		RundownTableModel.getInstance().setCompact(true);

		for (int x = 0; x < getModel().getColumnCount(); x++) {
			getColumnModel().getColumn(x).setCellEditor(new MyTableCellEditor());
		}
		this.addPropertyChangeListener(new RundownCellListener());
	}

	/**
	 * Resize the rundown columns based on compact mode or not
	 */
	public void resizeColumns() {
		// size the columns
		this.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		this.getColumnModel().getColumn(0).setMinWidth(60);
		this.getColumnModel().getColumn(0).setWidth(60);
		this.getColumnModel().getColumn(0).setMaxWidth(60);
		this.getColumnModel().getColumn(1).setMinWidth(60);
		this.getColumnModel().getColumn(1).setWidth(60);
		this.getColumnModel().getColumn(1).setMaxWidth(60);
		this.getColumnModel().getColumn(2).setMinWidth(140);
		this.getColumnModel().getColumn(3).setMinWidth(60);
		this.getColumnModel().getColumn(3).setWidth(60);
		this.getColumnModel().getColumn(3).setMaxWidth(60);
		this.getColumnModel().getColumn(4).setMinWidth(60);
		this.getColumnModel().getColumn(4).setWidth(60);
		this.getColumnModel().getColumn(4).setMaxWidth(60);
		this.getColumnModel().getColumn(5).setMinWidth(90);

		if (RundownTableModel.getInstance().isCompactMode() == false) {
			this.getColumnModel().getColumn(6).setMinWidth(60);
			this.getColumnModel().getColumn(6).setWidth(60);
			this.getColumnModel().getColumn(6).setMaxWidth(60);

			this.getColumnModel().getColumn(7).setMinWidth(60);
			this.getColumnModel().getColumn(7).setWidth(60);
			this.getColumnModel().getColumn(7).setMaxWidth(60);

			this.getColumnModel().getColumn(8).setMinWidth(5);

		} else if (RundownTableModel.getInstance().isCompactMode() == true) {
			this.getColumnModel().getColumn(6).setMinWidth(5);
			this.getColumnModel().getColumn(6).setWidth(5);

			this.getColumnModel().getColumn(7).setMinWidth(5);
			this.getColumnModel().getColumn(7).setWidth(5);

			this.getColumnModel().getColumn(8).setWidth(5);
		}
	}
}
