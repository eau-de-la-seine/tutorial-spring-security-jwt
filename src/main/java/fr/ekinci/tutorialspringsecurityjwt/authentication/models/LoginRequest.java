package fr.ekinci.tutorialspringsecurityjwt.authentication.models;

import lombok.Data;

/**
 * US: https://rapsodie.gal.local/issues/11135
 *
 * @author Gokan EKINCI
 */
@Data
public class LoginRequest {
	private String username, password;
	private boolean rememberMe;
}
