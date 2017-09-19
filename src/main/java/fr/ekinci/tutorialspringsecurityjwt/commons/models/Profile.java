package fr.ekinci.tutorialspringsecurityjwt.commons.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gokan EKINCI
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
	// TODO: Add other required fields
	private String id, firstName, lastName, email;
}
