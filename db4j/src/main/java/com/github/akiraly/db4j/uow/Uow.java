package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

@Nonnull
public class Uow {
	private final String user;

	public Uow(String user) {
		this.user = argNotNull(user, "user");
	}

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Uow [user=" + user + "]";
	}
}
