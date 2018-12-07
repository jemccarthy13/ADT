package test;

import java.awt.Image;

import org.junit.Assert;
import org.junit.Test;

import utilities.ImageLibrary;

/**
 * A JUnit test to test the image library
 */
public class ImageLibraryTest extends BaseTest {

	/**
	 * Test the image library
	 */
	@Test
	public void getImageIcon() {
		ImageLibrary instance = ImageLibrary.getInstance();
		instance.getImageIcon("invalid");
		Assert.assertTrue(BaseTest.outContent.toString().contains("(ImageLibrary) Unable to load: 'invalid.jpg'"));
	}

	/**
	 * Test the image library
	 */
	@Test
	public void getImage() {
		ImageLibrary.getImage("invalid");
		Assert.assertTrue(BaseTest.outContent.toString().contains("Unable to resolve invalid"));
	}

	/**
	 * Test the image library
	 */
	@Test
	public void getValidImage() {
		ImageLibrary.getImage("searchIcon");
		Assert.assertFalse(BaseTest.outContent.toString().contains("(ImageLibrary) Unable to resolve searchIcon"));
		Assert.assertFalse(BaseTest.outContent.toString().contains("(ImageLibrary) Unable to load: 'searchIcon.jpg'"));
	}

	/**
	 * Check for memorization of images within the image library
	 */
	@Test
	public void getExistingImage() {
		ImageLibrary.getImage("AF-Roundel");
		Assert.assertFalse(BaseTest.outContent.toString().contains("(ImageLibrary) Unable to resolve AF-Roundel"));
		Assert.assertFalse(BaseTest.outContent.toString().contains("(ImageLibrary) Unable to load: 'AF-Roundel.jpg'"));
		Image i = ImageLibrary.getImage("AF-Roundel");
		Assert.assertNotNull(i);
	}

	/**
	 * Check for memorization of images within the image library
	 */
	@Test
	public void tryPath() {
		ImageLibrary.getInstance().tryPath("garbage", "other");
		ImageLibrary.getInstance().tryPath("garbage", "garbage");
	}

	/**
	 * Check for memorization of images within the image library
	 */
	@Test
	public void createImage() {
		ImageLibrary.createImage("./fakedir");
		Assert.assertTrue(BaseTest.outContent.toString().contains("Image path does not exist: ./fakedir"));
	}
}
