package de.wwu.wmss.core;

public class Movement {

	private String movementIdentifier;
	private String movementName;	
	private String beatUnit;
	private int beatsPerMinute;

	public Movement() {
		super();
	}

	public String getIdentifier() {
		return movementIdentifier;
	}

	public void setMovementIdentifier(String identifier) {
		this.movementIdentifier = identifier;
	}

	public String getMovementName() {
		return movementName;
	}

	public void setMovementLabel(String movementName) {
		this.movementName = movementName;
	}

	public String getBeatUnit() {
		return beatUnit;
	}

	public void setBeatUnit(String beatunit) {
		this.beatUnit = beatunit;
	}
	
	public int getBeatsPerMinute() {
		return beatsPerMinute;
	}

	public void setBeatsPerMinute(int beatsPerMinute) {
		this.beatsPerMinute = beatsPerMinute;
	}

}
