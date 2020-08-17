package com.ssafy.lolbody.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leagueentries")
public class LeagueEntryListDto {
	@Id
	private String summonerId;
	private List<LeagueEntryDto> leagueEntryList;

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	public List<LeagueEntryDto> getLeagueEntryList() {
		return leagueEntryList;
	}

	public void setLeagueEntryList(List<LeagueEntryDto> leagueEntryList) {
		this.leagueEntryList = leagueEntryList;
	}

	@Override
	public String toString() {
		return "LeagueEntryListDto [summonerId=" + summonerId + ", leagueEntryList=" + leagueEntryList + "]";
	}

}
