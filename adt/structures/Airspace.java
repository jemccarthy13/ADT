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
	private String name = "";

	/** The color highlight this airspace should use for overlapping assets */
	private Color color = Color.WHITE;

	/**
	 * Override hashcode for comparisons to only allow unique airspace names
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	/**
	 * @return true iff asset has blank values
	 */
	@Override
	public boolean isBlank() {
		return super.isBlank() && this.getName().equals("") && this.getColor() == Color.WHITE;
	}

	/**
	 * Constructor
	 * 
	 * @param name     name of the airspace
	 * @param location keypads of the airspace
	 * @param lowAlt   lower block of the airspace
	 * @param upAlt    upper block of the airspace
	 * @param color    highlight color
	 */
	public Airspace(String name, String location, String lowAlt, String upAlt, Color color) {
		this.setName(name);
		this.setAirspace(location);
		this.setAltBlock(lowAlt, upAlt);
		this.setColor(color);
	}

	/**
	 * Default constructor
	 */
	public Airspace() {
		this.setName("");
		this.setAirspace("");
		this.setAltBlock("", "");
		this.setColor(Color.WHITE);
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
