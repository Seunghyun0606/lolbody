package com.ssafy.lolbody.dto;

public class RadarDto {
	private long timestamp;
	private String name;
	private RadarReferenceDto radarReference;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RadarReferenceDto getRadarReference() {
		return radarReference;
	}

	public void setRadarReference(RadarReferenceDto radarReference) {
		this.radarReference = radarReference;
	}

	@Override
	public String toString() {
		return "RadarDto [timestamp=" + timestamp + ", name=" + name + ", radarReference=" + radarReference + "]";
	}

}
