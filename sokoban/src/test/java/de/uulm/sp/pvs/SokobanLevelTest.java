package de.uulm.sp.pvs;

import java.io.FileNotFoundException;

import de.uulm.sp.pvs.sokoban.InvalidFileException;
import de.uulm.sp.pvs.sokoban.SokobanLevel;

public class SokobanLevelTest {
    public static void main(String[] args) throws FileNotFoundException, InvalidFileException {
        SokobanLevel level = new SokobanLevel("test_level.xml");
    }
}