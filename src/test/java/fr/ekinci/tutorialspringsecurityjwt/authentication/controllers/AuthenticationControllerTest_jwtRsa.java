package fr.ekinci.tutorialspringsecurityjwt.authentication.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ekinci.Application;
import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginRequest;
import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginResponse;
import fr.ekinci.tutorialspringsecurityjwt.authentication.services.ILoginService;
import fr.ekinci.tutorialspringsecurityjwt.authentication.services.LoginServiceMock;
import fr.ekinci.tutorialspringsecurityjwt.commons.configurations.dozer.DozerConfiguration;
import fr.ekinci.tutorialspringsecurityjwt.commons.models.Profile;
import fr.ekinci.tutorialspringsecurityjwt.security.configurations.WebSecurityConfig;
import fr.ekinci.tutorialspringsecurityjwt.security.services.IJwtService;
import fr.ekinci.tutorialspringsecurityjwt.security.services.ISessionService;
import fr.ekinci.tutorialspringsecurityjwt.security.services.JwtRsaService;
import io.jsonwebtoken.SignatureAlgorithm;
import org.dozer.Mapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Gokan EKINCI
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@ActiveProfiles("jwtRsa")
@ContextConfiguration(classes = {
	AuthenticationControllerTest_jwtHmac.DependenciesConfig.class
})
public class AuthenticationControllerTest_jwtRsa {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private ISessionService sessionService;

	@Test
	public void testLogin() throws Exception {
		final String route = "/login";
		final String sentBody = sentBody_ok();
		final String expectedResult = expectedResult_ok();

		this.mvc.perform(
			post(route)
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(sentBody)
		)
				.andExpect(status().isOk())
				.andExpect(content().json(expectedResult));
	}

	private String sentBody_ok() throws JsonProcessingException {
		final LoginRequest request = new LoginRequest();
		request.setUsername("username");
		request.setPassword("password");
		return mapper.writeValueAsString(request);
	}

	private String expectedResult_ok() throws JsonProcessingException {
		final String guid = "MOCK_GUID";
		final String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJndWlkXCI6XCJNT0NLX0dVSURcIixcInBhc3N3b3JkRXhwaXJlZFwiOmZhbHNlLFwicHJvZmlsZVwiOntcImlkXCI6bnVsbCxcImZpcnN0TmFtZVwiOlwiSm9oblwiLFwibGFzdE5hbWVcIjpcIkxFTk5PTlwiLFwiZW1haWxcIjpcImpvaG4ubGVubm9uQGdtYWlsLmNvbVwifX0ifQ._0n7fUb73WDy0l2h7ZL7X-RCvgUBquCeOSOoaKNaVVr5L4F914u6Rfa7ky4v58wm6pvDYIF4v04YJZAO2skPuQ";

		return mapper.writeValueAsString(
			LoginResponse.builder()
				.guid(guid)
				.passwordExpired(false)
				.profile(
					Profile.builder()
						.email("john.lennon@gmail.com")
						.firstName("John")
						.lastName("LENNON")
						.build())
				.token(token)
				.build()
		);
	}

	/**
	 * Tip for importing WebSecurityConfig class : https://github.com/spring-projects/spring-boot/issues/6514
	 */
	@Import({
		DozerConfiguration.class,

		// Needed for setting WebSecurityConfig: https://stackoverflow.com/a/39427897
		WebSecurityConfig.class,
		Application.class,
	})
	public static class DependenciesConfig {

		@Bean
		public IJwtService jwtService(
			@Value("${jwt.rsa.algorithm:RS512}") SignatureAlgorithm algorithm,
			@Value("${jwt.duration:#{null}}") Long duration,
			KeyPair rsaKeyPair
		) {
			return new JwtRsaService(algorithm, duration, rsaKeyPair);
		}

		@Bean
		public KeyPair rsaKeyPair() throws NoSuchAlgorithmException {
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			keyGen.initialize(1024);
			return keyGen.genKeyPair();
		}

		@Bean
		public ILoginService loginService(Mapper dozer, ObjectMapper mapper, IJwtService jwtService) {
			return new LoginServiceMock(null, dozer, mapper, jwtService);
		}
	}
}

