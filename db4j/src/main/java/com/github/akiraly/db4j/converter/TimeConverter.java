package com.github.akiraly.db4j.converter;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static java.util.Optional.ofNullable;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Nonnull
public class TimeConverter {
	private static final Function<OffsetDateTime, Date> offsetDateTime2DateFunction = offsetDateTime -> Date
			.from(offsetDateTime.toInstant());

	private static final Function<Date, OffsetDateTime> date2UtcOffsetDateTimeFunction = date -> OffsetDateTime
			.ofInstant(date.toInstant(), ZoneOffset.UTC);

	public OffsetDateTime date2UtcDateTime(Date date) {
		return date2UtcOffsetDateTimeFunction.apply(argNotNull(date, "date"));
	}

	public Optional<OffsetDateTime> date2OptUtcDateTime(@Nullable Date date) {
		return ofNullable(date).map(date2UtcOffsetDateTimeFunction);
	}

	@Nullable
	public Date dateTime2Date(Optional<OffsetDateTime> offsetDateTime) {
		return argNotNull(offsetDateTime, "offsetDateTime").map(
				offsetDateTime2DateFunction).orElse(null);
	}

	public Date dateTime2Date(OffsetDateTime offsetDateTime) {
		return offsetDateTime2DateFunction.apply(argNotNull(offsetDateTime,
				"offsetDateTime"));
	}
}
