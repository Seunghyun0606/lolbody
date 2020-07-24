package com.ssafy.lolbody.dto;

public class ProfileDto {
	private int profileIconId;
	private int summonerLevel;

	private String summonerName;
	private String tier;
	private String rank;
	private int leaguePoints;

	private int rankedWins;
	private int rankedLosses;
	private double rankedWinRate;

	private int blindWins;
	private int blindLosses;
	private double blindWinRate;

	private int rankedMostWins;
	private int rankedMostLosses;
	private double rankedMostWinRate;

	private int blindMostWins;
	private int blindMostLosses;
	private double blindMostWinRate;

	public int getProfileIconId() {
		return profileIconId;
	}

	public void setProfileIconId(int profileIconId) {
		this.profileIconId = profileIconId;
	}

	public int getSummonerLevel() {
		return summonerLevel;
	}

	public void setSummonerLevel(int summonerLevel) {
		this.summonerLevel = summonerLevel;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getRank() {
		return rank;
	}

	public void setRank(String rank) {
		this.rank = rank;
	}

	public int getLeaguePoints() {
		return leaguePoints;
	}

	public void setLeaguePoints(int leaguePoints) {
		this.leaguePoints = leaguePoints;
	}

	public int getRankedWins() {
		return rankedWins;
	}

	public void setRankedWins(int rankedWins) {
		this.rankedWins = rankedWins;
	}

	public int getRankedLosses() {
		return rankedLosses;
	}

	public void setRankedLosses(int rankedLosses) {
		this.rankedLosses = rankedLosses;
	}

	public double getRankedWinRate() {
		return rankedWinRate;
	}

	public void setRankedWinRate(double rankedWinRate) {
		this.rankedWinRate = rankedWinRate;
	}

	public int getBlindWins() {
		return blindWins;
	}

	public void setBlindWins(int blindWins) {
		this.blindWins = blindWins;
	}

	public int getBlindLosses() {
		return blindLosses;
	}

	public void setBlindLosses(int blindLosses) {
		this.blindLosses = blindLosses;
	}

	public double getBlindWinRate() {
		return blindWinRate;
	}

	public void setBlindWinRate(double blindWinRate) {
		this.blindWinRate = blindWinRate;
	}

	public int getRankedMostWins() {
		return rankedMostWins;
	}

	public void setRankedMostWins(int rankedMostWins) {
		this.rankedMostWins = rankedMostWins;
	}

	public int getRankedMostLosses() {
		return rankedMostLosses;
	}

	public void setRankedMostLosses(int rankedMostLosses) {
		this.rankedMostLosses = rankedMostLosses;
	}

	public double getRankedMostWinRate() {
		return rankedMostWinRate;
	}

	public void setRankedMostWinRate(double rankedMostWinRate) {
		this.rankedMostWinRate = rankedMostWinRate;
	}

	public int getBlindMostWins() {
		return blindMostWins;
	}

	public void setBlindMostWins(int blindMostWins) {
		this.blindMostWins = blindMostWins;
	}

	public int getBlindMostLosses() {
		return blindMostLosses;
	}

	public void setBlindMostLosses(int blindMostLosses) {
		this.blindMostLosses = blindMostLosses;
	}

	public double getBlindMostWinRate() {
		return blindMostWinRate;
	}

	public void setBlindMostWinRate(double blindMostWinRate) {
		this.blindMostWinRate = blindMostWinRate;
	}

	@Override
	public String toString() {
		return "ProfileDto [profileIconId=" + profileIconId + ", summonerLevel=" + summonerLevel + ", summonerName="
				+ summonerName + ", tier=" + tier + ", rank=" + rank + ", leaguePoints=" + leaguePoints
				+ ", rankedWins=" + rankedWins + ", rankedLosses=" + rankedLosses + ", rankedWinRate=" + rankedWinRate
				+ ", blindWins=" + blindWins + ", blindLosses=" + blindLosses + ", blindWinRate=" + blindWinRate
				+ ", rankedMostWins=" + rankedMostWins + ", rankedMostLosses=" + rankedMostLosses
				+ ", rankedMostWinRate=" + rankedMostWinRate + ", blindMostWins=" + blindMostWins + ", blindMostLosses="
				+ blindMostLosses + ", blindMostWinRate=" + blindMostWinRate + "]";
	}

}
