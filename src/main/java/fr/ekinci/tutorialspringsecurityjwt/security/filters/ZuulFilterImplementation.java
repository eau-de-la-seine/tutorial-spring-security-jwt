package fr.ekinci.tutorialspringsecurityjwt.security.filters;

import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.ZuulFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Gokan EKINCI
 */
@Slf4j
@Component
public class ZuulFilterImplementation extends ZuulFilter {

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	/**
	 * If return `true` or `false`, routing is done.
	 * If return `true` then execute {@link com.netflix.zuul.IZuulFilter#run()}
	 */
	@Override
	public boolean shouldFilter() {
		// TODO : Handle your users and their roles
		RequestContext.getCurrentContext()
			.setSendZuulResponse(
				SecurityContextHolder.getContext()
					.getAuthentication()
					.isAuthenticated()
			);

		return true;
	}

	@Override
	public Object run() {
		final RequestContext ctx = RequestContext.getCurrentContext();
		final HttpServletRequest request = ctx.getRequest();

		// Log called route
		log.info(String.format("[%s][%s][%s]",
			ZuulFilterImplementation.class.getSimpleName(),
			request.getMethod(),
			request.getRequestURL().toString()
		));

		// Return value is not used by Zuul yet
		return null;
	}
}
