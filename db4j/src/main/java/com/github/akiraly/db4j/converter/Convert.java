package com.github.akiraly.db4j.converter;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@Nonnull
public class Convert {
	private static final TimeConverter timeConverter = new TimeConverter();

	public static OffsetDateTime date2UtcDateTime(Date date) {
		return timeConverter.date2UtcDateTime(date);
	}

	public static Optional<OffsetDateTime> date2OptUtcDateTime(
			@Nullable Date date) {
		return timeConverter.date2OptUtcDateTime(date);
	}

	@Nullable
	public static Date dateTime2Date(Optional<OffsetDateTime> offsetDateTime) {
		return timeConverter.dateTime2Date(offsetDateTime);
	}

	public static Date dateTime2Date(OffsetDateTime offsetDateTime) {
		return timeConverter.dateTime2Date(offsetDateTime);
	}
}
