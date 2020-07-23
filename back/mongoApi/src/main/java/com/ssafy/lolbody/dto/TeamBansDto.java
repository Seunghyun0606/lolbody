package com.ssafy.lolbody.dto;

public class TeamBansDto {
	private int championId;
	private int pickTurn;

	public int getChampionId() {
		return championId;
	}

	public void setChampionId(int championId) {
		this.championId = championId;
	}

	public int getPickTurn() {
		return pickTurn;
	}

	public void setPickTurn(int pickTurn) {
		this.pickTurn = pickTurn;
	}

	@Override
	public String toString() {
		return "TeamBansDto [championId=" + championId + ", pickTurn=" + pickTurn + "]";
	}
}