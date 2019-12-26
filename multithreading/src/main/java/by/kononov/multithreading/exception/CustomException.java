package by.kononov.multithreading.exception;

/**
 * This is the CustomException class; it is used to create custom
 * exceptions.
 * 
 * @author Vitaly Kononov
 * @since 2019-12-12
 */
public class CustomException extends Exception{
	private static final long serialVersionUID = 1L;

	public CustomException() {
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}
}