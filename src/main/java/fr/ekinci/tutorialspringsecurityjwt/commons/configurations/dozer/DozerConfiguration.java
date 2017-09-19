package fr.ekinci.tutorialspringsecurityjwt.commons.configurations.dozer;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gokan EKINCI
 */
@Configuration
public class DozerConfiguration {

	/**
	 * Jackson LocalDate converter with dozer-jdk8-support dependency
	 */
	@Bean
	public Mapper dozer() {
		final List<String> mappingFiles = new ArrayList();
		mappingFiles.add("config/dozerJdk8Converters.xml");

		final DozerBeanMapper dozer = new DozerBeanMapper();
		dozer.setMappingFiles(mappingFiles);
		return dozer;
	}
}
