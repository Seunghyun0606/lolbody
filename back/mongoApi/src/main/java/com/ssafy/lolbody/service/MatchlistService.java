package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.repository.MatchlistRepository;

@Service
public class MatchlistService {
	@Autowired
	private MatchlistRepository matchlistRepository;

	public void save(MatchlistDto matchlistDto) {
		matchlistRepository.save(matchlistDto);
	}
	
	public MatchlistDto findBySummonerId(SummonerDto summonerDto) {
		MatchlistDto matchlistDto = matchlistRepository.findBySummonerId(summonerDto.getId());
		
		JSONArray jsonArr = new JSONArray(Api.getHttpRequest("http://static.developer.riotgames.com/docs/lol/seasons.json"));
		int season = ((JSONObject)jsonArr.getJSONObject(jsonArr.length()-1)).getInt("id");
		System.out.println(season);

		if (matchlistDto == null) {
			// 매치 데이터가 없는 유저
			matchlistDto = new MatchlistDto();
			matchlistDto.setSummonerId(summonerDto.getId());
			List<MatchReferenceDto> list = new ArrayList<>();
			int beginIndex = 0, endIndex = 100;
			while (true) {
				String query = "?season="+season+"&beginIndex=" + beginIndex + "&endIndex=" + endIndex;;
					
				try {
					JSONObject obj = new JSONObject(
						Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
								summonerDto.getAccountId() + query));
					JSONArray arr = obj.getJSONArray("matches");
					if (arr.length() == 0)
						break;
					for (int i = 0; i < arr.length(); i++) {
						String json = arr.getJSONObject(i).toString();
						MatchReferenceDto mr = new Gson().fromJson(json, MatchReferenceDto.class);
						list.add(mr);
					}
				} catch (Exception e) {
					break;
				}
				beginIndex += 100;
				endIndex += 100;
			}
			Collections.sort(list, (o1, o2) -> (o1.getTimestamp() + "").compareTo(o2.getTimestamp() + ""));
			matchlistDto.setMatches(list);

		} else {
			// 매치 데이터가 있는 유저
			List<MatchReferenceDto> matches = matchlistDto.getMatches();
			Long beginTime = matches.get(matches.size() - 1).getTimestamp() + 1;
			List<MatchReferenceDto> list = new ArrayList<>();
			int beginIndex = 0, endIndex = 100;
			while (true) {
				System.out.println("search " + beginIndex + " ~ " + endIndex);
				String query = "?season="+season+"&beginIndex=" + beginIndex + "&endIndex=" + endIndex
						+ "&beginTime=" + beginTime;
				try {
				JSONObject obj = new JSONObject(
						Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
								summonerDto.getAccountId() + query));
					JSONArray arr = obj.getJSONArray("matches");
					if (arr.length() == 0)
						break;
					for (int i = 0; i < arr.length(); i++) {
						String json = arr.getJSONObject(i).toString();
						MatchReferenceDto mr = new Gson().fromJson(json, MatchReferenceDto.class);
						list.add(mr);
					}
				} catch (Exception e) {
					break;
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
	
	public MatchlistDto findBySummonerId(SummonerDto summonerDto, int season) {
		MatchlistDto matchlistDto = matchlistRepository.findBySummonerId(summonerDto.getId());
		
		// 최대 시즌이 몇시즌인지 알기위한 작업
		JSONArray jsonArr = new JSONArray(Api.getHttpRequest("http://static.developer.riotgames.com/docs/lol/seasons.json"));

		if (matchlistDto == null) {
			// 매치 데이터가 없는 유저
			matchlistDto = new MatchlistDto();
			matchlistDto.setSummonerId(summonerDto.getId());
			List<MatchReferenceDto> list = new ArrayList<>();
			int beginIndex = 0, endIndex = 100;
			while (true) {
				System.out.println("search " + beginIndex + " ~ " + endIndex);
				String query = "?beginIndex=" + beginIndex + "&endIndex=" + endIndex;;
				if(season >= 0 && season < jsonArr.length())
					query += "&season="+season;
					
				try {
					JSONObject obj = new JSONObject(
						Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
								summonerDto.getAccountId() + query));
					JSONArray arr = obj.getJSONArray("matches");
					if (arr.length() == 0)
						break;
					for (int i = 0; i < arr.length(); i++) {
						String json = arr.getJSONObject(i).toString();
						MatchReferenceDto mr = new Gson().fromJson(json, MatchReferenceDto.class);
						list.add(mr);
					}
				} catch (Exception e) {
					break;
				}
				beginIndex += 100;
				endIndex += 100;
			}
			Collections.sort(list, (o1, o2) -> (o1.getTimestamp() + "").compareTo(o2.getTimestamp() + ""));
			matchlistDto.setMatches(list);

		} else {
			// 매치 데이터가 있는 유저
			List<MatchReferenceDto> matches = matchlistDto.getMatches();
			Long beginTime = matches.get(matches.size() - 1).getTimestamp() + 1;
			List<MatchReferenceDto> list = new ArrayList<>();
			int beginIndex = 0, endIndex = 100;
			while (true) {
				System.out.println("search " + beginIndex + " ~ " + endIndex);
				String query = "?beginIndex=" + beginIndex + "&endIndex=" + endIndex
						+ "&beginTime=" + beginTime;
				if(season >= 0 && season < jsonArr.length())
					query += "&season"+season;
				
				try {
				JSONObject obj = new JSONObject(
						Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
								summonerDto.getAccountId() + query));
					JSONArray arr = obj.getJSONArray("matches");
					if (arr.length() == 0)
						break;
					for (int i = 0; i < arr.length(); i++) {
						String json = arr.getJSONObject(i).toString();
						MatchReferenceDto mr = new Gson().fromJson(json, MatchReferenceDto.class);
						list.add(mr);
					}
				} catch (Exception e) {
					break;
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
