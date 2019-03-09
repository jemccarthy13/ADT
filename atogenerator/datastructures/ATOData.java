package datastructures;

import java.awt.Component;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import gui.ATOGeneratorFrame;
import structures.Asset;
import structures.ListOfAsset;
import swing.SingletonHolder;
import utilities.DebugUtility;

/**
 * A list of assets that are in the ATO.
 */
public class ATOData extends ListOfAsset {
	/**
	 * A file chooser to help choose files.
	 */
	static JFileChooser fc = new JFileChooser();

	private static final long serialVersionUID = 1480309653414453245L;
	private static ATOData instance = new ATOData();

	private ATOData() {
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
			((Component) SingletonHolder.getInstanceOf(ATOGeneratorFrame.class)).repaint();
		}
	}

	/**
	 * @param readObject
	 */
	protected static void setInstance(ATOData readObject) {
		ATOData.instance = readObject;
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
		for (Asset asst : instance) {
			if (!asst.isBlank()) {
				DebugUtility.debug(ATOData.class, asst.toString());
				msnDat += asst.toString();
			}
		}
		atoStr = atoStr + msnDat;

		String filePath = "ATO YA CHG 0 USMTF00.txt";

		DebugUtility.debug(ATOData.class, atoStr);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(filePath, false));
			writer.write(atoStr);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (IOException e) {
				DebugUtility.error(ATOData.class, "Error writing test ATO.");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @throws IOException
	 * 
	 */
	public void save() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream("ATOYA.proj");
			oos = new ObjectOutputStream(fos);
			// write object to file
			oos.writeObject(this);
			DebugUtility.debug(ATOData.class, "ATO Project saved.");
			// closing resources
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			try {
				if (oos != null)
					oos.close();
				if (fos != null)
					fos.close();
			} catch (IOException e2) {
				DebugUtility.error(ATOData.class, "Unable to close resources.");
			}
		}
	}

	@Override
	public void create() {
		this.add(new ATOAsset());
	}
}
