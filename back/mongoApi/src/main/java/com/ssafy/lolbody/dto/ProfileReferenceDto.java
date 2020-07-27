package com.ssafy.lolbody.dto;

public class ProfileReferenceDto {
	private long timestamp;

	private int profileIconId;
	private int summonerLevel;

	private String summonerName;
	private String tier;
	private String rank;
	private int leaguePoints;

	private MatchRecordDto rankedRecord;
	private MatchRecordDto blindRecord;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
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

	public MatchRecordDto getRankedRecord() {
		return rankedRecord;
	}

	public void setRankedRecord(MatchRecordDto rankedRecord) {
		this.rankedRecord = rankedRecord;
	}

	public MatchRecordDto getBlindRecord() {
		return blindRecord;
	}

	public void setBlindRecord(MatchRecordDto blindRecord) {
		this.blindRecord = blindRecord;
	}

	@Override
	public String toString() {
		return "ProfileReferenceDto [timestamp=" + timestamp + ", profileIconId=" + profileIconId + ", summonerLevel="
				+ summonerLevel + ", summonerName=" + summonerName + ", tier=" + tier + ", rank=" + rank
				+ ", leaguePoints=" + leaguePoints + ", rankedRecord=" + rankedRecord + ", blindRecord=" + blindRecord
				+ "]";
	}
}
