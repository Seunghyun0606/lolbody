package com.ssafy.lolbody.dto;

public class TierAnalysisReferenceDto {
	private TierAnalysisDto IRON;
	private TierAnalysisDto BRONZE;
	private TierAnalysisDto SILVER;
	private TierAnalysisDto GOLD;
	private TierAnalysisDto PLATINUM;
	private TierAnalysisDto DIAMOND;

	public TierAnalysisDto getIRON() {
		return IRON;
	}

	public void setIRON(TierAnalysisDto iRON) {
		IRON = iRON;
	}

	public TierAnalysisDto getBRONZE() {
		return BRONZE;
	}

	public void setBRONZE(TierAnalysisDto bRONZE) {
		BRONZE = bRONZE;
	}

	public TierAnalysisDto getSILVER() {
		return SILVER;
	}

	public void setSILVER(TierAnalysisDto sILVER) {
		SILVER = sILVER;
	}

	public TierAnalysisDto getGOLD() {
		return GOLD;
	}

	public void setGOLD(TierAnalysisDto gOLD) {
		GOLD = gOLD;
	}

	public TierAnalysisDto getPLATINUM() {
		return PLATINUM;
	}

	public void setPLATINUM(TierAnalysisDto pLATINUM) {
		PLATINUM = pLATINUM;
	}

	public TierAnalysisDto getDIAMOND() {
		return DIAMOND;
	}

	public void setDIAMOND(TierAnalysisDto dIAMOND) {
		DIAMOND = dIAMOND;
	}

	@Override
	public String toString() {
		return "TierAnalysisReferenceDto [IRON=" + IRON + ", BRONZE=" + BRONZE + ", SILVER=" + SILVER + ", GOLD=" + GOLD
				+ ", PLATINUM=" + PLATINUM + ", DIAMOND=" + DIAMOND + "]";
	}

}
