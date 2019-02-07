package stacksmanager;

import java.awt.Component;

import rundown.gui.RundownFrame;
import swing.BaseFrame;
import swing.SingletonHolder;

/**
 * A stacks frame
 */
public class StacksFrame extends BaseFrame {

	/**
	 * Generated serialization information
	 */
	private static final long serialVersionUID = -1604266565727670L;
	private Component content;

	@Override
	public void create() {
		this.content = (Component) SingletonHolder.getInstanceOf(StacksPanel.class);
		this.setTitle("Stacks");
		setSize(800, 900);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo((Component) SingletonHolder.getInstanceOf(RundownFrame.class));
		this.add(this.content);
	}
}
