package com.ssafy.lolbody.preset;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="perks")
public class PerkDto {
	@Id
	private int key;
	private String name;
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "PerkDto [key=" + key + ", name=" + name + "]";
	}
}
