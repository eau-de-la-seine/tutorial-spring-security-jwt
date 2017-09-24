package fr.ekinci.tutorialspringsecurityjwt.authentication.models;

import lombok.Data;

/**
 * @author Gokan EKINCI
 */
@Data
public class LoginRequest {
	private String username, password;
	private boolean rememberMe;
}
