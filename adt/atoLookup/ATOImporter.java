package atoLookup;

import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import structures.ATOAssets;
import structures.Asset;
import structures.ListOfAsset;
import swing.SingletonHolder;
import utilities.Configuration;
import utilities.DebugUtility;
import utilities.FileImporter;
import utilities.Output;
import utilities.Patterns;

/**
 * Import ATO assets from an ATO USMTF file
 */
public class ATOImporter implements FileImporter {
	/**
	 * Perform the import of the ATO
	 * 
	 * @todo - need to show settings for import (i.e. msn code checkbox, and
	 *       potentially time filter)
	 */
	@Override
	public void doImport(File f) {

		if (f != null) {
			String rootFolder = f.getParent();
			int numFilesProc = 0;

			HashMap<String, String> typeMap = new HashMap<String, String>();
			System.err.println("Need to store type map / type manager.");

			DebugUtility.trace(ATOImporter.class, rootFolder);
			File rootDir = new File(rootFolder);

			File[] fileList = rootDir.listFiles();

			if (fileList != null) {
				for (File file : fileList) {
					if (Patterns.extPattern.matcher(file.getName()).find()) {
						try {
							processFile(file.getPath(), typeMap);
							numFilesProc++;
						} catch (IOException e) {
							DebugUtility.debug(ATOAssets.class,
									"Unable to process: " + f.getName() + "\n" + e.getMessage());
						}
					}
				}
			}

			if (numFilesProc == 0) {
				Output.forceInfoMessage("Error", "No ATO files (USMTF00.txt) were imported.\n"
						+ "Please ensure you are selecting a valid USMTF00.txt file.");
			} else {
				ListOfAsset atoAssets = (ListOfAsset) SingletonHolder.getInstanceOf(ATOAssets.class);
				Output.showInfoMessage("Proceesed",
						"Processed " + numFilesProc + " file(s) and added " + atoAssets.size() + " asset(s).");
			}
		} else {
			Output.showInfoMessage("No file selected.", "No ATO CHG 0 file selected.");
		}

		File atoInfoFile = new File("ATOinfo.dat");
		try {
			ListOfAsset atoAssets = (ListOfAsset) SingletonHolder.getInstanceOf(ATOAssets.class);
			atoInfoFile.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(atoInfoFile));
			oos.writeObject(atoAssets);
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Configuration.getInstance().setATODatFileLoc(atoInfoFile.getAbsolutePath());
		((Component) SingletonHolder.getInstanceOf(ATOLookupFrame.class)).repaint();
	}

	/**
	 * @return a pattern to match for the desired ATO msn code
	 */
	public static Pattern getDesiredMsnCode() {

		boolean useAfghanCode = false;
		boolean useSyriaCode = true;
		boolean useIraqCode = true;
		boolean useOtherCode = false;
		String otherCode = "";
		boolean useAllCodes = true;

		String retPattern = "^/([";

		/** @todo need 'other code' functionality */
		if (useAfghanCode) {
			retPattern = retPattern + "A";
		}
		if (useSyriaCode) {
			retPattern = retPattern + "S";
		}
		if (useIraqCode) {
			retPattern = retPattern + "I";
		}
		if (useOtherCode) {
			retPattern = retPattern + otherCode;
		}

		retPattern = retPattern + "]+[0-9]+[A-Z]*)/";

		if (useAllCodes) {
			retPattern = "(.*)";
		}

		return Pattern.compile(retPattern);
	}

	/**
	 * Import assets from a particular file
	 * 
	 * @param path
	 * @param typeMap
	 * @throws IOException
	 */
	static void processFile(final String path, final HashMap<String, String> typeMap) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File(path)));

		String fileContents = "";
		final String[] strLineArray;

		String x;
		x = in.readLine();
		while (x != null) {
			fileContents += x;
			x = in.readLine();
		}
		in.close();

		fileContents = fileContents.replace("\r\n", "").replace("\n", "");

		strLineArray = fileContents.split("AMSNDAT");

		final Pattern strPattern = getDesiredMsnCode();

		// todo Auto-generated method stub
		for (int y = 0; y <= strLineArray.length; y++) {
			if (y < strLineArray.length) {
				String nline = strLineArray[y];
				boolean codeMatch = strPattern.matcher(nline).find();
				if (codeMatch && !nline.equals("")) {
					processLine(nline.toString(), typeMap);
				}
			}
		}
	}

	/**
	 * Process a particular line in the ATO
	 * 
	 * @param line    - the line to process
	 * @param typeMap - the type map so we can store FTR / TANK / etc.
	 */
	public static void processLine(String line, HashMap<String, String> typeMap) {

		String data = line;

		String[] strDataArray = data.split("/");

		// String msnNum = strDataArray[0];
		// String msnType = strDataArray[4];
		String location = strDataArray[6].replaceAll("DEPLOC:", "");

		if (Patterns.acTypePattern.matcher(data).find() == false) {
			return;
		}
		Matcher m = Patterns.acTypePattern.matcher(data);
		m.find();
		String specType = m.group(2);

		m = Patterns.fileLineCSPattern.matcher(data);
		m.find();
		String callsign = m.group(3).replace(" ", "");

		m = Patterns.vcsPattern.matcher(callsign);
		m.find();

		String firstChar = m.group(1);
		String secondChar = m.group(2);
		String numbers = m.group(3);
		String tkd = firstChar + secondChar + numbers;

		m = Patterns.atoM2Pattern.matcher(data);
		m.find();

		String mode2 = m.group(1);
		mode2 = String.format("%04d", Integer.parseInt(mode2));

		m = Patterns.msnTimingsPattern.matcher(data);
		m.find();
		String onStation = m.group(1);
		String offStation = m.group(2);

		m = Patterns.arInfoPattern.matcher(data);

		String arinfo = "";
		if (m.find()) {
			arinfo = "AR Data CAO ATO CHG 0 " + m.group(0);
		}

		String typ = "";
		// typ = typeDict(SpecType)

		ListOfAsset atoAssets = (ListOfAsset) SingletonHolder.getInstanceOf(ATOAssets.class);

		Asset newAsset = new Asset(tkd, mode2, arinfo, specType, typ, callsign, location);
		newAsset.setOnStation(onStation);
		newAsset.setOffStation(offStation);
		atoAssets.add(newAsset);
	}
}
