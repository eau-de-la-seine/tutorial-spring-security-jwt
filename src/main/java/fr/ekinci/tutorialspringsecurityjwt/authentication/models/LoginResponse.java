package fr.ekinci.tutorialspringsecurityjwt.authentication.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import fr.ekinci.tutorialspringsecurityjwt.commons.models.Profile;
import fr.ekinci.tutorialspringsecurityjwt.security.models.Session;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class is similar to {@link Session} but has a token field
 *
 * {@link JsonPropertyOrder} is required for Integration test result
 *
 * @author Gokan EKINCI
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"guid", "passwordExpired", "profile", "token"})
public class LoginResponse {
	private String guid;
	private Boolean passwordExpired;
	private Profile profile;
	private String token;
}
