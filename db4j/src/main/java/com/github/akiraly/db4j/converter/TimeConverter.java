package com.github.akiraly.db4j.converter;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static java.util.Optional.ofNullable;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Nonnull
public class TimeConverter {
	public OffsetDateTime date2UtcDateTime(Date date) {
		return OffsetDateTime
				.ofInstant(argNotNull(date, "date").toInstant(), ZoneOffset.UTC);
	}

	public Optional<OffsetDateTime> date2OptUtcDateTime(@Nullable Date date) {
		return ofNullable(date).map(dt -> date2UtcDateTime(dt));
	}

	@Nullable
	public Date dateTime2Date(Optional<OffsetDateTime> offsetDateTime) {
		return argNotNull(offsetDateTime, "offsetDateTime").map(
				odt -> dateTime2Date(odt)).orElse(null);
	}

	public Date dateTime2Date(OffsetDateTime offsetDateTime) {
		return Date.from(argNotNull(offsetDateTime, "offsetDateTime")
				.toInstant());
	}
}
