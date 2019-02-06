package messages;

import rundown.gui.RundownFrame;
import structures.LockedCells;

/**
 * A message containing a locked cell
 */
public class ADTLockedMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = 4709392770850033208L;

	private int row;
	private int column;
	private boolean locked;

	/**
	 * Initialization constructor
	 * 
	 * @param row
	 * @param column
	 * @param lock
	 */
	public ADTLockedMessage(int row, int column, boolean lock) {
		this.setSender(RundownFrame.getClient().getSessionID());
		this.row = row;
		this.column = column;
		this.locked = lock;
	}

	@Override
	public void process() {
		LockedCells.setLocked(getSender(), this.row, this.column, this.locked);
	}

	@Override
	public String getCommand() {
		String command = "";
		if (this.locked)
			command += "locked,";
		else
			command += "unlocked,";

		return command + this.row + "," + this.column;
	}

}
