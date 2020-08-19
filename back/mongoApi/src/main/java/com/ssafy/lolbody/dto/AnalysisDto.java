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
	private double killsPerMin;
	private double deathsPerMin;
	private double assistsPerMin;
	private double totalHealPerMin;
	private double damageSelfMitigatedPerMin;
	private double damageDealtToTurretsPerMin;
	private double timeCCingOthersPerMin;
	private double neutralMinionsKilledPerMin;
	private double totalTimeCrowdControlDealtPerMin;
	private double visionWardsBoughtInGamePerMin;
	private double neutralMinionsKilledEnemyJunglePerMin;
	private double wardsPlacedPerMin;
	private double wardsKilledPerMin;

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

	public double getKillsPerMin() {
		return killsPerMin;
	}

	public void setKillsPerMin(double killsPerMin) {
		this.killsPerMin = killsPerMin;
	}

	public double getDeathsPerMin() {
		return deathsPerMin;
	}

	public void setDeathsPerMin(double deathsPerMin) {
		this.deathsPerMin = deathsPerMin;
	}

	public double getAssistsPerMin() {
		return assistsPerMin;
	}

	public void setAssistsPerMin(double assistsPerMin) {
		this.assistsPerMin = assistsPerMin;
	}

	public double getTotalHealPerMin() {
		return totalHealPerMin;
	}

	public void setTotalHealPerMin(double totalHealPerMin) {
		this.totalHealPerMin = totalHealPerMin;
	}

	public double getDamageSelfMitigatedPerMin() {
		return damageSelfMitigatedPerMin;
	}

	public void setDamageSelfMitigatedPerMin(double damageSelfMitigatedPerMin) {
		this.damageSelfMitigatedPerMin = damageSelfMitigatedPerMin;
	}

	public double getDamageDealtToTurretsPerMin() {
		return damageDealtToTurretsPerMin;
	}

	public void setDamageDealtToTurretsPerMin(double damageDealtToTurretsPerMin) {
		this.damageDealtToTurretsPerMin = damageDealtToTurretsPerMin;
	}

	public double getTimeCCingOthersPerMin() {
		return timeCCingOthersPerMin;
	}

	public void setTimeCCingOthersPerMin(double timeCCingOthersPerMin) {
		this.timeCCingOthersPerMin = timeCCingOthersPerMin;
	}

	public double getNeutralMinionsKilledPerMin() {
		return neutralMinionsKilledPerMin;
	}

	public void setNeutralMinionsKilledPerMin(double neutralMinionsKilledPerMin) {
		this.neutralMinionsKilledPerMin = neutralMinionsKilledPerMin;
	}

	public double getTotalTimeCrowdControlDealtPerMin() {
		return totalTimeCrowdControlDealtPerMin;
	}

	public void setTotalTimeCrowdControlDealtPerMin(double totalTimeCrowdControlDealtPerMin) {
		this.totalTimeCrowdControlDealtPerMin = totalTimeCrowdControlDealtPerMin;
	}

	public double getVisionWardsBoughtInGamePerMin() {
		return visionWardsBoughtInGamePerMin;
	}

	public void setVisionWardsBoughtInGamePerMin(double visionWardsBoughtInGamePerMin) {
		this.visionWardsBoughtInGamePerMin = visionWardsBoughtInGamePerMin;
	}

	public double getNeutralMinionsKilledEnemyJunglePerMin() {
		return neutralMinionsKilledEnemyJunglePerMin;
	}

	public void setNeutralMinionsKilledEnemyJunglePerMin(double neutralMinionsKilledEnemyJunglePerMin) {
		this.neutralMinionsKilledEnemyJunglePerMin = neutralMinionsKilledEnemyJunglePerMin;
	}

	public double getWardsPlacedPerMin() {
		return wardsPlacedPerMin;
	}

	public void setWardsPlacedPerMin(double wardsPlacedPerMin) {
		this.wardsPlacedPerMin = wardsPlacedPerMin;
	}

	public double getWardsKilledPerMin() {
		return wardsKilledPerMin;
	}

	public void setWardsKilledPerMin(double wardsKilledPerMin) {
		this.wardsKilledPerMin = wardsKilledPerMin;
	}

	@Override
	public String toString() {
		return "AnalysisDto [totalDamageDealtToChampionsPerMin=" + totalDamageDealtToChampionsPerMin
				+ ", damageDealtToObjectivesPerMin=" + damageDealtToObjectivesPerMin + ", visionScorePerMin="
				+ visionScorePerMin + ", totalDamageTakenPerMin=" + totalDamageTakenPerMin
				+ ", totalMinionsKilledPerMin=" + totalMinionsKilledPerMin + ", killsRatio=" + killsRatio
				+ ", deathsRatio=" + deathsRatio + ", killAssistPerMin=" + killAssistPerMin + ", killsPerMin="
				+ killsPerMin + ", deathsPerMin=" + deathsPerMin + ", assistsPerMin=" + assistsPerMin
				+ ", totalHealPerMin=" + totalHealPerMin + ", damageSelfMitigatedPerMin=" + damageSelfMitigatedPerMin
				+ ", damageDealtToTurretsPerMin=" + damageDealtToTurretsPerMin + ", timeCCingOthersPerMin="
				+ timeCCingOthersPerMin + ", neutralMinionsKilledPerMin=" + neutralMinionsKilledPerMin
				+ ", totalTimeCrowdControlDealtPerMin=" + totalTimeCrowdControlDealtPerMin
				+ ", visionWardsBoughtInGamePerMin=" + visionWardsBoughtInGamePerMin
				+ ", neutralMinionsKilledEnemyJunglePerMin=" + neutralMinionsKilledEnemyJunglePerMin
				+ ", wardsPlacedPerMin=" + wardsPlacedPerMin + ", wardsKilledPerMin=" + wardsKilledPerMin + "]";
	}

}
