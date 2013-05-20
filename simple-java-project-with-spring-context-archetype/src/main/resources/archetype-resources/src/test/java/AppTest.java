package $package;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Nonnull
public class AppTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

	@Autowired
	private App app;

	@Before
	public void setUp() {
		checkNotNull(app, "Expected not null app");
	}

	@Test
	public void testApp() {
		LOGGER.debug(app.getMessage());
		Assert.assertEquals("Hello World!", app.getMessage());
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
