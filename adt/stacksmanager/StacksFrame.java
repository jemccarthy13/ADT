package stacksmanager;

import java.awt.Component;

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

	@Override
	public void create() {
		this.content = (Component) SingletonHolder.getInstanceOf(StacksPanel.class);
		this.setTitle("Stacks");
		setSize(800, 900);
		super.create();
	}
}
