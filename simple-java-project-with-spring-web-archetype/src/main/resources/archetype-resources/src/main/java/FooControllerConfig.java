package $package;

import javax.annotation.Nonnull;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Nonnull
public class FooControllerConfig {
	@Bean
	public FooController fooController() {
		return new FooController();
	}
}
