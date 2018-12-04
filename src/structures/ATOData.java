package structures;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import ato.ATOAsset;
import ato.ATOMaker;
import utilities.DebugUtility;

/**
 * A list of assets that are in the rundown.
 * 
 * @author John McCarthy
 */
public class ATOData extends ArrayList<ATOAsset> {

	private static final long serialVersionUID = 1480309653414453245L;
	private static ATOData instance = new ATOData();

	private ATOData() {
		this.add(new ATOAsset());
	}

	/**
	 * Singleton implementation
	 * 
	 * @return - single instance
	 */
	public static ATOData getInstance() {
		return instance;
	}

	/**
	 * Remove all assets from the rundown.
	 */
	public static void zeroize() {
		instance.clear();
	}

	/**
	 * Add a new blank asset to the rundown
	 */
	public static void addNew() {
		instance.add(new ATOAsset());
	}

	/**
	 * Check the rundown for an entirely blank row. If one doesn't exist, add one
	 * (to create the feel of a continuous form)
	 */
	public static void checkAddNew() {
		boolean blankRow = false;
		for (int x = 0; x < instance.size(); x++) {
			if (instance.get(x).isBlank()) {
				blankRow = true;
			}
		}
		if (!blankRow) {
			addNew();
			ATOMaker.getInstance().repaint();
		}
	}

	/**
	 * 
	 */
	public static void output() {
		String heading = "";

		heading += "OPER/VALIANT CHASE--UNCLASSIFIED//\n";
		heading += "REM/COSMO SHIELD//";
		heading += "MSGID/ATO/AOC 609/ATOWY/JUN/CHG 0//\n";
		heading += "REF/A/SPINS/CAOC/YMD:20240413//\n";
		heading += "REF/B/WEEKLY SPINS/CAOC/YMD:20240518//\n";
		heading += "REF/C/ACO/CAOC/YMD:20240413//\n";
		heading += "ADKNLDG/NO//\n";
		heading += "TIMEFRAM/FROM:040000ZJUN2018/TO:042359ZJUN2018//\n";
		heading += "HEADING/TASKING//\n";
		heading += "TASKCNTRY/US//\n";
		heading += "SVCTASK/F//\n";
		heading += "TASKUNIT/552 AEW (AF)/ICAO:KTIK//\n";

		String atoStr = heading;

		// AMSNDAT/MSNNO/AMC MSN/PKG ID/MSN CC/PRIM MSN TYPE/SEC MSN TYPE/ALERT
		// STATUS/DEPARTURE LOC/RECOVERY LOC//
		// MSNACFT/NO. AC/AC TYPE & MODEL/CALLSIGN/CONFIG/SEC CONFIG/IFF MODE1/IFF
		// MODE2/IFFMODE3//
		String msnDat = "";
		for (ATOAsset asst : instance) {
			System.out.println(asst.toString());
			msnDat += asst.toString();
		}
		atoStr = atoStr + msnDat;

		String filePath = "ATO YA CHG 0 USMTF00.txt";

		System.out.println(atoStr);
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
