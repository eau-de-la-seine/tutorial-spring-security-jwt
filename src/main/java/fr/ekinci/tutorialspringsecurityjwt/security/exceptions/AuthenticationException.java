package fr.ekinci.tutorialspringsecurityjwt.security.exceptions;

import fr.ekinci.tutorialspringsecurityjwt.commons.exceptions.AbstractException;
import org.springframework.http.HttpStatus;

/**
 * @author Gokan EKINCI
 */
public class AuthenticationException extends AbstractException {

	public AuthenticationException(String message) {
		super(message);
	}

	@Override
	public HttpStatus getStatus() {
		return HttpStatus.UNAUTHORIZED;
	}

	@Override
	public String getErrorCode() {
		return "UNAUTHORIZED_TO_ACCESS_ROUTE";
	}
}
