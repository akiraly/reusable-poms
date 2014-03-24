package com.github.akiraly.db4j.converter;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static java.util.Optional.ofNullable;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Nonnull
public class TimeConverter {
	public OffsetDateTime date2UtcDateTime(Date date) {
		return OffsetDateTime
				.ofInstant(argNotNull(date, "date").toInstant(), ZoneOffset.UTC);
	}

	public Optional<OffsetDateTime> date2OptUtcDateTime(@Nullable Date date) {
		return ofNullable(date).map(this::date2UtcDateTime);
	}

	@Nullable
	public Date dateTime2Date(Optional<OffsetDateTime> offsetDateTime) {
		return argNotNull(offsetDateTime, "offsetDateTime").map(
				this::dateTime2Date).orElse(null);
	}

	public Date dateTime2Date(OffsetDateTime offsetDateTime) {
		return Date.from(argNotNull(offsetDateTime, "offsetDateTime")
				.toInstant());
	}

	public OffsetDateTime calendar2DateTime(Calendar calendar) {
		return OffsetDateTime
				.ofInstant(argNotNull(calendar, "calendar").toInstant(), calendar.getTimeZone().toZoneId());
	}

	public Optional<OffsetDateTime> calendar2OptDateTime(@Nullable Calendar calendar) {
		return ofNullable(calendar).map(this::calendar2DateTime);
	}

	@Nullable
	public Calendar dateTime2Calendar(Optional<OffsetDateTime> offsetDateTime) {
		return argNotNull(offsetDateTime, "offsetDateTime").map(
				this::dateTime2Calendar).orElse(null);
	}

	public Calendar dateTime2Calendar(OffsetDateTime offsetDateTime) {
		argNotNull(offsetDateTime, "offsetDateTime");

		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(offsetDateTime.getOffset()), Locale.US);
		calendar.setTimeInMillis(offsetDateTime.toInstant().toEpochMilli());

		return calendar;
	}

}
