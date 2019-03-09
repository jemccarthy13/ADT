package outputframe;

import java.awt.Component;

import rundown.gui.RundownFrame;
import swing.BaseFrame;
import swing.SingletonHolder;

/**
 * 
 */
public class OutputFrame extends BaseFrame {

	/**
	 * Serialization information
	 */
	private static final long serialVersionUID = 613810609244724014L;

	@Override
	public void create() {
		this.content = (OutputPanel) SingletonHolder.getInstanceOf(OutputPanel.class);
		this.setTitle("Output");
		setSize(600, 700);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo((Component) SingletonHolder.getInstanceOf(RundownFrame.class));
		this.add(this.content);
		this.setVisible(true);
	}

	/**
	 * Put the given string in the output area
	 * 
	 * @param msg - output message to write to out area
	 */
	public void setOutput(String msg) {
		((OutputPanel) SingletonHolder.getInstanceOf(OutputPanel.class)).setOutput(msg);
	}

}
