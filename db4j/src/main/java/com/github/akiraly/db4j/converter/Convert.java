package com.github.akiraly.db4j.converter;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

@Nonnull
public class Convert {
	private static final TimeConverter timeConverter = new TimeConverter();

	public static DateTime date2UtcDateTime(Date date) {
		return timeConverter.date2UtcDateTime(date);
	}

	public static Optional<DateTime> date2OptUtcDateTime(@Nullable Date date) {
		return timeConverter.date2OptUtcDateTime(date);
	}

	@Nullable
	public static Date dateTime2Date(Optional<DateTime> dateTime) {
		return timeConverter.dateTime2Date(dateTime);
	}

	public static Date dateTime2Date(DateTime dateTime) {
		return timeConverter.dateTime2Date(dateTime);
	}
}
