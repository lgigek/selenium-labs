package br.com.lgigek.steps;

import br.com.lgigek.core.EvidenceHandler;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {

	private EvidenceHandler evidenceHandler;

	@Before
	public void setUp(final Scenario scenario) {
		evidenceHandler = EvidenceHandler.getInstance();
		evidenceHandler.setFolderPath(scenario.getSourceTagNames());
	}

	@After
	public void tearDown(final Scenario scenario) {
		if (scenario.getStatus().equals("failed")) {
			evidenceHandler.takeErrorEvidence();
		}
	}

}
