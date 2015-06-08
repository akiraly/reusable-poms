package com.github.akiraly.db4j.dao;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import javax.annotation.Nonnull;

@Nonnull
public class Bar {
	private final String barProp;

	public Bar(String barProp) {
		this.barProp = argNotNull(barProp, "barProp");
	}

	public String getBarProp() {
		return barProp;
	}
}
