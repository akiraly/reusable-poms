package $package;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import javax.annotation.Nonnull;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Nonnull
public abstract class AbstractFooControllerTest {

	protected MockMvc mockMvc;

	protected void testGetFoos() throws Exception {
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
}