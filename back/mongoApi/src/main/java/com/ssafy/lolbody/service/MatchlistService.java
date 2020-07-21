package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.MatchlistDto.MatchReferenceDto;
import com.ssafy.lolbody.dto.SummonerDTO;
import com.ssafy.lolbody.repository.MatchlistRepository;

@Service
public class MatchlistService {
	@Autowired
	private MatchlistRepository matchlistRepository;

	public void save(MatchlistDto matchlistDto) {
		matchlistRepository.save(matchlistDto);
	}

	public MatchlistDto findBySummonerId(SummonerDTO summonerDto) {
		MatchlistDto matchlistDto = matchlistRepository.findBySummonerId(summonerDto.getId());

		if (matchlistDto == null) {
			matchlistDto = new MatchlistDto();
			matchlistDto.setSummonerId(summonerDto.getId());
			List<MatchReferenceDto> list = new ArrayList<>();
			int season = 13, beginIndex = 0, endIndex = 100;
			while (true) {
				String query = "?season=" + season + "&beginIndex=" + beginIndex + "&endIndex=" + endIndex;
				JSONObject obj = new JSONObject(
						Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
								summonerDto.getAccountId() + query));
				JSONArray arr = obj.getJSONArray("matches");
				if (arr.length() == 0)
					break;
				for (int i = 0; i < arr.length(); i++) {
					JSONObject tmp = arr.getJSONObject(i);
					MatchReferenceDto mr = new MatchReferenceDto();
					mr.setGameId(tmp.getLong("gameId"));
					mr.setRole(tmp.getString("role"));
					mr.setSeason(tmp.getInt("season"));
					mr.setPlatformId(tmp.getString("platformId"));
					mr.setChampion(tmp.getInt("champion"));
					mr.setQueue(tmp.getInt("queue"));
					mr.setLane(tmp.getString("lane"));
					mr.setTimestamp(tmp.getLong("timestamp"));
					list.add(mr);
				}
				beginIndex += 100;
				endIndex += 100;
			}
			Collections.sort(list, (o1, o2) -> (o1.getTimestamp() + "").compareTo(o2.getTimestamp() + ""));
			matchlistDto.setMatches(list);

		} else {
			List<MatchReferenceDto> matches = matchlistDto.getMatches();
			String beginTime = matches.get(matches.size() - 1).getTimestamp() + "";
			List<MatchReferenceDto> list = new ArrayList<>();
			int season = 13, beginIndex = 0, endIndex = 100;
			while (true) {
				String query = "?season=" + season + "&beginIndex=" + beginIndex + "&endIndex=" + endIndex
						+ "&beginTime=" + beginTime;
				JSONObject obj = new JSONObject(
						Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
								summonerDto.getAccountId() + query));
				JSONArray arr = obj.getJSONArray("matches");
				if (arr.length() == 0)
					break;
				for (int i = 0; i < arr.length(); i++) {
					JSONObject tmp = arr.getJSONObject(i);
					MatchReferenceDto mr = new MatchReferenceDto();
					mr.setGameId(tmp.getLong("gameId"));
					mr.setRole(tmp.getString("role"));
					mr.setSeason(tmp.getInt("season"));
					mr.setPlatformId(tmp.getString("platformId"));
					mr.setChampion(tmp.getInt("champion"));
					mr.setQueue(tmp.getInt("queue"));
					mr.setLane(tmp.getString("lane"));
					mr.setTimestamp(tmp.getLong("timestamp"));
					list.add(mr);
				}
				beginIndex += 100;
				endIndex += 100;
			}
			Collections.sort(list, (o1, o2) -> (o1.getTimestamp() + "").compareTo(o2.getTimestamp() + ""));
			matches.addAll(list);

		}
		save(matchlistDto);
		return matchlistDto;
	}

}
