package messages;

import java.util.HashMap;

import structures.LockedCells;

/**
 * List of locked cells sent on initial connect to the server
 */
public class ADTLockedCellsMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = -5640885616591778178L;

	/**
	 * Constructor
	 * 
	 * @param key
	 */
	public ADTLockedCellsMessage(Integer key) {
		super(key);
	}

	@Override
	public void process() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
		HashMap<Integer, Integer[]> lockedCells = LockedCells.getLockedCells();
		return "locked," + lockedCells.get(this.getSender())[0] + "," + lockedCells.get(this.getSender())[1];
	}

}
