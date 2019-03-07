package datastructures;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import gui.ATOGeneratorFrame;
import swing.SingletonHolder;
import table.ATOTableModel;
import utilities.ADTTableModel;
import utilities.DebugUtility;
import utilities.FileImporter;

/**
 * An Importer for ATO .PROJ files
 */
public class ATOProjectImporter implements FileImporter {

	@Override
	public void doImport(File f) {
		if (f != null) {
			try {
				FileInputStream is = new FileInputStream(f.getAbsolutePath());
				ObjectInputStream ois = new ObjectInputStream(is);
				ATOData.setInstance((ATOData) ois.readObject());
				((ADTTableModel<?>) SingletonHolder.getInstanceOf(ATOTableModel.class)).setItems(ATOData.getInstance());

				((Component) SingletonHolder.getInstanceOf(ATOGeneratorFrame.class)).repaint();
				((Component) SingletonHolder.getInstanceOf(ATOGeneratorFrame.class)).validate();
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
}
