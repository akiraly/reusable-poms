package $package;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.result.PrintingResultHandler;
import org.springframework.util.CollectionUtils;

@Nonnull
class Slf4jPrintingResultHandler implements ResultHandler {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(Slf4jPrintingResultHandler.class);

	@Override
	public void handle(MvcResult result) throws Exception {
		StringBuilder resultAsString = new StringBuilder();
		new StringPrintingResultHandler(resultAsString).handle(result);

		LOGGER.debug("{}", resultAsString);
	}
}

@Nonnull
class StringPrintingResultHandler extends PrintingResultHandler {
	StringPrintingResultHandler(StringBuilder result) {
		super(new ToStringResultValuePrinter(result));
	}

	@Nonnull
	private static class ToStringResultValuePrinter implements
			ResultValuePrinter {
		private final StringBuilder result;

		private ToStringResultValuePrinter(StringBuilder result) {
			this.result = argNotNull(result, "result");
		}

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
