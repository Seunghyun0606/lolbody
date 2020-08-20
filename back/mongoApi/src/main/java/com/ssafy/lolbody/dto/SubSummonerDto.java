package com.ssafy.lolbody.dto;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subsummoners")
public class SubSummonerDto {
	@Id
	private String summonerId;
	private Map<String, SummonerDto> summonerMap;

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	public Map<String, SummonerDto> getSummonerMap() {
		return summonerMap;
	}

	public void setSummonerMap(Map<String, SummonerDto> summonerMap) {
		this.summonerMap = summonerMap;
	}

	@Override
	public String toString() {
		return "SubSummonerDto [summonerId=" + summonerId + ", summonerMap=" + summonerMap + "]";
	}

}