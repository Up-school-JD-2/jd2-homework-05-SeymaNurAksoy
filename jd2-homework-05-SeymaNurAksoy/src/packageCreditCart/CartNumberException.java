package packageCreditCart;

public class CartNumberException extends Exception {
	private final String text;

	public CartNumberException(String message, String text) {
		super(message);
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
