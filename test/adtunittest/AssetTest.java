package adtunittest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import structures.Asset;

/**
 * Test compare for Asset sort
 */
public class AssetTest {

	/**
	 * Test asset isBlank check
	 */
	@Test
	public void testIsBlank() {
		Asset a = new Asset();
		Assert.assertTrue(a.isBlank());
		a = new Asset("a", "", "", "", "", "", "");
		Assert.assertFalse(a.isBlank());
		a = new Asset("", "a", "", "", "", "", "");
		Assert.assertFalse(a.isBlank());
		a = new Asset("", "", "a", "", "", "", "");
		Assert.assertFalse(a.isBlank());
		a = new Asset("", "", "", "a", "", "", "");
		Assert.assertFalse(a.isBlank());
		a = new Asset("", "", "", "", "a", "", "");
		Assert.assertFalse(a.isBlank());
		a = new Asset("", "", "", "", "", "a", "");
		Assert.assertFalse(a.isBlank());
		a = new Asset("", "", "", "", "", "", "a");
		Assert.assertFalse(a.isBlank());

		a = new Asset("", "", "", "", "", "", "");
		a.setAltBlock("100", "");
		Assert.assertFalse(a.isBlank());

		a = new Asset("", "", "", "", "", "", "");
		a.setAltBlock("", "100");
		Assert.assertFalse(a.isBlank());

		a = new Asset("", "", "", "", "", "", "");
		a.setAirspace("a");
		Assert.assertFalse(a.isBlank());

		a = new Asset("", "", "", "", "", "", "");
		a.setStatus("a");
		Assert.assertFalse(a.isBlank());

		a = new Asset("", "", "", "", "", "", "");
		a.getID().setTypeCat("a");
		Assert.assertFalse(a.isBlank());

		a = new Asset("", "", "", "", "", "", "");
		a.setARData("a");
		Assert.assertFalse(a.isBlank());

		a = new Asset("", "", "", "", "", "", "");
		a.setOnStation("a");
		Assert.assertFalse(a.isBlank());

		a = new Asset("", "", "", "", "", "", "");
		a.setOffStation("a");
		Assert.assertFalse(a.isBlank());
	}

	/**
	 * Test asset isBlank check
	 */
	@Test
	public void testAltSep() {
		Asset a = new Asset();
		a.getID().setTypeCat("FTR");

		Asset b = new Asset();
		b.getID().setTypeCat("FTR");
		Assert.assertTrue(a.altSep(b) == 9);

		b.getID().setTypeCat("RPA");
		Assert.assertTrue(a.altSep(b) == 9);

		a.getID().setTypeCat("LFW");
		Assert.assertTrue(a.altSep(b) == 9);

		a.getID().setTypeCat("RPA");
		Assert.assertTrue(a.altSep(b) == 4);

	}

	/**
	 * Test the compare operation for sorting a List of Assets
	 */
	@Test
	public void testcompare() {
		Asset a = new Asset();
		a.setAltBlock("200", "200");
		Asset o = new Asset();
		o.setAltBlock("100", "100");
		Asset p = new Asset();
		p.setAltBlock("500", "500");
		Asset w = new Asset();
		w.setAltBlock("000", "000");
		Asset k = new Asset();
		k.setAltBlock("150", "");
		Asset m = new Asset();
		m.setAltBlock("", "");
		Asset n = new Asset();
		n.setAltBlock("", "");

		ArrayList<Asset> testList = new ArrayList<Asset>();

		testList.add(a);
		testList.add(o);
		testList.add(p);
		testList.add(w);
		testList.add(k);
		testList.add(m);
		testList.add(n);

		Collections.sort(testList);

		Assert.assertTrue(testList.get(0).equals(p));
	}

	/**
	 * Test the ability of assets to detect overlap
	 */
	@Test
	public void testAsset2DOverlap() {
		Asset first = new Asset();
		first.setAirspace("88AM123");
		Asset second = new Asset();
		second.setAirspace("88AM");

		HashSet<String> result = first.sharesAirspaceWith(second);

		ArrayList<String> list = new ArrayList<String>(result);
		Collections.sort(list);
		Assert.assertTrue(list.size() == 3);
		Assert.assertTrue(list.toString().equals("[88AM1, 88AM2, 88AM3]"));

		first.setAirspace("89AM");
		second.setAirspace("88AM");

		result = first.sharesAirspaceWith(second);
		Assert.assertTrue(result.size() == 0);
	}

	/**
	 * Test the ability of assets to detect overlap
	 */
	@Test
	public void testAssetStrings() {
		Asset first = new Asset("EE01", "1111", "", "F15C", "FTR", "EAGLE01", "");
		first.setAirspace("88AM");
		first.setAltBlock("100", "200");
		Assert.assertTrue(first.toString().equals("EE01/1111/88AM/F15C/FTR/EAGLE01/"));

		Assert.assertTrue(first.getApproval().trim().equals("EE01 1111 /  / 88AM / FL 100 to FL 200 /"));
	}

	/**
	 * Test the ability of assets to detect overlap
	 */
	@Test
	public void testAsset3DOverlap() {
		Asset first = new Asset();
		first.setAirspace("88AM");
		first.setAltBlock("100", "200");
		Asset second = new Asset();
		second.setAirspace("88AM");
		second.setAltBlock("190", "200");

		HashSet<String> result = first.conflictsWith(second);

		ArrayList<String> list = new ArrayList<String>(result);
		Collections.sort(list);
		Assert.assertTrue(list.size() == 9);
		Assert.assertTrue(list.toString().equals("[88AM1, 88AM2, 88AM3, 88AM4, 88AM5, 88AM6, 88AM7, 88AM8, 88AM9]"));

		first.setAltBlock("210", "210");
		result = first.conflictsWith(second);
		Assert.assertTrue(result.size() == 0);

		second.setAltBlock("210", "210");
		result = first.conflictsWith(second);
		Assert.assertTrue(result.size() == 9);
	}

}
