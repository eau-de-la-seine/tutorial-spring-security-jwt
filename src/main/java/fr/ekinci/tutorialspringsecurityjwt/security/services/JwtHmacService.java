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
 * -> HMAC, see {@link JwtRsaService} for RSA version
 *
 * @author Gokan EKINCI
 */
@Slf4j
@Service
@Profile("jwtHmac")
public class JwtHmacService implements IJwtService {
	private static final String LOG_HEADER = "[JWT][HMAC][SERVICE]";
	private static final String BEARER_PREFIX = "Bearer ";
	private static final Authentication notAuthenticated;

	static {
		notAuthenticated = AuthenticationImpl.builder()
			.authenticated(false)
			.name("NOT_AUTHENTICATED_USER") // "Principal must not be null"
			.build();
	}

	private final String secretKey;
	private final SignatureAlgorithm algorithm;

	@Autowired
	public JwtHmacService(
		@Value("${jwt.hmac.secret.key}") String secretKey,
		@Value("${jwt.hmac.algorithm:HS512}") SignatureAlgorithm algorithm
	) {
		this.secretKey = secretKey;
		this.algorithm = algorithm;
	}

	@Override
	public Authentication verify(HttpServletRequest request) {
		final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader == null) {
			log.trace("{} Authorization header not found", LOG_HEADER);
			return notAuthenticated;
		}

		final String[] splittedAuthorizationHeader = authorizationHeader.split(BEARER_PREFIX);
		if (splittedAuthorizationHeader.length != 2) {
			log.trace("{} Authorization header bad format", LOG_HEADER);
			return notAuthenticated;
		}

		try {
			final String subject = Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(splittedAuthorizationHeader[1])
				.getBody()
				.getSubject();

			return AuthenticationImpl.builder()
				.authenticated(true)
				.principal(subject)
				.build();
		} catch (SignatureException e) {
			log.trace(String.format("%s Authorization header bad format", LOG_HEADER), e);
			return notAuthenticated;
		}
	}

	@Override
	public String sign(String subject) {
		return Jwts.builder()
			.setSubject(subject)
			.signWith(algorithm, secretKey)
			.compact();
	}
}
