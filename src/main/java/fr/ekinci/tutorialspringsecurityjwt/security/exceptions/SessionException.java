package fr.ekinci.tutorialspringsecurityjwt.security.exceptions;

import fr.ekinci.tutorialspringsecurityjwt.commons.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

/**
 * @author Gokan EKINCI
 */
public class SessionException extends AbstractException {
	public SessionException(String message, Throwable t) {
		super(message, t);
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.UNAUTHORIZED;
	}

	@Override
	public String getErrorCode() {
		return "UNAUTHORIZED_TO_GET_SESSION";
	}
}
