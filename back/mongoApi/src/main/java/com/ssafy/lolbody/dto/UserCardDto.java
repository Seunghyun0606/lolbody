package com.ssafy.lolbody.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usercards")
public class UserCardDto {
	@Id
	private String summonerId;
	private List<UserCardReferenceDto> userCardList;

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	public List<UserCardReferenceDto> getUserCardList() {
		return userCardList;
	}

	public void setUserCardList(List<UserCardReferenceDto> userCardList) {
		this.userCardList = userCardList;
	}

	@Override
	public String toString() {
		return "UserCardDto [summonerId=" + summonerId + ", userCardList=" + userCardList + "]";
	}

}
