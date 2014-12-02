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
package com.github.akiraly.ver4j;

import javax.annotation.Nonnull;

@Nonnull
public class ArgExceptionFactory extends AExceptionFactory {
	@Override
	public RuntimeException notNullException(Object name) {
		return newException("Argument \"%s\" is null.", name);
	}

	@Override
	protected RuntimeException notEmptyException(String type, Object name) {
		return newException("%s argument \"%s\" is empty.", type, name);
	}

	@Override
	public RuntimeException instanceOfException(Class<?> clazz, Object name) {
		return newException("Argument \"%s\" is not instanceof %s.", name,
				clazz);
	}

	@Override
	protected RuntimeException createException(String message) {
		return new IllegalArgumentException(message);
	}
}
