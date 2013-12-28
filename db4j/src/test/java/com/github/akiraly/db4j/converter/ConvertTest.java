package com.github.akiraly.db4j.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import javax.annotation.Nonnull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.google.common.base.Optional;

@Nonnull
public class ConvertTest {

	@Test(timeout = 800)
	public void testDate2UtcDateTime() {
		Date date = new Date();
		DateTime dateTime = Convert.date2UtcDateTime(date);
		assertNotNull(dateTime);
		assertEquals(DateTimeZone.UTC, dateTime.getZone());
		assertEquals(date, dateTime.toDate());
	}

	@Test(timeout = 800)
	public void testDate2OptUtcDateTime() {
		assertFalse(Convert.date2OptUtcDateTime(null).isPresent());

		Date date = new Date();
		Optional<DateTime> dateTime = Convert.date2OptUtcDateTime(date);
		assertNotNull(dateTime);
		assertTrue(dateTime.isPresent());
		assertEquals(DateTimeZone.UTC, dateTime.get().getZone());
		assertEquals(date, dateTime.get().toDate());
	}

	@Test(timeout = 800)
	public void testDateTime2Date() {
		DateTime dateTime = new DateTime(DateTimeZone.UTC);
		Date date = Convert.dateTime2Date(dateTime);
		assertNotNull(date);
		assertEquals(dateTime.toDate(), date);

		assertNull(Convert.dateTime2Date(Optional.<DateTime> absent()));

		Date date2 = Convert.dateTime2Date(Optional.of(dateTime));
		assertNotNull(date2);
		assertEquals(dateTime.toDate(), date2);
	}
}
