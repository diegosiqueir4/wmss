package de.wwu.wmss.core;

public class PerformanceMedium {

	private String mediumTypeDescription="";
	private String mediumTypeId="";
	private String mediumDescription="";
	private String mediumId="";
	private String mediumCode="";
	private String mediumScoreDescription="";	
	private String movementId;
	private String scoreId;
	private boolean solo;
	private boolean ensemble;
	private String action;
	
	public PerformanceMedium() {
		super();
	}

	public String getTypeLabel() {
		return mediumTypeDescription;
	}

	public void setTypeLabel(String typeDescription) {
		this.mediumTypeDescription = typeDescription;
	}

	public String getTypeIdentifier() {
		return mediumTypeId;
	}

	public void setTypeIdentifier(String typeClassification) {
		this.mediumTypeId = typeClassification;
	}

	public String getLabel() {
		return mediumDescription;
	}

	public void setLabel(String mediumDescription) {
		this.mediumDescription = mediumDescription;
	}

	public String getIdentifier() {
		return mediumId;
	}

	public void setIdentifier(String mediumClassification) {
		this.mediumId = mediumClassification;
	}

	public boolean isSolo() {
		return solo;
	}

	public void setSolo(boolean solo) {
		this.solo = solo;
	}

	public String getMovementId() {
		return movementId;
	}

	public void setMovementId(String movementId) {
		this.movementId = movementId;
	}

	public String getScoreId() {
		return scoreId;
	}

	public void setScoreId(String scoreId) {
		this.scoreId = scoreId;
	}

	public String getScoreLabel() {
		return mediumScoreDescription;
	}

	public void setScoreLabel(String mediumScoreDescription) {
		this.mediumScoreDescription = mediumScoreDescription;
	}

	public boolean isEnsemble() {
		return ensemble;
	}

	public void setEnsemble(boolean ensemble) {
		this.ensemble = ensemble;
	}

	public String getCode() {
		return mediumCode;
	}

	public void setMediumCode(String mediumCode) {
		this.mediumCode = mediumCode;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
}

