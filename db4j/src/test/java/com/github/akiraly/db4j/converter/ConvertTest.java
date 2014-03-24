package com.github.akiraly.db4j.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

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

	@Test(timeout = 600)
	public void testCalendar2DateTime() {
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.UTC), Locale.US);
		OffsetDateTime offsetDateTime = Convert.calendar2DateTime(calendar);
		assertNotNull(offsetDateTime);
		assertDateTimeEqualsCalendar(offsetDateTime, calendar);
	}

	@Test(timeout = 600)
	public void testCalendar2OptDateTime() {
		assertFalse(Convert.calendar2OptDateTime(null).isPresent());

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneOffset.UTC), Locale.US);
		Optional<OffsetDateTime> offsetDateTime = Convert
				.calendar2OptDateTime(calendar);
		assertNotNull(offsetDateTime);
		assertTrue(offsetDateTime.isPresent());
		assertDateTimeEqualsCalendar(offsetDateTime.get(), calendar);
	}

	@Test(timeout = 600)
	public void testDateTime2Calendar() {
		OffsetDateTime offsetDateTime = OffsetDateTime.now(ZoneOffset.UTC);
		Calendar calendar = Convert.dateTime2Calendar(offsetDateTime);
		assertNotNull(calendar);
		assertDateTimeEqualsCalendar(offsetDateTime, calendar);

		assertNull(Convert.dateTime2Date(Optional.<OffsetDateTime> empty()));

		Calendar calendar2 = Convert.dateTime2Calendar(Optional.of(offsetDateTime));
		assertNotNull(calendar2);
		assertDateTimeEqualsCalendar(offsetDateTime, calendar2);
	}

	private void assertDateTimeEqualsCalendar(OffsetDateTime offsetDateTime, Calendar calendar) {
		assertEquals(offsetDateTime.toInstant(), calendar.toInstant());
		assertEquals(TimeZone.getTimeZone(offsetDateTime.getOffset()), calendar.getTimeZone());
	}
}
