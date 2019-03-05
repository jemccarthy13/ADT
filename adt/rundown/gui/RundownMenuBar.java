package rundown.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.RowSorter.SortKey;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import atoLookup.ATOImporter;
import atoLookup.ATOLookupFrame;
import messages.ADTAtoDatMessage;
import messages.ADTForceUnlockMessage;
import rundown.model.RundownTable;
import rundown.model.RundownTableModel;
import structures.ATOAssets;
import structures.KeypadFinder;
import structures.LockedCells;
import structures.RundownAssets;
import swing.SingletonHolder;
import utilities.Configuration;
import utilities.DebugUtility;
import utilities.FileChooser;
import utilities.Output;

/**
 * The custom menu bar for this application.
 */
public class RundownMenuBar extends JMenuBar {

	private static final long serialVersionUID = 473353569286481639L;

	private boolean developer = true;

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
	public JMenuItem doImport = new JMenuItem("ATO Import");
	/** the grid settings menu option */
	public JMenuItem setGrid = new JMenuItem("Grid Settings");
	/** the type manager menu option */
	public JMenuItem typeManager = new JMenuItem("Type Manager");
	/** the zeroize menu option */
	public JMenuItem zeroize = new JMenuItem("Zeroize");
	/** allow selection of a non-standard server */
	public JMenuItem newServer = new JMenuItem("New Server");
	/** the refresh menu option */
	public JMenuItem refresh = new JMenuItem("Refresh");

	/** developer menu */
	private JMenu develop = new JMenu("Developer");
	/** private dialog for testing */
	public JMenuItem testKeypadFinder = new JMenuItem("Test Keypad Finder");
	/** private dialog for testing */
	public JMenuItem testCircleKeypadFinder = new JMenuItem("Test circle keypads");
	/** force everyone out of edits */
	public JMenuItem forceUnlock = new JMenuItem("Force Unlock");

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
		this.settings.add(this.newServer);

		if (this.developer) {
			this.add(this.develop);
			this.develop.add(this.testKeypadFinder);
			this.develop.add(this.forceUnlock);
			this.testKeypadFinder.addActionListener(this.listener);
			this.forceUnlock.addActionListener(this.listener);
			this.testCircleKeypadFinder.addActionListener(this.listener);
			this.develop.add(this.testCircleKeypadFinder);
		}

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
			if (e.getSource().equals(RundownMenuBar.this.newServer)) {
				Configuration.setServerAddress(JOptionPane.showInputDialog(null, "Input new server IP address:"));
				RundownFrame.getClient().newServer();
			} else if (e.getSource().equals(RundownMenuBar.this.forceUnlock)) {
				RundownFrame.getClient().sendMessage(new ADTForceUnlockMessage());
				// RundownFrame.getClient().sendMessage("forced");
				RundownTable.getInstance().editCellAt(-1, -1);
				LockedCells.getInstance().clear();
				for (Integer user : LockedCells.getInstance().keySet()) {
					LockedCells.unlockUser(user);
				}
				((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
			} else if (e.getSource().equals(RundownMenuBar.this.doImport)) {
				FileChooser.selectAndLoadFile("Select an ATO", new FileNameExtensionFilter("ATO", "txt"),
						Configuration.getInstance().getATOLoadLoc(), new ATOImporter());
				RundownFrame.getClient().sendMessage(new ADTAtoDatMessage());
				// RundownFrame.getClient().sendMessage("-1,atodat," +
				// Configuration.getInstance().getATODatFileLoc());
			} else if (e.getSource().equals(RundownMenuBar.this.testKeypadFinder)) {
				JDialog dialog = new JDialog();
				dialog.setSize(600, 600);
				JPanel panel = new JPanel();
				dialog.setLayout(new GridLayout(3, 1, 200, 50));
				panel.setLayout(new BorderLayout(200, 50));
				final JTextArea keypadbox = new JTextArea();
				panel.add(keypadbox, BorderLayout.NORTH);
				final JTextArea outputbox = new JTextArea();
				panel.add(outputbox, BorderLayout.CENTER);
				outputbox.setWrapStyleWord(true);
				JButton submit = new JButton("Submit");
				final KeypadFinder finder = Configuration.getInstance().getKeypadFinder();
				submit.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						outputbox.setLineWrap(true);
						HashSet<String> result = finder.getKeypads(keypadbox.getText());
						outputbox.setText(result.toString() + "\r\n" + result.size());
					}

				});
				dialog.add(submit, BorderLayout.SOUTH);
				dialog.add(panel);
				dialog.setVisible(true);
			} else if (e.getSource().equals(RundownMenuBar.this.testCircleKeypadFinder)) {
				JDialog dialog = new JDialog();
				dialog.setSize(600, 600);
				JPanel panel = new JPanel();
				dialog.setLayout(new GridLayout(3, 1, 200, 50));
				panel.setLayout(new BorderLayout(200, 50));
				final JTextArea keypadbox = new JTextArea();
				panel.add(keypadbox, BorderLayout.NORTH);
				final JTextArea outputbox = new JTextArea();
				panel.add(outputbox, BorderLayout.CENTER);
				outputbox.setWrapStyleWord(true);
				JButton submit = new JButton("Submit");
				final KeypadFinder finder = Configuration.getInstance().getKeypadFinder();
				submit.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						outputbox.setLineWrap(true);
						HashSet<String> result = finder.getKillboxFromCircle(keypadbox.getText(), Double.valueOf(10.0));
						outputbox.setText(result.toString() + "\r\n" + result.size());
					}

				});
				dialog.add(submit, BorderLayout.SOUTH);
				dialog.add(panel);
				dialog.setVisible(true);
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
				((Component) SingletonHolder.getInstanceOf(ATOLookupFrame.class)).repaint();
				((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();
			} else if (e.getSource().equals(RundownMenuBar.this.refresh)) {
				DebugUtility.trace(RundownMenuBar.class, "Refresh option pressed.");

				List<? extends SortKey> keys = RundownTable.getInstance().getRowSorter().getSortKeys();
				RundownTable.getInstance().setRowSorter(null);
				((Component) SingletonHolder.getInstanceOf(RundownFrame.class)).repaint();

				TableRowSorter<TableModel> sorter;
				sorter = new TableRowSorter<TableModel>(
						(TableModel) SingletonHolder.getInstanceOf(RundownTableModel.class));
				sorter.setSortKeys(keys);
				sorter.setRowFilter(null);
				RundownTable.getInstance().setRowSorter(sorter);
			}
		}

	}

}
