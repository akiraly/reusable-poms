package com.github.akiraly.db4j.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.junit.Test;

@Nonnull
public class ConvertTest {

	@Test(timeout = 600)
	public void testDate2UtcDateTime() {
		Date date = new Date();
		OffsetDateTime offsetDateTime = Convert.date2UtcDateTime(date);
		assertNotNull(offsetDateTime);
		assertEquals(ZoneOffset.UTC, offsetDateTime.getOffset());
		assertEquals(date.toInstant(), offsetDateTime.toInstant());
	}

	@Test(timeout = 600)
	public void testDate2OptUtcDateTime() {
		assertFalse(Convert.date2OptUtcDateTime(null).isPresent());

		Date date = new Date();
		Optional<OffsetDateTime> offsetDateTime = Convert
				.date2OptUtcDateTime(date);
		assertNotNull(offsetDateTime);
		assertTrue(offsetDateTime.isPresent());
		assertEquals(ZoneOffset.UTC, offsetDateTime.get().getOffset());
		assertEquals(date.toInstant(), offsetDateTime.get().toInstant());
	}

	@Test(timeout = 600)
	public void testDateTime2Date() {
		OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC);
		Date date = Convert.dateTime2Date(offsetDateTime);
		assertNotNull(date);
		assertEquals(offsetDateTime.toInstant(), date.toInstant());

		assertNull(Convert.dateTime2Date(Optional.<OffsetDateTime> empty()));

		Date date2 = Convert.dateTime2Date(Optional.of(offsetDateTime));
		assertNotNull(date2);
		assertEquals(offsetDateTime.toInstant(), date2.toInstant());
	}
}
