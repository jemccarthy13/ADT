package rundown.gui;

import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import outputframe.OutputFrame;
import rundown.model.RundownTable;
import structures.Airspace;
import structures.AirspaceList;
import structures.Asset;
import structures.ListOfAsset;
import structures.PreviousAssets;
import structures.RundownAssets;
import swing.Singleton;
import swing.SingletonHolder;

/**
 * A class that holds the Rundown Context Menu
 */
public class RundownContextMenu extends JPopupMenu implements Singleton {
	/** Serialization information */
	private static final long serialVersionUID = -4977943761212186294L;

	/** Show conflict context menu option */
	final JMenuItem showConflict = new JMenuItem("Show Conflict");

	/** Copy approval context menu option */
	final JMenuItem copyApp = new JMenuItem("Copy Approval");

	/** Resolve conflict context menu option */
	final JMenuItem resolveConflict = new JMenuItem("Resolve Conflict");

	/** Resolve all conflicts context menu option */
	final JMenuItem resolveAll = new JMenuItem("Resolve All Conflicts");

	/** Show airspace overlap context menu option */
	final JMenuItem showAirspaces = new JMenuItem("Show Airspace Overlap");

	/** Remove from rundown context menu option */
	final JMenuItem deleteItem = new JMenuItem("No longer in the BMA");

	/**
	 * Default constructor
	 */
	public RundownContextMenu() {
		this.create();
	}

	private ActionListener menuListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// because the table can be sorted, we need to get the row in terms of the model
			RundownTable table = RundownTable.getInstance();
			table.getSelectedRow();
			int row = table.convertRowIndexToModel(table.getSelectedRow());

			// now we retrieve the selected asset because this is a context menu in the
			// context of the selected asset
			Asset selected = RundownAssets.getInstance().get(row);

			if (e.getSource().equals(RundownContextMenu.this.deleteItem)) {
				((PreviousAssets) SingletonHolder.getInstanceOf(PreviousAssets.class)).add(selected);
				System.out.println("Previous count now: "
						+ ((PreviousAssets) SingletonHolder.getInstanceOf(PreviousAssets.class)).size());
				RundownAssets.getInstance().remove(row);
				((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
			} else if (e.getSource().equals(RundownContextMenu.this.copyApp)) {
				StringSelection stringSelection = new StringSelection(selected.getApproval());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);
			} else if (e.getSource().equals(RundownContextMenu.this.resolveConflict)) {
				selected.setInConflict(false);
				((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
			} else if (e.getSource().equals(RundownContextMenu.this.resolveAll)) {
				for (Asset asst : RundownAssets.getInstance()) {
					asst.setInConflict(false);
				}
				((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
			} else if (e.getSource().equals(RundownContextMenu.this.showConflict)
					|| e.getSource().equals(RundownContextMenu.this.showAirspaces)) {
				OutputFrame frame = (OutputFrame) SingletonHolder.getInstanceOf(OutputFrame.class);
				String builder = "";

				ListOfAsset compareList = (ListOfAsset) SingletonHolder.getInstanceOf(AirspaceList.class);
				if (e.getSource().equals(RundownContextMenu.this.showConflict)) {
					compareList = RundownAssets.getInstance();
				}
				for (Asset a : compareList) {
					if (!a.equals(selected) && (e.getSource().equals(RundownContextMenu.this.showConflict)
							|| (e.getSource().equals(RundownContextMenu.this.showAirspaces)
									&& ((Airspace) a).isAddToRundown()))) {
						if (selected.conflictsWith(a).size() > 0) {
							builder += "-------------------------\n";

							if (e.getSource().equals(RundownContextMenu.this.showConflict)) {
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
				frame.setOutput(builder);
				frame.setTitle("Conflict");
				frame.setVisible(true);
			}
		}

	};

	@Override
	public void create() {
		this.showAirspaces.addActionListener(this.menuListener);
		this.showConflict.addActionListener(this.menuListener);
		this.deleteItem.addActionListener(this.menuListener);
		this.copyApp.addActionListener(this.menuListener);
		this.resolveConflict.addActionListener(this.menuListener);
		this.resolveAll.addActionListener(this.menuListener);

		this.add(this.showConflict);
		this.add(this.resolveConflict);
		this.add(this.resolveAll);
		this.addSeparator();
		this.add(this.showAirspaces);
		this.addSeparator();
		this.add(this.copyApp);
		this.addSeparator();
		this.add(this.deleteItem);

		this.addPopupMenuListener(new PopupMenuListener() {

			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						RundownTable table = RundownTable.getInstance();
						int rowAtPoint = table.rowAtPoint(
								SwingUtilities.convertPoint(RundownContextMenu.this, new Point(0, 0), table));
						if (rowAtPoint > -1) {
							table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
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

	}
}
