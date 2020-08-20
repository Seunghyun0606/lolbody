package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.SubSummonerDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.repository.MatchlistRepository;
import com.ssafy.lolbody.repository.SubSummonerRepository;

@Service
public class MatchlistService {
	@Autowired
	private MatchlistRepository matchlistRepository;
	@Autowired
	private SubSummonerRepository subSummonerRepository;

	public void save(MatchlistDto matchlistDto) {
		matchlistRepository.save(matchlistDto);
	}

	public MatchlistDto findBySummonerId(SummonerDto summoner) throws Exception {
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

		MatchlistDto matchlistDto = matchlistRepository.findBySummonerId(summoner.getId());

		JSONArray jsonArr = new JSONArray(
				Api.getHttpRequest("http://static.developer.riotgames.com/docs/lol/seasons.json"));
		int season = ((JSONObject) jsonArr.getJSONObject(jsonArr.length() - 1)).getInt("id"), idx = 0;

		if (matchlistDto == null) {
			// 매치 데이터가 없는 유저
			matchlistDto = new MatchlistDto();
			matchlistDto.setSummonerId(summoner.getId());
			List<MatchReferenceDto> list = new ArrayList<>();
			int beginIndex = 0, endIndex = 100;
			while (true) {
				String accountId = "", key = "";
				boolean isForbidden = false, isEnd = false, isOk = false;
				int cnt = 10 * tokens.length;

				while (cnt-- > 0) {
					key = tokens[idx];
					idx = (idx + 1) % tokens.length;
					if (summonerMap.containsKey(key)) {
						accountId = summonerMap.get(key).getAccountId();
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
							accountId = tmpSummoner.getId();
						}
					}
					String query = "?season=" + season + "&beginIndex=" + beginIndex + "&endIndex=" + endIndex;
					String str = Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
							accountId + query, key);
					if (str.equals("Fail")) {
						isEnd = true;
						break;
					} else if (str.equals("Timeout")) {
						continue;
					} else if (str.equals("Forbidden")) {
						isForbidden = true;
						continue;
					} else {
						isOk = true;
						JSONObject obj = new JSONObject(str);
						JSONArray arr = obj.getJSONArray("matches");
						if (arr.length() == 0) {
							isEnd = true;
							break;
						}
						for (int i = 0; i < arr.length(); i++) {
							String json = arr.getJSONObject(i).toString();
							MatchReferenceDto mr = new Gson().fromJson(json, MatchReferenceDto.class);
							list.add(mr);
						}
						break;
					}
				}

				if (isEnd)
					break;
				if (isForbidden)
					throw new TimeoutException("서브 키 요청이 너무 많습니다. (만료된 key 포함)");
				if (!isOk)
					throw new TimeoutException("서브 키 요청이 너무 많습니다.");

				beginIndex += 100;
				endIndex += 100;
			}
			Collections.sort(list, (o1, o2) -> (o1.getTimestamp() + "").compareTo(o2.getTimestamp() + ""));
			matchlistDto.setMatches(list);

		} else {
			// 매치 데이터가 있는 유저
			List<MatchReferenceDto> matches = matchlistDto.getMatches();
			long beginTime = 0;
			if (matches.size() != 0)
				beginTime = matches.get(matches.size() - 1).getTimestamp() + 1;
			List<MatchReferenceDto> list = new ArrayList<>();
			int beginIndex = 0, endIndex = 100;
			while (true) {
				String accountId = "", key = "";
				boolean isForbidden = false, isEnd = false, isOk = false;
				int cnt = 10 * tokens.length;

				while (cnt-- > 0) {
					key = tokens[idx];
					idx = (idx + 1) % tokens.length;
					if (summonerMap.containsKey(key)) {
						accountId = summonerMap.get(key).getAccountId();
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
							accountId = tmpSummoner.getId();
						}
					}
					String query = "?season=" + season + "&beginIndex=" + beginIndex + "&endIndex=" + endIndex
							+ "&beginTime=" + beginTime;
					String str = Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
							accountId + query, key);
					if (str.equals("Fail")) {
						isEnd = true;
						break;
					} else if (str.equals("Timeout")) {
						continue;
					} else if (str.equals("Forbidden")) {
						isForbidden = true;
						continue;
					} else {
						isOk = true;
						JSONObject obj = new JSONObject(str);
						JSONArray arr = obj.getJSONArray("matches");
						if (arr.length() == 0) {
							isEnd = true;
							break;
						}
						for (int i = 0; i < arr.length(); i++) {
							String json = arr.getJSONObject(i).toString();
							MatchReferenceDto mr = new Gson().fromJson(json, MatchReferenceDto.class);
							list.add(mr);
						}
						break;
					}
				}

				if (isEnd)
					break;
				if (isForbidden)
					throw new TimeoutException("서브 키 요청이 너무 많습니다. (만료된 key 포함)");
				if (!isOk)
					throw new TimeoutException("서브 키 요청이 너무 많습니다.");

				beginIndex += 100;
				endIndex += 100;
			}
			Collections.sort(list, (o1, o2) -> (o1.getTimestamp() + "").compareTo(o2.getTimestamp() + ""));
			matches.addAll(list);

		}
		save(matchlistDto);
		return matchlistDto;
	}

	public MatchlistDto findBySummonerId(SummonerDto summoner, int season) {
		MatchlistDto matchlistDto = matchlistRepository.findBySummonerId(summoner.getId());

		// 최대 시즌이 몇시즌인지 알기위한 작업
		JSONArray jsonArr = new JSONArray(
				Api.getHttpRequest("http://static.developer.riotgames.com/docs/lol/seasons.json"));

		if (matchlistDto == null) {
			// 매치 데이터가 없는 유저
			matchlistDto = new MatchlistDto();
			matchlistDto.setSummonerId(summoner.getId());
			List<MatchReferenceDto> list = new ArrayList<>();
			int beginIndex = 0, endIndex = 100;
			while (true) {
				String query = "?beginIndex=" + beginIndex + "&endIndex=" + endIndex;
				;
				if (season >= 0 && season < jsonArr.length())
					query += "&season=" + season;

				try {
					JSONObject obj = new JSONObject(
							Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
									summoner.getAccountId() + query));
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
			long beginTime = 0;
			if (matches.size() != 0)
				beginTime = matches.get(matches.size() - 1).getTimestamp() + 1;
			List<MatchReferenceDto> list = new ArrayList<>();
			int beginIndex = 0, endIndex = 100;
			while (true) {
				String query = "?beginIndex=" + beginIndex + "&endIndex=" + endIndex + "&beginTime=" + beginTime;
				if (season >= 0 && season < jsonArr.length())
					query += "&season" + season;

				try {
					JSONObject obj = new JSONObject(
							Api.get("https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account",
									summoner.getAccountId() + query));
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

	public Map<String, Integer> getLaneFrequency(SummonerDto summoner) {
		MatchlistDto matchlistDto = matchlistRepository.findBySummonerId(summoner.getId());

		List<MatchReferenceDto> matches = matchlistDto.getMatches();
		Collections.sort(matches, (o1, o2) -> Long.compare(o2.getTimestamp(), o1.getTimestamp()));
		matches = matches.stream().filter(o -> o.getTimestamp() >= 1578596400000l && o.getQueue() == 420)
				.collect(Collectors.toList());

		Map<String, Integer> lane = new LinkedHashMap<>();
		for (MatchReferenceDto matchRef : matches) {
			if (matchRef.getRole().equals("DUO_SUPPORT")) {
				if (!matchRef.getLane().equals("NONE")) {
					if (!lane.containsKey(matchRef.getLane()))
						lane.put(matchRef.getLane(), 0);
					lane.put(matchRef.getLane(), lane.get(matchRef.getLane()) + 1);
				} else {
					if (!lane.containsKey("SUPPORT"))
						lane.put("SUPPORT", 0);
					lane.put("SUPPORT", lane.get("SUPPORT") + 1);
				}
			} else {
				if (!lane.containsKey(matchRef.getLane()))
					lane.put(matchRef.getLane(), 0);
				lane.put(matchRef.getLane(), lane.get(matchRef.getLane()) + 1);
			}
		}
		return lane;
	}

	public MatchlistDto findOnly(SummonerDto summoner) throws Exception {
		MatchlistDto matchlistDto = matchlistRepository.findBySummonerId(summoner.getId());
		if (matchlistDto == null) {
			return findBySummonerId(summoner);
		}
		return matchlistDto;
	}

}
