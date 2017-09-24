package fr.ekinci.tutorialspringsecurityjwt.security.configurations;

import fr.ekinci.tutorialspringsecurityjwt.security.filters.AuthenticationFilter;
import fr.ekinci.tutorialspringsecurityjwt.security.services.IJwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.GenericFilterBean;

/**
 * @author Gokan EKINCI
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	private final GenericFilterBean authenticationFilter;

	@Autowired
	public WebSecurityConfig(IJwtService jwtService) {
		this.authenticationFilter = new AuthenticationFilter(jwtService);
	}

	/**
	 * The {@link AuthenticationFilter} won't be applied for:
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.mvcMatchers(HttpMethod.GET,
				"/ping"
			)
			.mvcMatchers(HttpMethod.POST,
				"/login"
			);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
				.disable()
			.authorizeRequests()
				.anyRequest().authenticated()
//			.and()
//			.formLogin()
//				.loginPage("/login").permitAll()
			.and()
			.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
