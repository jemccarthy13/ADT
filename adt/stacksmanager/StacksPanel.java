package stacksmanager;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import structures.Asset;
import structures.RundownAssets;
import swing.ActionButton;
import swing.BasePanel;
import utilities.DebugUtility;
import utilities.Fonts;

/**
 * The Panel that organizes the stack output
 */
public class StacksPanel extends BasePanel {

	/**
	 * Stack output area
	 */
	JTextArea outArea = new JTextArea();

	/**
	 * Check box for whether or not output should be in FTM format
	 */
	JCheckBox ftmFormat = new JCheckBox();

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
			DebugUtility.error(StacksButtonListener.class, "Unimplemented - TODO - generate stack");
			DebugUtility.trace(StacksButtonListener.class, formatRundown(-1));
		}

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
	 * Format the rundown to pretty up the output
	 * 
	 * TODO - fix so that rundown is printed in order of highest -> lowest
	 * 
	 * @param lenAirspace - maximum length of airspaces
	 * @return a String formatted with the rundown
	 */
	String formatRundown(int lenAirspace) {
		RundownAssets.getInstance();

		// find the max airspace length, or use the parameter
		int asLen = lenAirspace == -1 ? getAirspaceLen() : lenAirspace;

		String retVal = "***** START STACK *****\n";

		for (Asset ast : RundownAssets.getInstance()) {
			String m2 = ast.getMode2().equals("") ? "____" : ast.getMode2();

			int numScores = asLen - ast.getAirspace().length();
			StringBuffer outputBuffer = new StringBuffer(numScores);
			for (int i = 0; i < numScores; i++) {
				outputBuffer.append("_");
			}
			String uScores = outputBuffer.toString();

			String airspace = "";
			if (ast.getAirspace().length() > asLen) {
				airspace = ast.getAirspace().substring(1, 9) + "...";
			} else {
				airspace = ast.getAirspace() + uScores;
			}

			retVal += ast.getVCS() + " " + m2 + "___" + airspace + "__ " + ast.getAltRangeStr() + ast.getTxAlt() + "\n";
		}

		retVal += "****** END STACK ******";
		if (this.ftmFormat.isSelected()) {
			retVal = retVal.replaceAll("_", "");
			retVal = retVal.replaceAll("\n", "//\n");
			retVal += "//";
		}
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

		JRadioButton bma = new JRadioButton("BMA");
		JRadioButton byGrids = new JRadioButton("By Grids");
		JRadioButton byAirspace = new JRadioButton("By Airspace");
		this.ftmFormat = new JCheckBox("FTM Format");
		JTextArea inGrids = new JTextArea();

		gridSubPanel1.add(bma);
		gridSubPanel1.add(this.ftmFormat);
		gridSubPanel1.add(byAirspace);
		gridSubPanel1.add(new JLabel(""));
		gridSubPanel1.add(byGrids);
		gridSubPanel1.add(new JLabel(""));
		gridSubPanel1.add(inGrids);

		ButtonGroup group = new ButtonGroup();
		group.add(bma);
		group.add(byAirspace);
		group.add(byGrids);

		bma.setFont(Fonts.serif);
		byAirspace.setFont(Fonts.serif);
		byGrids.setFont(Fonts.serif);
		inGrids.setFont(Fonts.serif);
		this.ftmFormat.setFont(Fonts.serif);

		subPanel1.add(gridSubPanel1, BorderLayout.NORTH);

		JPanel gridSubPanel2 = new JPanel();
		gridSubPanel2.setLayout(new GridLayout(1, 3, 10, 10));
		gridSubPanel2.add(new JLabel(""));

		JPanel gridSubSubPanel = new JPanel();
		gridSubSubPanel.setLayout(new GridLayout(3, 1, 20, 10));
		gridSubSubPanel.add(new JLabel(""));
		gridSubSubPanel.add(new ActionButton("Generate", new StacksButtonListener()));
		gridSubSubPanel.add(new JLabel(""));

		gridSubPanel2.add(gridSubSubPanel);

		gridSubPanel2.add(new JLabel(""));
		subPanel1.add(gridSubPanel2, BorderLayout.CENTER);

		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.add(subPanel1, BorderLayout.NORTH);

		this.outArea = new JTextArea();
		JScrollPane pane = new JScrollPane();
		pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setViewportView(this.outArea);
		this.outArea.setFont(Fonts.serif);
		this.add(pane, BorderLayout.CENTER);
	}

}