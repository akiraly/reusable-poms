package com.github.akiraly.ver4j;

import static org.junit.Assert.assertSame;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class VerifierTest {
	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgNotNullFail() {
		Verify.argNotNull(null, "foo");
	}

	@Test(timeout = 200)
	public void testArgNotNullPass() {
		Object in = new Object();
		Object out = Verify.argNotNull(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateNotNullFail() {
		Verify.stateNotNull(null, "foo");
	}

	@Test(timeout = 200)
	public void testStateNotNullPass() {
		Object in = new Object();
		Object out = Verify.stateNotNull(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultNotNullFail() {
		Verify.resultNotNull(null, "foo");
	}

	@Test(timeout = 200)
	public void testResultNotNullPass() {
		Object in = new Object();
		Object out = Verify.resultNotNull(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgCollNotNullFail() {
		Verify.argNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgCollNotNullFail2() {
		Verify.argNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testArgCollNotNullPass() {
		ImmutableList<String> in = ImmutableList.of("");
		ImmutableList<String> out = Verify.argNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateCollNotNullFail() {
		Verify.stateNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateCollNotNullFail2() {
		Verify.stateNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testStateCollNotNullPass() {
		ImmutableList<String> in = ImmutableList.of("");
		ImmutableList<String> out = Verify.stateNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultCollNotNullFail() {
		Verify.resultNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultCollNotNullFail2() {
		Verify.resultNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testResultCollNotNullPass() {
		ImmutableList<String> in = ImmutableList.of("");
		ImmutableList<String> out = Verify.resultNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgIterableNotNullFail() {
		Verify.argNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgIterableNotNullFail2() {
		Verify.argNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testArgIterableNotNullPass() {
		Iterable<String> in = ImmutableList.of("");
		Iterable<String> out = Verify.argNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateIterableNotNullFail() {
		Verify.stateNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateIterableNotNullFail2() {
		Verify.stateNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testStateIterableNotNullPass() {
		Iterable<String> in = ImmutableList.of("");
		Iterable<String> out = Verify.stateNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultIterableNotNullFail() {
		Verify.resultNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultIterableNotNullFail2() {
		Verify.resultNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 200)
	public void testResultIterableNotNullPass() {
		Iterable<String> in = ImmutableList.of("");
		Iterable<String> out = Verify.resultNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgArrayNotNullFail() {
		Verify.argNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgArrayNotNullFail2() {
		Verify.argNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 200)
	public void testArgArrayNotNullPass() {
		String[] in = new String[] { "" };
		String[] out = Verify.argNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateArrayNotNullFail() {
		Verify.stateNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateArrayNotNullFail2() {
		Verify.stateNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 200)
	public void testStateArrayNotNullPass() {
		String[] in = new String[] { "" };
		String[] out = Verify.stateNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultArrayNotNullFail() {
		Verify.resultNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultArrayNotNullFail2() {
		Verify.resultNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 200)
	public void testResultArrayNotNullPass() {
		String[] in = new String[] { "" };
		String[] out = Verify.resultNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgMapNotNullFail() {
		Verify.argNotEmpty((Map<?, ?>) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgMapNotNullFail2() {
		Verify.argNotEmpty(ImmutableMap.of(), "foo");
	}

	@Test(timeout = 200)
	public void testArgMapNotNullPass() {
		ImmutableMap<String, Integer> in = ImmutableMap.of("", 0);
		ImmutableMap<String, Integer> out = Verify.argNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateMapNotNullFail() {
		Verify.stateNotEmpty((Map<?, ?>) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateMapNotNullFail2() {
		Verify.stateNotEmpty(ImmutableMap.of(), "foo");
	}

	@Test(timeout = 200)
	public void testStateMapNotNullPass() {
		ImmutableMap<String, Integer> in = ImmutableMap.of("", 0);
		ImmutableMap<String, Integer> out = Verify.stateNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultMapNotNullFail() {
		Verify.resultNotEmpty((Map<?, ?>) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultMapNotNullFail2() {
		Verify.resultNotEmpty(ImmutableMap.of(), "foo");
	}

	@Test(timeout = 200)
	public void testResultMapNotNullPass() {
		ImmutableMap<String, Integer> in = ImmutableMap.of("", 0);
		ImmutableMap<String, Integer> out = Verify.resultNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgIsTrueFail() {
		Verify.argIsTrue(false, "foo");
	}

	@Test(timeout = 200)
	public void testArgIsTruePass() {
		Verify.argIsTrue(true, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateIsTrueFail() {
		Verify.stateIsTrue(false, "foo");
	}

	@Test(timeout = 200)
	public void testStateIsTruePass() {
		Verify.stateIsTrue(true, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultIsTrueFail() {
		Verify.resultIsTrue(false, "foo");
	}

	@Test(timeout = 200)
	public void testResultIsTruePass() {
		Verify.resultIsTrue(true, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 200)
	public void testArgIsFalseFail() {
		Verify.argIsFalse(true, "foo");
	}

	@Test(timeout = 200)
	public void testArgIsFalsePass() {
		Verify.argIsFalse(false, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 200)
	public void testStateIsFalseFail() {
		Verify.stateIsFalse(true, "foo");
	}

	@Test(timeout = 200)
	public void testStateIsFalsePass() {
		Verify.stateIsFalse(false, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 200)
	public void testResultIsFalseFail() {
		Verify.resultIsFalse(true, "foo");
	}

	@Test(timeout = 200)
	public void testResultIsFalsePass() {
		Verify.resultIsFalse(false, "foo");
	}
}
