package $package;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Nonnull
public class AppStarter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(AppStarter.class);

	public static void main(String[] args) {
		LOGGER.info("Starting up...");
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				CommonAppConfig.class)) {
			LOGGER.info("Started up.");
		}
	}
}
