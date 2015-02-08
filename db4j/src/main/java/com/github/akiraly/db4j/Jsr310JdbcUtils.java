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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Nonnull;

@Nonnull
public abstract class Jsr310JdbcUtils {
	private static final TimeZone UTC = TimeZone.getTimeZone(ZoneOffset.UTC);

	static final Calendar UTC_CAL = newCalendarBuilder().build();

	private Jsr310JdbcUtils() {
	}

	private static Calendar.Builder newCalendarBuilder() {
		return new Calendar.Builder().setTimeZone(UTC).setLocale(Locale.US);
	}

	public static Calendar toUtcCalendar(LocalDateTime localDateTime) {
		argNotNull(localDateTime, "localDateTime");
		return newCalendarBuilder().setInstant(
				localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli()).build();
	}

	public static Calendar toUtcCalendar(LocalDate localDate) {
		argNotNull(localDate, "localDate");
		return newCalendarBuilder().setDate(localDate.getYear(),
				localDate.getMonthValue() - 1, localDate.getDayOfMonth())
				.build();
	}
}
