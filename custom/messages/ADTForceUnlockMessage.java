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
		LockedCells.getInstance().clear();
		for (Integer user : LockedCells.getInstance().keySet()) {
			RundownFrame.getClient().sendMessage(new ADTUnlockUserMessage(RundownFrame.getClient().getSessionID()));
			LockedCells.unlockUser(user);
		}
	}

	@Override
	public String getCommand() {
		return "forced";
	}

}
