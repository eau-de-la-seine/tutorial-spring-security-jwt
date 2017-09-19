package fr.ekinci.tutorialspringsecurityjwt.security.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

/**
 * A POJO Implementation for {@link org.springframework.security.core.Authentication}
 *
 * @author Gokan EKINCI
 */
@Data
@Builder
public class AuthenticationImpl implements Authentication {
	private Collection<? extends GrantedAuthority> authorities;
	private Serializable credentials;
	private Serializable details;
	private String principal;
	private boolean authenticated;
	private String name;
}