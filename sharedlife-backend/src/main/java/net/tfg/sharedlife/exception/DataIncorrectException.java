package net.tfg.sharedlife.exception;

public class DataIncorrectException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public DataIncorrectException(String message) {
		super(message);
	}
}
