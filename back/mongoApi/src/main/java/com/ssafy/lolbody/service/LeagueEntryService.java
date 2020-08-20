package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.LeagueEntryListDto;
import com.ssafy.lolbody.repository.LeagueEntryRepository;

@Service
public class LeagueEntryService {
	@Autowired
	private LeagueEntryRepository leagueEntryRepository;

	public List<LeagueEntryDto> findBySummonerId(String summonerId) throws Exception {
		LeagueEntryListDto leagueEntryList = new LeagueEntryListDto();
		String str = Api.get("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner", summonerId);
		if (str.equals("Timeout"))
			throw new TimeoutException("메인 키 요청이 너무 많습니다.");
		JSONArray arr = new JSONArray(str);
		List<LeagueEntryDto> list = new ArrayList<>();
		for (int i = 0; i < arr.length(); i++) {
			String json = arr.getJSONObject(i).toString();
			LeagueEntryDto leagueEntry = new Gson().fromJson(json, LeagueEntryDto.class);
			list.add(leagueEntry);
		}
		leagueEntryList.setSummonerId(summonerId);
		leagueEntryList.setLeagueEntryList(list);
		leagueEntryRepository.save(leagueEntryList);

		return list;
	}

	public List<LeagueEntryDto> findOnly(String summonerId) throws Exception {
		LeagueEntryListDto leagueEntryList = leagueEntryRepository.findBySummonerId(summonerId);
		if (leagueEntryList == null) {
			return findBySummonerId(summonerId);
		}
		return leagueEntryList.getLeagueEntryList();
	}
}
