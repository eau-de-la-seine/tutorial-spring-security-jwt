package fr.ekinci.tutorialspringsecurityjwt.security.services;

import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Gokan EKINCI
 */
public interface IJwtService {

	/**
	 * Verify subject by given HTTP request
	 *
	 * @param request		HTTP Request
	 * @return				The subject, never return null (authenticated may be false)
	 */
	Authentication verify(HttpServletRequest request);

	/**
	 * Sign subject and return JWT token
	 *
	 * @param subject		User data (may be his id)
	 * @return				JWT Token
	 */
	String sign(String subject);
}
