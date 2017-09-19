package fr.ekinci.tutorialspringsecurityjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;


/**
 * Extending {@link SpringBootServletInitializer} is required for war project (because Spring-Boot is a jar project by default but we're using Tomcat)
 *
 * @author Gokan EKINCI
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		new SpringApplication(Application.class).run(args);
	}
}
