package fr.ekinci.tutorialspringsecurityjwt.authentication.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginRequest;
import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginResponse;
import fr.ekinci.tutorialspringsecurityjwt.commons.models.Profile;
import fr.ekinci.tutorialspringsecurityjwt.security.models.Session;
import fr.ekinci.tutorialspringsecurityjwt.security.services.IJwtService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * TODO : Create a real LoginService
 *
 * @author Gokan EKINCI
 */
@Primary
@Service
public class LoginServiceMock implements ILoginService {
	private final static String LOG_HEADER = "[LOGIN][SERVICE]";

	private final String host;
	private final RestTemplate restTemplate;
	private final Mapper dozer;
	private final ObjectMapper mapper;
	private final IJwtService jwtService;

	public LoginServiceMock(
		// String host,
		RestTemplate restTemplate,
		Mapper dozer,
		ObjectMapper mapper,
		IJwtService jwtService
	) {
		this.host = null;
		this.restTemplate = restTemplate;
		this.dozer = dozer;
		this.mapper = mapper;
		this.jwtService = jwtService;
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		final LoginResponse loginResponse = LoginResponse.builder()
			.guid("MOCK_GUID")
			.passwordExpired(false)
			.profile(
				Profile.builder()
					.email("john.lennon@gmail.com")
					.firstName("John")
					.lastName("LENNON")
					.build()
			)
			.build();

		try {
			loginResponse.setToken(
				jwtService.sign(
					mapper.writeValueAsString(
						dozer.map(loginResponse, Session.class)
					)
				)
			);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(
				String.format("%s An error happened during transforming object to JSON", LOG_HEADER),
				e
			);
		}

		return loginResponse;
	}
}
