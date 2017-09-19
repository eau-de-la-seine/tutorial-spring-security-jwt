package fr.ekinci.tutorialspringsecurityjwt.security.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.ekinci.tutorialspringsecurityjwt.security.exceptions.SessionException;
import fr.ekinci.tutorialspringsecurityjwt.security.models.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author Gokan EKINCI
 */
@Service
public class SessionService implements ISessionService {
	private final static String LOG_HEADER = "[SESSION][SERVICE]";
	private final ObjectMapper mapper;

	@Autowired
	public SessionService(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public Session getSession() {
		try {
			return mapper.readValue(
				(String) SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
				Session.class
			);
		} catch (IOException e) {
			throw new SessionException(
				String.format("%s An error happened when reading principal from SecurityContextHolder", LOG_HEADER),
				e
			);
		}
	}
}
