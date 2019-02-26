package messages;

import rundown.gui.RundownFrame;
import rundown.model.RundownTableModel;
import swing.SingletonHolder;
import utilities.ConflictComparer;

/**
 * A message containing an update to the rundown
 */
public class ADTUpdateMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = -7358691697935281626L;

	private int row;
	private int column;
	private String value;

	/**
	 * Constructor
	 * 
	 * @param r
	 * @param c
	 * @param val
	 */
	public ADTUpdateMessage(int r, int c, String val) {
		this.row = r;
		this.column = c;
		this.value = val;
	}

	@Override
	public void process() {
		((RundownTableModel) SingletonHolder.getInstanceOf(RundownTableModel.class)).setValueAt(this.value, this.row,
				this.column, false, false);
		ConflictComparer.checkConflicts(this.row);
		RundownFrame.refresh();
	}

	@Override
	public String getCommand() {
		return "set," + this.value + "," + this.row + "," + this.column;
	}
}
