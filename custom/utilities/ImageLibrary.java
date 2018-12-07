package utilities;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

/**
 * Create all images used in game for reference.
 */
public class ImageLibrary extends HashMap<String, ImageIcon> {

	private static final long serialVersionUID = 8298201257452310707L;

	/**
	 * The location of the graphics library
	 */
	private String libPath = "resources/";

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
		Image img = null;
		ImageIcon icon = getInstance().getImageIcon(name);
		DebugUtility.debug(ImageLibrary.class, "Icon: " + icon);
		if (icon == null) {
			DebugUtility.debug(ImageLibrary.class, "Unable to resolve " + name);
		} else {
			img = icon.getImage();
		}
		return img;
	}

	/**
	 * Returns an image from the instance of the library if the image exists. If the
	 * image does not exist, try some of the graphics library paths in order to load
	 * the graphic flyweight
	 * 
	 * @param name - the image to look for
	 * @return an ImageIcon, if it can be created
	 */
	public ImageIcon getImageIcon(String name) {
		String fullName = name + ".jpg";
		ImageIcon retVal = null;
		// if the image exists, get it and return it
		if (containsKey(name)) {
			retVal = get(name);
		} else {
			if (!tryPath("./", fullName)) {
				DebugUtility.error(ImageLibrary.class, "Unable to load: '" + fullName + "'");
			}

			if (containsKey(fullName)) {
				retVal = get(fullName);
			}
		}
		return retVal;
	}

	/**
	 * Try to find the image resource in a given relative path
	 * 
	 * @param path - the relative path to look in
	 * @param name - the name of the image to find
	 * @return whether or not the path had the image
	 */
	public boolean tryPath(String path, String name) {
		boolean success = false;
		if (name == null) {
			DebugUtility.error("Unable to load: '" + name + "' " + path);
		} else {

			File folder = new File(this.libPath + path + "/");
			File[] listOfFiles = folder.listFiles();
			if (listOfFiles != null) {
				for (File file : listOfFiles) {
					if (file.isFile() && name.toUpperCase().equals(file.getName().toUpperCase())) {
						put(name, createImage(file.getPath().replaceAll("\\\\", "/")));
						success = true;
						continue;
					}
				}
			}
		}

		return success;
	}

	/**
	 * Loads an image from a given path
	 * 
	 * @param path
	 * @return an ImageIcon if it was loaded, otherwise an error
	 */
	public static ImageIcon createImage(String path) {
		File f = new File(path);
		ImageIcon thisIcon = null;
		if (!f.exists()) {
			DebugUtility.error("Image path does not exist: " + path);
		}
		String fullPath = "/" + path.replace("resources/", "").replace("resources\\", "");
		URL iconURL = System.class.getResource(fullPath);
		// this is the path within the jar file

		if (iconURL == null) {
			DebugUtility.error("Cannot find resource" + fullPath);
		} else {
			thisIcon = new ImageIcon(System.class.getResource(fullPath));
		}
		return thisIcon;
	}
}