package com.ssafy.lolbody.dto;

public class BadgeDto {
	private String name;
	private int tier;

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

	@Override
	public int hashCode() {
		return (name + tier).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		BadgeDto o = (BadgeDto) obj;
		return (this.name.equals(o.name) && this.tier == o.tier);
	}

	@Override
	public String toString() {
		return "BadgeDto [name=" + name + ", tier=" + tier + "]";
	}

}
