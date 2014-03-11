package $package;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@Nonnull
public class FooControllerTest extends AbstractFooControllerTest {

	@Before
	public void beforeTest() {
		mockMvc = MockMvcBuilders.standaloneSetup(new FooController()).build();
	}

	@Test(timeout = 2000)
	@Override
	public void testGetFoos() throws Exception {
		super.testGetFoos();
	}
}
