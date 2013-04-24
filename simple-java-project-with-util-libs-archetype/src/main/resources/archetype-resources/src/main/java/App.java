package $$package;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.Nonnull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Nonnull
public class App {
	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		checkArgument(args != null, "Expected not null arguments.");
		LOGGER.info("Hello World!");
	}
}
