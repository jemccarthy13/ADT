package structures;

import java.io.Serializable;

/**
 * A substructure of Asset that holds ID information
 */
public class Identity implements Serializable {

	/** Serialization information */
	private static final long serialVersionUID = -8341992304012473691L;
	private String vcs = "";
	private String fullCallsign = "";
	private String mode2 = "";
	private String specType = "";
	private String typeCat = "";

	/**
	 * @param other other ID to compare to
	 * @return true iff Identities are equal
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Identity)) {
			return false;
		}
		Identity oth = (Identity) other;
		return this.mode2.equals(oth.getMode2()) && this.fullCallsign.equals(oth.getFullCallsign())
				&& this.vcs.equals(oth.getVCS());
	}

	@Override
	public int hashCode() {
		return (this.mode2 + this.fullCallsign + this.vcs).hashCode();
	}

	/**
	 * @return the vcs
	 */
	public String getVCS() {
		return this.vcs;
	}

	/**
	 * @param vcs the vcs to set
	 */
	public void setVCS(String vcs) {
		this.vcs = vcs;
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
	 * @return the mode2
	 */
	public String getMode2() {
		return this.mode2;
	}

	/**
	 * @param mode2 the mode2 to set
	 */
	public void setMode2(String mode2) {
		this.mode2 = mode2;
	}

	/**
	 * @return true iff ID is blank
	 */
	public boolean isBlank() {
		return this.vcs.equals("") && this.mode2.equals("") && this.fullCallsign.equals("");
	}

	/**
	 * @return specific type of this asset
	 */
	public String getSpecType() {
		return this.specType;
	}

	/**
	 * Set the specific type of this aircraft
	 * 
	 * @param specType - the specific type to set
	 */
	public void setSpecType(String specType) {
		this.specType = specType;
	}

	/**
	 * @return the category associated with this asset
	 */
	public String getTypeCat() {
		return this.typeCat;
	}

	/**
	 * Set the categorical type of this aircraft
	 * 
	 * @param typeCat - new type category
	 */
	public void setTypeCat(String typeCat) {
		this.typeCat = typeCat;
	}
}
