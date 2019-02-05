package stacksmanager;

import javax.swing.JPanel;

import rundown.gui.RundownFrame;
import swing.BaseFrame;
import swing.GUI;

/**
 * A stacks frame
 */
public class StacksFrame extends BaseFrame {

	/**
	 * Generated serialization information
	 */
	private static final long serialVersionUID = -1604266565727670L;
	private JPanel content;

	@Override
	public void create() {
		this.content = GUI.PANELS.getInstanceOf(StacksPanel.class);
		this.setTitle("Stacks");
		setSize(800, 900);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(GUI.FRAMES.getInstanceOf(RundownFrame.class));
		this.add(this.content);
	}
}
