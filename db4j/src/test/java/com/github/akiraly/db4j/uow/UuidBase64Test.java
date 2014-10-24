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
package com.github.akiraly.db4j.uow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.IntStream;

import javax.annotation.Nonnull;

import org.junit.Test;

import com.github.akiraly.db4j.UuidBase64;

@Nonnull
public class UuidBase64Test {

	@Test(timeout = 1000)
	public void testEncodeDecode() {
		Set<UUID> generatedUuids = new HashSet<>();
		IntStream.range(0, 100000).forEach(i -> {
			UUID uuid = UUID.randomUUID();
			assertTrue(generatedUuids.add(uuid));

			String encoded = UuidBase64.encode(uuid);
			assertNotNull(encoded);
			UUID uuid2 = UuidBase64.decode(encoded);
			assertEquals(uuid, uuid2);
			assertEquals(22, encoded.length());
		});
	}

}
