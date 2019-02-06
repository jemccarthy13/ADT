package messages;

import structures.LockedCells;

/**
 * Command to unlock all cells
 */
public class ADTUnlockUserMessage extends ADTBaseMessage {

	/** For serialization */
	private static final long serialVersionUID = 5305041953255525094L;

	/**
	 * @param sessionID
	 */
	public ADTUnlockUserMessage(int sessionID) {
		super(sessionID);
	}

	@Override
	public void process() {
		LockedCells.unlockUser(this.getSender());
	}

	@Override
	public String getCommand() {
		return "unlock user";
	}

}
