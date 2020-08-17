package com.ssafy.lolbody.dto;

public class BadgeDto {
	private String badge;
	private int color;

	public String getBadge() {
		return badge;
	}

	public void setBadge(String badge) {
		this.badge = badge;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "BadgeDto [badge=" + badge + ", color=" + color + "]";
	}

}
