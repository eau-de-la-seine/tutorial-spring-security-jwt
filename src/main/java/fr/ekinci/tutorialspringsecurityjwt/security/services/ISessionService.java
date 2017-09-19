package fr.ekinci.tutorialspringsecurityjwt.security.services;

import fr.ekinci.tutorialspringsecurityjwt.security.models.Session;

/**
 * @author Gokan EKINCI
 */
public interface ISessionService {

	/**
	 * Return user's session
	 *
	 * @return		User's session. It's never null.
	 */
	Session getSession();
}
