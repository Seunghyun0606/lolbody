package com.ssafy.lolbody.dto;

public class PlayerRecordDto {
	private String name;
	private String champ;
	private int kills;
	private int deaths;
	private int assists;
	private double kda;
	private double ka;
	private String spell1;
	private String spell2;
	private int item0;
	private int item1;
	private int item2;
	private int item3;
	private int item4;
	private int item5;
	private int item6;
	private String perk;
	private String perkStyle;
	private int level;
	private int gold;
	private int cs;
	private double csPerMin;
	private String line;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChamp() {
		return champ;
	}

	public void setChamp(String champ) {
		this.champ = champ;
	}

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

	public double getKda() {
		return kda;
	}

	public void setKda(double kda) {
		this.kda = kda;
	}

	public double getKa() {
		return ka;
	}

	public void setKa(double ka) {
		this.ka = ka;
	}

	public String getSpell1() {
		return spell1;
	}

	public void setSpell1(String spell1) {
		this.spell1 = spell1;
	}

	public String getSpell2() {
		return spell2;
	}

	public void setSpell2(String spell2) {
		this.spell2 = spell2;
	}

	public int getItem0() {
		return item0;
	}

	public void setItem0(int item0) {
		this.item0 = item0;
	}

	public int getItem1() {
		return item1;
	}

	public void setItem1(int item1) {
		this.item1 = item1;
	}

	public int getItem2() {
		return item2;
	}

	public void setItem2(int item2) {
		this.item2 = item2;
	}

	public int getItem3() {
		return item3;
	}

	public void setItem3(int item3) {
		this.item3 = item3;
	}

	public int getItem4() {
		return item4;
	}

	public void setItem4(int item4) {
		this.item4 = item4;
	}

	public int getItem5() {
		return item5;
	}

	public void setItem5(int item5) {
		this.item5 = item5;
	}

	public int getItem6() {
		return item6;
	}

	public void setItem6(int item6) {
		this.item6 = item6;
	}

	public String getPerk() {
		return perk;
	}

	public void setPerk(String perk) {
		this.perk = perk;
	}

	public String getPerkStyle() {
		return perkStyle;
	}

	public void setPerkStyle(String perkStyle) {
		this.perkStyle = perkStyle;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getCs() {
		return cs;
	}

	public void setCs(int cs) {
		this.cs = cs;
	}

	public double getCsPerMin() {
		return csPerMin;
	}

	public void setCsPerMin(double csPerMin) {
		this.csPerMin = csPerMin;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	@Override
	public String toString() {
		return "PlayerRecordDto [name=" + name + ", champ=" + champ + ", kills=" + kills + ", deaths=" + deaths
				+ ", assists=" + assists + ", kda=" + kda + ", ka=" + ka + ", spell1=" + spell1 + ", spell2=" + spell2
				+ ", item0=" + item0 + ", item1=" + item1 + ", item2=" + item2 + ", item3=" + item3 + ", item4=" + item4
				+ ", item5=" + item5 + ", item6=" + item6 + ", perk=" + perk + ", perkStyle=" + perkStyle + ", level="
				+ level + ", gold=" + gold + ", cs=" + cs + ", csPerMin=" + csPerMin + ", line=" + line + "]";
	}

}
