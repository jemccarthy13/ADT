package datastructures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.ATOGeneratorFrame;
import swing.GUI;
import table.ATOTableModel;
import utilities.Configuration;
import utilities.DebugUtility;

/**
 * A list of assets that are in the ATO.
 */
public class ATOData extends ArrayList<ATOAsset> {
	/**
	 * A file chooser to help choose files.
	 */
	static JFileChooser fc = new JFileChooser();

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
			GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).repaint();
		}
	}

	/**
	 * Choose and load a file that is an ATO project
	 */
	public static void loadAssets() {
		Configuration.getInstance().setLoadSuccess(false);
		File f = new File(".");

		fc.setDialogTitle("Choose ATO projecct file...");
		fc.setCurrentDirectory(f);
		fc.setFileFilter(new FileNameExtensionFilter("ATO Proj Files", "proj", "PROJ"));

		int result = JFileChooser.CANCEL_OPTION;
		String configFilePath = Configuration.getInstance().getATOProjFileLoc();

		if (!configFilePath.equals("")) {
			File testFile = new File(configFilePath);
			if (testFile.exists()) {
				fc.setSelectedFile(testFile);
				fc.approveSelection();
				result = JFileChooser.APPROVE_OPTION;
			}
		} else {
			result = fc.showOpenDialog(null);
		}

		if (result == JFileChooser.APPROVE_OPTION) {
			Configuration.getInstance().setLoadSuccess(true);
			f = fc.getSelectedFile();

			if (f != null) {
				try {
					FileInputStream is = new FileInputStream(f.getAbsolutePath());
					ObjectInputStream ois = new ObjectInputStream(is);
					ATOData.instance = (ATOData) ois.readObject();
					GUI.MODELS.getInstanceOf(ATOTableModel.class).setItems(instance);

					GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).repaint();
					GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).validate();
					ois.close();
					is.close();

					String message = "Project loaded: \n" + "-- " + f.getAbsolutePath();
					DebugUtility.debug(ATOData.class, message);

				} catch (IOException e) {
					DebugUtility.error(ATOData.class, "Error loading " + f.getName(), e);
				} catch (ClassNotFoundException e) {
					DebugUtility.error(ATOData.class, "Unable to cast to ATOData.");
				}
			}
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
				writer.close();
			} catch (IOException e) {
				DebugUtility.error("Error writing test ATO.");
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
				oos.close();
				fos.close();
			} catch (IOException e2) {
				DebugUtility.error(ATOData.class, "Unable to close resources.");
			}
		}
	}
}
