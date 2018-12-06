package rundown.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import atoLookup.ATOLookupForm;
import main.RundownFrame;
import rundown.model.RundownTable;
import structures.ATOAssets;
import structures.RundownAssets;
import swing.GUI;
import utilities.Configuration;
import utilities.DebugUtility;
import utilities.Output;

/**
 * The custom menu bar for this application.
 * 
 * @author John McCarthy
 *
 */
public class RundownMenuBar extends JMenuBar {

	private static final long serialVersionUID = 473353569286481639L;

	/**
	 * Singleton implementation
	 * 
	 * @return single instance
	 */
	public static RundownMenuBar getInstance() {
		return instance;
	}

	private static RundownMenuBar instance = new RundownMenuBar();

	private JMenu settings = new JMenu("Settings");
	/** the import ATO menu option */
	JMenuItem doImport = new JMenuItem("ATO Import");
	/** the grid settings menu option */
	JMenuItem setGrid = new JMenuItem("Grid Settings");
	/** the type manager menu option */
	JMenuItem typeManager = new JMenuItem("Type Manager");
	/** the zeroize menu option */
	JMenuItem zeroize = new JMenuItem("Zeroize");
	/** the refresh menu option */
	JMenuItem refresh = new JMenuItem("Refresh");

	private RundownMenuListener listener = new RundownMenuListener();

	private RundownMenuBar() {
		this.add(this.settings);

		this.doImport.setMnemonic(KeyEvent.VK_I);
		this.setGrid.setMnemonic(KeyEvent.VK_G);
		this.typeManager.setMnemonic(KeyEvent.VK_T);
		this.zeroize.setMnemonic(KeyEvent.VK_Z);

		this.settings.add(this.doImport);
		this.settings.add(this.setGrid);
		this.settings.add(this.typeManager);
		this.settings.add(this.zeroize);

		this.refresh.addActionListener(this.listener);
		this.add(this.refresh);

		this.settings.addActionListener(this.listener);
		for (int x = 0; x < this.settings.getItemCount(); x++) {
			this.settings.getItem(x).addActionListener(this.listener);
		}
	}

	private class RundownMenuListener implements ActionListener {

		public RundownMenuListener() {
			// empty constructor
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(RundownMenuBar.this.doImport)) {
				ATOAssets.doImport();
				RundownFrame.getClient().sendMessage("-1,atodat," + Configuration.getInstance().getATODatFileLoc());
			} else if (e.getSource().equals(RundownMenuBar.this.setGrid)) {
				DebugUtility.error(RundownMenuBar.class, "Setting grid not implemented");
			} else if (e.getSource().equals(RundownMenuBar.this.typeManager)) {
				DebugUtility.error(RundownMenuBar.class, "Type manager not implemented");
			} else if (e.getSource().equals(RundownMenuBar.this.zeroize)) {
				if (Output.showConfirmMessage("Confirm zeroize", "Are you sure?")) {
					ATOAssets.zeroize();
					RundownAssets.zeroize();
					RundownAssets.checkAddNew();
				}
				GUI.FRAMES.getInstanceOf(ATOLookupForm.class).repaint();
				RundownFrame.getInstance().repaint();
			} else if (e.getSource().equals(RundownMenuBar.this.refresh)) {
				DebugUtility.debug(RundownMenuBar.class, "Refresh option pressed.");
				RundownTable.getInstance().setRowSorter(null);
				RundownFrame.getInstance().repaint();
				RundownTable.getInstance().setAutoCreateRowSorter(true);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// do nothing
				}
			}
		}

	}

}
