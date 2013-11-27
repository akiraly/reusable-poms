package com.github.akiraly.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;
import javax.persistence.Entity;

import org.hibernate.annotations.Immutable;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Nonnull
@Entity
@Immutable
public class Uow extends AbstractPersistable<Long> {
	private static final long serialVersionUID = -6140188419595214529L;

	private final String user;

	public Uow(String user) {
		this.user = argNotNull(user, "user");
	}

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Uow [getUser()=" + getUser() + ", getId()=" + getId() + "]";
	}
}
