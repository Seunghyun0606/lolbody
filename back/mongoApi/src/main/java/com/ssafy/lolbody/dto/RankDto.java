package com.ssafy.lolbody.dto;

public class RankDto {
	private String tier;
	private String rank;
	private int leaguePoints;
	private int wins;
	private int losses;
	private double winRate;

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

	public double getWinRate() {
		return winRate;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}

	@Override
	public String toString() {
		return "RankDto [tier=" + tier + ", rank=" + rank + ", leaguePoints=" + leaguePoints + ", wins=" + wins
				+ ", losses=" + losses + ", winRate=" + winRate + "]";
	}

}
