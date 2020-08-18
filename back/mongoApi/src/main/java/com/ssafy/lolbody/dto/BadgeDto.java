package com.ssafy.lolbody.dto;

public class BadgeDto {
	private String name;
	private int tier;
	private int cnt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "BadgeDto [name=" + name + ", tier=" + tier + ", cnt=" + cnt + "]";
	}

}
