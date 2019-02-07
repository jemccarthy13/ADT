package structures;

import java.awt.Color;

/**
 * Representation of an airspace.
 * 
 * Has lower and upper altitude, location, and name.
 */
public class Airspace extends Asset {
	private static final long serialVersionUID = -534783494176132498L;

	/** The name of this airspace */
	private String name;
	private Color color;

	/**
	 * Override hashcode for comparisons to only allow unique airspace names
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * Constructor
	 * 
	 * @param name
	 * @param location
	 * @param lowAlt
	 * @param upAlt
	 */
	public Airspace(String name, String location, String lowAlt, String upAlt) {
		this.setName(name);
		this.setAirspace(location);
		this.setAltBlock(lowAlt, upAlt);
	}

	/**
	 * Default constructor
	 */
	public Airspace() {
		this.setName("");
		this.setAirspace("");
		this.setAltBlock("", "");
	}

	/**
	 * @return the name of this airspace
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @param val - the new name for this airspace
	 */
	public void setName(String val) {
		this.name = val;
	}

	/**
	 * @param c - new color for this airspace
	 */
	public void setColor(Color c) {
		this.color = c;
	}

	/**
	 * @return the color of the highlighting for this airspace
	 */
	public Color getColor() {
		return this.color;
	}

}
