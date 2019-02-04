package utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;

import structures.KeypadFinder;

/**
 * A utility class to help get neighboring keypads
 */
public class CGRSKeypadFinder implements KeypadFinder {

	private HashMap<String, HashSet<String>> killboxDict = new HashMap<String, HashSet<String>>();

	@Override
	public HashSet<String> findKeypads(DIR direct, String killbox, String[] keypads) {
		Matcher m = Patterns.killboxPattern.matcher(killbox);
		m.find();
		Patterns.killboxPattern.matcher(killbox).find();

		String number = m.group(1);
		String letters = m.group(2);

		HashSet<String> strArray = new HashSet<String>();

		String newKeypad = "";

		switch (direct) {
		case SELF:
			for (String num : keypads) {
				newKeypad = number + letters + num;
				strArray.add(newKeypad.toUpperCase());
			}
			break;
		case LT:
			for (String num : keypads) {
				newKeypad = number + letters.substring(0, 1) + (char) ((letters.substring(1, 2).charAt(0)) - 1) + num;
				strArray.add(newKeypad.toUpperCase());
			}
			break;
		case RT:
			for (String num : keypads) {
				newKeypad = number + letters.substring(0, 1) + (char) ((letters.substring(1, 2).charAt(0)) + 1) + num;
				strArray.add(newKeypad.toUpperCase());
			}
			break;
		case UP:
			for (String num : keypads) {
				newKeypad = String.valueOf(Integer.parseInt(number) + 1) + letters.substring(0, 1)
						+ letters.substring(1, 2) + num;
				strArray.add(newKeypad.toUpperCase());
			}
			break;
		case DOWN:
			for (String num : keypads) {
				newKeypad = String.valueOf(Integer.parseInt(number) - 1) + letters.substring(0, 1)
						+ letters.substring(1, 2) + num;
				strArray.add(newKeypad.toUpperCase());
			}
			break;
		case UPLT:
			for (String num : keypads) {
				newKeypad = String.valueOf(Integer.parseInt(number) + 1) + letters.substring(0, 1)
						+ (char) ((letters.substring(1, 2).charAt(0)) - 1) + num;

				strArray.add(newKeypad.toUpperCase());
			}
			break;
		case UPRT:
			for (String num : keypads) {
				newKeypad = String.valueOf(Integer.parseInt(number) + 1) + letters.substring(0, 1)
						+ (char) ((letters.substring(1, 2).charAt(0)) + 1) + num;
				strArray.add(newKeypad.toUpperCase());
			}
			break;
		case DOWNLT:
			for (String num : keypads) {
				newKeypad = String.valueOf(Integer.parseInt(number) - 1) + letters.substring(0, 1)
						+ (char) ((letters.substring(1, 2).charAt(0)) - 1) + num;
				strArray.add(newKeypad.toUpperCase());
			}
			break;
		case DOWNRT:
			for (String num : keypads) {
				newKeypad = String.valueOf(Integer.parseInt(number) - 1) + letters.substring(0, 1)
						+ (char) ((letters.substring(1, 2).charAt(0)) + 1) + num;
				strArray.add(newKeypad.toUpperCase());
			}
			break;
		default:
			break;
		}

		return strArray;
	}

	@Override
	public HashSet<String> getKeypads(String representation) {
		if (this.killboxDict.containsKey(representation) == false) {
			Matcher m = Patterns.airspacePattern.matcher(representation);
			m.find();
			String row = m.group(1);
			String col = m.group(2);
			String keypad = m.group(3);
			String modifier = m.group(4);

			String keypadNums;
			String keypadRangeBottom;
			String keypadRangeTop;
			HashSet<String> keypads = new HashSet<String>();

			if (representation.contains(" ")) {
				for (String str : representation.split(" ")) {
					keypads.addAll(getKeypads(str));
				}
			}
			String namedAS = representation;

			if (row.equals("")) {
				row = namedAS;
				String expansion = expandNamedASToKillbox(representation);

				for (String box : expansion.split(" ")) {
					Matcher m2 = Patterns.ignorePattern.matcher(box);
					if (m2.find() == false) {
						if (!box.trim().equals("") && !box.trim().equals(row)) {
							keypads.addAll(getKeypads(box.trim()));
						}
						if (box.trim().equals(row)) {
							keypads.add(row);
						}
					}
				}
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
				switch (keypad) {
				case "1":
					keypads.addAll(findKeypads(KeypadFinder.DIR.LT, representation, new String[] { "3", "6" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.UPLT, representation, new String[] { "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.UP, representation, new String[] { "7", "8" }));
					keypads.addAll(
							findKeypads(KeypadFinder.DIR.SELF, representation, new String[] { "1", "2", "4", "5" }));
					break;
				case "2":
					keypads.addAll(findKeypads(KeypadFinder.DIR.UP, representation, new String[] { "7", "8", "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation,
							new String[] { "1", "2", "3", "4", "5", "6" }));
					break;
				case "3":
					keypads.addAll(findKeypads(KeypadFinder.DIR.UP, representation, new String[] { "8", "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.UPRT, representation, new String[] { "7" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.RT, representation, new String[] { "1", "4" }));
					keypads.addAll(
							findKeypads(KeypadFinder.DIR.SELF, representation, new String[] { "2", "3", "5", "6" }));
					break;
				case "4":
					keypads.addAll(findKeypads(KeypadFinder.DIR.LT, representation, new String[] { "3", "6", "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation,
							new String[] { "1", "2", "4", "5", "7", "8" }));
					break;
				case "5":
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation,
							new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" }));
					break;
				case "6":
					keypads.addAll(findKeypads(KeypadFinder.DIR.RT, representation, new String[] { "1", "4", "7" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation,
							new String[] { "2", "3", "5", "6", "8", "9" }));
					break;
				case "7":
					keypads.addAll(findKeypads(KeypadFinder.DIR.LT, representation, new String[] { "6", "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWNLT, representation, new String[] { "3" }));
					keypads.addAll(
							findKeypads(KeypadFinder.DIR.SELF, representation, new String[] { "4", "5", "7", "8" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWN, representation, new String[] { "1", "2" }));
					break;
				case "8":
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWN, representation, new String[] { "1", "2", "3" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation,
							new String[] { "4", "5", "6", "7", "8", "9" }));
					break;
				case "9":
					keypads.addAll(findKeypads(KeypadFinder.DIR.RT, representation, new String[] { "4", "7" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWNRT, representation, new String[] { "1" }));
					keypads.addAll(
							findKeypads(KeypadFinder.DIR.SELF, representation, new String[] { "5", "6", "8", "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWN, representation, new String[] { "2", "3" }));
					break;
				default:
					break;
				}
			}

			else if (modifier.toUpperCase().equals("+4C")) {
				switch (keypad) {
				case "1":
					keypads.addAll(findKeypads(KeypadFinder.DIR.LT, representation, new String[] { "3" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.UPLT, representation, new String[] { "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.UP, representation, new String[] { "7" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation, new String[] { "1" }));
					break;
				case "3":
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation, new String[] { "3" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.UP, representation, new String[] { "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.UPRT, representation, new String[] { "7" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.RT, representation, new String[] { "1" }));
					break;
				case "7":
					keypads.addAll(findKeypads(KeypadFinder.DIR.LT, representation, new String[] { "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWNLT, representation, new String[] { "3" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation, new String[] { "7" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWN, representation, new String[] { "1" }));
					break;
				case "9":
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWN, representation, new String[] { "3" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.SELF, representation, new String[] { "9" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.RT, representation, new String[] { "7" }));
					keypads.addAll(findKeypads(KeypadFinder.DIR.DOWNRT, representation, new String[] { "1" }));
					break;
				default:
					break;
				}
			}
			if (this.killboxDict.containsKey(representation) == false) {
				this.killboxDict.put(representation, keypads);
			}
		}

		return this.killboxDict.get(representation);
	}

	/**
	 * Expand a named airspace to it's component killboxes
	 * 
	 * @param representation - the keypad representation to expand
	 * @return the set of keypads that a named airspace contains
	 */
	private String expandNamedASToKillbox(String representation) {
		// TODO Auto-generated method stub
		DebugUtility.trace(CGRSKeypadFinder.class, "Trying to find keypads for " + representation);
		DebugUtility.error(CGRSKeypadFinder.class, "Unimplemented", new UnsupportedOperationException("Unimplemented"));
		return null;
	}

	@Override
	public ArrayList<String> getKeypadsInCircle(ArrayList<String> coords, String centerPoint, long radius,
			String origin) {
		DebugUtility.error(CGRSKeypadFinder.class, "Not implemented",
				new UnsupportedOperationException("not implemented"));
		return null;
	}

}
