package com.ssafy.lolbody.dto;

public class StasticsLineDto {
	private AnalysisDto mean;
	private AnalysisDto std;

	public AnalysisDto getMean() {
		return mean;
	}

	public void setMean(AnalysisDto mean) {
		this.mean = mean;
	}

	public AnalysisDto getStd() {
		return std;
	}

	public void setStd(AnalysisDto std) {
		this.std = std;
	}

	@Override
	public String toString() {
		return "StasticsLineDto [mean=" + mean + ", std=" + std + "]";
	}

}
