package myException;

public class NullEntityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message;

	public NullEntityException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
 