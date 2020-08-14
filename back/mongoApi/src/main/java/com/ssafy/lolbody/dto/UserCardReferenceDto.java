package com.ssafy.lolbody.dto;

public class UserCardReferenceDto {
	private long timestamp;
	private String summonerName;
	private int profileIconId;
	private int summonerLevel;
	private RankDto soloRank;
	private RankDto freeRank;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
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

	public RankDto getSoloRank() {
		return soloRank;
	}

	public void setSoloRank(RankDto soloRank) {
		this.soloRank = soloRank;
	}

	public RankDto getFreeRank() {
		return freeRank;
	}

	public void setFreeRank(RankDto freeRank) {
		this.freeRank = freeRank;
	}

	@Override
	public String toString() {
		return "UserCardReferenceDto [timestamp=" + timestamp + ", summonerName=" + summonerName + ", profileIconId="
				+ profileIconId + ", summonerLevel=" + summonerLevel + ", soloRank=" + soloRank + ", freeRank="
				+ freeRank + "]";
	}

}
