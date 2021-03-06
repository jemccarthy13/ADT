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
 * The Panel that formats the stack output
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

	/**
	 * The input of Grids for a "By Grids" stack generation
	 */
	private JTextArea inGrids;

	/**
	 * Generated Serialization
	 */
	private static final long serialVersionUID = -495091892968581366L;

	/**
	 * An ActionListener specifically for the stacks "Generate" button
	 */
	private class StacksButtonListener implements ActionListener {

		public StacksButtonListener() {
			// empty constructor so it's available
		}

		/**
		 * When the generate button is pressed, create stack output based on the
		 * selected option and then display the output in the outArea
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			String outText = "";
			if (StacksPanel.this.bma.isSelected()) {
				outText = formatRundown();
			} else if (StacksPanel.this.byAirspace.isSelected()) {
				outText = formatAirspaces();
			} else if (StacksPanel.this.byGrids.isSelected()) {
				outText = formatGrids();
			}
			if (StacksPanel.this.ftmFormat.isSelected()) {
				outText = outText.replaceAll("[/>]", "").replaceAll("\n", "//\n");
				outText += "//";
			}
			StacksPanel.this.outArea.setText(outText);
		}

	}

	/**
	 * Sort the rundown assets based on upper altitude. Asset:compareTo does the
	 * altitude comparison and Collections.sort uses .compareTo
	 * 
	 * @return - a sorted list of assets based on upper altitude
	 */
	private ArrayList<Asset> getSortedAssets() {
		ArrayList<Asset> sorted = new ArrayList<Asset>();
		sorted.addAll(RundownAssets.getInstance());
		Collections.sort(sorted);
		return sorted;
	}

	/**
	 * Based on the user's desired grids (entered in the grids JTextField) calculate
	 * the overlap with that new airspace and display it
	 * 
	 * @return formatted string
	 */
	String formatGrids() {
		// format grids better
		String grids = this.inGrids.getText().replaceAll(",", "");
		this.inGrids.setText(grids.toUpperCase());

		// build the temporary airspace for comparison
		Airspace space = new Airspace();
		space.setAltBlock("000", "999");
		space.setAirspace(grids.toUpperCase());
		space.setName("GRIDS-" + grids);

		// do comparison and generate output
		String retVal = "****** GRIDS " + grids + " ******\r\n";
		for (Asset a : getSortedAssets()) {
			if (space.sharesAirspaceWith(a).size() > 0)
				retVal += formatAsset(a);
		}
		retVal += "******** END " + grids + " ******\r\n";
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

		// go through each of the airspaces, if it conflicts with assets then
		// add to the dictionary of airspace->asset
		for (Asset asst : (AirspaceList) (SingletonHolder.getInstanceOf(AirspaceList.class))) {
			Airspace as = (Airspace) asst;
			for (Asset other : RundownAssets.getInstance()) {
				if (as.conflictsWith(other).size() > 0) {
					if (dict.containsKey(as.getName()) == false) {
						dict.put(as.getName(), new ArrayList<Asset>());
					}
					dict.get(as.getName()).add(other);
				}
			}
		}

		// build the output based on the list of overlaps found
		retVal += "AIRSPACES: \r\n";
		for (String name : dict.keySet()) {
			retVal += "--------- " + name + "--------- \r\n";
			Collections.sort(dict.get(name));
			for (Asset a : dict.get(name)) {
				if (!a.isBlank())
					retVal += formatAsset(a);
			}
			retVal += "--------- ---------\r\n";
		}
		return retVal;
	}

	/**
	 * Format an asset for stack output
	 * 
	 * VCS M2 / / AIRSPACE / FL XXX to FL XXX / TX AT FL YYY
	 * 
	 * @param ast asset to format
	 * @return formatted string IAW asset format
	 */
	String formatAsset(Asset ast) {
		String m2 = ast.getID().getMode2().equals("") ? "     " : ast.getID().getMode2() + " ";
		return "> " + ast.getID().getVCS() + " " + m2 + " / " + ast.getAirspace() + " / " + ast.getAlt().toString()
				+ ast.getTxAlt() + "\r\n";
	}

	/**
	 * Format the rundown to pretty up the output
	 * 
	 * @param lenAirspace - maximum length of airspaces
	 * @return a String formatted with the rundown
	 */
	String formatRundown() {

		String retVal = "> ***** START STACK *****\r\n";

		for (Asset ast : getSortedAssets()) {
			if (!ast.isBlank()) {
				retVal += formatAsset(ast);
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
