package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

@Nonnull
public class Foo {
	private String bar;

	public Foo(String bar) {
		this.bar = argNotNull(bar, "bar");
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}
}
