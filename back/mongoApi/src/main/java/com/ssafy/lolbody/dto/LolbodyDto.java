package com.ssafy.lolbody.dto;

import java.util.List;

public class LolbodyDto {
	private long timestamp;
	private UserCardReferenceDto userCardReference;
	private List<RadarDto> radarList;
	private List<CountDto> champList;
	private List<CountDto> lineList;
	private StasticsDto stastics;
	private RadarReferenceDto radar;
	private AnalysisDto analysis;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public UserCardReferenceDto getUserCardReference() {
		return userCardReference;
	}

	public void setUserCardReference(UserCardReferenceDto userCardReference) {
		this.userCardReference = userCardReference;
	}

	public List<RadarDto> getRadarList() {
		return radarList;
	}

	public void setRadarList(List<RadarDto> radarList) {
		this.radarList = radarList;
	}

	public List<CountDto> getChampList() {
		return champList;
	}

	public void setChampList(List<CountDto> champList) {
		this.champList = champList;
	}

	public List<CountDto> getLineList() {
		return lineList;
	}

	public void setLineList(List<CountDto> lineList) {
		this.lineList = lineList;
	}

	public StasticsDto getStastics() {
		return stastics;
	}

	public void setStastics(StasticsDto stastics) {
		this.stastics = stastics;
	}

	public RadarReferenceDto getRadar() {
		return radar;
	}

	public void setRadar(RadarReferenceDto radar) {
		this.radar = radar;
	}

	public AnalysisDto getAnalysis() {
		return analysis;
	}

	public void setAnalysis(AnalysisDto analysis) {
		this.analysis = analysis;
	}

	@Override
	public String toString() {
		return "LolbodyDto [timestamp=" + timestamp + ", userCardReference=" + userCardReference + ", radarList="
				+ radarList + ", champList=" + champList + ", lineList=" + lineList + ", stastics=" + stastics
				+ ", radar=" + radar + ", analysis=" + analysis + "]";
	}

}