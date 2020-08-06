package com.ssafy.lolbody.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tmp")
public class MatchRecordDto {
	@Id
	private long gameId;
	private int queue;
	private long timestamp;
	private long duration;
	private boolean noGame;
	private int myIndex;
	private String myTeam;
	private TeamRecordDto blueTeam;
	private TeamRecordDto redTeam;

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public int getQueue() {
		return queue;
	}

	public void setQueue(int queue) {
		this.queue = queue;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public boolean isNoGame() {
		return noGame;
	}

	public void setNoGame(boolean noGame) {
		this.noGame = noGame;
	}

	public int getMyIndex() {
		return myIndex;
	}

	public void setMyIndex(int myIndex) {
		this.myIndex = myIndex;
	}

	public String getMyTeam() {
		return myTeam;
	}

	public void setMyTeam(String myTeam) {
		this.myTeam = myTeam;
	}

	public TeamRecordDto getBlueTeam() {
		return blueTeam;
	}

	public void setBlueTeam(TeamRecordDto blueTeam) {
		this.blueTeam = blueTeam;
	}

	public TeamRecordDto getRedTeam() {
		return redTeam;
	}

	public void setRedTeam(TeamRecordDto redTeam) {
		this.redTeam = redTeam;
	}

	@Override
	public String toString() {
		return "MatchRecordDto [gameId=" + gameId + ", queue=" + queue + ", timestamp=" + timestamp + ", duration="
				+ duration + ", noGame=" + noGame + ", myIndex=" + myIndex + ", myTeam=" + myTeam + ", blueTeam="
				+ blueTeam + ", redTeam=" + redTeam + "]";
	}

}
