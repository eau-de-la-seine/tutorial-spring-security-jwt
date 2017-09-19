package fr.ekinci.tutorialspringsecurityjwt.mustbeauthenticatedroute;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gokan EKINCI
 */
@Slf4j
@RestController
public class MustBeAuthenticatedRouteController {
	private static final String LOG_HEADER = "[MUST-BE-AUTHENTICATED-ROUTE]";

	@RequestMapping(value = "/must-be-authenticated-route")
	public ResponseEntity<String> mustBeAuthenticatedRoute() {
		log.debug("{} mustBeAuthenticatedRoute", LOG_HEADER);
		return new ResponseEntity<>("mustBeAuthenticatedRoute", HttpStatus.OK);
	}
}
