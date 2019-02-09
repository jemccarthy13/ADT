package structures;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;

import utilities.Configuration;

/**
 * Representation of an asset under control.
 */
public class Asset extends ArrayList<Object> implements Comparable<Asset> {

	private static final long serialVersionUID = 8634221915912471387L;
	private String vcs = "";
	private String mode2 = "";
	private String location = "";
	private Altitude altBlock = new Altitude();
	private String alttransit = "";
	private String status = "";
	private String specType = "";
	private String typeCat = "";
	private String fullCallsign = "";
	private boolean addToRundown = false;
	private String onStation = "";
	private String offStation = "";
	private String arData = "";
	private String airspace = "";
	/**
	 * A flag whether this asst is in conflict with another, or not
	 */
	public boolean inConflict = false;
	private Color highlightColor = Color.WHITE;

	@Override
	public Object set(int columnIndex, Object aValue) {
		switch (columnIndex) {
		case 0:
			setAddToRundown((Boolean) aValue);
			break;
		case 1:
			setFullCallsign(aValue.toString());
			break;
		case 2:
			setVCS(aValue.toString());
			break;
		case 3:
			setMode2(aValue.toString());
			break;
		case 4:
			setSpecType(aValue.toString());
			break;
		case 5:
			setTypeCat(aValue.toString());
			break;
		case 6:
			setOnStation(aValue.toString());
			break;
		case 7:
			setOffStation(aValue.toString());
			break;
		default:
			break;
		}
		return aValue;
	}

	@Override
	public boolean equals(Object other) {
		Asset oth = (Asset) (other);
		return (this.mode2.equals(oth.mode2) && this.fullCallsign.equals(oth.fullCallsign) && this.vcs.equals(oth.vcs));
	}

	@Override
	public Object get(int index) {
		switch (index) {
		case 0:
			return isAddToRundown();
		case 1:
			return getFullCallsign();
		case 2:
			return getVCS();
		case 3:
			return getMode2();
		case 4:
			return getSpecType();
		case 5:
			return getTypeCat();
		case 6:
			return getOnStation();
		case 7:
			return getOffStation();
		default:
			return getFullCallsign();
		}
	}

	/**
	 * Create a new asset with the given properties.
	 * 
	 * @param tkd
	 * @param mode2
	 * @param arinfo
	 * @param specType
	 * @param typ
	 * @param callsign
	 * @param location2
	 */
	public Asset(String tkd, String mode2, String arinfo, String specType, String typ, String callsign,
			String location2) {
		this.vcs = tkd;
		this.mode2 = mode2;
		this.arData = arinfo;
		this.setSpecType(specType);
		this.setTypeCat(typ);
		this.setFullCallsign(callsign);
	}

	/**
	 * Create a blank asset
	 */
	public Asset() {
	}

	/**
	 * @return true iff ATOLookup should add this Asset to the rundown.
	 */
	public boolean isAddToRundown() {
		return this.addToRundown;
	}

	/**
	 * @param addToRundown whether or not to set add to rundown flag for this asset
	 */
	public void setAddToRundown(boolean addToRundown) {
		this.addToRundown = addToRundown;
	}

	/**
	 * String together some of the information for the asset.
	 */
	@Override
	public String toString() {
		return this.vcs + "/" + this.mode2 + "/" + this.arData + "/" + this.getSpecType() + "/" + this.getTypeCat()
				+ "/" + this.getFullCallsign() + "/" + this.location;
	}

	/**
	 * @return Asset's voice callsign
	 */
	public String getVCS() {
		return this.vcs;
	}

	/**
	 * @param string a new vcs for the asset
	 */
	public void setVCS(String string) {
		this.vcs = string;
	}

	/**
	 * @return Asset's ATO (or updated) mode2
	 */
	public String getMode2() {
		return this.mode2;
	}

	/**
	 * @param string a new Mode 2 for the asset
	 */
	public void setMode2(String string) {
		this.mode2 = string;
	}

	/**
	 * @return Asset's lower altitude
	 */
	public Altitude getAlt() {
		return this.altBlock;
	}

	/**
	 * @return Asset's transit altitude
	 */
	public String getTxAlt() {
		return this.alttransit;
	}

	/**
	 * @return Asset's current approval
	 */
	public String getAirspace() {
		return this.airspace;
	}

	/**
	 * @param airspace Asset's new approval
	 */
	public void setAirspace(String airspace) {
		this.airspace = airspace;
	}

	/**
	 * @return current asset status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status to set status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the specType
	 */
	public String getSpecType() {
		return this.specType;
	}

	/**
	 * @param specType the specType to set
	 */
	public void setSpecType(String specType) {
		this.specType = specType;
	}

	/**
	 * @return the typeCat
	 */
	public String getTypeCat() {
		return this.typeCat;
	}

	/**
	 * @param typeCat the typeCat to set
	 */
	public void setTypeCat(String typeCat) {
		this.typeCat = typeCat;
	}

	/**
	 * @return the fullCallsign
	 */
	public String getFullCallsign() {
		return this.fullCallsign;
	}

	/**
	 * @param fullCallsign the fullCallsign to set
	 */
	public void setFullCallsign(String fullCallsign) {
		this.fullCallsign = fullCallsign;
	}

	/**
	 * @return the onStation
	 */
	public String getOnStation() {
		return this.onStation;
	}

	/**
	 * @param onStation the onStation to set
	 */
	public void setOnStation(String onStation) {
		this.onStation = onStation;
	}

	/**
	 * @return the offStation
	 */
	public String getOffStation() {
		return this.offStation;
	}

	/**
	 * @param offStation the offStation to set
	 */
	public void setOffStation(String offStation) {
		this.offStation = offStation;
	}

	/**
	 * Set a new altitude block
	 * 
	 * @param lower - lower altitude
	 * @param upper - upper altitude
	 */
	public void setAltBlock(String lower, String upper) {
		this.altBlock = new Altitude(lower, upper);
	}

	/**
	 * @param other - another asset
	 * @return the altitude separation allowed between this and another asset
	 */
	public int altSep(Asset other) {
		int altSep = 9;
		if (this.getTypeCat().equals(other.getTypeCat()) && this.getTypeCat().equals("RPA")) {
			altSep = 4;
		}
		return altSep;
	}

	/**
	 * Determine if this asset's 3D airspace approval conflicts with another asset's
	 * 
	 * @param other - the other asset to compare with
	 * @return - a HashSet of any overlapping keypads, if found
	 */
	public HashSet<String> conflictsWith(Asset other) {
		HashSet<String> result = new HashSet<String>();
		if (this.getAlt().overlaps(other.getAlt(), altSep(other))) {
			result = this.sharesAirspaceWith(other);
		}
		// otherwise return empty keypad set (no overlap)
		return result;
	}

	/**
	 * Determine if this asset shares airspace with another asset
	 * 
	 * @param other - the other asset to compare to
	 * @return - a HashSet of any overlapping keypads, if found
	 */
	public HashSet<String> sharesAirspaceWith(Asset other) {
		KeypadFinder finder = Configuration.getInstance().getKeypadFinder();

		HashSet<String> thisKeypads = finder.getKeypads(this.airspace);
		HashSet<String> otherKeypads = finder.getKeypads(other.airspace);

		HashSet<String> overlap = new HashSet<String>(thisKeypads);
		overlap.retainAll(otherKeypads);
		return overlap;
	}

	/**
	 * @return true iff Asset is entirely blank
	 */
	public boolean isBlank() {
		return this.vcs.equals("") && this.mode2.equals("") && this.getFullCallsign().equals("")
				&& this.location.equals("") && this.altBlock.isBlank() && this.getStatus().equals("")
				&& this.getSpecType().equals("") && this.getTypeCat().equals("") && this.getOnStation().equals("")
				&& this.getOffStation().equals("") && this.arData.equals("") && this.getAirspace().equals("");
	}

	@Override
	public int compareTo(Asset o) {
		String thisUpper = this.altBlock.getUpper();
		String thisLower = this.altBlock.getLower();
		String othUpper = o.altBlock.getUpper();
		String othLower = o.altBlock.getLower();

		thisUpper = (thisUpper.equals("")) ? thisLower : thisUpper;
		othUpper = (othUpper.equals("")) ? othLower : othUpper;

		if (thisUpper.equals(""))
			thisUpper = "000";
		if (othUpper.equals(""))
			othUpper = "000";
		return Integer.parseInt(othUpper) - Integer.parseInt(thisUpper);
	}

	/**
	 * Setter for airspace highlight color
	 * 
	 * @param color - the color to use
	 */
	public void setAirspaceHighlightColor(Color color) {
		this.highlightColor = color;
	}

	/**
	 * getter for highlight color
	 * 
	 * @return highlighting color for this asset
	 */
	public Color getHighlightColor() {
		return this.highlightColor;
	}
}
