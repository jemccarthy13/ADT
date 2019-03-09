package structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

import swing.SingletonHolder;
import utilities.DebugUtility;
import utilities.Patterns;

/**
 * A utility class to help get neighboring keypads
 */
public class KeypadFinderGARS extends KeypadFinder {

	/**
	 * From a starting killbox, travel in a direction direct to find keypads
	 */
	@Override
	public HashSet<String> findKeypads(DIR direct, String killbox, String[] keypads) {
		Matcher m = Patterns.garsPattern.matcher(killbox);
		m.find();

		int number = Integer.parseInt(m.group(1));
		String letters = m.group(2);

		String quad = m.group(3);

		HashSet<String> strArray = new HashSet<String>();

		if (letters.length() == 1) {
			letters = ((char) ((letters).substring(0, 1).charAt(0) + direct.getRowOffset())) + "";
		} else {
			letters = letters.substring(0, 1) + ((char) ((letters).substring(1, 2).charAt(0) + direct.getRowOffset()));
		}
		// here is the translation - directional ROW/COLUMN offset math
		// number = row +/- rowOffset
		// letters = substring (1st letter) + substring(2nd letter) + column offset
		// num = keypad
		for (String num : keypads) {
			strArray.add((number + direct.getColumnOffset()) + letters + quad + num);
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
		DebugUtility.error(KeypadFinderGARS.class, "Using gars");
		HashSet<String> keypads = new HashSet<String>();
		String row = "";
		String col = "";
		String quad = "";
		String keypad = "";
		String modifier = "";

		Matcher m = Patterns.garsAirspacePattern.matcher(killbox);
		boolean success = m.find();

		if (m.groupCount() < 5 || !success) {
			DebugUtility.error(this.getClass(), "BAD GARS grid:  " + killbox);
			DebugUtility.error(this.getClass(), "Attempting named airspaces expansion...");
		} else {
			row = m.group(1);
			col = m.group(2);
			quad = m.group(3);
			keypad = m.group(4);
			modifier = m.group(5);
			DebugUtility.error(KeypadFinderGARS.class, row + "," + col + "," + quad + "," + keypad + "," + modifier);
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
			Matcher m4 = Patterns.numPattern.matcher(keypad);

			if (quad.equals("")) {
				// quad = "1-4";
				keypads.addAll(keypadsFromKillbox(row + col + "1"));
				keypads.addAll(keypadsFromKillbox(row + col + "2"));
				keypads.addAll(keypadsFromKillbox(row + col + "3"));
				keypads.addAll(keypadsFromKillbox(row + col + "4"));
			} else if (m3.find()) {
				keypadRangeBottom = m3.group(1);
				keypadRangeTop = m3.group(2);
				for (int x = Integer.parseInt(keypadRangeBottom); x <= Integer.parseInt(keypadRangeTop); x++) {
					DebugUtility.error(KeypadFinderGARS.class, "." + row + col + quad + x);
					keypads.add(row + col + quad + x);
				}
			} else if (m4.find() && !quad.equals("")) {
				keypadNums = m4.group(1);

				DebugUtility.error(KeypadFinderGARS.class, keypadNums);
				for (int x = 0; x <= keypadNums.length() - 1; x++) {
					DebugUtility.error(KeypadFinderGARS.class,
							"..." + row + col + quad + keypadNums.substring(x, x + 1));
					keypads.add(row + col + quad + keypadNums.substring(x, x + 1));
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
	public HashSet<String> getKeypadsInCircle(ArrayList<String> coords, String centerPoint, Double radius) {
		DebugUtility.error(KeypadFinderGARS.class, "Not implemented",
				new UnsupportedOperationException("not implemented"));
		return null;
	}
}
