package org.slf4j.impl;

import static java.lang.Thread.currentThread;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.spi.LoggerFactoryBinder;

public class StaticLoggerBinder implements LoggerFactoryBinder {
	public static final String REQUESTED_API_VERSION = "1.6";

	private static final StaticLoggerBinder SINGLETON = new StaticLoggerBinder();

	private final CountingLoggerFactory loggerFactory = new CountingLoggerFactory();

	public static StaticLoggerBinder getSingleton() {
		return SINGLETON;
	}

	@Override
	public CountingLoggerFactory getLoggerFactory() {
		return loggerFactory;
	}

	@Override
	public String getLoggerFactoryClassStr() {
		return loggerFactory.getClass().getName();
	}

	public static class CountingLoggerFactory implements ILoggerFactory {
		private final Map<String, Logger> loggers = new ConcurrentHashMap<>();

		private void setLogger(String name, Logger logger) {
			loggers.put(name, logger);
		}

		@Override
		public Logger getLogger(String name) {
			Logger logger = loggers.get(name);
			if (logger == null)
				synchronized (this) {
					logger = loggers.get(name);
					if (logger == null) {
						logger = createCountingLogger(name);
						setLogger(name, logger);

					}
				}
			return logger;
		}

		private Logger createCountingLogger(final String name) {
			return (Logger) Proxy.newProxyInstance(currentThread()
					.getContextClassLoader(), new Class<?>[] { Logger.class },
					new LoggerInvocationHandler(name));
		}

		public int getCallCount(Logger logger) {
			return ((LoggerInvocationHandler) Proxy
					.getInvocationHandler(logger)).callCount;
		}
	}

	private static class LoggerInvocationHandler implements InvocationHandler {
		private final String loggerName;
		private int callCount;

		private LoggerInvocationHandler(String loggerName) {
			this.loggerName = loggerName;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) {
			callCount++;
			if ("getName".equals(method.getName())
					&& (args == null || args.length == 0))
				return loggerName;
			if (Boolean.TYPE.equals(method.getReturnType()))
				return true;
			System.out.println(loggerName + " " + method.getName() + " "
					+ Arrays.toString(args));
			return null;
		}
	}
}
