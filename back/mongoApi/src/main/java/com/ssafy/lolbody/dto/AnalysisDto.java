package com.ssafy.lolbody.dto;

public class AnalysisDto {
	private double totalDamageDealtToChampionsPerMin;
	private double damageDealtToObjectivesPerMin;
	private double visionScorePerMin;
	private double totalDamageTakenPerMin;
	private double totalMinionsKilledPerMin;
	private double killsRatio;
	private double deathsRatio;
	private double killAssistPerMin;

	public double getTotalDamageDealtToChampionsPerMin() {
		return totalDamageDealtToChampionsPerMin;
	}

	public void setTotalDamageDealtToChampionsPerMin(double totalDamageDealtToChampionsPerMin) {
		this.totalDamageDealtToChampionsPerMin = totalDamageDealtToChampionsPerMin;
	}

	public double getDamageDealtToObjectivesPerMin() {
		return damageDealtToObjectivesPerMin;
	}

	public void setDamageDealtToObjectivesPerMin(double damageDealtToObjectivesPerMin) {
		this.damageDealtToObjectivesPerMin = damageDealtToObjectivesPerMin;
	}

	public double getVisionScorePerMin() {
		return visionScorePerMin;
	}

	public void setVisionScorePerMin(double visionScorePerMin) {
		this.visionScorePerMin = visionScorePerMin;
	}

	public double getTotalDamageTakenPerMin() {
		return totalDamageTakenPerMin;
	}

	public void setTotalDamageTakenPerMin(double totalDamageTakenPerMin) {
		this.totalDamageTakenPerMin = totalDamageTakenPerMin;
	}

	public double getTotalMinionsKilledPerMin() {
		return totalMinionsKilledPerMin;
	}

	public void setTotalMinionsKilledPerMin(double totalMinionsKilledPerMin) {
		this.totalMinionsKilledPerMin = totalMinionsKilledPerMin;
	}

	public double getKillsRatio() {
		return killsRatio;
	}

	public void setKillsRatio(double killsRatio) {
		this.killsRatio = killsRatio;
	}

	public double getDeathsRatio() {
		return deathsRatio;
	}

	public void setDeathsRatio(double deathsRatio) {
		this.deathsRatio = deathsRatio;
	}

	public double getKillAssistPerMin() {
		return killAssistPerMin;
	}

	public void setKillAssistPerMin(double killAssistPerMin) {
		this.killAssistPerMin = killAssistPerMin;
	}

	@Override
	public String toString() {
		return "AnalysisDto [totalDamageDealtToChampionsPerMin=" + totalDamageDealtToChampionsPerMin
				+ ", damageDealtToObjectivesPerMin=" + damageDealtToObjectivesPerMin + ", visionScorePerMin="
				+ visionScorePerMin + ", totalDamageTakenPerMin=" + totalDamageTakenPerMin
				+ ", totalMinionsKilledPerMin=" + totalMinionsKilledPerMin + ", killsRatio=" + killsRatio
				+ ", deathsRatio=" + deathsRatio + ", killAssistPerMin=" + killAssistPerMin + "]";
	}

}
