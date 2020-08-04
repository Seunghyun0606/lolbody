package com.ssafy.lolbody.dto;

import java.util.List;

public class SummonerValueResultDto {
	private SummonerValueDto lane1;
	private SummonerValueDto lane2;
	private List<String> badges;
	public SummonerValueDto getLane1() {
		return lane1;
	}
	public void setLane1(SummonerValueDto lane1) {
		this.lane1 = lane1;
	}
	public SummonerValueDto getLane2() {
		return lane2;
	}
	public void setLane2(SummonerValueDto lane2) {
		this.lane2 = lane2;
	}
	public List<String> getBadges() {
		return badges;
	}
	public void setBadges(List<String> badges) {
		this.badges = badges;
	}
	@Override
	public String toString() {
		return "SummonerValueResultDto [lane1=" + lane1 + ", lane2=" + lane2 + ", badges=" + badges + "]";
	}
}
