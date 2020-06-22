package de.uulm.sp.pvs.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;

import de.uulm.sp.pvs.sokoban.SokobanLevel;

public class SokobanUtils {
	public static void main(String[] args) throws IOException {
		// loadLevels("/home/jj17/pvs/Blatt09/");
		final Path startingDir = Paths.get(args[0]);
		PathMatcher matcher =
    FileSystems.getDefault().getPathMatcher("glob:*.{java,class}");

	Path filename = startingDir
	if (matcher.matches(filename)) {
		System.out.println(filename);
	}
	}

	/**
	 * 
	 * @param dirPath folder with multiple Sokoban_level.xml files
	 * @return List<> of created Sokobanlevels
	 */
	public static List<SokobanLevel> loadLevels(String dirPath) {

		File folder = new File(dirPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("f      " + listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("d      " + listOfFiles[i].getName());
			}
		}

		matcher = FileSystems.getDefault()
                    .getPathMatcher("glob:*.txt");
		final fileName = startingDir.getFileName();
		if (null!=	fileName &&  mat)
		
		return null;
	}
}