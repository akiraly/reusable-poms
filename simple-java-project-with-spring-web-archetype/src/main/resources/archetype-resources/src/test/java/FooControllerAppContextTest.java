package $package;

import java.util.List;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import foo.bar.baz.FooControllerAppContextTest.Config;

@Nonnull
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { FooControllerConfig.class, Config.class })
public class FooControllerAppContextTest extends AbstractFooControllerTest {

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void beforeTest() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Override
	@Test(timeout = 2000)
	public void testGetFoos() throws Exception {
		super.testGetFoos();
	}

	/**
	 * Add jackson converter explicitly because it seems spring 4.0 in test mode
	 * is not doing it.
	 */
	@Configuration
	@EnableWebMvc
	@Nonnull
	protected static class Config extends WebMvcConfigurerAdapter {

		@Override
		public void configureMessageConverters(
				List<HttpMessageConverter<?>> converters) {
			super.configureMessageConverters(converters);
			converters.add(new MappingJackson2HttpMessageConverter());
		}
	}
}
