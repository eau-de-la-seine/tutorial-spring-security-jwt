package fr.ekinci.tutorialspringsecurityjwt.security.services;

import fr.ekinci.tutorialspringsecurityjwt.security.models.AuthenticationImpl;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * Modes:
 * -> Header with Bearer (not cookie)
 * -> RSA, see {@link JwtHmacService} for HMAC version
 *
 * @author Gokan EKINCI
 */
@Slf4j
@Service
@Profile("jwtRsa")
public class JwtRsaService implements IJwtService {
	private static final String LOG_HEADER = "[JWT][RSA][SERVICE]";
	private static final String BEARER_PREFIX = "Bearer ";
	private static final Authentication notAuthenticated;

	static {
		notAuthenticated = AuthenticationImpl.builder()
			.authenticated(false)
			.build();
	}

	private final SignatureAlgorithm algorithm;

	@Autowired
	public JwtRsaService(

	) {
		// TODO
		this.algorithm = null;
	}

	@Override
	public Authentication verify(HttpServletRequest request) {
		// TODO
		throw new UnsupportedOperationException();
	}

	@Override
	public String sign(String subject) {
		// TODO
		throw new UnsupportedOperationException();
	}
}
