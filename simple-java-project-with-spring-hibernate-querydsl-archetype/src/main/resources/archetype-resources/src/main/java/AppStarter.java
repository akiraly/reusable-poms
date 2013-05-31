package $package;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Nonnull
public class AppStarter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppStarter.class);

	public static void main(String[] args) {
		LOGGER.info("Starting up...");
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				Config.class)) {
			LOGGER.info(context.getBean(App.class).getMessage());
		}
	}

	@Configuration
	@Nonnull
	static class Config {
		@Bean
		public App app() {
			return new App("Hello World!");
		}
	}
}
