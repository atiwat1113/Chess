package myException;

public class NullPointException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public NullPointException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}

}
 