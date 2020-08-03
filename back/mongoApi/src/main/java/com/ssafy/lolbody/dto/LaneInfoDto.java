package com.ssafy.lolbody.dto;

public class LaneInfoDto {
	private String position;
	private long visionScore;
	private double csPerMin;
	private int kills;
	private int deaths;
	private int assist;
	private long gameDuration;
	private long totalDamageDealtToChampions;
	private long totalDamageTaken;
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public long getVisionScore() {
		return visionScore;
	}
	public void setVisionScore(long visionScore) {
		this.visionScore = visionScore;
	}
	public double getCsPerMin() {
		return csPerMin;
	}
	public void setCsPerMin(double csPerMin) {
		this.csPerMin = csPerMin;
	}
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public int getAssist() {
		return assist;
	}
	public void setAssist(int assist) {
		this.assist = assist;
	}
	public long getGameDuration() {
		return gameDuration;
	}
	public void setGameDuration(long gameDuration) {
		this.gameDuration = gameDuration;
	}
	public long getTotalDamageDealtToChampions() {
		return totalDamageDealtToChampions;
	}
	public void setTotalDamageDealtToChampions(long totalDamageDealtToChampions) {
		this.totalDamageDealtToChampions = totalDamageDealtToChampions;
	}
	public long getTotalDamageTaken() {
		return totalDamageTaken;
	}
	public void setTotalDamageTaken(long totalDamageTaken) {
		this.totalDamageTaken = totalDamageTaken;
	}
	@Override
	public String toString() {
		return "LaneInfoDto [position=" + position + ", visionScore=" + visionScore + ", csPerMin=" + csPerMin
				+ ", kills=" + kills + ", deaths=" + deaths + ", assist=" + assist + ", gameDuration=" + gameDuration
				+ ", totalDamageDealtToChampions=" + totalDamageDealtToChampions + ", totalDamageTaken="
				+ totalDamageTaken + "]";
	}
}
