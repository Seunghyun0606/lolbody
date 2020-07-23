package com.ssafy.lolbody.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matchlists")
public class MatchlistDto {
	@Id
	private String summonerId;
	private List<MatchReferenceDto> matches;

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	public List<MatchReferenceDto> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchReferenceDto> matches) {
		this.matches = matches;
	}

	@Override
	public String toString() {
		return "MatchlistDto [summonerId=" + summonerId + ", matches=" + matches + "]";
	}

}
