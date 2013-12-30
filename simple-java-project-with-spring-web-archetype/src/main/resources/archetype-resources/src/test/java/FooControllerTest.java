package $package;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Nonnull
public class FooControllerTest {

	private MockMvc mockMvc;

	@Before
	public void beforeTest() {
		mockMvc = MockMvcBuilders.standaloneSetup(new FooController()).build();
	}

	@Test
	public void testGetFoos() throws Exception {
		MediaType jsonUtf8 = MediaType
				.parseMediaType("application/json;charset=UTF-8");
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/foo").accept(jsonUtf8))
				.andDo(new Slf4jPrintingResultHandler())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(
						MockMvcResultMatchers.content().contentType(jsonUtf8))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.2.name")
								.value("foo2"))
				.andExpect(
						MockMvcResultMatchers.jsonPath("$.3.bar.id").value(1))
				.andReturn();
		Map<Long, Foo> resultAsObject = new ObjectMapper().readValue(result
				.getResponse().getContentAsString(),
				new TypeReference<Map<Long, Foo>>() {
				});
		assertNotNull(resultAsObject);
		assertEquals(5, resultAsObject.size());
	}

	private static class Slf4jPrintingResultHandler implements ResultHandler {
		private static final Logger LOGGER = LoggerFactory
				.getLogger(Slf4jPrintingResultHandler.class);

		@Override
		public void handle(MvcResult result) throws Exception {
			StringPrintingResultHandler handler = new StringPrintingResultHandler();
			handler.handle(result);

			LOGGER.debug("{}", handler.getPrinter().result);
		}
	}

	@Nonnull
	private static class StringPrintingResultHandler extends
			PrintingResultHandler {

		private StringPrintingResultHandler() {
			super(new ToStringResultValuePrinter());
		}

		@Override
		protected ToStringResultValuePrinter getPrinter() {
			return (ToStringResultValuePrinter) super.getPrinter();
		}

		private static class ToStringResultValuePrinter implements
				ResultValuePrinter {
			private final StringBuilder result = new StringBuilder();

			@Override
			public void printHeading(String heading) {
				result.append(String.format("\n%20s:\n", heading));
			}

			@Override
			public void printValue(String label, Object value) {
				if (value != null && value.getClass().isArray())
					value = CollectionUtils.arrayToList(value);
				result.append(String.format("%20s = %s\n", label, value));
			}
		}
	}
}
