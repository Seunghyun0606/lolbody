package com.ssafy.lolbody.dto;

public class BadgeDto {
	private String name;
	private int tier;
	private int cnt;
	private String comment;

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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "BadgeDto [name=" + name + ", tier=" + tier + ", cnt=" + cnt + ", comment=" + comment + "]";
	}

}
