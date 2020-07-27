package com.ssafy.lolbody.dto;

import java.util.Map;

public class MatchRecordDto {
	private int mostCham;
	private String mostLine;
	private String secondLine;

	private WinRateDto totalRecord;
	private WinRateDto mostChamRecord;
	private WinRateDto mostLineRecord;
	private WinRateDto secondLineRecord;

	private Map<String, WinRateDto> lineRecord;
	private Map<Integer, WinRateDto> chamRecord;

	public int getMostCham() {
		return mostCham;
	}

	public void setMostCham(int mostCham) {
		this.mostCham = mostCham;
	}

	public String getMostLine() {
		return mostLine;
	}

	public void setMostLine(String mostLine) {
		this.mostLine = mostLine;
	}

	public String getSecondLine() {
		return secondLine;
	}

	public void setSecondLine(String secondLine) {
		this.secondLine = secondLine;
	}

	public WinRateDto getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(WinRateDto totalRecord) {
		this.totalRecord = totalRecord;
	}

	public WinRateDto getMostChamRecord() {
		return mostChamRecord;
	}

	public void setMostChamRecord(WinRateDto mostChamRecord) {
		this.mostChamRecord = mostChamRecord;
	}

	public WinRateDto getMostLineRecord() {
		return mostLineRecord;
	}

	public void setMostLineRecord(WinRateDto mostLineRecord) {
		this.mostLineRecord = mostLineRecord;
	}

	public WinRateDto getSecondLineRecord() {
		return secondLineRecord;
	}

	public void setSecondLineRecord(WinRateDto secondLineRecord) {
		this.secondLineRecord = secondLineRecord;
	}

	public Map<String, WinRateDto> getLineRecord() {
		return lineRecord;
	}

	public void setLineRecord(Map<String, WinRateDto> lineRecord) {
		this.lineRecord = lineRecord;
	}

	public Map<Integer, WinRateDto> getChamRecord() {
		return chamRecord;
	}

	public void setChamRecord(Map<Integer, WinRateDto> chamRecord) {
		this.chamRecord = chamRecord;
	}

	@Override
	public String toString() {
		return "MatchRecordDto [mostCham=" + mostCham + ", mostLine=" + mostLine + ", secondLine=" + secondLine
				+ ", totalRecord=" + totalRecord + ", mostChamRecord=" + mostChamRecord + ", mostLineRecord="
				+ mostLineRecord + ", secondLineRecord=" + secondLineRecord + ", lineRecord=" + lineRecord
				+ ", chamRecord=" + chamRecord + "]";
	}

}
