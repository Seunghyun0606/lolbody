package com.ssafy.lolbody.dto;

public class RecentGamesDto {
	private int spell1Id;
	private int spell2Id;
	private String champName;
	private String lane;
	private boolean win;
	public int getSpell1Id() {
		return spell1Id;
	}
	public void setSpell1Id(int spell1Id) {
		this.spell1Id = spell1Id;
	}
	public int getSpell2Id() {
		return spell2Id;
	}
	public void setSpell2Id(int spell2Id) {
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
				+ ", lane=" + lane + ", win=" + win + "]";
	}
}
