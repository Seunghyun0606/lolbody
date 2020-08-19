package com.ssafy.lolbody.dto;

public class StasticsTierDto {
	private StasticsLineDto TOTAL;
	private StasticsLineDto TOP;
	private StasticsLineDto JUNGLE;
	private StasticsLineDto MID;
	private StasticsLineDto BOTTOM;
	private StasticsLineDto SUPPORT;

	public StasticsLineDto getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(StasticsLineDto tOTAL) {
		TOTAL = tOTAL;
	}

	public StasticsLineDto getTOP() {
		return TOP;
	}

	public void setTOP(StasticsLineDto tOP) {
		TOP = tOP;
	}

	public StasticsLineDto getJUNGLE() {
		return JUNGLE;
	}

	public void setJUNGLE(StasticsLineDto jUNGLE) {
		JUNGLE = jUNGLE;
	}

	public StasticsLineDto getMID() {
		return MID;
	}

	public void setMID(StasticsLineDto mID) {
		MID = mID;
	}

	public StasticsLineDto getBOTTOM() {
		return BOTTOM;
	}

	public void setBOTTOM(StasticsLineDto bOTTOM) {
		BOTTOM = bOTTOM;
	}

	public StasticsLineDto getSUPPORT() {
		return SUPPORT;
	}

	public void setSUPPORT(StasticsLineDto sUPPORT) {
		SUPPORT = sUPPORT;
	}

	@Override
	public String toString() {
		return "StasticsTierDto [TOTAL=" + TOTAL + ", TOP=" + TOP + ", JUNGLE=" + JUNGLE + ", MID=" + MID + ", BOTTOM="
				+ BOTTOM + ", SUPPORT=" + SUPPORT + "]";
	}

}
