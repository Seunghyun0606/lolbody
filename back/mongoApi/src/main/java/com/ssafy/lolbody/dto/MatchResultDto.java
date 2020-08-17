package com.ssafy.lolbody.dto;

import java.util.List;
import java.util.Set;

public class MatchResultDto {
	private Set<BadgeDto> badgeSet;
	private List<MatchRecordDto> matchRecordList;

	public Set<BadgeDto> getBadgeSet() {
		return badgeSet;
	}

	public void setBadgeSet(Set<BadgeDto> badgeSet) {
		this.badgeSet = badgeSet;
	}

	public List<MatchRecordDto> getMatchRecordList() {
		return matchRecordList;
	}

	public void setMatchRecordList(List<MatchRecordDto> matchRecordList) {
		this.matchRecordList = matchRecordList;
	}

	@Override
	public String toString() {
		return "MatchResultDto [badgeSet=" + badgeSet + ", matchRecordList=" + matchRecordList + "]";
	}

}
