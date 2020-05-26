package de.uulm.sp.pvs.sokoban;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SokobanLevel {
    FileInputStream xsdFile = new FileInputStream("sokoban.xsd");

    final char[][] board;
    final String name;
    Difficulty difficulty;

    SokobanLevel(String pathToXML) throws FileNotFoundException, InvalidFileException {
        if (isValidXML(pathToXML)){}
    }

    static boolean isValidXML(String pathToXML, String pathToXSD) throws FileNotFoundException {
        
        return false;
    }

    public class InvalidFileException extends Exception {
        private static final long serialVersionUID = 1L;
    }
}