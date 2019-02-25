package outputframe;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import swing.BasePanel;
import utilities.Fonts;

/**
 * Output Panel for generic output
 */
public class OutputPanel extends BasePanel {

	/**
	 * Serialization information
	 */
	private static final long serialVersionUID = -8072108318814265759L;

	private JTextArea outArea;
	/**
	 * Viewing area for the output area
	 */
	private JScrollPane pane;

	@Override
	public void create() {
		this.setFont(Fonts.serif);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.outArea = new JTextArea();
		this.outArea.setLineWrap(true);
		this.pane = new JScrollPane();
		this.pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.pane.setViewportView(this.outArea);
		this.outArea.setFont(Fonts.serif);
		this.add(this.pane, BorderLayout.CENTER);
	}

	/**
	 * Write the desired output to the out area
	 * 
	 * @param msg
	 */
	public void setOutput(String msg) {
		this.outArea.setText(msg);
	}

}
