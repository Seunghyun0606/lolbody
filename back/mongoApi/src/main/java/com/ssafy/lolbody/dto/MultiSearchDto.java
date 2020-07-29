package com.ssafy.lolbody.dto;

import java.util.List;

public class MultiSearchDto {
	private String summonerName;
	private int totalGame;
	private int wins;
	private int losses;
	private int summonerLevel;
	private String highestAchievedSeasonTier;
	private String lane;
	private List<RecentGamesDto> recentGames;
	private List<String> mostChamp;
	private List<Boolean> recentMatchResults;
	
	public String getHighestAchievedSeasonTier() {
		return highestAchievedSeasonTier;
	}
	public void setHighestAchievedSeasonTier(String highestAchievedSeasonTier) {
		this.highestAchievedSeasonTier = highestAchievedSeasonTier;
	}
	public String getSummonerName() {
		return summonerName;
	}
	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}
	public int getTotalGame() {
		return totalGame;
	}
	public void setTotalGame(int totalGame) {
		this.totalGame = totalGame;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLosses() {
		return losses;
	}
	public void setLosses(int losses) {
		this.losses = losses;
	}
	public int getSummonerLevel() {
		return summonerLevel;
	}
	public void setSummonerLevel(int summonerLevel) {
		this.summonerLevel = summonerLevel;
	}
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	public List<RecentGamesDto> getRecentGames() {
		return recentGames;
	}
	public void setRecentGames(List<RecentGamesDto> recentGames) {
		this.recentGames = recentGames;
	}
	public List<String> getMostChamp() {
		return mostChamp;
	}
	public void setMostChamp(List<String> mostChamp) {
		this.mostChamp = mostChamp;
	}
	public List<Boolean> getRecentMatchResults() {
		return recentMatchResults;
	}
	public void setRecentMatchResults(List<Boolean> recentMatchResults) {
		this.recentMatchResults = recentMatchResults;
	}
	@Override
	public String toString() {
		return "MultiSearchDto [summonerName=" + summonerName + ", totalGame=" + totalGame + ", wins=" + wins
				+ ", losses=" + losses + ", summonerLevel=" + summonerLevel + ", highestAchievedSeasonTier="
				+ highestAchievedSeasonTier + ", lane=" + lane + ", recentGame=" + recentGames + ", mostChamp="
				+ mostChamp + ", recentMatchlist=" + recentMatchResults + "]";
	}
}
