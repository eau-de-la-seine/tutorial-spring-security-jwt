package fr.ekinci.tutorialspringsecurityjwt.commons.configurations.resttemplate;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Gokan EKINCI
 */
@Configuration
public class RestTemplateConfiguration {

	@Bean
	public RestTemplate restTemplate (RestTemplateBuilder builder) {
		return builder
			.build();
	}
}
