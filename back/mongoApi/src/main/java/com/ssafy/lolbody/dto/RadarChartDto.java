package com.ssafy.lolbody.dto;

import java.util.List;

public class RadarChartDto {
	private List<LaneInfoDto> lane1;
	private List<LaneInfoDto> lane2;
	private String tier;
	
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public List<LaneInfoDto> getLane1() {
		return lane1;
	}
	public void setLane1(List<LaneInfoDto> lane1) {
		this.lane1 = lane1;
	}
	public List<LaneInfoDto> getLane2() {
		return lane2;
	}
	public void setLane2(List<LaneInfoDto> lane2) {
		this.lane2 = lane2;
	}
	@Override
	public String toString() {
		return "RadarChartDto [lane1=" + lane1 + ", lane2=" + lane2 + ", tier=" + tier + "]";
	}
	
}
