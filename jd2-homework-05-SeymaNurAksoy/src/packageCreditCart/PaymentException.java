package packageCreditCart;

public class PaymentException extends Exception {

	private final String text;

	public PaymentException(String message, String text) {
		super(message);
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
