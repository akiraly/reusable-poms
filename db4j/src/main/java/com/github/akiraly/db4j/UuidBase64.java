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
package com.github.akiraly.db4j;

import static com.github.akiraly.ver4j.Verify.argNotNull;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.UUID;

import javax.annotation.Nonnull;

@Nonnull
public class UuidBase64 {
	private static final UuidBase64 INSTANCE = new UuidBase64();

	public static String encode(UUID uuid) {
		return INSTANCE.encodeToString(uuid);
	}

	public static UUID decode(String encodedUuid) {
		return INSTANCE.decodeFromString(encodedUuid);
	}

	private final Encoder encoder = Base64.getUrlEncoder().withoutPadding();

	private final Decoder decoder = Base64.getUrlDecoder();

	/**
	 * @return the 22 length, url safe, base64 encoded form of the uuid
	 */
	public String encodeToString(UUID uuid) {
		argNotNull(uuid, "uuid");
		ByteBuffer uuidBytes = ByteBuffer.allocate(16)
				.putLong(uuid.getMostSignificantBits())
				.putLong(uuid.getLeastSignificantBits());
		return encoder.encodeToString(uuidBytes.array());
	}

	/**
	 * @param encodedUuid
	 *            the 22 length, url safe, base64 encoded form of the uuid
	 */
	public UUID decodeFromString(String encodedUuid) {
		ByteBuffer uuidBytes = ByteBuffer.wrap(decoder.decode(argNotNull(
				encodedUuid, "encodedUuid")));
		return new UUID(uuidBytes.getLong(), uuidBytes.getLong());
	}
}
