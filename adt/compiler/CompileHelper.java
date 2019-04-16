package compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * A helper utility to write a small compile script
 */
public class CompileHelper {

	/**
	 * Main starting point to generate the script
	 * 
	 * @param args - command line args
	 */
	public static void main(String[] args) {
		String startDir = ".";

		File f = new File(startDir);

		String classP = classPath(f, false);
		String compileCommand = "javac -cp " + (char) (34) + classP + (char) (34) + " " + classPath(f, true);
		String runCommand = "java -cp " + (char) (34) + classP + (char) (34) + " " + "main/ADTApp";
		System.out.println(compileCommand);
		System.out.println(runCommand);

		System.out.println(compileCommand.replace("javac -cp", "jar cvfe ADT.jar adt.main.ADTApp")
				.replace("java", "class").replaceAll("" + (char) (34) + ".*" + (char) (34) + " ", ""));

		// TODO - write to file
		File yourFile = new File("./commands.bat");
		try {
			yourFile.createNewFile(); // if file already exists will do nothing
			FileOutputStream oFile = new FileOutputStream(yourFile, false);
			oFile.write((compileCommand + "\n" + runCommand).getBytes());
			oFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String classPath(File f, boolean compile) {
		String retVal = "";

		if (f.getPath().contains("test") || f.getPath().contains("bin") || f.getPath().contains(".git")
				|| f.getPath().contains(".gradle") || f.getPath().contains(".settings")) {
			return "";
		}
		if (f.isDirectory()) {
			boolean hasJava = false;
			File[] fileList = f.listFiles();
			if (fileList != null) {
				for (File subdir : f.listFiles()) {
					retVal += classPath(subdir, compile);
					if (subdir.getPath().contains(".java")) {
						hasJava = true;
					}
				}
			}
			if (hasJava || (compile == false)) {
				retVal += f.getPath() + (compile ? "/*.java " : ";");
			}
		}
		return retVal.replaceAll("\\\\", "/");
	}

	/**
	 * Handle a file/directory
	 * 
	 * @param f - the file to process
	 * @return the compile script
	 */
	public static String process(File f) {
		String retVal = "";

		if (f.isDirectory()) {
			File[] fileList = f.listFiles();
			if (fileList != null) {
				for (File subdir : f.listFiles()) {
					String result = process(subdir);
					if (!result.equals("")) {
						retVal += result;
					}
				}
			}
		} else {
			String path = f.getPath();
			// System.out.println(path.substring(path.lastIndexOf("."), path.length()));
			if (path.contains(".")) {
				if (f.isFile() && path.substring(path.lastIndexOf("."), path.length()).equals(".java")) {
					// System.out.println(path);
					retVal += path + " ";
				}
			}
		}
		return retVal;
	}
}
