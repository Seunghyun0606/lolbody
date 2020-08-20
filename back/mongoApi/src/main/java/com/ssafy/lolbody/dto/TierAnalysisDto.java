package com.ssafy.lolbody.dto;

public class TierAnalysisDto {
	private AnalysisDto TOTAL;
	private AnalysisDto TOP;
	private AnalysisDto JUNGLE;
	private AnalysisDto MID;
	private AnalysisDto BOTTOM;
	private AnalysisDto SUPPORT;

	public AnalysisDto getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(AnalysisDto tOTAL) {
		TOTAL = tOTAL;
	}

	public AnalysisDto getTOP() {
		return TOP;
	}

	public void setTOP(AnalysisDto tOP) {
		TOP = tOP;
	}

	public AnalysisDto getJUNGLE() {
		return JUNGLE;
	}

	public void setJUNGLE(AnalysisDto jUNGLE) {
		JUNGLE = jUNGLE;
	}

	public AnalysisDto getMID() {
		return MID;
	}

	public void setMID(AnalysisDto mID) {
		MID = mID;
	}

	public AnalysisDto getBOTTOM() {
		return BOTTOM;
	}

	public void setBOTTOM(AnalysisDto bOTTOM) {
		BOTTOM = bOTTOM;
	}

	public AnalysisDto getSUPPORT() {
		return SUPPORT;
	}

	public void setSUPPORT(AnalysisDto sUPPORT) {
		SUPPORT = sUPPORT;
	}

	@Override
	public String toString() {
		return "TierAnalysisDto [TOTAL=" + TOTAL + ", TOP=" + TOP + ", JUNGLE=" + JUNGLE + ", MID=" + MID + ", BOTTOM="
				+ BOTTOM + ", SUPPORT=" + SUPPORT + "]";
	}

}
