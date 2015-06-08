package com.github.akiraly.db4j.dao;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.entity.EntityWithStringId;

@Nonnull
public class Foo {
	private final EntityWithStringId<Bar> bar;

	private final String name;

	private final LocalDateTime dt;

	public Foo(EntityWithStringId<Bar> bar, String name, LocalDateTime dt) {
		this.bar = argNotNull(bar, "bar");
		this.name = argNotNull(name, "name");
		this.dt = argNotNull(dt, "dt");
	}

	public EntityWithStringId<Bar> getBar() {
		return bar;
	}

	public String getName() {
		return name;
	}

	public LocalDateTime getDt() {
		return dt;
	}

}
