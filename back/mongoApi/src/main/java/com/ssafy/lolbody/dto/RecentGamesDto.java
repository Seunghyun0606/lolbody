package com.ssafy.lolbody.dto;

import java.util.List;

public class RecentGamesDto {
	private String spell1Id;
	private String spell2Id;
	private String champName;
	private String lane;
	private boolean win;
	private KDADto kda;
	
	
	public KDADto getKda() {
		return kda;
	}
	public void setKda(KDADto kda) {
		this.kda = kda;
	}
	public String getSpell1Id() {
		return spell1Id;
	}
	public void setSpell1Id(String spell1Id) {
		this.spell1Id = spell1Id;
	}
	public String getSpell2Id() {
		return spell2Id;
	}
	public void setSpell2Id(String spell2Id) {
		this.spell2Id = spell2Id;
	}
	public String getChampName() {
		return champName;
	}
	public void setChampName(String champName) {
		this.champName = champName;
	}
	public String getLane() {
		return lane;
	}
	public void setLane(String lane) {
		this.lane = lane;
	}
	
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	@Override
	public String toString() {
		return "RecentGamesDto [spell1Id=" + spell1Id + ", spell2Id=" + spell2Id + ", champName=" + champName
				+ ", lane=" + lane + ", win=" + win + ", kda=" + kda + "]";
	}
}
