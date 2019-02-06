package messages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import structures.ATOAssets;
import utilities.Configuration;
import utilities.DebugUtility;

/**
 * A message containing the location of the ATODAT file
 */
public class ADTAtoDatMessage extends ADTBaseMessage {

	/** Serialization */
	private static final long serialVersionUID = -2504237197163913604L;

	private String atoLoc = " ";

	/**
	 * Public constructor
	 */
	public ADTAtoDatMessage() {
		super();
		this.atoLoc = Configuration.getInstance().getATODatFileLoc();
	}

	@Override
	public void process() {
		this.atoLoc = this.atoLoc.trim();
		if (!this.atoLoc.trim().equals("")) {
			DebugUtility.debug(ADTAtoDatMessage.class, "Loading: " + this.atoLoc);
			File f = new File(this.atoLoc);
			try {
				System.out.println(ATOAssets.staticInstance().size());
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				ATOAssets.resetInstance((ATOAssets) (ois.readObject()));
				ois.close();
				DebugUtility.debug(ADTAtoDatMessage.class, "Updated ATO Lookup");
				System.out.println(ATOAssets.staticInstance().size());
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
		return "atodat," + this.atoLoc;
	}

}
