package utilities;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * A static location for Fonts.
 */
public class Fonts {

	private Fonts() {
	}

	/** Size 20 plain SansSerif */
	public static Font serif = new Font("SansSerif", Font.PLAIN, 20);

	/** Size 20 Bold SansSerif */
	public static Font serifBold = new Font("SansSerif", Font.BOLD, 20);

	/**
	 * Set the font for user interface elements
	 * 
	 * @param f - the font to use
	 */
	public static void setUIFont(FontUIResource f) {
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				FontUIResource orig = (FontUIResource) value;
				Font font = new Font(f.getFontName(), orig.getStyle(), f.getSize());
				UIManager.put(key, new FontUIResource(font));
			}
		}
	}
}
