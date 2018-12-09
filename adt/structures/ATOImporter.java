package structures;

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

import atoLookup.ATOLookupFrame;
import swing.GUI;
import utilities.Configuration;
import utilities.DebugUtility;
import utilities.Output;
import utilities.Patterns;

/**
 * Import ATO assets from an ATO USMTF file
 */
public class ATOImporter implements Importer {
	/**
	 * Perform the import of the ATO
	 * 
	 * TODO - need to show settings for import (i.e. msn code checkbox, and
	 * potentially time filter)
	 */
	@Override
	public void doImport(File f) {

		if (f != null) {
			String rootFolder = f.getParent();
			int numFilesProc = 0;

			HashMap<String, String> typeMap = new HashMap<String, String>();
			System.err.println("Need to store type map / type manager.");

			File rootDir = new File(rootFolder);

			for (File file : rootDir.listFiles()) {
				if (Patterns.extPattern.matcher(file.getName()).find()) {
					DebugUtility.debug(ATOAssets.class, "Processing: " + file.getName());
					try {
						processFile(f.getPath(), typeMap);
						numFilesProc++;
					} catch (IOException e) {
						DebugUtility.debug(ATOAssets.class,
								"Unable to process: " + f.getName() + "\n" + e.getMessage());
					}
				}
			}

			if (numFilesProc == 0) {
				Output.showInfoMessage("Error", "No ATO files (USMTF00.txt) were imported.\n"
						+ "PLease ensure you are selecting a valid USMTF00.txt file.");
			} else
				Output.showInfoMessage("Proceesed", "Processed " + numFilesProc + " file(s) and added "
						+ ATOAssets.staticInstance().size() + " asset(s).");
		} else {
			Output.showInfoMessage("No file selected.", "No ATO CHG 0 file selected.");
		}

		File atoInfoFile = new File("ATOinfo.dat");
		try {
			atoInfoFile.createNewFile();
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(atoInfoFile));
			oos.writeObject(ATOAssets.staticInstance());
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Configuration.getInstance().setATODatFileLoc(atoInfoFile.getAbsolutePath());
		GUI.FRAMES.getInstanceOf(ATOLookupFrame.class).repaint();
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

		System.err.println("Need 'other code' functionality");
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
	static void processFile(String path, HashMap<String, String> typeMap) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(new File(path)));

		String line = "";
		String fileContents = "";
		String[] strLineArray;

		String x;
		x = in.readLine();
		while (x != null) {
			fileContents += x;
			x = in.readLine();
		}
		in.close();

		fileContents = fileContents.replace("\r\n", "").replace("\n", "");

		strLineArray = fileContents.split("AMSNDAT");

		// int lineCount = strLineArray.length;

		Pattern strPattern = getDesiredMsnCode();

		// Forms(IMPORTOPTIONS).ProgressLbl.width = 0
		// Forms(IMPORTOPTIONS).ProgressLbl.Visible = True
		// Forms(IMPORTOPTIONS).ProcessingLbl.Caption = "Processing: " & fName

		boolean msnCodeMatch;
		for (int y = 0; y < strLineArray.length; y++) {
			line = strLineArray[y];
			msnCodeMatch = strPattern.matcher(line).find();
			if (msnCodeMatch) {
				processLine(line.toString(), typeMap);
			}

			// DebugUtility.debug(ATOAssets.class, ((((double) (y) / lineCount)) * 100 + " %
			// complete"));
			// Forms(IMPORTOPTIONS).ProgressLbl.width = CInt((lineCount / totalCount) *
			// (Forms(IMPORTOPTIONS).ProgressFrame.width * 0.95))
			// Forms(IMPORTOPTIONS).Repaint
			/// lineCount = lineCount + 1
		}

		DebugUtility.debug(ATOAssets.class, "ATO load 100% complete");
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

		data = data.replace(".", "");

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
		// String onStation = m.group(1);
		// String offtation = m.group(2);

		// String[] strArray = data.split("//");

		// Pattern arctPatt = Pattern.compile("(ARCT)");
		// Pattern infoPatt = Pattern.compile(".*NAME:(.*)-/");

		String arinfo = "AR Data CAO ATO CHG 0";

		// If getGroupMatches(strPattern, CStr(st)).count > 0 Then
		// Set matches = getGroupMatches(infoPatt, CStr(st))
		// mat = matches(0)
		// arinfo = matches(1)
		// If (Len(mat) > 0) Then
		// arinfo = arinfo & CStr(mat) & "/" & arinfo & Chr(10)
		// End If
		// End If
		// Next
		// If arinfo = "AR Data CAO ATO CHG 0 " & Chr(10) Then arinfo = vbNullString

		String typ = "";
		// typ = typeDict(SpecType)

		// DebugUtility.debug(ATOAssets.class,
		// tkd + "/" + mode2 + "/" + arinfo + "/" + specType + "/" + typ + "/" +
		// callsign + "/" + location);

		ATOAssets.staticInstance().add(new Asset(tkd, mode2, arinfo, specType, typ, callsign, location));

		// m2boRS.AddNew
		// m2boRS!VCS = tkd
		// m2boRS!Mode2 = Mode2
		// m2boRS!Remarks = arinfo
		// m2boRS!SpecType = SpecType
		// m2boRS!Type = typ
		// m2boRS!FullCallsign = Callsign
		// m2boRS!ICAO = Location
		// m2boRS.Update
		// }
	}
}
