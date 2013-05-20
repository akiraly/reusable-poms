package $package;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

@Nonnull
public class App {
	private final String message;

	public App(String message) {
		this.message = checkNotNull(message,
				"Expected not null message argument.");
	}

	public String getMessage() {
		return message;
	}
}
