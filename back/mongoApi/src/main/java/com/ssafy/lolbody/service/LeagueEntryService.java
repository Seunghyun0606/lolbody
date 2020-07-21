package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.LeagueEntryDTO;
import com.ssafy.lolbody.repository.LeagueEntryRepository;

@Service
public class LeagueEntryService {
	@Autowired
	private LeagueEntryRepository leagueEntryRepository;
	
	public void save(List<LeagueEntryDTO> leagueEntryList) {
		leagueEntryRepository.saveAll(leagueEntryList);
	}
	
	public List<LeagueEntryDTO> findAll() {
		return leagueEntryRepository.findAll();
	}
	
	public List<LeagueEntryDTO> findBySummonerId(String summonerId) {
		List<LeagueEntryDTO> leagueEntryList = leagueEntryRepository.findBySummonerId(summonerId);
		if(leagueEntryList.size() == 0) {
			JSONArray arr = new JSONArray(Api.get("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner", summonerId));
			leagueEntryList = new ArrayList<>();
			for(int i = 0; i < arr.length(); i++) {
				JSONObject obj = arr.getJSONObject(i);
				LeagueEntryDTO leagueEntry = new LeagueEntryDTO();
				leagueEntry.setLeagueId(obj.getString("leagueId"));
				leagueEntry.setQueueType(obj.getString("queueType"));
				leagueEntry.setTier(obj.getString("tier"));
				leagueEntry.setRank(obj.getString("rank"));
				leagueEntry.setSummonerId(obj.getString("summonerId"));
				leagueEntry.setSummonerName(obj.getString("summonerName"));
				leagueEntry.setLeaguePoints(obj.getInt("leaguePoints"));
				leagueEntry.setWins(obj.getInt("wins"));
				leagueEntry.setLosses(obj.getInt("losses"));
				leagueEntry.setVeteran(obj.getBoolean("veteran"));
				leagueEntry.setInactive(obj.getBoolean("inactive"));
				leagueEntry.setFreshBlood(obj.getBoolean("freshBlood"));
				leagueEntry.setHotStreak(obj.getBoolean("hotStreak"));
				leagueEntryList.add(leagueEntry);
			}
			save(leagueEntryList);
		}
		return leagueEntryList;
	}
}
