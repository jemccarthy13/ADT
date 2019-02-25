package utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public HashSet<String> getKeypadsInCircle(ArrayList<String> coords, String centerPoint, Double radius,
			String origin) {
		HashSet<String> retVal = new HashSet<String>();

		String startRow = "1";
		String startCol = "A";

		String nOrigin = getCoordDigitFormat(origin, 4, 0);
		String eOrigin = getCoordDigitFormat(origin, 5, 1);

		String nCenter = getCoordDigitFormat(centerPoint, 4, 0);
		String eCenter = getCoordDigitFormat(centerPoint, 5, 1);

		System.err.println("N Center in DMS: " + nCenter);
		System.err.println("E Center in DMS: " + eCenter);
		System.err.println("N Origin in DMS: " + nOrigin);
		System.err.println("E Origin in DMS: " + eOrigin);

		/** TODO - check to ensure centerpoint is not lower than the origin */
		for (String pad : coords) {
			String[] cornerCoord = pad.split(" ");
			String[] padSplit = pad.split(" ");

			String padRow = padSplit[0];
			String padCol = padSplit[1];

			int nKillboxOffset = ((Integer.parseInt(cornerCoord[0]) - Integer.parseInt(startRow)) * 50) + 50;
			int eKillboxOffset = (Integer.parseInt(cornerCoord[1]) - (startCol.charAt(0) - 64)) * 50;

			System.err.println("Checking " + pad + " " + (Integer.parseInt(nOrigin) + nKillboxOffset) + " "
					+ (Integer.parseInt(eOrigin) + eKillboxOffset));

			int count = 1;

			for (int x = 1; x <= 4; x++) {
				float nKeypadOffset = -(50 * ((float) (x - 1) / 3));

				for (int y = 1; y <= 4; y++) {
					float eKeypadOffset = (50 * ((float) (y - 1) / 3));

					float nCornerPoint = Long.parseLong(nOrigin) + nKillboxOffset + nKeypadOffset;
					float eCornerPoint = Long.parseLong(eOrigin) + eKillboxOffset + eKeypadOffset;

					Double longDelta = Math.cos(3.141592653 * ((nCornerPoint / 100) / 180)) * 60;

					double distance = getDistance(Double.parseDouble(nCenter), nCornerPoint,
							Double.parseDouble(eCenter), eCornerPoint, longDelta);

					System.err.println(nCornerPoint + " " + eCornerPoint + " " + distance);
					if (distance < (radius * radius)) {

						int chr1 = Integer.parseInt(padCol) / 26;

						String col1stChr = "";
						if (chr1 != 0) {
							col1stChr = String.valueOf((char) (chr1 + 64));
						}

						int chr2 = Integer.parseInt(padCol) % 26;
						String col2ndChr = String.valueOf((char) (chr2 + 64));

						String keypadStr = padRow + col1stChr + col2ndChr + count;
						System.err.println(keypadStr);

						HashSet<String> keypadColl = new HashSet<String>();

						int keypadNum = count - 1;

						DIR direct = DIR.SELF;

						switch (keypadNum) {
						case 0:
						case 3:
						case 6:
							keypadNum = keypadNum + 3;
							direct = DIR.LT;
							break;
						default:
							break;
						}

						keypadColl = findKeypads(direct, padRow + col1stChr + col2ndChr,
								new String[] { String.valueOf(keypadNum) });

						if (y != 4) {
							System.err.println(keypadColl);
							retVal.addAll(keypadColl);
						}

						keypadNum = count - 3;
						direct = DIR.SELF;

						switch (keypadNum) {
						case -2:
						case -1:
						case 0:
							keypadNum = keypadNum + 9;
							direct = DIR.UP;
							break;
						default:
							break;
						}

						keypadColl = findKeypads(direct, padRow + col1stChr + col2ndChr,
								new String[] { String.valueOf(keypadNum) });

						if (y != 4) {
							System.err.println(keypadColl);
							retVal.addAll(keypadColl);
						}

						keypadNum = count - 4;
						direct = DIR.SELF;

						switch (keypadNum) {
						case -3:
							keypadNum = 9;
							direct = DIR.UPLT;
							break;
						case -2:
							keypadNum = 7;
							direct = DIR.UP;
							break;
						case -1:
							keypadNum = 8;
							direct = DIR.UP;
							break;
						case 0:
							keypadNum = 3;
							direct = DIR.LT;
							break;
						case 3:
							keypadNum = 6;
							direct = DIR.LT;
							break;
						default:
							break;
						}

						keypadColl = findKeypads(direct, padRow + col1stChr + col2ndChr,
								new String[] { String.valueOf(keypadNum) });

						if (y != 4) {
							System.err.println(keypadColl);
							retVal.addAll(keypadColl);
						}

						System.err.println(keypadStr);
						retVal.add(keypadStr);
					}

					if (y != 3) {
						count++;
					}
					if (count > 9) {
						count = 7;
					}

				}

			}

			// int chr1 = Integer.parseInt(padCol) / 26;

			// String col1stChr = chr1 != 0 ? String.valueOf((char) (chr1 + 64)) : "";

			// int chr2 = Integer.parseInt(padCol) % 26;
			// String col2ndChr = String.valueOf((char) (chr2 + 64));

			// HashSet<String> selfPads = getKeypads(padRow + col1stChr + col2ndChr);

			// System.err.println(selfPads);
			// retVal.addAll(selfPads);
		}

		return retVal;
	}

	private double getDistance(double x1, double x2, double y1, double y2, Double longDelta) {
		double xdelt = ((x2 - x1) / 100) * 60;
		double ydelt = ((y2 - y1) / 100) * (longDelta / 60);

		System.err.println(xdelt + " " + ydelt);
		return Math.sqrt((xdelt * xdelt) + (ydelt * ydelt));
	}

	private String getCoordDigitFormat(String coord, int numDigits, int dir) {
		String newCoord = coord.replaceAll(" ", "").replaceAll("\\.", "").replaceAll(":", "").toUpperCase();

		String coordPattern = "([0-9]+)[NS ]([0-9]+)[EW ]";

		newCoord = convertPartToDecimal(newCoord);

		Pattern p = Pattern.compile(coordPattern);
		Matcher m = p.matcher(newCoord);
		m.find();

		String expandedCoord = m.group(dir + 1) + "0000";

		String retCoord = expandedCoord.substring(0, numDigits);
		System.err.println(retCoord);

		if (dir == 0 && coord.contains("S")) {
			retCoord = "-" + retCoord;
		}
		if (dir == 1 && coord.contains("W")) {
			retCoord = "-" + retCoord;
		}
		return retCoord;
	}

	private String convertPartToDecimal(String coord) {

		String convPattern = "([0-9][0-9])([0-9][0-9]).*([NS])";

		Pattern p = Pattern.compile(convPattern);
		Matcher m = p.matcher(coord);
		m.find();

		String conversion = m.group(1);

		Double minutes = (Double.parseDouble(m.group(2)) / 60) * 100;

		String formattedMinutes = String.format("%02.0f", minutes);
		conversion = conversion + formattedMinutes + m.group(3);

		convPattern = "[NS]([0-9][0-9][0-9])([0-9][0-9]).*([EW])";

		p = Pattern.compile(convPattern);
		m = p.matcher(coord);
		m.find();

		String degrees = m.group(1);

		minutes = (Double.parseDouble(m.group(2)) / 60) * 100;
		formattedMinutes = String.format("%02.0f", minutes);

		conversion = conversion + degrees + formattedMinutes + m.group(3);

		return conversion;
	}

	@Override
	public HashSet<String> getKillboxFromCircle(String centerPt, Double radius) {

		// String origin = Configuration.getOrigin();
		String origin = "1030S02200E";
		String startRow = "1";
		String startCol = "A";

		String centerPoint = centerPt.replaceAll("\\.", "");
		DebugUtility.error(getClass(),
				"Center: " + centerPoint + "\r\n" + "Origin: " + origin + "\r\n" + "Radius: " + radius);

		// Convert the center and origin to easier formats to handle
		Long nOriginNum = Long.parseLong(getCoordDigitFormat(origin, 4, 0));
		Long eOriginNum = Long.parseLong(getCoordDigitFormat(origin, 5, 1));

		Long nCoordNum = Long.parseLong(getCoordDigitFormat(centerPoint, 4, 0));
		Long eCoordNum = Long.parseLong(getCoordDigitFormat(centerPoint, 5, 1));

		double nmCoordNum = nCoordNum + ((radius / 60) * 100);
		double smCoordNum = nCoordNum - ((radius / 60) * 100);

		Double longDelta = Math.cos(3.141592654 * ((nCoordNum / 100) / 180)) * 60;

		double emCoordNum = eCoordNum + (radius / longDelta) * 100;
		double wmCoordNum = eCoordNum - (radius / longDelta) * 100;

		// "Increments" are measured in increments of 50 (which, in our new math = 1/2 a
		// degree
		int nmIncr = (int) ((nmCoordNum - nOriginNum) / 50);
		int smIncr = (int) ((smCoordNum - nOriginNum) / 50);
		int emIncr = (int) ((emCoordNum - eOriginNum) / 50);
		int wmIncr = (int) ((wmCoordNum - eOriginNum) / 50);

		System.err.println("::: " + smCoordNum + " " + nCoordNum + " " + nOriginNum + " " + smIncr);
		System.err.println("::: " + wmCoordNum + " " + eCoordNum + " " + eOriginNum + " " + wmIncr);

		ArrayList<String> coords = new ArrayList<String>();

		// From the S most to N most row, from the W most to E most column

		for (int rowIdx = smIncr; rowIdx <= nmIncr; rowIdx++) {
			for (int colIdx = wmIncr; colIdx < emIncr; colIdx++) {
				System.err.println("coords: " + (colIdx + startCol.charAt(0) - 64));
				coords.add(rowIdx + Integer.parseInt(startRow) + " " + (colIdx + startCol.charAt(0) - 64));

				// (colIdx + Asc(startCol) - 64))
			}
		}

		// Now go through each killbox and find the keypads
		HashSet<String> kpads = new HashSet<String>();

		for (String pad : getKeypadsInCircle(coords, centerPoint, radius, origin)) {
			kpads.add(pad);
		}

		// We use a dictionary as an interim step to ensure no duplicates
		return kpads;
	}

}
