package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import swing.SingletonHolder;
import utilities.GridSettings;

/**
 * Defines a interface of how to find keypads in a particular reference system
 * 
 * To implement a new reference system, extend this class and primarily define
 * keypadsFromKillbox, which is the base level representation (a singular
 * killbox such as 88AM in CGRS) expanded into keypads
 * 
 * For an example, see how CGRS and GARS KeypadFinders are defined.
 */
public abstract class KeypadFinder {

	private HashMap<String, HashSet<String>> killboxDict = new HashMap<String, HashSet<String>>();

	/**
	 * Find keypads in a given direction from a starting point killbox, direction,
	 * and the keypads in the adjacent killbox
	 * 
	 * i.e. (UPRT, "88AM", {7}) would return 89AM7 since 89AM is the killbox up and
	 * to the right of 88AM.
	 * 
	 * @param direct  - the direction of the adjacent killbox
	 * @param killbox - the starting killbox
	 * @param keypads - an array of keypads in the adjacent killbox
	 * @return - a list of Strings representing keypads
	 */
	public abstract HashSet<String> findKeypads(DIR direct, String killbox, String[] keypads);

	/**
	 * Get the keypads from a given representation
	 * 
	 * @param rep - the representation of approval
	 * @return - the individual keypads of that representation
	 */
	public HashSet<String> getKeypads(String rep) {
		HashSet<String> keypads = new HashSet<String>();
		if (this.killboxDict.containsKey(rep)) {
			keypads = this.killboxDict.get(rep);
		} else {
			if (rep.contains(" ")) {
				String app = rep.substring(rep.lastIndexOf(" "));

				String subRep = rep.substring(0, rep.lastIndexOf(" ")).trim();

				keypads.addAll(this.keypadsFromKillbox(app));
				keypads.addAll(this.getKeypads(subRep));

				this.killboxDict.put(rep, keypads);
			} else {
				keypads.addAll(this.keypadsFromKillbox(rep));
				this.killboxDict.put(rep, keypads);
			}
		}

		return keypads;
	}

	/**
	 * Get keypads from a given singular killbox, i.e. "88AM1+"
	 * 
	 * @param killbox - the representation (row, column, keypad/modifier)
	 * @return - keypads from the killbox
	 */
	public abstract HashSet<String> keypadsFromKillbox(String killbox);

	/**
	 * Given coordinates, a centerpoint, radius, and origin coords, get the list of
	 * keypads within the circle
	 * 
	 * @param coords
	 * @param centerPoint
	 * @param radius
	 * @param origin
	 * @return - list of keypads that reside within a circle
	 */
	public abstract HashSet<String> getKeypadsInCircle(ArrayList<String> coords, String centerPoint, Double radius);

	/**
	 * Given a centerpoint and radius, expand this circle into it's encompassing
	 * keypads.
	 * 
	 * @param centerPt - center of the circle
	 * @param radius   - radius of the circle
	 * @return - list of keypads in circle
	 */
	public HashSet<String> getRepresentationFromCircle(String centerPt, Double radius) {

		// Retreive grid settings
		GridSettings settings = ((GridSettings) SingletonHolder.getInstanceOf(GridSettings.class));
		String origin = settings.getOrigin();

		// Replace any "." in the coordinate string
		// What this does is redefine coordinate math - 0300.1200 = 03001200.
		// When coordinates are converted using "getCoordDigitFormat" there are no
		// decimals and we can do straight integer math on whole numbers
		String centerPoint = centerPt.replaceAll("\\.", "");

		// Convert the center and origin to easier formats to handle
		Long nOriginNum = Long.parseLong(getCoordDigitFormat(origin, 4, 0));
		Long eOriginNum = Long.parseLong(getCoordDigitFormat(origin, 5, 1));

		// This is the new coordinate format for the centerpoint
		Long nCenterNum = Long.parseLong(getCoordDigitFormat(centerPoint, 4, 0));
		Long eCenterNum = Long.parseLong(getCoordDigitFormat(centerPoint, 5, 1));

		// As the latitude moves towards the poles, nautical miles horizontally per
		// degree decrease, so we need to calculate that
		Double milesLonPerDegree = Math.cos(Math.PI * ((nCenterNum / 100) / 180)) * 60;

		// there is only half a degree per killbox
		Double milesLonPerKillbox = milesLonPerDegree / 2;

		// For latitude there are 30 nm per 1 killbox (30')
		// or 60 nm per degree of latitude
		int milesLatPerKillbox = 30;

		//
		// Now: with all of this setup, we now have the ability to bound our grid check.
		//
		// We are solving the Gauss circle problem
		// https://math.stackexchange.com/questions/590937/number-of-cells-inside-a-circle
		//
		// Because the CGRS grid extends much further than the radius of a ROZ or
		// circle, we limit our detailed lattice vertex level search to only killboxes
		// that are likely to have keypads within the circle.
		//
		// (i.e. somewhere in this 30 min by 30 min grid is a keypad which falls within
		// the circle)
		//
		// these N,S,E, and W-most corners are the boundaries for our lattice check,
		// convered to our "new math" coordinates
		double nmCenterNum = nCenterNum + ((radius / milesLatPerKillbox) * 100);
		double smCenterNum = nCenterNum - ((radius / milesLatPerKillbox) * 100);
		double emCoordNum = eCenterNum + (radius / milesLonPerKillbox) * 100;
		double wmCoordNum = eCenterNum - (radius / milesLonPerKillbox) * 100;

		// "Increments" are the difference from N,S,E, and w-most corners to the origin,
		// divided by 50 (which is 1/2 a degree in our new math)
		// Therefore, these "increments" are the raw row and column that are the N,S,E
		// and W-most
		int nmIncr = (int) ((nmCenterNum - nOriginNum) / 50);
		int smIncr = (int) ((smCenterNum - nOriginNum) / 50);
		int emIncr = (int) ((emCoordNum - eOriginNum) / 50);
		int wmIncr = (int) ((wmCoordNum - eOriginNum) / 50);

		// From the S most to N most row, from the W most to E most column
		// build the list of killboxes to check (in terms of raw integers: row 1 column
		// 0, etc)
		ArrayList<String> coords = new ArrayList<String>();
		for (int rowIdx = smIncr; rowIdx <= nmIncr; rowIdx++) {
			for (int colIdx = wmIncr; colIdx <= emIncr; colIdx++) {
				coords.add(rowIdx + Integer.parseInt(settings.getStartRow()) + " "
						+ (colIdx + settings.getStartCol().charAt(0) - 64));
			}
		}

		// Now go through each raw killbox and find the keypads
		HashSet<String> kpads = new HashSet<String>();

		for (String pad : getKeypadsInCircle(coords, centerPoint, radius)) {
			kpads.add(pad);
		}

		// We use a dictionary as an interim step to ensure no duplicates
		return kpads;
	}

	/**
	 * Turn a coordinate into a format for easy math.
	 * 
	 * @param coord     - the coordinate to convert
	 * @param numDigits - the number of digits this coordinate should have (0 = N/S,
	 *                  1 = E/W)
	 * @param dir       - east or west
	 * @return formatted coordinate
	 */
	public String getCoordDigitFormat(String coord, int numDigits, int dir) {
		String newCoord = coord.replaceAll(" ", "").replaceAll("\\.", "").replaceAll(":", "").toUpperCase();

		String coordPattern = "([0-9]+)[NS ]([0-9]+)[EW ]";

		newCoord = convertPartToDecimal(newCoord);

		Pattern p = Pattern.compile(coordPattern);
		Matcher m = p.matcher(newCoord);
		m.find();

		String expandedCoord = m.group(dir + 1) + "0000";

		String retCoord = expandedCoord.substring(0, numDigits);

		if (dir == 0 && coord.contains("S")) {
			retCoord = "-" + retCoord;
		}
		if (dir == 1 && coord.contains("W")) {
			retCoord = "-" + retCoord;
		}
		return retCoord;
	}

	/**
	 * Take a coordinate and convert it to deg decimal minutes.
	 * 
	 * @param coord - Input coordinate
	 * @return New coordinate
	 */
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
}
