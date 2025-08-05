package de.organizer.exception;

/**
 * Wird geworfen, wenn ein erforderliches Feld bei einer Objekterzeugung fehlt.
 */

public class MissingFieldException extends RuntimeException {
	
	private static final long serialVersionUID = -5915924179481754602L;

	public MissingFieldException(String message) {
		
		super(message);
	}

}
