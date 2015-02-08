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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import javax.annotation.Nonnull;

@Nonnull
public abstract class ResultSets {
	private ResultSets() {
	}

	public static UUID readBase64Uuid(ResultSet rs, String colName)
			throws SQLException {
		String encodedUuid = argNotNull(rs, "rs").getString(
				argNotNull(colName, "colName"));
		return UuidBase64.decode(encodedUuid);
	}

	public static LocalDateTime readLocalDateTime(ResultSet rs, String colName)
			throws SQLException {
		Timestamp timestamp = argNotNull(rs, "rs").getTimestamp(
				argNotNull(colName, "colName"), Jsr310JdbcUtils.UTC_CAL);
		return LocalDateTime.ofInstant(timestamp.toInstant(), ZoneOffset.UTC);
	}

	public static LocalDate readLocalDate(ResultSet rs, String colName)
			throws SQLException {
		Date date = argNotNull(rs, "rs").getDate(
				argNotNull(colName, "colName"), Jsr310JdbcUtils.UTC_CAL);
		return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()),
				ZoneOffset.UTC).toLocalDate();
	}
}
