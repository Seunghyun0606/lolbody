package com.ssafy.lolbody.dto;

public class KDADto {
	private int kills;
	private int deaths;
	private int assists;
	public int getKills() {
		return kills;
	}
	public void setKills(int kills) {
		this.kills = kills;
	}
	public int getDeaths() {
		return deaths;
	}
	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}
	public int getAssists() {
		return assists;
	}
	public void setAssists(int assists) {
		this.assists = assists;
	}
	@Override
	public String toString() {
		return "KDADto [kills=" + kills + ", deaths=" + deaths + ", assists=" + assists + "]";
	}
}
