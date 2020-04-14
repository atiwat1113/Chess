package myException;

public class IsPromotingException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private String message;

	public IsPromotingException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
