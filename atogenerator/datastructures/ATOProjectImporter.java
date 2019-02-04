package datastructures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import gui.ATOGeneratorFrame;
import structures.Importer;
import swing.GUI;
import table.ATOTableModel;
import utilities.DebugUtility;

/**
 * An Importer for ATO .PROJ files
 */
public class ATOProjectImporter implements Importer {

	@Override
	public void doImport(File f) {
		try {
			FileInputStream is = new FileInputStream(f.getAbsolutePath());
			ObjectInputStream ois = new ObjectInputStream(is);
			ATOData.setInstance((ATOData) ois.readObject());
			GUI.MODELS.getInstanceOf(ATOTableModel.class).setItems(ATOData.getInstance());

			GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).repaint();
			GUI.FRAMES.getInstanceOf(ATOGeneratorFrame.class).validate();
			ois.close();
			is.close();

			String message = "Project loaded: \n" + "-- " + f.getAbsolutePath();
			DebugUtility.debug(ATOData.class, message);

		} catch (IOException e) {
			DebugUtility.error(ATOData.class, "Error loading " + f.getName(), e);
		} catch (ClassNotFoundException e) {
			DebugUtility.error(ATOData.class, "Unable to load ATOData from " + f.getName());
		}
	}
}
