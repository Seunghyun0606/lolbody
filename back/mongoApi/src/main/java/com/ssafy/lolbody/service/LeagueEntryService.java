package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.LeagueEntryListDto;
import com.ssafy.lolbody.dto.SubSummonerDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.repository.LeagueEntryRepository;
import com.ssafy.lolbody.repository.SubSummonerRepository;

@Service
public class LeagueEntryService {
	@Autowired
	private LeagueEntryRepository leagueEntryRepository;
	@Autowired
	private SubSummonerRepository subSummonerRepository;

	public List<LeagueEntryDto> findBySummonerId(SummonerDto summoner) throws Exception {
		SubSummonerDto subSummoner = subSummonerRepository.findBySummonerId(summoner.getId());
		Map<String, SummonerDto> summonerMap;
		if (subSummoner == null) {
			subSummoner = new SubSummonerDto();
			summonerMap = new HashMap<>();
			subSummoner.setSummonerId(summoner.getId());
		} else {
			summonerMap = subSummoner.getSummonerMap();
		}
		String[] tokens = Api.getTokens();
		String str = "", summonerId = "", key = "";
		boolean isForbidden = false;
		int idx = 0, cnt = 10 * tokens.length;
		while (cnt-- > 0) {
			key = tokens[idx];
			idx = (idx + 1) % tokens.length;
			if (summonerMap.containsKey(key)) {
				summonerId = summonerMap.get(key).getId();
			} else {
				String json = Api.get("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name",
						summoner.getSubName(), key);
				if (json.equals("Timeout")) {
					continue;
				} else if (json.equals("Forbidden")) {
					isForbidden = true;
					continue;
				} else if (json.equals("Fail")) {
					continue;
				} else {
					SummonerDto tmpSummoner = new Gson().fromJson(json, SummonerDto.class);
					summonerMap.put(key, tmpSummoner);
					subSummoner.setSummonerMap(summonerMap);
					subSummonerRepository.save(subSummoner);
					summonerId = tmpSummoner.getId();
				}
			}

			str = Api.get("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner", summonerId, key);
			if (str.equals("Timeout")) {
				continue;
			} else if (str.equals("Forbidden")) {
				isForbidden = true;
				continue;
			} else if (str.equals("Fail")) {
				continue;
			} else {
				JSONArray arr = new JSONArray(str);
				LeagueEntryListDto leagueEntryList = new LeagueEntryListDto();
				List<LeagueEntryDto> list = new ArrayList<>();
				for (int i = 0; i < arr.length(); i++) {
					String json = arr.getJSONObject(i).toString();
					LeagueEntryDto leagueEntry = new Gson().fromJson(json, LeagueEntryDto.class);
					list.add(leagueEntry);
				}
				leagueEntryList.setSummonerId(summoner.getId());
				leagueEntryList.setLeagueEntryList(list);
				leagueEntryRepository.save(leagueEntryList);

				return list;
			}
		}
		if (isForbidden)
			throw new TimeoutException("서브 키 요청이 너무 많습니다. (만료된 key 포함)");
		throw new TimeoutException("서브 키 요청이 너무 많습니다.");
	}

	public List<LeagueEntryDto> findOnly(SummonerDto summoner) throws Exception {
		LeagueEntryListDto leagueEntryList = leagueEntryRepository.findBySummonerId(summoner.getId());
		if (leagueEntryList == null) {
			return findBySummonerId(summoner);
		}
		return leagueEntryList.getLeagueEntryList();
	}
}
