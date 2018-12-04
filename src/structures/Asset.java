package structures;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Representation of an asset under control.
 * 
 * @author John McCarthy
 */
public class Asset implements Serializable {

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
	 * @param addToRundown
	 *            whether or not to set add to rundown flag for this asset
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
	 * @param string
	 *            a new vcs for the asset
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
	 * @param string
	 *            a new Mode 2 for the asset
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
	 * @param val
	 *            - new value for asset's upper altitude
	 */
	public void setLowerAlt(String val) {
		this.altlower = val;
	}

	/**
	 * @return Asset's upper altitude
	 */
	public String getUpperAlt() {
		return this.altupper;
	}

	/**
	 * @param val
	 *            - new value for asset's lower altitude
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
	 * @param airspace
	 *            Asset's new approval
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
	 * @param status
	 *            to set status
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
	 * @param specType
	 *            the specType to set
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
	 * @param typeCat
	 *            the typeCat to set
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
	 * @param fullCallsign
	 *            the fullCallsign to set
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
	 * @param onStation
	 *            the onStation to set
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
	 * @param offStation
	 *            the offStation to set
	 */
	public void setOffStation(String offStation) {
		this.offStation = offStation;
	}

	/**
	 * Compare two assets with their VCS
	 */
	public static Comparator<Asset> AssetVCSCompare = new Comparator<Asset>() {
		@Override
		public int compare(Asset o1, Asset o2) {
			return (o1.getVCS().toUpperCase().compareTo(o2.getVCS().toUpperCase()));
		}
	};
	/**
	 * Compare two assets by Mode 2.
	 */
	public static Comparator<Asset> AssetMode2Compare = new Comparator<Asset>() {
		@Override
		public int compare(Asset o1, Asset o2) {
			return (o1.getMode2().toUpperCase().compareTo(o2.getMode2().toLowerCase()));
		}
	};

	/**
	 * Compare two assets with their lower altitude.
	 */
	public static Comparator<Asset> AltLowerompare = new Comparator<Asset>() {
		@Override
		public int compare(Asset o1, Asset o2) {
			return (o1.getLowerAlt().toUpperCase().compareTo(o2.getLowerAlt().toLowerCase()));
		}
	};

	/**
	 * Comapre two assets with their upper altitude.
	 */
	public static Comparator<Asset> AltUpperCompare = new Comparator<Asset>() {
		@Override
		public int compare(Asset o1, Asset o2) {
			return (o1.getUpperAlt().toUpperCase().compareTo(o2.getUpperAlt().toLowerCase()));
		}
	};

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
