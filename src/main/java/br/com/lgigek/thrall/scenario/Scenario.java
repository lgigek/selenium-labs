package br.com.lgigek.thrall.scenario;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Scenario {

	private static final Logger logger = LogManager.getLogger(Scenario.class);
	private static List<ScenarioInfo> infoList = new ArrayList<>();

	public static void fail(String message) {
		logger.error(message);
		throw new RuntimeException(message);
	}

	public static void clearInfoList() {
		infoList = new ArrayList<ScenarioInfo>();
	}

	public static void setInfo(ScenarioInfo info) {
		infoList.add(info);
	}

	public static String getInfoValue(String desiredInfoName) {
		for (ScenarioInfo info : infoList) {
			if (info.getInfoName().equals(desiredInfoName))
				return info.getValue();
		}
		return null;
	}

}