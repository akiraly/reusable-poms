package com.github.akiraly.db4j;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.springframework.data.jpa.domain.AbstractPersistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass
public abstract class JacksonAbstractPersistable<PK extends Serializable>
		extends AbstractPersistable<PK> {
	private static final long serialVersionUID = 1581661326452053346L;

	@Override
	@JsonIgnore
	public boolean isNew() {
		return super.isNew();
	}
}
