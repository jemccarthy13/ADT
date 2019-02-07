package messages;

import rundown.gui.RundownFrame;
import swing.BaseFrame;
import swing.SingletonHolder;

/**
 * Message containing client/server ID
 */
public class ADTIdMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = 6141325178121992949L;

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public ADTIdMessage(int id) {
		super(id);
	}

	@Override
	public void process() {
		((BaseFrame) SingletonHolder.getInstanceOf(RundownFrame.class))
				.established(Integer.parseInt(getCommand().split(",")[1]));
	}

	@Override
	public String getCommand() {
		return "id," + this.getSender();
	}

}
