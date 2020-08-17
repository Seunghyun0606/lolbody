package com.ssafy.lolbody.dto;

import java.util.List;

public class SummonerValueResultDto {
	private RadarDto lane1;
	private RadarDto lane2;
	private List<String> badges;
	public RadarDto getLane1() {
		return lane1;
	}
	public void setLane1(RadarDto lane1) {
		this.lane1 = lane1;
	}
	public RadarDto getLane2() {
		return lane2;
	}
	public void setLane2(RadarDto lane2) {
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
