package com.ssafy.lolbody.dto;

public class PositionDto {
	private String lane;
	private int rate;
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}
	@Override
	public String toString() {
		return "PositionDto [lane=" + lane + ", rate=" + rate + "]";
	}
}
