package com.ssafy.lolbody.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tmp")
public class MatchDto {
	@Id
	private long gameId;
	private List<ParticipantIdentityDto> participantIdentities;
	private int queueId;
	private String gameType;
	private long gameDuration;
	private List<TeamStatsDto> teams;
	private String platformId;
	private long gameCreation;
	private int seasonId;
	private String gameVersion;
	private int mapId;
	private String gameMode;
	private List<ParticipantDto> participants;
	private boolean flag;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public List<ParticipantIdentityDto> getParticipantIdentities() {
		return participantIdentities;
	}

	public void setParticipantIdentities(List<ParticipantIdentityDto> participantIdentities) {
		this.participantIdentities = participantIdentities;
	}

	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	public String getGameType() {
		return gameType;
	}

	public void setGameType(String gameType) {
		this.gameType = gameType;
	}

	public long getGameDuration() {
		return gameDuration;
	}

	public void setGameDuration(long gameDuration) {
		this.gameDuration = gameDuration;
	}

	public List<TeamStatsDto> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamStatsDto> teams) {
		this.teams = teams;
	}

	public String getPlatformId() {
		return platformId;
	}

	public void setPlatformId(String platformId) {
		this.platformId = platformId;
	}

	public long getGameCreation() {
		return gameCreation;
	}

	public void setGameCreation(long gameCreation) {
		this.gameCreation = gameCreation;
	}

	public int getSeasonId() {
		return seasonId;
	}

	public void setSeasonId(int seasonId) {
		this.seasonId = seasonId;
	}

	public String getGameVersion() {
		return gameVersion;
	}

	public void setGameVersion(String gameVersion) {
		this.gameVersion = gameVersion;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getGameMode() {
		return gameMode;
	}

	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}

	public List<ParticipantDto> getParticipants() {
		return participants;
	}

	public void setParticipants(List<ParticipantDto> participants) {
		this.participants = participants;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "MatchDto [gameId=" + gameId + ", participantIdentities=" + participantIdentities + ", queueId="
				+ queueId + ", gameType=" + gameType + ", gameDuration=" + gameDuration + ", teams=" + teams
				+ ", platformId=" + platformId + ", gameCreation=" + gameCreation + ", seasonId=" + seasonId
				+ ", gameVersion=" + gameVersion + ", mapId=" + mapId + ", gameMode=" + gameMode + ", participants="
				+ participants + ", flag=" + flag + "]";
	}

}
