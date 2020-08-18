package com.ssafy.lolbody.dto;

import java.util.List;
import java.util.Map;

public class MatchResultDto {
	private Map<String, BadgeDto> badgeMap;
	private List<MatchRecordDto> matchRecordList;

	public Map<String, BadgeDto> getBadgeMap() {
		return badgeMap;
	}

	public void setBadgeMap(Map<String, BadgeDto> badgeMap) {
		this.badgeMap = badgeMap;
	}

	public List<MatchRecordDto> getMatchRecordList() {
		return matchRecordList;
	}

	public void setMatchRecordList(List<MatchRecordDto> matchRecordList) {
		this.matchRecordList = matchRecordList;
	}

	@Override
	public String toString() {
		return "MatchResultDto [badgeMap=" + badgeMap + ", matchRecordList=" + matchRecordList + "]";
	}

}
