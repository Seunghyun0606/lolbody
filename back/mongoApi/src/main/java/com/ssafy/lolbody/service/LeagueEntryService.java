package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.repository.LeagueEntryRepository;

@Service
public class LeagueEntryService {
	@Autowired
	private LeagueEntryRepository leagueEntryRepository;
	
	public void save(List<LeagueEntryDto> leagueEntryList) {
		leagueEntryRepository.saveAll(leagueEntryList);
	}
	
	public List<LeagueEntryDto> findAll() {
		return leagueEntryRepository.findAll();
	}
	
	public List<LeagueEntryDto> findBySummonerId(String summonerId) {
		List<LeagueEntryDto> leagueEntryList = leagueEntryRepository.findBySummonerId(summonerId);
		if(leagueEntryList.size() == 0) {
			JSONArray arr = new JSONArray(Api.get("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner", summonerId));
			leagueEntryList = new ArrayList<>();
			for(int i = 0; i < arr.length(); i++) {
				String json = arr.getJSONObject(i).toString();
				LeagueEntryDto leagueEntry = new Gson().fromJson(json, LeagueEntryDto.class);
				leagueEntryList.add(leagueEntry);
			}
			save(leagueEntryList);
		}
		return leagueEntryList;
	}
}
