package structures;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Holds the list of currently locked cells.
 */
public class LockedCells extends HashMap<Integer, Integer[]> {
	/** Serialization variable */
	private static final long serialVersionUID = -7315738248730715655L;

	private static LockedCells instance = new LockedCells();

	/**
	 * @return the current list of locked cells
	 */
	public static LockedCells getLockedCells() {
		return instance;
	}

	/**
	 * Set a particular cell as locked by the given user
	 * 
	 * @param user   - user who has locked the cell
	 * @param row    - row of the cell
	 * @param column - col of the cell
	 * @param locked
	 */
	public static void setLocked(Integer user, Integer row, Integer column, boolean locked) {
		if (locked) {
			instance.put(user, (Integer[]) Arrays.asList(row, column).toArray());
		} else {
			instance.remove(user);
		}
	}

	/**
	 * Clear all locks of a particular user
	 * 
	 * @param user - the user whose locks should be cleared
	 */
	public static void unlockUser(Integer user) {
		setLocked(user, -1, -1, false);
	}

	/**
	 * Check if a given cell is marked as locked
	 * 
	 * @param row    - row of the cell
	 * @param column - col of the cell
	 * @return true iff a user has the cell locked
	 */
	public static boolean isLocked(int row, int column) {
		boolean locked = false;
		for (Integer user : instance.keySet()) {
			if (instance.get(user)[0] == row && instance.get(user)[1] == column) {
				locked = true;
			}
		}
		return locked;
	}
}
