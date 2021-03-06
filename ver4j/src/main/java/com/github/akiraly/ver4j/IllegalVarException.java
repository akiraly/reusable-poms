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

/**
 * A {@link RuntimeException} variant for variable (local/global) related
 * errors.
 */
@Nonnull
public class IllegalVarException extends RuntimeException {
	private static final long serialVersionUID = 2273098191220061931L;

	public IllegalVarException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalVarException(String message) {
		super(message);
	}
}
