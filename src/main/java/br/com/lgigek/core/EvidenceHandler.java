package br.com.lgigek.core;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import cucumber.api.Scenario;

public class EvidenceHandler {

	Browser browser = Browser.getInstance();
	private static EvidenceHandler instance;
	private String folderPath;
	private int stepNumber;

	private static final Logger logger = LogManager.getLogger(EvidenceHandler.class);

	private EvidenceHandler() {
	}

	public static EvidenceHandler getInstance() {
		if (instance == null)
			instance = new EvidenceHandler();
		return instance;
	}

	public void setFolderPath(final Scenario scenario) {
		logger.trace("Setting evidence folder path");
		stepNumber = 1;

		folderPath = System.getProperty("user.dir") + "/target/evidences/" + browser.getBrowserName() + "/";
		for (String tag : scenario.getSourceTagNames()) {
			folderPath += tag.replace("@", "") + "-";
		}
		folderPath = folderPath.substring(0, folderPath.length() - 1) + "/"
				+ new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss").format(Calendar.getInstance().getTime()).toString() + "/";
		folderPath = folderPath.replace("/", File.separator);
		logger.trace("Evidence folder path={}", folderPath);
	}

	public void takeEvidence(String description) {
		createFile(description);
	}

	public void takeErrorEvidence() {
		createFile("Failed scenario evidence");
	}

	private void createFile(String descriptionEvidenceName) {
		logger.trace("Creating evidence file");
		String filePath = String.format("%s/%03d-%s.png", folderPath, stepNumber, descriptionEvidenceName);

		File file = ((TakesScreenshot) browser.getDriver()).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(file, new File(filePath));
			logger.trace("Evidence file {} created", filePath);
		} catch (IOException e) {
			logger.error("Failed to create evidence file {}, error message: {}", filePath, e.getMessage());
		}
		stepNumber += 1;
	}

}
