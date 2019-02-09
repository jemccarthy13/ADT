package adttest;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import structures.Asset;

/**
 * Test compare for Asset sort
 */
public class AssetTest {

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

		ArrayList<Asset> testList = new ArrayList<Asset>();

		testList.add(a);
		testList.add(o);
		testList.add(p);
		testList.add(w);
		testList.add(k);

		Collections.sort(testList);

		for (Asset b : testList) {
			System.out.println(b.getAlt().getLower() + "-" + b.getAlt().getUpper());
		}
	}
}
