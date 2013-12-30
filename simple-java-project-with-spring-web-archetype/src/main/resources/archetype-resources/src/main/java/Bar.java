package $package;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import com.google.common.annotations.VisibleForTesting;

@Nonnull
public class Bar {
	private Long id;

	public Bar(Long id) {
		this.id = argNotNull(id, "id");
	}

	@VisibleForTesting
	private Bar() {
	}

	public Long getId() {
		return id;
	}
}
