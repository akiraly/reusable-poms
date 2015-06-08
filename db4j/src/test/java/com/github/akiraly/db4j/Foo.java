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

import javax.annotation.Nonnull;

@Nonnull
public class Foo {
	private final String bar;

	private final LocalDateTime dt;

	private final LocalDate localDate;

	public Foo(String bar, LocalDateTime dt) {
		this(bar, dt, argNotNull(dt, "dt").toLocalDate());
	}

	public Foo(String bar, LocalDateTime dt, LocalDate localDate) {
		this.bar = argNotNull(bar, "bar");
		this.dt = argNotNull(dt, "dt");
		this.localDate = argNotNull(localDate, "localDate");
	}

	public String getBar() {
		return bar;
	}

	public LocalDateTime getDt() {
		return dt;
	}

	public LocalDate getLocalDate() {
		return localDate;
	}
}
