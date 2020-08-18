package com.ssafy.lolbody.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lolbodies")
public class LolbodyListDto {
	@Id
	private String summonerId;
	private List<LolbodyDto> lolbodyList;

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	public List<LolbodyDto> getLolbodyList() {
		return lolbodyList;
	}

	public void setLolbodyList(List<LolbodyDto> lolbodyList) {
		this.lolbodyList = lolbodyList;
	}

	@Override
	public String toString() {
		return "LolbodyListDto [summonerId=" + summonerId + ", lolbodyList=" + lolbodyList + "]";
	}

}
