package com.ssafy.lolbody.dto;

import java.util.List;
import java.util.Map;

public class MatchFrameDto {
	private Map<String, MatchParticipantFrameDto> participantFrames;
	private List<MatchEventDto> events;
	private long timestamp;
	public Map<String, MatchParticipantFrameDto> getParticipantFrames() {
		return participantFrames;
	}
	public void setParticipantFrames(Map<String, MatchParticipantFrameDto> participantFrames) {
		this.participantFrames = participantFrames;
	}
	public List<MatchEventDto> getEvents() {
		return events;
	}
	public void setEvents(List<MatchEventDto> events) {
		this.events = events;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "MatchFrameDto [participantFrames=" + participantFrames + ", events=" + events + ", timestamp="
				+ timestamp + "]";
	}
}
