/**
 * Copyright (c) 2004-2011 QOS.ch
 * All rights reserved.
 *
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 *
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 *
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package org.apache.juli.logging;

import static org.junit.Assert.assertTrue;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

public class InvokeJuliTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(InvokeJuliTest.class);

	private final int beforeCount = getCount();

	@Test(timeout = 100)
	public void testIsEnabledAPI() {
		// assume that we are running over slf4j-jdk14
		Log log = LogFactory.getLog(InvokeJuliTest.class);
		assertTrue(log.isTraceEnabled());
		assertTrue(log.isDebugEnabled());
		assertTrue(log.isInfoEnabled());
		assertTrue(log.isWarnEnabled());
		assertTrue(log.isErrorEnabled());
		assertTrue(log.isFatalEnabled());
		assertCallCount(6);
	}

	@Test(timeout = 150)
	public void testPrintAPI() {
		Log log = LogFactory.getLog(InvokeJuliTest.class);
		Exception e = new Exception("just testing");

		log.trace(null);
		log.trace("trace message");

		log.debug(null);
		log.debug("debug message");

		log.info(null);
		log.info("info  message");

		log.warn(null);
		log.warn("warn message");

		log.error(null);
		log.error("error message");

		log.fatal(null);
		log.fatal("fatal message");

		log.trace(null, e);
		log.trace("trace message", e);

		log.debug(null, e);
		log.debug("debug message", e);

		log.info(null, e);
		log.info("info  message", e);

		log.warn(null, e);
		log.warn("warn message", e);

		log.error(null, e);
		log.error("error message", e);

		log.fatal(null, e);
		log.fatal("fatal message", e);

		assertCallCount(24);
	}

	@Test(timeout = 150)
	public void testJdbcPool() {
		LogFactory.getLog(DataSource.class).info("info test");
		LogFactory.getLog(ConnectionPool.class).warn("warn test");
		new PoolProperties().setValidatorClassName("foo");
	}

	private void assertCallCount(int callIncrement) {
		int currentCount = getCount();
		assertTrue("Expected a call count increment at least by "
				+ callIncrement + " from " + beforeCount + " to "
				+ currentCount, currentCount >= beforeCount + callIncrement);
	}

	private static int getCount() {
		return StaticLoggerBinder.getSingleton().getLoggerFactory()
				.getCallCount(LOGGER);
	}
}
