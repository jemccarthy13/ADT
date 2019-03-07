package stacksmanager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import structures.Airspace;
import structures.AirspaceList;
import structures.Asset;
import structures.RundownAssets;
import swing.ActionButton;
import swing.BasePanel;
import swing.SingletonHolder;
import utilities.Fonts;

/**
 * The Panel that organizes the stack output
 */
public class StacksPanel extends BasePanel {

	/**
	 * Stack output area
	 */
	JTextArea outArea;

	/**
	 * Viewing area for the output area
	 */
	private JScrollPane pane;

	/**
	 * Check box for whether or not output should be in FTM format
	 */
	JCheckBox ftmFormat;

	/**
	 * Button to generate stack output
	 */
	private ActionButton generateBtn;

	/**
	 * Generate a BMA rundown
	 */
	JRadioButton bma;

	/**
	 * Generate a rundown by certain grids
	 */
	JRadioButton byGrids;

	/**
	 * Generate a rundown by airspaces in the AS Manager
	 */
	JRadioButton byAirspace;

	private JTextArea inGrids;

	/**
	 * Generated Serialization
	 */
	private static final long serialVersionUID = -495091892968581366L;

	private class StacksButtonListener implements ActionListener {

		public StacksButtonListener() {
			// empty constructor so it's available
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String outText = "";
			if (StacksPanel.this.bma.isSelected()) {
				outText = formatRundown(-1);
			} else if (StacksPanel.this.byAirspace.isSelected()) {
				outText = formatAirspaces();
			} else if (StacksPanel.this.byGrids.isSelected()) {
				outText = formatGrids();
			}
			if (StacksPanel.this.ftmFormat.isSelected()) {
				outText = outText.replaceAll("/", "");
				outText = outText.replaceAll("\n", "//\n");
				outText = outText.replaceAll(">", "");
				outText += "//";
			}
			StacksPanel.this.outArea.setText(outText);
		}

	}

	/**
	 * Sort the rundown assets
	 * 
	 * @return - a sorted list of assets based on upper altitude
	 */
	private ArrayList<Asset> getSortedAssets() {
		ArrayList<Asset> sorted = new ArrayList<Asset>();
		sorted.addAll(RundownAssets.getInstance());
		Collections.sort(sorted);
		return sorted;
	}

	private int getAirspaceLen() {
		int max = 12;

		for (Asset ast : RundownAssets.getInstance()) {
			if (ast.getAirspace().length() > max) {
				max = ast.getAirspace().length();
			}
		}

		return max;
	}

	/**
	 * Based on the user's desired grids (entered in the grids JTextField) calculate
	 * the overlap with that new airspace and display it
	 * 
	 * @return formatted string
	 */
	String formatGrids() {
		String grids = this.inGrids.getText().replaceAll(",", "");
		this.inGrids.setText(this.inGrids.getText().toUpperCase());
		Airspace space = new Airspace();
		space.setAltBlock("000", "999");
		space.setAirspace(grids.toUpperCase());
		space.setName("GRIDS");

		int asLen = getAirspaceLen();
		String retVal = "********* GRIDS **********\r\n";

		for (Asset a : getSortedAssets()) {
			if (space.sharesAirspaceWith(a).size() > 0)
				retVal += formatAsset(a, asLen);
		}

		retVal += "**********************";
		return retVal;
	}

	/**
	 * Check each of the airspaces and generate a rundown
	 * 
	 * @return a formatted output text
	 */
	String formatAirspaces() {
		String retVal = "";

		HashMap<String, ArrayList<Asset>> dict = new HashMap<String, ArrayList<Asset>>();

		for (Airspace as : (AirspaceList) (SingletonHolder.getInstanceOf(AirspaceList.class))) {
			for (Asset other : RundownAssets.getInstance()) {
				if (as.conflictsWith(other).size() > 0) {
					if (dict.containsKey(as.getName()) == false) {
						dict.put(as.getName(), new ArrayList<Asset>());
					}
					dict.get(as.getName()).add(other);
				}
			}
		}
		retVal += "AIRSPACES: \r\n";
		for (String name : dict.keySet()) {
			retVal += "--------- " + name + "--------- \r\n";
			Collections.sort(dict.get(name));
			for (Asset a : dict.get(name)) {
				if (!a.isBlank())
					retVal += formatAsset(a, 24);
			}
			retVal += "--------- ---------\r\n";
		}
		return retVal;
	}

	/**
	 * Format an asset for the rundown
	 * 
	 * @param ast         asset to format
	 * @param lenAirspace length of longest airspace approval
	 * @return formatted string
	 */
	String formatAsset(Asset ast, int lenAirspace) {
		int asLen = lenAirspace == -1 ? getAirspaceLen() : lenAirspace;

		String m2 = ast.getID().getMode2().equals("") ? "    " : ast.getID().getMode2();

		m2 = m2 + " ";

		String airspace = "";
		if (ast.getAirspace().length() > asLen) {
			airspace = ast.getAirspace().substring(1, 9) + "...";
		} else {
			airspace = ast.getAirspace();
		}

		return "> " + ast.getID().getVCS() + " " + m2 + " / " + airspace + " / " + ast.getAlt().toString()
				+ ast.getTxAlt() + "\r\n";
	}

	/**
	 * Format the rundown to pretty up the output
	 * 
	 * @param lenAirspace - maximum length of airspaces
	 * @return a String formatted with the rundown
	 */
	String formatRundown(int lenAirspace) {

		String retVal = "> ***** START STACK *****\r\n";

		for (Asset ast : getSortedAssets()) {
			if (!ast.isBlank()) {
				retVal += formatAsset(ast, lenAirspace);
			}
		}

		retVal += "> ****** END STACK ******";

		return retVal;
	}

	@Override
	public void create() {
		JPanel subPanel1 = new JPanel();

		this.setFont(Fonts.serif);
		subPanel1.setLayout(new BorderLayout());
		JPanel gridSubPanel1 = new JPanel();
		gridSubPanel1.setLayout(new GridLayout(4, 2, 10, 10));
		gridSubPanel1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		this.bma = new JRadioButton("BMA");
		this.byGrids = new JRadioButton("By Grids");
		this.byAirspace = new JRadioButton("By Airspace");
		this.ftmFormat = new JCheckBox("FTM Format");
		this.inGrids = new JTextArea();

		gridSubPanel1.add(this.bma);
		this.bma.setSelected(true);
		gridSubPanel1.add(this.ftmFormat);
		gridSubPanel1.add(this.byAirspace);
		gridSubPanel1.add(new JLabel(""));
		gridSubPanel1.add(this.byGrids);
		gridSubPanel1.add(new JLabel(""));
		gridSubPanel1.add(this.inGrids);

		ButtonGroup group = new ButtonGroup();
		group.add(this.bma);
		group.add(this.byAirspace);
		group.add(this.byGrids);

		this.bma.setFont(Fonts.serif);
		this.byAirspace.setFont(Fonts.serif);
		this.byGrids.setFont(Fonts.serif);
		this.inGrids.setFont(Fonts.serif);
		this.ftmFormat.setFont(Fonts.serif);

		subPanel1.add(gridSubPanel1, BorderLayout.NORTH);

		JPanel gridSubPanel2 = new JPanel();
		gridSubPanel2.setLayout(new GridLayout(1, 3, 10, 10));
		gridSubPanel2.add(new JLabel(""));

		JPanel gridSubSubPanel = new JPanel();
		gridSubSubPanel.setLayout(new GridLayout(3, 1, 20, 10));
		gridSubSubPanel.add(new JLabel(""));
		this.generateBtn = new ActionButton("Generate", new StacksButtonListener());
		gridSubSubPanel.add(this.generateBtn);
		gridSubSubPanel.add(new JLabel(""));

		gridSubPanel2.add(gridSubSubPanel);

		gridSubPanel2.add(new JLabel(""));
		subPanel1.add(gridSubPanel2, BorderLayout.CENTER);

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.add(subPanel1, BorderLayout.NORTH);

		this.outArea = new JTextArea();
		this.outArea.setLineWrap(true);
		this.pane = new JScrollPane();
		this.pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.pane.setViewportView(this.outArea);
		this.outArea.setFont(Fonts.serif);
		this.add(this.pane, BorderLayout.CENTER);
	}

}
