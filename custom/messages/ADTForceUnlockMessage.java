package messages;

import main.ADTClient;
import rundown.gui.RundownFrame;
import rundown.model.RundownTable;
import structures.LockedCells;
import swing.SingletonHolder;

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
			ADTClient client = ((RundownFrame) SingletonHolder.getInstanceOf(RundownFrame.class)).getClient();

			client.sendMessage(new ADTUnlockUserMessage(client.getSessionID()));
			LockedCells.unlockUser(user);
		}
	}

	@Override
	public String getCommand() {
		return "forced";
	}

}
