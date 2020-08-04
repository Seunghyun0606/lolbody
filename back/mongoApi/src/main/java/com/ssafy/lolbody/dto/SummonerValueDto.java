package com.ssafy.lolbody.dto;

public class SummonerValueDto {
	private double aggressiveness;
	private double stability;
	private double influence;
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
	@Override
	public String toString() {
		return "SummonerValueDto [aggressiveness=" + aggressiveness + ", stability=" + stability + ", influence="
				+ influence + "]";
	}
}	
