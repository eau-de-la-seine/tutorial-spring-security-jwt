package fr.ekinci.tutorialspringsecurityjwt.commons.exceptions;

import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Gokan EKINCI
 */
@ToString
public abstract class AbstractException extends RuntimeException {

	protected ResponseEntity forwardedResponse;

	public AbstractException(String message) {
		super(message);
	}

	public AbstractException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor to build {@link AbstractException} with a message and existing {@link ResponseEntity}
	 *
	 * @param message			indication {@link String} to be displayed to end user
	 * @param responseEntity	{@link ResponseEntity} to be returned by api
	 */
	public AbstractException(String message, ResponseEntity responseEntity) {
		super(message);
		this.forwardedResponse = responseEntity;
	}

	public abstract HttpStatus getStatus();

	public abstract String getErrorCode();

	public ResponseEntity getForwardedResponse() {
		return this.forwardedResponse;
	}

	/**
	 * Check if exception contains inner forwarded response to process
	 *
	 * @return					true if {@code forwardedResponse} is not empty, false else
	 */
	public boolean hasForwardedResponse() {
		return forwardedResponse != null;
	}
}
