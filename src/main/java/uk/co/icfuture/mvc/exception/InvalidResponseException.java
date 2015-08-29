package uk.co.icfuture.mvc.exception;

public class InvalidResponseException extends Exception {

	private static final long serialVersionUID = -6905006405894541797L;

	public InvalidResponseException(String message) {
		super(message);
	}

}
