package ato;

import javax.swing.JFrame;

import utilities.ImageLibrary;

/**
 * A class to generate valid, formatted ATOs in USMTF00.txt format.
 *
 */
public class ATOMaker extends JFrame {

	private static final long serialVersionUID = 6718631382663500003L;

	private static ATOMaker instance = new ATOMaker();

	/**
	 * Singleton instance function
	 * 
	 * @return single ATOMaker instance
	 */
	public static ATOMaker getInstance() {
		return instance;
	}

	private ATOMaker() {
		this.add(ATOPanel.getInstance());
		this.setSize(1600, 600);
		this.setLocationRelativeTo(null);
		this.setIconImage(ImageLibrary.getImage("AF-Roundel"));
		// FontUIResource font = new FontUIResource("Verdana", Font.PLAIN, 24);
		// UIManager.put("Table.font", font);
		this.setTitle("ATO Generator");
	}

	/**
	 * Starting point of ATO maker
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		instance.setVisible(true);
		instance.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
