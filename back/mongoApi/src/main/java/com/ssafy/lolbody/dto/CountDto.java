package com.ssafy.lolbody.dto;

public class CountDto {
	private String name;
	private int cnt;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	@Override
	public String toString() {
		return "CountDto [name=" + name + ", cnt=" + cnt + "]";
	}

}
