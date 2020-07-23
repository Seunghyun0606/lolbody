package com.ssafy.lolbody.dto;

public class MatchParticipantFrameDto {
	private int participantId;
	private int minionsKilled;
	private int teamScore;
	private int dominionScore;
	private int totalGold;
	private int level;
	private int xp;
	private int currentGold;
	private MatchPositionDto position;
	private int jungleMinionsKilled;
	public int getParticipantId() {
		return participantId;
	}
	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}
	public int getMinionsKilled() {
		return minionsKilled;
	}
	public void setMinionsKilled(int minionsKilled) {
		this.minionsKilled = minionsKilled;
	}
	public int getTeamScore() {
		return teamScore;
	}
	public void setTeamScore(int teamScore) {
		this.teamScore = teamScore;
	}
	public int getDominionScore() {
		return dominionScore;
	}
	public void setDominionScore(int dominionScore) {
		this.dominionScore = dominionScore;
	}
	public int getTotalGold() {
		return totalGold;
	}
	public void setTotalGold(int totalGold) {
		this.totalGold = totalGold;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getXp() {
		return xp;
	}
	public void setXp(int xp) {
		this.xp = xp;
	}
	public int getCurrentGold() {
		return currentGold;
	}
	public void setCurrentGold(int currentGold) {
		this.currentGold = currentGold;
	}
	public MatchPositionDto getPosition() {
		return position;
	}
	public void setPosition(MatchPositionDto position) {
		this.position = position;
	}
	public int getJungleMinionsKilled() {
		return jungleMinionsKilled;
	}
	public void setJungleMinionsKilled(int jungleMinionsKilled) {
		this.jungleMinionsKilled = jungleMinionsKilled;
	}
	@Override
	public String toString() {
		return "MatchParticipantFrameDto [participantId=" + participantId + ", minionsKilled=" + minionsKilled
				+ ", teamScore=" + teamScore + ", dominionScore=" + dominionScore + ", totalGold=" + totalGold
				+ ", level=" + level + ", xp=" + xp + ", currentGold=" + currentGold + ", position=" + position
				+ ", jungleMinionsKilled=" + jungleMinionsKilled + "]";
	}
}
