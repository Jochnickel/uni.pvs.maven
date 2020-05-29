package de.uulm.sp.pvs.sokoban;

public class InvalidFileException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidFileException(String s) {
        super(s);
    }
}