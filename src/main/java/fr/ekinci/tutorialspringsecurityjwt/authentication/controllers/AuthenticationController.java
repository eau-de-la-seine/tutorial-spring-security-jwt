package fr.ekinci.tutorialspringsecurityjwt.authentication.controllers;

import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginRequest;
import fr.ekinci.tutorialspringsecurityjwt.authentication.models.LoginResponse;
import fr.ekinci.tutorialspringsecurityjwt.authentication.services.ILoginService;
import fr.ekinci.tutorialspringsecurityjwt.commons.models.Profile;
import fr.ekinci.tutorialspringsecurityjwt.security.services.ISessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gokan EKINCI
 */
@Slf4j
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
	private static final String LOG_HEADER = "[AUTHENTICATION][CONTROLLERS]";
	private final ILoginService loginService;
	private final ISessionService sessionService;

	@Autowired
	public AuthenticationController(
		ILoginService loginService,
		ISessionService sessionService
	) {
		this.loginService = loginService;
		this.sessionService = sessionService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<LoginResponse> login(
		@RequestBody LoginRequest loginRequest
	) {
		log.info("{}[POST] login() -> request body : {}", LOG_HEADER, loginRequest);
		return new ResponseEntity<>(loginService.login(loginRequest), HttpStatus.OK);
	}

	@RequestMapping(value = "/me", method = RequestMethod.GET)
	public ResponseEntity<Profile> profile() {
		log.info("{}[GET] profile()", LOG_HEADER);
		return new ResponseEntity<>(sessionService.getSession().getProfile(), HttpStatus.OK);
	}
}
