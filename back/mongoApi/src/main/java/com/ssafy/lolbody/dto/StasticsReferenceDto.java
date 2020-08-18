package com.ssafy.lolbody.dto;

public class StasticsReferenceDto {
	private StasticsTierDto TOTAL;
	private StasticsTierDto IRON;
	private StasticsTierDto BRONZE;
	private StasticsTierDto SILVER;
	private StasticsTierDto GOLD;
	private StasticsTierDto PLATINUM;
	private StasticsTierDto DIAMOND;

	public StasticsTierDto getTOTAL() {
		return TOTAL;
	}

	public void setTOTAL(StasticsTierDto tOTAL) {
		TOTAL = tOTAL;
	}

	public StasticsTierDto getIRON() {
		return IRON;
	}

	public void setIRON(StasticsTierDto iRON) {
		IRON = iRON;
	}

	public StasticsTierDto getBRONZE() {
		return BRONZE;
	}

	public void setBRONZE(StasticsTierDto bRONZE) {
		BRONZE = bRONZE;
	}

	public StasticsTierDto getSILVER() {
		return SILVER;
	}

	public void setSILVER(StasticsTierDto sILVER) {
		SILVER = sILVER;
	}

	public StasticsTierDto getGOLD() {
		return GOLD;
	}

	public void setGOLD(StasticsTierDto gOLD) {
		GOLD = gOLD;
	}

	public StasticsTierDto getPLATINUM() {
		return PLATINUM;
	}

	public void setPLATINUM(StasticsTierDto pLATINUM) {
		PLATINUM = pLATINUM;
	}

	public StasticsTierDto getDIAMOND() {
		return DIAMOND;
	}

	public void setDIAMOND(StasticsTierDto dIAMOND) {
		DIAMOND = dIAMOND;
	}

	@Override
	public String toString() {
		return "StasticsReferenceDto [TOTAL=" + TOTAL + ", IRON=" + IRON + ", BRONZE=" + BRONZE + ", SILVER=" + SILVER
				+ ", GOLD=" + GOLD + ", PLATINUM=" + PLATINUM + ", DIAMOND=" + DIAMOND + "]";
	}

}
