package co.com.nexos.exceptions;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -1209327365876165187L;

	public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
