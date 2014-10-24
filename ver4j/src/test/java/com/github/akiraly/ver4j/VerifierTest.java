/**
 * Copyright 2014 Attila Kiraly
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.akiraly.ver4j;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

@Nonnull
public class VerifierTest {
	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgNotNullFail() {
		Verify.argNotNull(null, "foo");
	}

	@Test(timeout = 300)
	public void testArgNotNullFailWithSupplier() {
		try {
			Verify.argNotNull(null, new Supplier<Supplier<String>>() {
				@Override
				public Supplier<String> get() {
					return () -> "foo";
				}
			});
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().contains("foo"));
		}
	}

	@Test(timeout = 300)
	public void testArgNotNullPass() {
		Object in = new Object();
		Object out = Verify.argNotNull(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarNotNullFail() {
		Verify.varNotNull(null, "foo");
	}

	@Test(timeout = 300)
	public void testVarNotNullPass() {
		Object in = new Object();
		Object out = Verify.varNotNull(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldNotNullFail() {
		Verify.fieldNotNull(null, "foo");
	}

	@Test(timeout = 300)
	public void testFieldNotNullPass() {
		Object in = new Object();
		Object out = Verify.fieldNotNull(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateNotNullFail() {
		Verify.stateNotNull(null, "foo");
	}

	@Test(timeout = 300)
	public void testStateNotNullPass() {
		Object in = new Object();
		Object out = Verify.stateNotNull(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultNotNullFail() {
		Verify.resultNotNull(null, "foo");
	}

	@Test(timeout = 300)
	public void testResultNotNullPass() {
		Object in = new Object();
		Object out = Verify.resultNotNull(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgCollNotNullFail() {
		Verify.argNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgCollNotNullFail2() {
		Verify.argNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testArgCollNotNullPass() {
		ImmutableList<String> in = ImmutableList.of("");
		ImmutableList<String> out = Verify.argNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarCollNotNullFail() {
		Verify.varNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarCollNotNullFail2() {
		Verify.varNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testVarCollNotNullPass() {
		ImmutableList<String> in = ImmutableList.of("");
		ImmutableList<String> out = Verify.varNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldCollNotNullFail() {
		Verify.fieldNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldCollNotNullFail2() {
		Verify.fieldNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testFieldCollNotNullPass() {
		ImmutableList<String> in = ImmutableList.of("");
		ImmutableList<String> out = Verify.fieldNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateCollNotNullFail() {
		Verify.stateNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateCollNotNullFail2() {
		Verify.stateNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testStateCollNotNullPass() {
		ImmutableList<String> in = ImmutableList.of("");
		ImmutableList<String> out = Verify.stateNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultCollNotNullFail() {
		Verify.resultNotEmpty((Collection<?>) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultCollNotNullFail2() {
		Verify.resultNotEmpty(ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testResultCollNotNullPass() {
		ImmutableList<String> in = ImmutableList.of("");
		ImmutableList<String> out = Verify.resultNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgIterableNotNullFail() {
		Verify.argNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgIterableNotNullFail2() {
		Verify.argNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testArgIterableNotNullPass() {
		Iterable<String> in = ImmutableList.of("");
		Iterable<String> out = Verify.argNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarIterableNotNullFail() {
		Verify.varNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarIterableNotNullFail2() {
		Verify.varNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testVarIterableNotNullPass() {
		Iterable<String> in = ImmutableList.of("");
		Iterable<String> out = Verify.varNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldIterableNotNullFail() {
		Verify.fieldNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldIterableNotNullFail2() {
		Verify.fieldNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testFieldIterableNotNullPass() {
		Iterable<String> in = ImmutableList.of("");
		Iterable<String> out = Verify.fieldNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateIterableNotNullFail() {
		Verify.stateNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateIterableNotNullFail2() {
		Verify.stateNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testStateIterableNotNullPass() {
		Iterable<String> in = ImmutableList.of("");
		Iterable<String> out = Verify.stateNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultIterableNotNullFail() {
		Verify.resultNotEmpty((Iterable<?>) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultIterableNotNullFail2() {
		Verify.resultNotEmpty((Iterable<?>) ImmutableList.of(), "foo");
	}

	@Test(timeout = 300)
	public void testResultIterableNotNullPass() {
		Iterable<String> in = ImmutableList.of("");
		Iterable<String> out = Verify.resultNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgArrayNotNullFail() {
		Verify.argNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgArrayNotNullFail2() {
		Verify.argNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 300)
	public void testArgArrayNotNullPass() {
		String[] in = new String[] { "" };
		String[] out = Verify.argNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarArrayNotNullFail() {
		Verify.varNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarArrayNotNullFail2() {
		Verify.varNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 300)
	public void testVarArrayNotNullPass() {
		String[] in = new String[] { "" };
		String[] out = Verify.varNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldArrayNotNullFail() {
		Verify.fieldNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldArrayNotNullFail2() {
		Verify.fieldNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 300)
	public void testFieldArrayNotNullPass() {
		String[] in = new String[] { "" };
		String[] out = Verify.fieldNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateArrayNotNullFail() {
		Verify.stateNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateArrayNotNullFail2() {
		Verify.stateNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 300)
	public void testStateArrayNotNullPass() {
		String[] in = new String[] { "" };
		String[] out = Verify.stateNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultArrayNotNullFail() {
		Verify.resultNotEmpty((Object[]) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultArrayNotNullFail2() {
		Verify.resultNotEmpty(new Object[0], "foo");
	}

	@Test(timeout = 300)
	public void testResultArrayNotNullPass() {
		String[] in = new String[] { "" };
		String[] out = Verify.resultNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgMapNotNullFail() {
		Verify.argNotEmpty((Map<?, ?>) null, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgMapNotNullFail2() {
		Verify.argNotEmpty(ImmutableMap.of(), "foo");
	}

	@Test(timeout = 300)
	public void testArgMapNotNullPass() {
		ImmutableMap<String, Integer> in = ImmutableMap.of("", 0);
		ImmutableMap<String, Integer> out = Verify.argNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarMapNotNullFail() {
		Verify.varNotEmpty((Map<?, ?>) null, "foo");
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarMapNotNullFail2() {
		Verify.varNotEmpty(ImmutableMap.of(), "foo");
	}

	@Test(timeout = 300)
	public void testVarMapNotNullPass() {
		ImmutableMap<String, Integer> in = ImmutableMap.of("", 0);
		ImmutableMap<String, Integer> out = Verify.varNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldMapNotNullFail() {
		Verify.fieldNotEmpty((Map<?, ?>) null, "foo");
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldMapNotNullFail2() {
		Verify.fieldNotEmpty(ImmutableMap.of(), "foo");
	}

	@Test(timeout = 300)
	public void testFieldMapNotNullPass() {
		ImmutableMap<String, Integer> in = ImmutableMap.of("", 0);
		ImmutableMap<String, Integer> out = Verify.fieldNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateMapNotNullFail() {
		Verify.stateNotEmpty((Map<?, ?>) null, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateMapNotNullFail2() {
		Verify.stateNotEmpty(ImmutableMap.of(), "foo");
	}

	@Test(timeout = 300)
	public void testStateMapNotNullPass() {
		ImmutableMap<String, Integer> in = ImmutableMap.of("", 0);
		ImmutableMap<String, Integer> out = Verify.stateNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultMapNotNullFail() {
		Verify.resultNotEmpty((Map<?, ?>) null, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultMapNotNullFail2() {
		Verify.resultNotEmpty(ImmutableMap.of(), "foo");
	}

	@Test(timeout = 300)
	public void testResultMapNotNullPass() {
		ImmutableMap<String, Integer> in = ImmutableMap.of("", 0);
		ImmutableMap<String, Integer> out = Verify.resultNotEmpty(in, "foo");
		assertSame(in, out);
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgIsTrueFail() {
		Verify.argIsTrue(false, "foo");
	}

	@Test(timeout = 300)
	public void testArgIsTruePass() {
		Verify.argIsTrue(true, "foo");
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarIsTrueFail() {
		Verify.varIsTrue(false, "foo");
	}

	@Test(timeout = 300)
	public void testVarIsTruePass() {
		Verify.varIsTrue(true, "foo");
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldIsTrueFail() {
		Verify.fieldIsTrue(false, "foo");
	}

	@Test(timeout = 300)
	public void testFieldIsTruePass() {
		Verify.fieldIsTrue(true, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateIsTrueFail() {
		Verify.stateIsTrue(false, "foo");
	}

	@Test(timeout = 300)
	public void testStateIsTruePass() {
		Verify.stateIsTrue(true, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultIsTrueFail() {
		Verify.resultIsTrue(false, "foo");
	}

	@Test(timeout = 300)
	public void testResultIsTruePass() {
		Verify.resultIsTrue(true, "foo");
	}

	@Test(expected = IllegalArgumentException.class, timeout = 300)
	public void testArgIsFalseFail() {
		Verify.argIsFalse(true, "foo");
	}

	@Test(timeout = 300)
	public void testArgIsFalsePass() {
		Verify.argIsFalse(false, "foo");
	}

	@Test(expected = IllegalVarException.class, timeout = 300)
	public void testVarIsFalseFail() {
		Verify.varIsFalse(true, "foo");
	}

	@Test(timeout = 300)
	public void testVarIsFalsePass() {
		Verify.varIsFalse(false, "foo");
	}

	@Test(expected = IllegalFieldException.class, timeout = 300)
	public void testFieldIsFalseFail() {
		Verify.fieldIsFalse(true, "foo");
	}

	@Test(timeout = 300)
	public void testFieldIsFalsePass() {
		Verify.fieldIsFalse(false, "foo");
	}

	@Test(expected = IllegalStateException.class, timeout = 300)
	public void testStateIsFalseFail() {
		Verify.stateIsFalse(true, "foo");
	}

	@Test(timeout = 300)
	public void testStateIsFalsePass() {
		Verify.stateIsFalse(false, "foo");
	}

	@Test(expected = IllegalResultException.class, timeout = 300)
	public void testResultIsFalseFail() {
		Verify.resultIsFalse(true, "foo");
	}

	@Test(timeout = 300)
	public void testResultIsFalsePass() {
		Verify.resultIsFalse(false, "foo");
	}
}
