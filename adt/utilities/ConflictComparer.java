package utilities;

import structures.Asset;
import structures.RundownAssets;

/**
 * Helper class that compares for conflicts between assets
 */
public class ConflictComparer {

	/**
	 * Check conflicts between this row's asset and another
	 * 
	 * @param row - the row of the current asset
	 */
	public static void checkConflicts(int row) {
		boolean hasConflict = false;
		// so get the 'current' - changed asset
		Asset first = RundownAssets.getInstance().get(row);
		int count = 0;

		// loop through all other assets
		for (Asset other : RundownAssets.getInstance()) {
			// if not itself
			if (row != count) {
				// check for new overlaps
				if (first.conflictsWith(other).size() > 0) {
					DebugUtility.trace(ConflictComparer.class,
							"New conflict between " + first.getAirspace() + " and " + other.getAirspace());
					// flag it for me and the other guy
					hasConflict = true;
					other.setInConflict(true);
				}
			}
			// next
			count++;
		}

		// if I have a conflict, trigger me
		if (hasConflict) {
			first.setInConflict(true);
		} else {
			first.setInConflict(false);
		}

		int cnt1 = 0;

		// now loop through every asset that has a conflict, including the new one
		// to check to see if any conflicts have been resolved
		for (Asset fst : RundownAssets.getInstance()) {
			if (fst.isInConflict() == true) {
				hasConflict = false;
				count = 0;
				for (Asset other : RundownAssets.getInstance()) {
					if (cnt1 != count) {
						if (fst.conflictsWith(other).size() > 0) {
							DebugUtility.trace(ConflictComparer.class,
									"Existing conflict between " + fst.getAirspace() + " and " + other.getAirspace());
							hasConflict = true;
						}
					}
					count++;
				}
				if (hasConflict) {
					fst.setInConflict(true);
				} else {
					fst.setInConflict(false);
				}
			}
			cnt1++;
		}
	}
}
