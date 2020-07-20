package com.ssafy.lolbody.dto;

public class LeagueEntryDTO {
	String leagueId;
//	String summonerId; // Player's encrypted summonerId.
	String summonerName;
	String queueType;
	String tier;
	String leagueRank;
	int leaguePoints;
	int wins; // Winning team on Summoners Rift.
	int losses; // Losing team on Summoners Rift.
//	boolean hotStreak;
//	boolean veteran;
//	boolean freshBlood;
//	boolean inactive;

	public LeagueEntryDTO() {
	}

	public LeagueEntryDTO(String leagueId, String summonerName, String queueType, String tier, String leagueRank,
			int leaguePoints, int wins, int losses) {
		this.leagueId = leagueId;
		this.summonerName = summonerName;
		this.queueType = queueType;
		this.tier = tier;
		this.leagueRank = leagueRank;
		this.leaguePoints = leaguePoints;
		this.wins = wins;
		this.losses = losses;
	}

	public String getLeagueId() {
		return leagueId;
	}

	public void setLeagueId(String leagueId) {
		this.leagueId = leagueId;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}

	public String getQueueType() {
		return queueType;
	}

	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}

	public String getTier() {
		return tier;
	}

	public void setTier(String tier) {
		this.tier = tier;
	}

	public String getRank() {
		return leagueRank;
	}

	public void setRank(String leagueRank) {
		this.leagueRank = leagueRank;
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

	@Override
	public String toString() {
		return "LeagueEntryDTO [leagueId=" + leagueId + ", summonerName=" + summonerName + ", queueType=" + queueType
				+ ", tier=" + tier + ", leagueRank=" + leagueRank + ", leaguePoints=" + leaguePoints + ", wins=" + wins
				+ ", losses=" + losses + "]";
	}

}
