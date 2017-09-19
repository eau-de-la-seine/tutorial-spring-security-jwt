package fr.ekinci.tutorialspringsecurityjwt.commons.controllers;

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
public class PingController {
	private static final String LOG_HEADER = "[COMMONS][COMMANDS]";

	@RequestMapping(value = "/ping")
	public ResponseEntity<String> ping() {
		log.debug("{} Ping command received !", LOG_HEADER);
		return new ResponseEntity<>("pong", HttpStatus.OK);
	}
}
