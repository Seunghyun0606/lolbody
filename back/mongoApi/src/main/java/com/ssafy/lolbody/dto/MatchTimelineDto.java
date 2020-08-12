package com.ssafy.lolbody.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matchtimelines")
public class MatchTimelineDto {
	@Id
	private long gameId;
	private List<MatchFrameDto> frames;
	private long frameInterval;

	public List<MatchFrameDto> getFrames() {
		return frames;
	}

	public void setFrames(List<MatchFrameDto> frames) {
		this.frames = frames;
	}

	public long getFrameInterval() {
		return frameInterval;
	}

	public void setFrameInterval(long frameInterval) {
		this.frameInterval = frameInterval;
	}

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	@Override
	public String toString() {
		return "MatchTimelineDto [gameId=" + gameId + ", frames=" + frames + ", frameInterval=" + frameInterval + "]";
	}

}
