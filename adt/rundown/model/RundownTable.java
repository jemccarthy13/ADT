package rundown.model;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashSet;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;

import outputframe.OutputFrame;
import rundown.gui.RundownCellListener;
import rundown.gui.RundownFrame;
import structures.Airspace;
import structures.AirspaceList;
import structures.Asset;
import structures.ListOf;
import structures.LockedCells;
import structures.PreviousAssets;
import structures.RundownAssets;
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

	private TableCellRenderer renderer;
	/**
	 * list of current recognized conflicts
	 */
	public HashSet<String> listOfConflicts = new HashSet<String>();

	private RundownCellListener listener = new RundownCellListener();

	/**
	 * @return the rundown cell listener (which runs conflict checks)
	 */
	public RundownCellListener getCellListener() {
		return this.listener;
	}

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
		this.renderer = new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 8107150085372915277L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				if (RundownTable.getInstance().getValueAt(row, 9).equals(Boolean.TRUE)
						&& (column == 3 || column == 4)) {
					c.setBackground(Color.RED);
				} else if (LockedCells.isLocked(row, column)) {
					c.setBackground(Color.gray.darker());
				} else if (column == 2) {
					Color colr = (Color) (RundownTable.getInstance().getValueAt(row, 10));
					if (colr != Color.WHITE) {
						c.setBackground(colr);
					}
				} else if (!isSelected) {
					c.setBackground(RundownTable.getInstance().getBackground());
				}
				return c;
			}
		};
		this.setDefaultRenderer(String.class, this.renderer);

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
		this.addPropertyChangeListener(this.listener);

		final JMenuItem showConflict = new JMenuItem("Show Conflict");
		final JMenuItem copyApp = new JMenuItem("Copy Approval");
		final JMenuItem resolveConflict = new JMenuItem("Resolve Conflict");
		final JMenuItem resolveAll = new JMenuItem("Resolve All Conflicts");
		final JMenuItem showAirspaces = new JMenuItem("Show Airspace Overlap");
		final JMenuItem deleteItem = new JMenuItem("No longer in the BMA");

		ActionListener menuListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				RundownTable.this.getSelectedRow();
				int row = RundownTable.this.convertRowIndexToModel(RundownTable.this.getSelectedRow());
				Asset selected = RundownAssets.getInstance().get(row);

				if (e.getSource().equals(deleteItem)) {
					PreviousAssets.getInstance().add(selected);
					System.out.println("Previous count now: " + PreviousAssets.getInstance().size());
					RundownAssets.getInstance().remove(row);
					((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
				} else if (e.getSource().equals(copyApp)) {
					StringSelection stringSelection = new StringSelection(selected.getApproval());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);
				} else if (e.getSource().equals(resolveConflict)) {
					selected.setInConflict(false);
					((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
				} else if (e.getSource().equals(resolveAll)) {
					for (Asset ass : RundownAssets.getInstance()) {
						ass.setInConflict(false);
					}
					((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
				} else if (e.getSource().equals(showConflict) || e.getSource().equals(showAirspaces)) {
					OutputFrame frame = (OutputFrame) SingletonHolder.getInstanceOf(OutputFrame.class);
					String builder = "";

					@SuppressWarnings("unchecked")
					ListOf<Asset> compareList = (ListOf<Asset>) SingletonHolder.getInstanceOf(AirspaceList.class);
					if (e.getSource().equals(showConflict)) {
						compareList = RundownAssets.getInstance();
					}
					for (Asset a : compareList) {
						if (!a.equals(selected) && (e.getSource().equals(showConflict)
								|| (e.getSource().equals(showAirspaces) && ((Airspace) a).isAddToRundown()))) {
							if (selected.conflictsWith(a).size() > 0) {
								builder += "-------------------------\n";

								if (e.getSource().equals(showConflict)) {
									builder += selected.getID().getVCS() + " conflicts with " + a.getID().getVCS()
											+ " in:\n";
								} else {
									builder += selected.getID().getVCS() + " is in " + ((Airspace) a).getName() + "\n";
								}
								for (String conflict : selected.conflictsWith(a)) {
									builder += conflict + "\n";
								}
								builder += "-------------------------\n";
							}
						}
					}
					System.out.println(builder);
					frame.setOutput(builder);
					frame.setTitle("Conflict");
					frame.setVisible(true);
				}
			}

		};

		final JPopupMenu popupMenu = new JPopupMenu();

		showAirspaces.addActionListener(menuListener);
		showConflict.addActionListener(menuListener);
		deleteItem.addActionListener(menuListener);
		copyApp.addActionListener(menuListener);
		resolveConflict.addActionListener(menuListener);
		resolveAll.addActionListener(menuListener);

		popupMenu.add(showConflict);
		popupMenu.add(resolveConflict);
		popupMenu.add(resolveAll);
		popupMenu.addSeparator();
		popupMenu.add(showAirspaces);
		popupMenu.addSeparator();
		popupMenu.add(copyApp);
		popupMenu.addSeparator();
		popupMenu.add(deleteItem);

		popupMenu.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						int rowAtPoint = RundownTable.this
								.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), RundownTable.this));
						if (rowAtPoint > -1) {
							RundownTable.this.setRowSelectionInterval(rowAtPoint, rowAtPoint);
						}
					}
				});
			}

			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				// Auto-generated method stub

			}

			@Override
			public void popupMenuCanceled(PopupMenuEvent e) {
				// Auto-generated method stub

			}
		});

		this.setComponentPopupMenu(popupMenu);
	}

	/**
	 * Resize the rundown columns based on compact mode or not
	 */
	public void resizeColumns() {
		DebugUtility.trace(RundownTable.class, "Resizing...");
		int[] minWidths = { 60, 60, 140, 60, 60, 90, 60, 60, 60, 0, 0 };
		int[] widths = { 60, 60, 140, 60, 60, 60, 100, 100, 100, 0, 0 };
		int[] maxWidths = { 60, 60, 400, 60, 60, 60, 100, 100, 100, 0, 0 };

		// size the columns
		TableColumnModel cModel = this.getColumnModel();
		int columnCount = ((TableModel) SingletonHolder.getInstanceOf(RundownTableModel.class)).getColumnCount();
		DebugUtility.trace(RundownTable.class, "Columns: " + columnCount);
		for (int x = 0; x < columnCount; x++) {
			cModel.getColumn(x).setMinWidth(minWidths[x]);
			cModel.getColumn(x).setWidth(widths[x]);
			cModel.getColumn(x).setMaxWidth(maxWidths[x]);
			cModel.getColumn(x).setCellRenderer(this.renderer);
			if (Configuration.getCompact() != 0 && x > 5) {
				cModel.getColumn(x).setMinWidth(5);
				cModel.getColumn(x).setWidth(5);
				cModel.getColumn(x).setMaxWidth(5);
			}
		}
	}
}
