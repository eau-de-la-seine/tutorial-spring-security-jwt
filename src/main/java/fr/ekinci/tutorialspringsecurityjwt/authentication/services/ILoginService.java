package fr.ekinci.tutorialspringsecurityjwt.authentication.services;

import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginRequest;
import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginResponse;

/**
 * @author Gokan EKINCI
 */
public interface ILoginService {
	/**
	 * Authenticate user identified by credentials in {@code loginRequest}
	 *
	 * @param loginRequest credentials of user attempting authentication
	 * @return {@link LoginResponse} status of login response
	 */
	LoginResponse login(LoginRequest loginRequest);
}
