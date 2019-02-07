package utilities;

import java.util.regex.Pattern;

/**
 * A static location/container class for compiled Patterns.
 */
public class Patterns {
	/** ATO file extension pattern */
	public static Pattern extPattern = Pattern.compile("(USMTF00).*txt");

	/** ATO mission code pattern */
	public static Pattern msnCodePattern = Pattern.compile("^\\/([A-Z]+[0-9]+[A-Z]*)\\/");

	/** AC specific type pattern */
	public static Pattern acTypePattern = Pattern.compile("/(ACTYP|OTHAC):([A-Z0-9]*)/");

	/** ATO callsign pattern */
	public static Pattern fileLineCSPattern = Pattern
			.compile("/(ACTYP|OTHAC):([A-Z0-9]*)/" + "([A-Z][A-Z]+[A-Z] *[0-9]+)");

	/** ATO VCS Pattern */
	public static Pattern vcsPattern = Pattern.compile("([A-Z])[A-Z]+([A-Z])([0-9]+)");
	/** ATO Mode2 pattern */
	public static Pattern atoM2Pattern = Pattern.compile("/[2I]([0-9]+)[A-Z]*/3([0-9]+)[/$\n]");

	/** ATO mission timing pattern */
	public static Pattern msnTimingsPattern = Pattern.compile("AMSNLOC/([0-9A-Z]+)/([0-9A-Z]+)[/$\n]");

	/** Altitude in feet pattern */
	public static Pattern altFeetPattern = Pattern.compile("(([0-9]+[,]*[0-9]*) *ft)");

	/** Number pattern */
	public static Pattern numPattern = Pattern.compile("([0-9]+)");
	/** range of numbers pattern */
	public static Pattern rangePattern = Pattern.compile("([0-9])-([0-9])");
	/** killbox pattern */
	public static Pattern killboxPattern = Pattern.compile("([0-9]+)([A-Za-z]+)([0-9]*)");
	/** airspace pattern */
	public static Pattern airspacePattern = Pattern.compile("([0-9]+)([A-Za-z]+)([0-9]*-*[0-9]*)([+4cC]+)*");
	/** words that aren't airspace approvals that should be ignored */
	public static Pattern ignorePattern = Pattern.compile("(tx|TX|Tx|via|VIA|Via|from|FROM|From|to|TO|To)");

	/** altitude block pattern */
	public static Pattern altBlockPattern = Pattern.compile("([0-9][0-9][0-9])-([0-9][0-9][0-9])");

	/** mirc paste VCS pattern */
	public static Pattern mircVCSPattern = Pattern.compile("^([A-Z])[A-Z]*([A-Z])([0-9]*)");
	/** mirc paste AS pattern */
	public static Pattern mircASPattern = Pattern.compile("[/_ ]+([0-9]*[A-Za-z]+[A-Za-z0-9, \\+]*)[, _/]+");
	/** mirc paste Flight level pattern */
	public static Pattern mircFLPattern = Pattern.compile("FL *([0-9]+)[- TO /FL ]*([0-9]+)*");
	/** mirc paste transit flight level pattern */
	public static Pattern mircTxPattern = Pattern.compile("TX *(FL)*([ 0-9]+)[ -]*(FL)*(TO FL)*([ 0-9]+)*");

}
