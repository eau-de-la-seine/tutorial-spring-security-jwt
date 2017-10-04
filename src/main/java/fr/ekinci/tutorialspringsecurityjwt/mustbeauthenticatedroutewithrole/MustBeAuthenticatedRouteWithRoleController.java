package fr.ekinci.tutorialspringsecurityjwt.mustbeauthenticatedroutewithrole;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gokan EKINCI
 */
@Slf4j
@RestController
public class MustBeAuthenticatedRouteWithRoleController {
	private static final String LOG_HEADER = "[MUST-BE-AUTHENTICATED-ROUTE-WITH-ROLE]";

	@Secured({"ROLE_USER"})
	@RequestMapping(value = "/must-be-authenticated-route-with-role")
	public ResponseEntity<String> mustBeAuthenticatedRouteWithRole() {
		log.debug("{} mustBeAuthenticatedRouteWithRole", LOG_HEADER);
		return new ResponseEntity<>("mustBeAuthenticatedRouteWithRole", HttpStatus.OK);
	}
}
