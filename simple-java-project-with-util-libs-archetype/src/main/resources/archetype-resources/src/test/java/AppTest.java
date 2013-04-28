package $package;

import javax.annotation.Nonnull;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Nonnull
public class AppTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppTest.class);

	@Test
	public void testApp() {
		LOGGER.error("This test will fail.");
		Assert.fail();
	}
}
