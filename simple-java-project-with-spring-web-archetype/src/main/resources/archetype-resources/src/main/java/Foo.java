package $package;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

import com.google.common.annotations.VisibleForTesting;

@Nonnull
public class Foo {
	private Long id;

	private String name;

	private Bar bar;

	public Foo(Long id, String name, Bar bar) {
		this.id = argNotNull(id, "id");
		this.name = argNotNull(name, "name");
		this.bar = argNotNull(bar, "bar");
	}

	@VisibleForTesting
	private Foo() {
	}

	public Long getId() {
		return id;
	}

	public Bar getBar() {
		return bar;
	}

	public String getName() {
		return name;
	};
}
