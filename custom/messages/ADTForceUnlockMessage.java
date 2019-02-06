package messages;

import rundown.gui.RundownFrame;
import rundown.model.RundownTable;
import structures.LockedCells;

/**
 * Force unlock all cells on the rundown
 */
public class ADTForceUnlockMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = 9217719855514010072L;

	@Override
	public void process() {
		RundownTable.getInstance().editCellAt(-1, -1);
		LockedCells.getLockedCells().clear();
		for (Integer user : LockedCells.getLockedCells().keySet()) {
			RundownFrame.getClient().sendMessage(new ADTUnlockUserMessage(RundownFrame.getClient().getSessionID()));
			LockedCells.unlockUser(user);
		}
	}

	@Override
	public String getCommand() {
		return "forced";
	}

}
