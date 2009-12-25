package org.harpeng.serverstatus.utils;

public class LoggerSingleton {
	private static Logger logger;

	private LoggerSingleton() {
	}

	public static Logger getInstance() {
		if (logger == null) {
			logger = new Logger("servercontrol");
		}
		return logger;
	}
}
