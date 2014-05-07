package com.github.akiraly.db4j.uow;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.github.akiraly.db4j.JacksonAbstractPersistable;

@Nonnull
@Entity
@Table(name = "uow")
@Immutable
public class Uow extends JacksonAbstractPersistable<Long> {
	private static final long serialVersionUID = -6140188419595214529L;

	private String user;

	public Uow(String user) {
		this.user = argNotNull(user, "user");
	}

	/**
	 * For Hibernate
	 */
	protected Uow() {
	}

	public String getUser() {
		return user;
	}

	@Override
	public String toString() {
		return "Uow [getUser()=" + getUser() + ", getId()=" + getId() + "]";
	}
}
