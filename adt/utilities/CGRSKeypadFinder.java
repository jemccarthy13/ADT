package utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

import structures.AirspaceList;
import structures.DIR;
import structures.KeypadFinder;
import swing.SingletonHolder;

/**
 * A utility class to help get neighboring keypads
 */
public class CGRSKeypadFinder extends KeypadFinder {

	/**
	 * From a starting killbox, travel in a direction direct to find keypads
	 */
	@Override
	public HashSet<String> findKeypads(DIR direct, String killbox, String[] keypads) {
		Matcher m = Patterns.killboxPattern.matcher(killbox);
		m.find();

		int number = Integer.parseInt(m.group(1));
		String letters = m.group(2);

		HashSet<String> strArray = new HashSet<String>();

		if (letters.length() == 1) {
			letters = ((char) ((letters).substring(0, 1).charAt(0) + direct.getColumnOffset())) + "";
		} else {
			letters = letters.substring(0, 1)
					+ ((char) ((letters).substring(1, 2).charAt(0) + direct.getColumnOffset()));
		}
		// here is the translation - directional ROW/COLUMN offset math
		// number = row +/- rowOffset
		// letters = substring (1st letter) + substring(2nd letter) + column offset
		// num = keypad
		for (String num : keypads) {
			strArray.add((number + direct.getRowOffset()) + letters + num);
		}

		return strArray;
	}

	/**
	 * Get a set of keypads
	 * 
	 * @param killbox
	 * @return keypads from a killbox
	 */
	@Override
	public HashSet<String> keypadsFromKillbox(String killbox) {

		DebugUtility.error(GARSKeypadFinder.class, "Using cgrs");
		HashSet<String> keypads = new HashSet<String>();
		String row = "";
		String col = "";
		String keypad = "";
		String modifier = "";

		Matcher m = Patterns.airspacePattern.matcher(killbox);
		boolean success = m.find();

		if (m.groupCount() < 4 || !success) {
			DebugUtility.error(this.getClass(), "BAD CGRS grid:  " + killbox);
			DebugUtility.error(this.getClass(), "Attempting named airspaces expansion...");
		} else {
			row = m.group(1);
			col = m.group(2);
			keypad = m.group(3);
			modifier = m.group(4);
		}

		String keypadNums;
		String keypadRangeBottom;
		String keypadRangeTop;

		if (row.equals("") && !killbox.trim().equals("")) {
			String expansion = ((AirspaceList) SingletonHolder.getInstanceOf(AirspaceList.class)).expand(killbox);
			// this call will also recursively expand airspaces and airspace killboxes
			keypads.addAll(getKeypads(expansion));
		} else if (modifier == null) {
			if (keypad.equals("")) {
				keypad = "1-9";
			}

			Matcher m3 = Patterns.rangePattern.matcher(keypad);
			if (m3.find()) {
				keypadRangeBottom = m3.group(1);
				keypadRangeTop = m3.group(2);
				for (int x = Integer.parseInt(keypadRangeBottom); x <= Integer.parseInt(keypadRangeTop); x++) {
					keypads.add(row + col + x);
				}
			}

			Matcher m4 = Patterns.numPattern.matcher(keypad);
			if (m4.find()) {
				keypadNums = m4.group(1);
				for (int x = 0; x <= keypadNums.length() - 1; x++) {
					keypads.add(row + col + keypadNums.substring(x, x + 1));
				}
			}
		}

		else if (modifier.equals("+")) {
			switch (Integer.parseInt(keypad)) {
			case 1:
				keypads.addAll(findKeypads(DIR.LT, killbox, new String[] { "3", "6" }));
				keypads.addAll(findKeypads(DIR.UPLT, killbox, new String[] { "9" }));
				keypads.addAll(findKeypads(DIR.UP, killbox, new String[] { "7", "8" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "1", "2", "4", "5" }));
				break;
			case 2:
				keypads.addAll(findKeypads(DIR.UP, killbox, new String[] { "7", "8", "9" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "1", "2", "3", "4", "5", "6" }));
				break;
			case 3:
				keypads.addAll(findKeypads(DIR.UP, killbox, new String[] { "8", "9" }));
				keypads.addAll(findKeypads(DIR.UPRT, killbox, new String[] { "7" }));
				keypads.addAll(findKeypads(DIR.RT, killbox, new String[] { "1", "4" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "2", "3", "5", "6" }));
				break;
			case 4:
				keypads.addAll(findKeypads(DIR.LT, killbox, new String[] { "3", "6", "9" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "1", "2", "4", "5", "7", "8" }));
				break;
			case 5:
				keypads.addAll(
						findKeypads(DIR.SELF, killbox, new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
				break;
			case 6:
				keypads.addAll(findKeypads(DIR.RT, killbox, new String[] { "1", "4", "7" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "2", "3", "5", "6", "8", "9" }));
				break;
			case 7:
				keypads.addAll(findKeypads(DIR.LT, killbox, new String[] { "6", "9" }));
				keypads.addAll(findKeypads(DIR.DOWNLT, killbox, new String[] { "3" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "4", "5", "7", "8" }));
				keypads.addAll(findKeypads(DIR.DOWN, killbox, new String[] { "1", "2" }));
				break;
			case 8:
				keypads.addAll(findKeypads(DIR.DOWN, killbox, new String[] { "1", "2", "3" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "4", "5", "6", "7", "8", "9" }));
				break;
			case 9:
				keypads.addAll(findKeypads(DIR.RT, killbox, new String[] { "4", "7" }));
				keypads.addAll(findKeypads(DIR.DOWNRT, killbox, new String[] { "1" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "5", "6", "8", "9" }));
				keypads.addAll(findKeypads(DIR.DOWN, killbox, new String[] { "2", "3" }));
				break;
			default:
				break;
			}
		}

		else if (modifier.toUpperCase().equals("+4C")) {
			switch (Integer.parseInt(keypad)) {
			case 1:
				keypads.addAll(findKeypads(DIR.LT, killbox, new String[] { "3" }));
				keypads.addAll(findKeypads(DIR.UPLT, killbox, new String[] { "9" }));
				keypads.addAll(findKeypads(DIR.UP, killbox, new String[] { "7" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "1" }));
				break;
			case 3:
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "3" }));
				keypads.addAll(findKeypads(DIR.UP, killbox, new String[] { "9" }));
				keypads.addAll(findKeypads(DIR.UPRT, killbox, new String[] { "7" }));
				keypads.addAll(findKeypads(DIR.RT, killbox, new String[] { "1" }));
				break;
			case 7:
				keypads.addAll(findKeypads(DIR.LT, killbox, new String[] { "9" }));
				keypads.addAll(findKeypads(DIR.DOWNLT, killbox, new String[] { "3" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "7" }));
				keypads.addAll(findKeypads(DIR.DOWN, killbox, new String[] { "1" }));
				break;
			case 9:
				keypads.addAll(findKeypads(DIR.DOWN, killbox, new String[] { "3" }));
				keypads.addAll(findKeypads(DIR.SELF, killbox, new String[] { "9" }));
				keypads.addAll(findKeypads(DIR.RT, killbox, new String[] { "7" }));
				keypads.addAll(findKeypads(DIR.DOWNRT, killbox, new String[] { "1" }));
				break;
			default:
				break;
			}
		}
		return keypads;
	}

	@Override
	public ArrayList<String> getKeypadsInCircle(ArrayList<String> coords, String centerPoint, long radius,
			String origin) {
		DebugUtility.error(CGRSKeypadFinder.class, "Not implemented",
				new UnsupportedOperationException("not implemented"));
		return null;
	}

}
