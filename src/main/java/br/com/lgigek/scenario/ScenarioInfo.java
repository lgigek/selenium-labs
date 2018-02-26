package br.com.lgigek.scenario;

public class ScenarioInfo {

	private String infoName;
	private String value;

	public ScenarioInfo(String infoName, String value) {
		this.infoName = infoName;
		this.value = value;
	}
	
	public String getInfoName() {
		return infoName;
	}

	public void setInfoName(String info) {
		this.infoName = info;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
