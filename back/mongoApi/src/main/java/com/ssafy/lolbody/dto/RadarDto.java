package com.ssafy.lolbody.dto;

public class RadarDto {
	private int games;
	private RadarReferenceDto radarReference;

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

	public RadarReferenceDto getRadarReference() {
		return radarReference;
	}

	public void setRadarReference(RadarReferenceDto radarReference) {
		this.radarReference = radarReference;
	}

	@Override
	public String toString() {
		return "RadarDto [games=" + games + ", radarReference=" + radarReference + "]";
	}

}
