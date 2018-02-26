package br.com.lgigek.scenario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Scenario {

	private static final Logger logger = LogManager.getLogger(Scenario.class);

	public static void fail(String message) {
		logger.error(message);
		throw  new RuntimeException(message);
	}

}
