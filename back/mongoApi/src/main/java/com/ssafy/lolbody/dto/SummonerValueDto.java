package com.ssafy.lolbody.dto;

public class SummonerValueDto {
	private double aggressiveness;
	private double stability;
	private double influence;
	private String lane;
	public double getAggressiveness() {
		return aggressiveness;
	}
	public void setAggressiveness(double aggressiveness) {
		this.aggressiveness = aggressiveness;
	}
	public double getStability() {
		return stability;
	}
	public void setStability(double stability) {
		this.stability = stability;
	}
	public double getInfluence() {
		return influence;
	}
	public void setInfluence(double influence) {
		this.influence = influence;
	}
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	@Override
	public String toString() {
		return "SummonerValueDto [aggressiveness=" + aggressiveness + ", stability=" + stability + ", influence="
				+ influence + ", lane=" + lane + "]";
	}
}	
