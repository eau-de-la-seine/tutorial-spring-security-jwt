package fr.ekinci.tutorialspringsecurityjwt.security.services;

import fr.ekinci.tutorialspringsecurityjwt.security.models.AuthenticationImpl;
import io.jsonwebtoken.JwtBuilder;
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
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

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
	private final Long duration;
	private final KeyPair rsaKeyPair;

	@Autowired
	public JwtRsaService(
			@Value("${jwt.rsa.algorithm:RS512}") SignatureAlgorithm algorithm,
			@Value("${jwt.duration:#{null}}") Long duration,
			KeyPair rsaKeyPair
	) {
		this.algorithm = algorithm;
		this.duration = duration;
		this.rsaKeyPair = rsaKeyPair;
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
				.setSigningKey(rsaKeyPair.getPublic())
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
		final JwtBuilder builder = Jwts.builder()
			.setSubject(subject)
			// .setIssuedAt(new Date()) // Can't use for Integration test
			.signWith(algorithm, rsaKeyPair.getPrivate());

		return (duration != null) ?
			builder.setExpiration(new Date(System.currentTimeMillis() + duration)).compact()
			: builder.compact();
	}
}
