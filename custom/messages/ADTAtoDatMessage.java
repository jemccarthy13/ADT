package messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import structures.ATOAssets;
import utilities.Configuration;

/**
 * A message containing the location of the ATODAT file
 */
public class ADTAtoDatMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = -2504237197163913604L;

	@Override
	public void process() {
		String atoLoc = Configuration.getInstance().getATODatFileLoc().trim();
		if (!atoLoc.equals("")) {
			File f = new File(atoLoc);
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				ATOAssets.resetInstance((ATOAssets) (ois.readObject()));
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Configuration.getInstance().setATODatFileLoc(f.getAbsolutePath());
		}
	}

	@Override
	public String getCommand() {
		return "atodat," + Configuration.getInstance().getATODatFileLoc();
	}

}
