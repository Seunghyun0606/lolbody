package com.ssafy.lolbody.dto;

public class WinRateDto {
	private int totalGames;
	private int wins;
	private int losses;
	private double winRate;

	public WinRateDto() {
	}

	public WinRateDto(int totalGames, int wins, int losses, double winRate) {
		this.totalGames = totalGames;
		this.wins = wins;
		this.losses = losses;
		this.winRate = winRate;
	}

	public int getTotalGames() {
		return totalGames;
	}

	public void setTotalGames(int totalGames) {
		this.totalGames = totalGames;
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
		return "WinRateDto [totalGames=" + totalGames + ", wins=" + wins + ", losses=" + losses + ", winRate=" + winRate
				+ "]";
	}

}
