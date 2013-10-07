package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class CompositeVerifier {
	private final Verifier argV;

	private final Verifier stateV;

	private final Verifier resultV;

	public CompositeVerifier(Verifier argV, Verifier stateV, Verifier resultV) {
		this.argV = argV;
		this.stateV = stateV;
		this.resultV = resultV;
	}

	public CompositeVerifier() {
		this(new Verifier(new ArgExceptionFactory()), //
				new Verifier(new StateExceptionFactory()), //
				new Verifier(new ResultExceptionFactory()));
	}

	public <T> T argNotNull(T object, String name) {
		return argV.notNull(object, name);
	}

	public <T> T stateNotNull(T object, String name) {
		return stateV.notNull(object, name);
	}

	public <T> T resultNotNull(T object, String name) {
		return resultV.notNull(object, name);
	}

	public void argIsTrue(boolean check, String message, Object... params) {
		argV.isTrue(check, message, params);
	}

	public void stateIsTrue(boolean check, String message, Object... params) {
		stateV.isTrue(check, message, params);
	}

	public void resultIsTrue(boolean check, String message, Object... params) {
		resultV.isTrue(check, message, params);
	}

	public void argIsFalse(boolean check, String message, Object... params) {
		argV.isFalse(check, message, params);
	}

	public void stateIsFalse(boolean check, String message, Object... params) {
		stateV.isFalse(check, message, params);
	}

	public void resultIsFalse(boolean check, String message, Object... params) {
		resultV.isFalse(check, message, params);
	}

}
