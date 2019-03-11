package utilities;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

/**
 * Create all images used in game for reference.
 */
public class ImageLibrary extends HashMap<String, Image> {

	private static final long serialVersionUID = 8298201257452310707L;

	private static ImageLibrary m_instance = new ImageLibrary();

	/**
	 * Default private constructor - singleton fly weight pattern
	 */
	private ImageLibrary() {
		DebugUtility.trace(this.getClass(), "On the fly initialized image library");
	}

	/**
	 * Returns the single instance of the sprite library
	 * 
	 * @return SpriteLibrary instance
	 */
	public static ImageLibrary getInstance() {
		return m_instance;
	}

	/**
	 * Static access wrapper method to retrieve a paintable image
	 * 
	 * @param name - the image to look for
	 * @return an Image if it can be retrieved
	 */
	public static Image getImage(String name) {
		Image retVal;
		if (m_instance.containsKey(name)) {
			retVal = m_instance.get(name);
		} else {
			Image img = null;
			ImageIcon icon = null;
			URL location = ImageLibrary.class.getClassLoader().getResource(name);

			if (location == null)
				location = ImageLibrary.class.getClassLoader().getResource("graphics/" + name);
			if (location == null)
				location = ImageLibrary.class.getClassLoader().getResource("resources/" + name);

			if (location != null)
				icon = new ImageIcon(location);

			if (icon != null) {
				icon.setDescription(name);
				DebugUtility.trace(ImageLibrary.class, "On the fly loaded " + icon.getDescription());
				img = icon.getImage();
			}
			m_instance.put(name, img);
			retVal = img;
		}
		return retVal;
	}
}