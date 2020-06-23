package de.uulm.sp.pvs.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import de.uulm.sp.pvs.sokoban.InvalidFileException;
import de.uulm.sp.pvs.sokoban.SokobanLevel;

public class SokobanUtils {
	public static void main(final String[] args) throws Exception {
		loadLevels("/mnt/d/Uni/pvs/Blatt09");
	}

	/**
	 * 
	 * @param dirPath folder with multiple Sokoban_level.xml files
	 * @return List<> of created Sokobanlevels
	 */
	public static List<SokobanLevel> loadLevels(final String dirPath)
			throws FileNotFoundException, InvalidFileException {
		final var asd = Paths.get(dirPath);
		if (Files.notExists(asd)) {
			throw new FileNotFoundException();
		}
		final List<SokobanLevel> list = new LinkedList<SokobanLevel>();
		try {
			Files.list(asd).filter(a -> a.toString().endsWith(".xml")).forEach(fName -> {
				try {
					list.add(new SokobanLevel(fName.toString()));
				} catch (FileNotFoundException | InvalidFileException e) {
					System.out.printf("Didn't add %s\n", fName.toString());
				}
			});
		} catch (IOException e) {
			throw new InvalidFileException("This Path does not lead to a directory");
		}
		return list;
	}
}