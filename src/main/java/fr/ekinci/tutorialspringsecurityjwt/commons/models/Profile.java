package fr.ekinci.tutorialspringsecurityjwt.commons.models;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link JsonPropertyOrder} is required for Integration test result
 *
 * @author Gokan EKINCI
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "firstName", "lastName", "email"})
public class Profile {
	// TODO : Add other required fields if necessary
	private String id, firstName, lastName, email;
}
