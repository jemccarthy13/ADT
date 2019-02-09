package compiler;

import java.io.File;

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

		String command = "javac -cp " + classPath(f, false) + " " + classPath(f, true);
		System.out.println(command);
	}

	private static String classPath(File f, boolean compile) {
		String retVal = "";

		if (f.getPath().contains("test") || f.getPath().contains("bin") || f.getPath().contains(".git")
				|| f.getPath().contains(".gradle") || f.getPath().contains(".settings")) {
			return "";
		}
		if (f.isDirectory()) {
			boolean hasJava = false;
			for (File subdir : f.listFiles()) {
				retVal += classPath(subdir, compile);
				if (subdir.getPath().contains(".java")) {
					hasJava = true;
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
			for (File subdir : f.listFiles()) {
				String result = process(subdir);
				if (!result.equals("")) {
					retVal += result;
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
