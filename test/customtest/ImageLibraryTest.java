package customtest;

import java.awt.Image;

import org.junit.Assert;
import org.junit.Test;

import teststructures.BaseTest;
import utilities.DebugUtility;
import utilities.ImageLibrary;

/**
 * A JUnit test to test the image library
 */
public class ImageLibraryTest extends BaseTest {

	/**
	 * Test the image library
	 */
	@Test
	public void getInvalidImage() {
		Image i = ImageLibrary.getImage("invalid");
		Assert.assertNull(i);
	}

	/**
	 * Test the image library
	 */
	@Test
	public void getValidImage() {
		Image i = ImageLibrary.getImage("searchIcon.jpg");
		Assert.assertNotNull(i);
		DebugUtility.error(ImageLibraryTest.class, i.toString());
	}

	/**
	 * Check for memorization of images within the image library
	 */
	@Test
	public void getExistingImage() {
		ImageLibrary.getImage("AF-Roundel.jpg");
		Image i = ImageLibrary.getImage("AF-Roundel.jpg");
		Assert.assertNotNull(i);
	}
}
