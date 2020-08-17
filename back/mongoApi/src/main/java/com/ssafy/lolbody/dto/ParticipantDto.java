package com.ssafy.lolbody.dto;

import java.util.List;

public class ParticipantDto {
	private int participantId;
	private int championId;
	private List<RuneDto> runes;
	private ParticipantStatsDto stats;
	private int teamId;
	private ParticipantTimelineDto timeline;
	private int spell1Id;
	private int spell2Id;
	private String highestAchievedSeasonTier;
	private List<MasteryDto> masteries;
	private String name;
	private int x;
	private int y;
	private String line;
	private double matchGrade;
	private AnalysisDto analysis;
	private RadarReferenceDto radar;

	public int getParticipantId() {
		return participantId;
	}

	public void setParticipantId(int participantId) {
		this.participantId = participantId;
	}

	public int getChampionId() {
		return championId;
	}

	public void setChampionId(int championId) {
		this.championId = championId;
	}

	public List<RuneDto> getRunes() {
		return runes;
	}

	public void setRunes(List<RuneDto> runes) {
		this.runes = runes;
	}

	public ParticipantStatsDto getStats() {
		return stats;
	}

	public void setStats(ParticipantStatsDto stats) {
		this.stats = stats;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public ParticipantTimelineDto getTimeline() {
		return timeline;
	}

	public void setTimeline(ParticipantTimelineDto timeline) {
		this.timeline = timeline;
	}

	public int getSpell1Id() {
		return spell1Id;
	}

	public void setSpell1Id(int spell1Id) {
		this.spell1Id = spell1Id;
	}

	public int getSpell2Id() {
		return spell2Id;
	}

	public void setSpell2Id(int spell2Id) {
		this.spell2Id = spell2Id;
	}

	public String getHighestAchievedSeasonTier() {
		return highestAchievedSeasonTier;
	}

	public void setHighestAchievedSeasonTier(String highestAchievedSeasonTier) {
		this.highestAchievedSeasonTier = highestAchievedSeasonTier;
	}

	public List<MasteryDto> getMasteries() {
		return masteries;
	}

	public void setMasteries(List<MasteryDto> masteries) {
		this.masteries = masteries;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public double getMatchGrade() {
		return matchGrade;
	}

	public void setMatchGrade(double matchGrade) {
		this.matchGrade = matchGrade;
	}

	public AnalysisDto getAnalysis() {
		return analysis;
	}

	public void setAnalysis(AnalysisDto analysis) {
		this.analysis = analysis;
	}

	public RadarReferenceDto getRadar() {
		return radar;
	}

	public void setRadar(RadarReferenceDto radar) {
		this.radar = radar;
	}

	@Override
	public String toString() {
		return "ParticipantDto [participantId=" + participantId + ", championId=" + championId + ", runes=" + runes
				+ ", stats=" + stats + ", teamId=" + teamId + ", timeline=" + timeline + ", spell1Id=" + spell1Id
				+ ", spell2Id=" + spell2Id + ", highestAchievedSeasonTier=" + highestAchievedSeasonTier + ", masteries="
				+ masteries + ", name=" + name + ", x=" + x + ", y=" + y + ", line=" + line + ", matchGrade="
				+ matchGrade + ", analysis=" + analysis + ", radar=" + radar + "]";
	}

}