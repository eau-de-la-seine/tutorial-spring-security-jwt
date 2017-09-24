package fr.ekinci.tutorialspringsecurityjwt.security.filters;

import fr.ekinci.tutorialspringsecurityjwt.security.services.IJwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This filter is executed before each request (login excepted)
 * Don't make this filter a @Component, otherwise the filter will be executed more than once
 *
 * @author Gokan EKINCI
 */
@Slf4j
public class AuthenticationFilter extends GenericFilterBean {
	private static final String LOG_HEADER = "[AUTHENTICATION][FILTER]";

	private final IJwtService jwtService;

	public AuthenticationFilter(IJwtService jwtService) {
		this.jwtService = jwtService;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
		throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		log.debug("{} URL: ", LOG_HEADER, httpServletRequest.getRequestURL());


		final Authentication authentication = jwtService.verify(httpServletRequest);

		if (!authentication.isAuthenticated()) {
			log.trace("{} JWT Authentication failed", LOG_HEADER);
		}

		SecurityContextHolder
			.getContext()
			.setAuthentication(authentication);

		filterChain.doFilter(servletRequest, servletResponse);
	}
}
