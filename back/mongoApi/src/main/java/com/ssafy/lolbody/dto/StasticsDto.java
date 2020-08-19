package com.ssafy.lolbody.dto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stastics")
public class StasticsDto {
	@Id
	private int queueId;
	private StasticsReferenceDto stastics;
	private TierAnalysisReferenceDto tierAnalysis;

	public int getQueueId() {
		return queueId;
	}

	public void setQueueId(int queueId) {
		this.queueId = queueId;
	}

	public StasticsReferenceDto getStastics() {
		return stastics;
	}

	public void setStastics(StasticsReferenceDto stastics) {
		this.stastics = stastics;
	}

	public TierAnalysisReferenceDto getTierAnalysis() {
		return tierAnalysis;
	}

	public void setTierAnalysis(TierAnalysisReferenceDto tierAnalysis) {
		this.tierAnalysis = tierAnalysis;
	}

	@Override
	public String toString() {
		return "StasticsDto [queueId=" + queueId + ", stastics=" + stastics + ", tierAnalysis=" + tierAnalysis + "]";
	}

}
