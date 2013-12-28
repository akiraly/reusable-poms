package com.github.akiraly.db4j.converter;

import static com.github.akiraly.ver4j.Verify.argNotNull;
import static com.google.common.base.Optional.fromNullable;

import java.util.Date;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import com.google.common.base.Function;
import com.google.common.base.Optional;

@Nonnull
public class TimeConverter {
	private static final Function<DateTime, Date> dateTime2DateFunction = new Function<DateTime, Date>() {
		@Override
		public Date apply(DateTime dateTime) {
			return dateTime.toDate();
		}
	};

	private static final Function<Date, DateTime> date2UtcDateTimeFunction = new Function<Date, DateTime>() {
		@Override
		public DateTime apply(Date date) {
			return new DateTime(date, DateTimeZone.UTC);
		}
	};

	public DateTime date2UtcDateTime(Date date) {
		return date2UtcDateTimeFunction.apply(argNotNull(date, "date"));
	}

	public Optional<DateTime> date2OptUtcDateTime(@Nullable Date date) {
		return fromNullable(date).transform(date2UtcDateTimeFunction);
	}

	@Nullable
	public Date dateTime2Date(Optional<DateTime> dateTime) {
		return argNotNull(dateTime, "dateTime")
				.transform(dateTime2DateFunction).orNull();
	}

	public Date dateTime2Date(DateTime dateTime) {
		return dateTime2DateFunction.apply(argNotNull(dateTime, "dateTime"));
	}
}
