package de.uulm.sp.pvs.sokoban;

public class ValidationFileNotFoundException extends RuntimeException {
    public ValidationFileNotFoundException(Exception e) {
        super(e);
    }

    public ValidationFileNotFoundException(String message) {
        super(message);
	}

	private static final long serialVersionUID = 1L;
}