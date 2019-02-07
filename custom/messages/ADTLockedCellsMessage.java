package messages;

import structures.LockedCells;
import utilities.DebugUtility;

/**
 * List of locked cells sent on initial connect to the server
 */
public class ADTLockedCellsMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = -5640885616591778178L;

	private int row;
	private int column;

	/**
	 * Constructor
	 * 
	 * @param key
	 * @param r   - row to lock
	 * @param c   - column to lock
	 */
	public ADTLockedCellsMessage(Integer key, int r, int c) {
		super(key);
		this.row = r;
		this.column = c;
	}

	@Override
	public void process() {
		LockedCells.setLocked(this.getSender(), this.row, this.column, true);
	}

	@Override
	public String getCommand() {
		DebugUtility.error(Object.class, "locked cells should not be null: " + this.getSender());
		return "locked," + this.row + "," + this.column;
	}

}
