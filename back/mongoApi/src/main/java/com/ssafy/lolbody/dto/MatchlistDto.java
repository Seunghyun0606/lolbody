package com.ssafy.lolbody.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matchlists")
public class MatchlistDto {
	@Id
	private String summonerId;
	private List<MatchReferenceDto> matches;

	public static class MatchReferenceDto {
		long gameId;
		String role;
		int season;
		String platformId;
		int champion;
		int queue;
		String lane;
		long timestamp;

		public long getGameId() {
			return gameId;
		}

		public void setGameId(long gameId) {
			this.gameId = gameId;
		}

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		public int getSeason() {
			return season;
		}

		public void setSeason(int season) {
			this.season = season;
		}

		public String getPlatformId() {
			return platformId;
		}

		public void setPlatformId(String platformId) {
			this.platformId = platformId;
		}

		public int getChampion() {
			return champion;
		}

		public void setChampion(int champion) {
			this.champion = champion;
		}

		public int getQueue() {
			return queue;
		}

		public void setQueue(int queue) {
			this.queue = queue;
		}

		public String getLane() {
			return lane;
		}

		public void setLane(String lane) {
			this.lane = lane;
		}

		public long getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}

		@Override
		public String toString() {
			return "MatchReferenceDto [gameId=" + gameId + ", role=" + role + ", season=" + season + ", platformId="
					+ platformId + ", champion=" + champion + ", queue=" + queue + ", lane=" + lane + ", timestamp="
					+ timestamp + "]";
		}

	}

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	public List<MatchReferenceDto> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchReferenceDto> matches) {
		this.matches = matches;
	}

	@Override
	public String toString() {
		return "MatchlistDto [summonerId=" + summonerId + ", matches=" + matches + "]";
	}

}
