package com.github.akiraly.db4j.uow;

import javax.annotation.Nonnull;

import com.github.akiraly.db4j.LongId;

@Nonnull
public class Uow {
	private final Id id;

	private final String user;

	public Uow(Id id, String user) {
		this.id = id;
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Uow [getUser()=" + getUser() + ", getId()=" + getId() + "]";
	}

	@Nonnull
	public static class Id extends LongId<Uow> {
		private static final long serialVersionUID = -851262705731916738L;

		public Id(long id) {
			super(id, Uow.class);
		}
	}
}
