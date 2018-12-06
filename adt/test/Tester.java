package test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;

import server.ADTServer;
import utilities.DebugUtility;
import utilities.Patterns;

/**
 * Testing class
 * 
 * @author John McCarthy
 *
 */
public class Tester {

	/**
	 * Main entry point of tests
	 * 
	 * @param args
	 *            - command line args
	 */
	public static void main(String[] args) {
		testInject();
	}

	/**
	 * Inject messages
	 */
	public static void testInject() {
		// putting some data in the table
		for (int x = 0; x < 5; x++) {
			ADTServer.sendMessage("-1,set,DUDE" + x + "," + x + ",0", -1);
		}
	}

	/**
	 * Test pattern matching
	 * 
	 */
	public static void testRegex() {
		Matcher typeMatcher = Patterns.acTypePattern.matcher("OTHAC:F22");
		typeMatcher.find();
		System.out.println(typeMatcher.group(2));
	}

	/**
	 * Generate a fake ATO file.
	 */
	public static void generateATO() {
		String atoStr;

		atoStr = "OPER/TEST TEST2--UNCLASSIFIED//\n" + "MSGID/ATO/AOC 609/ATOWY/JUN/CHG//\n" + "ADKNLDG/NO//\n"
				+ "TIMEFRAM/FROM:040000ZJUN2018/TO:042359ZJUN2018//\n" + "HEADING/TASKING//\n" + "TASKCNTRY/AF//\n"
				+ "SVCTASK/F//\n" + "TASKUNIT/999 AEW (AF)/ICAO:KTIK//\n";
		String msnDat;
		for (int x = 1; x < 1001; x++) {
			String formatX;
			formatX = String.format("%d", x, 4);
			msnDat = "AMSNDAT/I" + formatX + "/-/-/-/GDALT/-/1H/DEPLOC:KTIK/ARRLOC:KTIK//\n"
					+ "MSNACFT/1/ACTYP:F22/RAPTOR" + formatX + "/-/-/144/2" + formatX + "/3" + formatX + "//\n"
					+ "AMSNLOC/04000ZJUN2018/042359ZJUN2018//";

			atoStr = atoStr + msnDat;
		}

		String filePath = "ATO XX CHG 0 USMTF00.txt";

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filePath, true));
			writer.write(atoStr);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				DebugUtility.printError("Error writing test ATO.");
				e.printStackTrace();
			}
		}

	}
}
