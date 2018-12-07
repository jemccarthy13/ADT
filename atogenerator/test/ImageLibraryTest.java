package test;

import org.junit.Assert;
import org.junit.Test;

import utilities.ImageLibrary;

/**
 * A JUnit test to test the image library
 */
@SuppressWarnings("static-method")
public class ImageLibraryTest extends BaseTest {

	/**
	 * Test the image library
	 */
	@Test
	public void getImageIcon() {
		ImageLibrary instance = ImageLibrary.getInstance();
		instance.getImageIcon("invalid");
		Assert.assertTrue(outContent.toString().contains("(ImageLibrary) Unable to load: 'invalid.jpg'"));
	}

	/**
	 * Test the image library
	 */
	@Test
	public void getImage() {
		ImageLibrary.getImage("invalid");
		Assert.assertTrue(outContent.toString().contains("(ImageLibrary) Unable to resolve invalid"));
	}
}
