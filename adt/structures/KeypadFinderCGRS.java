package structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

import swing.SingletonHolder;
import utilities.DebugUtility;
import utilities.GridSettings;
import utilities.Patterns;

/**
 * A utility class to help get neighboring keypads utilizing CGRS as the
 * reference system.
 * 
 * To get coordinates with a circle this class will:
 * 
 * - Convert the coordinate (center) and origin to deg decimal minutes
 * 
 * - Multiply that number by 100 (to make math easier)
 * 
 * - Find which killboxs are the boundary limits (N,E,S and W)
 * 
 * - Go through each of the killboxes from boundary to boundary
 * 
 * - Check each keypad intersection coordinate and check if that coordinate is <
 * radius distance from the center
 * 
 * - If so, add surrounding keypads to the list of touching keypads (to have a
 * little bit of a buffer to avoid false negatives)
 * 
 * @todo code golf
 */
public class KeypadFinderCGRS extends KeypadFinder {

	/**
	 * See KeypadFinder
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
	 * See KeypadFinder
	 */
	@Override
	public HashSet<String> keypadsFromKillbox(String killbox) {

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

	/**
	 * See KeypadFinder
	 */
	@Override
	public HashSet<String> getKeypadsInCircle(ArrayList<String> coords, String centerPoint, Double radius) {
		HashSet<String> retVal = new HashSet<String>();

		GridSettings settings = ((GridSettings) SingletonHolder.getInstanceOf(GridSettings.class));

		String nOrigin = getCoordDigitFormat(settings.getOrigin(), 4, 0);
		String eOrigin = getCoordDigitFormat(settings.getOrigin(), 5, 1);

		String nCenter = getCoordDigitFormat(centerPoint, 4, 0);
		String eCenter = getCoordDigitFormat(centerPoint, 5, 1);

		/** @todo - check to ensure centerpoint is not lower than the origin */
		for (String pad : coords) {

			// break up the row/col pairings so we can do math
			String[] padSplit = pad.split(" ");
			String padRow = padSplit[0];
			String padCol = padSplit[1];

			// find the north western most corner of the current row/col
			int nKillboxOffset = ((Integer.parseInt(padRow) - Integer.parseInt(settings.getStartRow())) * 50) + 50;
			int eKillboxOffset = (Integer.parseInt(padCol) - (settings.getStartCol().charAt(0) - 64)) * 50;

			// start at the first keypad
			int startingKeypad = 1;

			int numGridRows = 3;
			int numGridCols = 3;
			for (int x = 1; x <= numGridRows; x++) {

				float nKeypadOffset = -(50 * ((float) (x - 1) / numGridRows));

				for (int y = 1; y <= numGridCols; y++) {

					float eKeypadOffset = (50 * ((float) (y - 1) / numGridCols));

					float nCornerPoint = Long.parseLong(nOrigin) + nKillboxOffset + nKeypadOffset;
					float eCornerPoint = Long.parseLong(eOrigin) + eKillboxOffset + eKeypadOffset;

					int chr1 = Integer.parseInt(padCol) / 26;

					String col1stChr = "";
					if (chr1 != 0) {
						col1stChr = String.valueOf((char) (chr1 + 64));
					}

					int chr2 = Integer.parseInt(padCol) % 26;
					String col2ndChr = String.valueOf((char) (chr2 + 64));

					String keypadStr = padRow + col1stChr + col2ndChr + startingKeypad;

					System.err.println("Testing: " + keypadStr);

					boolean inCircle = testKeypad(Double.parseDouble(nCenter), Double.parseDouble(eCenter),
							nCornerPoint, eCornerPoint, radius);

					if (inCircle) {
						retVal.add(keypadStr);
					}
					startingKeypad++;
				}
			}
		}

		return retVal;
	}

	/**
	 * Test a square to see if it is in the circle
	 * 
	 * @param nCenter
	 * @param eCenter
	 * @param nCornerPoint
	 * @param eCornerPoint
	 * @param radius
	 * @return true iff keypad is within the circle
	 */
	public boolean testKeypad(Double nCenter, Double eCenter, float nCornerPoint, float eCornerPoint, Double radius) {
		Double longDelta = Math.cos(Math.PI * ((nCornerPoint / 100) / 180)) * 30;

		double testRadius = (radius % 10 == 0) ? radius + 1 : radius;
		if (testRadius < 10)
			testRadius += 5;
		boolean retval = false;
		if (getDistance(nCornerPoint, nCenter, eCornerPoint, eCenter, longDelta) < testRadius)
			retval = true;
		if (getDistance(nCornerPoint, nCenter, eCornerPoint + 50 / 3, eCenter, longDelta) < testRadius)
			retval = true;
		if (getDistance(nCornerPoint - 50 / 3, nCenter, eCornerPoint, eCenter, longDelta) < testRadius)
			retval = true;
		if (getDistance(nCornerPoint - 50 / 3, nCenter, eCornerPoint + 50 / 3, eCenter, longDelta) < testRadius)
			retval = true;

		if (getDistance(nCornerPoint, nCenter, eCornerPoint + 50 / 6, eCenter, longDelta) < testRadius)
			retval = true;
		if (getDistance(nCornerPoint - 50 / 6, nCenter, eCornerPoint, eCenter, longDelta) < testRadius)
			retval = true;
		if (getDistance(nCornerPoint - 50 / 6, nCenter, eCornerPoint + 50 / 3, eCenter, longDelta) < testRadius)
			retval = true;
		if (getDistance(nCornerPoint - 50 / 3, nCenter, eCornerPoint + 50 / 6, eCenter, longDelta) < testRadius)
			retval = true;

		System.err.println("Found one corner: " + retval);
		return retval;
	}

	private double getDistance(double x1, double x2, double y1, double y2, Double nmPerDegLon) {
		double xdelt = ((x2 - x1) / 50) * 30;
		double ydelt = ((y2 - y1) / 50) * (nmPerDegLon);
		return Math.sqrt((xdelt * xdelt) + (ydelt * ydelt));
	}
}
