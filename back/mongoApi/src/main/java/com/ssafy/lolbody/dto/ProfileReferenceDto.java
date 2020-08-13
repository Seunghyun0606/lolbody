package com.ssafy.lolbody.dto;

public class ProfileReferenceDto {
	private long timestamp;
	private int games;

	private int profileIconId;
	private int summonerLevel;

	private String summonerName;
	private String tier;
	private String rank;
	private int leaguePoints;

	private TotalRecordDto rankedRecord;
	private TotalRecordDto blindRecord;

	private SummonerValueResultDto summonerValue;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getGames() {
		return games;
	}

	public void setGames(int games) {
		this.games = games;
	}

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

	public TotalRecordDto getRankedRecord() {
		return rankedRecord;
	}

	public void setRankedRecord(TotalRecordDto rankedRecord) {
		this.rankedRecord = rankedRecord;
	}

	public TotalRecordDto getBlindRecord() {
		return blindRecord;
	}

	public void setBlindRecord(TotalRecordDto blindRecord) {
		this.blindRecord = blindRecord;
	}

	public SummonerValueResultDto getSummonerValue() {
		return summonerValue;
	}

	public void setSummonerValue(SummonerValueResultDto summonerValue) {
		this.summonerValue = summonerValue;
	}

	@Override
	public String toString() {
		return "ProfileReferenceDto [timestamp=" + timestamp + ", games=" + games + ", profileIconId=" + profileIconId
				+ ", summonerLevel=" + summonerLevel + ", summonerName=" + summonerName + ", tier=" + tier + ", rank="
				+ rank + ", leaguePoints=" + leaguePoints + ", rankedRecord=" + rankedRecord + ", blindRecord="
				+ blindRecord + ", summonerValue=" + summonerValue + "]";
	}

}
