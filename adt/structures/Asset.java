package structures;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashSet;

import swing.SingletonHolder;
import utilities.GridSettings;

/**
 * Representation of an asset under control.
 */
public class Asset implements Comparable<Asset>, Serializable {

	private static final long serialVersionUID = 8634221915912471387L;

	// Mode 2, callsign, vcs features
	private Identity ID = new Identity();

	// airspace approved
	private String airspace = "";

	// altitude block
	private Altitude altBlock = new Altitude();

	// transit altitude
	/** @todo transit altitudes and conflicts in transit */
	private String alttransit = "";

	// comments of an asset
	private String status = "";
	private boolean addToRundown = false;

	// on/off station times
	private String onStation = "";
	private String offStation = "";

	// AR data
	private String arData = "";

	@Override
	public int hashCode() {
		return this.ID.hashCode();
	}

	/**
	 * A flag whether this asst is in conflict with another, or not
	 */
	private boolean inConflict = false;
	private Color highlightColor = Color.WHITE;

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		Asset oth = (Asset) (other);
		Identity self = this.getID();
		Identity othID = oth.getID();
		return (self.getMode2().equals(othID.getMode2()) && self.getFullCallsign().equals(othID.getFullCallsign())
				&& self.getVCS().equals(othID.getVCS()));
	}

	/**
	 * 
	 * @return Identity object that holds ID info for this asset
	 */
	public Identity getID() {
		return this.ID;
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
	 * @param location
	 */
	public Asset(String tkd, String mode2, String arinfo, String specType, String typ, String callsign,
			String location) {
		this.getID().setVCS(tkd);
		this.getID().setMode2(mode2);
		this.arData = arinfo;
		this.getID().setSpecType(specType);
		this.getID().setTypeCat(typ);
		this.getID().setFullCallsign(callsign);
		this.airspace = location;
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
		return this.getID().getVCS() + "/" + this.getID().getMode2() + "/" + this.airspace + "/"
				+ this.getID().getSpecType() + "/" + this.getID().getTypeCat() + "/" + this.getID().getFullCallsign()
				+ "/" + this.arData;
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
		if (this.getID().getTypeCat().equals(other.getID().getTypeCat()) && this.getID().getTypeCat().equals("RPA")) {
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
		KeypadFinder finder = ((GridSettings) SingletonHolder.getInstanceOf(GridSettings.class)).getKeypadFinder();

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
		return this.getID().getVCS().equals("") && this.getID().getMode2().equals("")
				&& this.getID().getFullCallsign().equals("") && this.airspace.equals("") && this.altBlock.isBlank()
				&& this.getStatus().equals("") && this.getID().getSpecType().equals("")
				&& this.getID().getTypeCat().equals("") && this.getOnStation().equals("")
				&& this.getOffStation().equals("") && this.arData.equals("");
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

	/**
	 * Get a formatted approval string
	 * 
	 * @return formatted approval
	 */
	public String getApproval() {
		return this.getID().getVCS() + " " + this.getID().getMode2() + " /  / " + this.airspace + " / FL "
				+ this.getAlt().getLower() + " to FL " + this.getAlt().getUpper() + " / " + this.status;
	}

	/**
	 * @return the inConflict
	 */
	public boolean isInConflict() {
		return this.inConflict;
	}

	/**
	 * @param inConflict the inConflict to set
	 */
	public void setInConflict(boolean inConflict) {
		this.inConflict = inConflict;
	}

	/**
	 * @return Asset's AR information
	 */
	public Object getARData() {
		// todo Auto-generated method stub
		return this.arData;
	}
}
