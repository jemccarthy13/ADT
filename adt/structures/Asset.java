package structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;

import utilities.Configuration;
import utilities.Patterns;

/**
 * Representation of an asset under control.
 * 
 * @author John McCarthy
 */
public class Asset extends ArrayList<Object> {

	private static final long serialVersionUID = 8634221915912471387L;
	private String vcs = "";
	private String mode2 = "";
	private String location = "";
	private String altlower = "";
	private String altupper = "";
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
	public String getLowerAlt() {
		return this.altlower;
	}

	/**
	 * @param val - new value for asset's upper altitude
	 */
	public void setLowerAlt(String val) {
		this.altlower = val.replaceAll(" ", "");
	}

	/**
	 * @return Asset's upper altitude
	 */
	public String getUpperAlt() {
		return this.altupper.replaceAll(" ", "");
	}

	/**
	 * @param val - new value for asset's lower altitude
	 */
	public void setUpperAlt(String val) {
		this.altupper = val;
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
	 * Get the string representation of the altitude range for this asset
	 * 
	 * @return - altitude range, "XXX-XXX"
	 */
	public String getAltRange() {
		String rng = "";
		if (this.altlower.equals("") && this.altupper.equals("")) {
			rng = "-";
		} else if (this.altlower.equals("")) {
			rng = this.altupper + "-" + this.altupper;
		} else if (this.altupper.equals("")) {
			rng = this.altlower + "-" + this.altlower;
		} else {
			rng = this.altlower + "-" + this.altupper;
		}
		return rng;
	}

	/**
	 * @param other - the other asset to check for altitude overlap
	 * @return true iff altitude ranges overlap given the asset type
	 */
	public boolean checkAltOverlaps(Asset other) {
		String curRng = getAltRange();
		String othRng = other.getAltRange();

		int max = 1;
		int min = 0;

		if (!(curRng.equals("-") || othRng.equals("-"))) {
			int altSep = 9;
			if (this.getTypeCat().equals(other.getTypeCat()) && this.getTypeCat().equals("RPA")) {
				altSep = 4;
			}
			Matcher rng1Match = Patterns.altBlockPattern.matcher(curRng);
			Matcher rng2Match = Patterns.altBlockPattern.matcher(othRng);
			rng1Match.find();
			rng2Match.find();

			int newAltB = Integer.parseInt(rng1Match.group(1));
			int newAltT = Integer.parseInt(rng1Match.group(2));

			int curAltB = Integer.parseInt(rng2Match.group(1)) - altSep;
			int curAltT = Integer.parseInt(rng2Match.group(2)) + altSep;

			max = newAltB >= curAltB ? newAltB : curAltB;
			min = newAltT <= curAltT ? newAltT : curAltT;
		}
		return (max - min) <= 0;
	}

	/**
	 * Determine if this asset's 3D airspace approval conflicts with another asset's
	 * 
	 * @param other - the other asset to compare with
	 * @return - a HashSet of any overlapping keypads, if found
	 */
	public HashSet<String> conflictsWith(Asset other) {
		HashSet<String> result = new HashSet<String>();
		if (checkAltOverlaps(other)) {
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
				&& this.altupper.equals("") && this.location.equals("") && this.altlower.equals("")
				&& this.getStatus().equals("") && this.getSpecType().equals("") && this.getTypeCat().equals("")
				&& this.getOnStation().equals("") && this.getOffStation().equals("") && this.arData.equals("")
				&& this.getAirspace().equals("");
	}
}
