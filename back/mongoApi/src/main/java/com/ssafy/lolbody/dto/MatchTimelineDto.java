package com.ssafy.lolbody.dto;

import java.util.List;

public class MatchTimelineDto {
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
	@Override
	public String toString() {
		return "MatchTimelineDto [frames=" + frames + ", frameInterval=" + frameInterval + "]";
	}
}
