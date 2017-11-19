package fr.ekinci.tutorialspringsecurityjwt.commons.configurations.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Jackson configuration for:
 *        * Using Java 8 dates with {@link com.fasterxml.jackson.datatype.jsr310.JavaTimeModule}
 *        * Using Iso format with {@link com.fasterxml.jackson.databind.util.ISO8601DateFormat} . Example of ISO date format : 2014-12-20T02:30:00
 *        * Excluding null fields with {@link com.fasterxml.jackson.annotation.JsonInclude.Include#NON_NULL}
 *
 * See : https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/http/converter/json/Jackson2ObjectMapperBuilder.html
 */
@Configuration
public class JacksonConfiguration {

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		return new Jackson2ObjectMapperBuilder()
			.dateFormat(new ISO8601DateFormat())
			.serializationInclusion(JsonInclude.Include.NON_NULL)
			.failOnUnknownProperties(false)
			.modulesToInstall(new JavaTimeModule());
	}
}
