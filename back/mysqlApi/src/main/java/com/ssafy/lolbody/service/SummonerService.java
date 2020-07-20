package com.ssafy.lolbody.service;

import java.io.*;
import java.net.URL;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.lolbody.config.ApiKey;
import com.ssafy.lolbody.dao.SummonerDAO;
import com.ssafy.lolbody.dto.LeagueEntryDTO;
import com.ssafy.lolbody.dto.SummonerDTO;

@Component
public class SummonerService {
	@Autowired
	SummonerDAO dao;

	public SummonerDTO getSummoner(String summonerName) {
		SummonerDTO summonerDto = dao.getSummoner(summonerName);
		if (summonerDto == null) {
			summonerDto = new SummonerDTO();
			String str = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name";
			str += "/" + summonerName;
			str += "?api_key=" + ApiKey.key;
			try {
				URL url = new URL(str);
				String line = "", result = "";
				BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((line = bf.readLine()) != null) {
					result = result.concat(line);
				}

				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(result);
				summonerDto.setAccountId((String) obj.get("accountId"));
				summonerDto.setProfileIconId(Integer.parseInt(obj.get("profileIconId").toString()));
				summonerDto.setRevisionDate((long) obj.get("revisionDate"));
				summonerDto.setName((String) obj.get("name"));
				summonerDto.setId((String) obj.get("id"));
				summonerDto.setPuuid((String) obj.get("puuid"));
				summonerDto.setSummonerLevel((long) obj.get("summonerLevel"));
				dao.insertSummoner(summonerDto);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return summonerDto;
	}

	public LeagueEntryDTO getSummonerTier(String summonerName) {
		LeagueEntryDTO leagueDto = dao.getSummonerTier(summonerName);
		if (leagueDto == null) {
			leagueDto = new LeagueEntryDTO();
			SummonerDTO summonerDTO = getSummoner(summonerName);
			String str = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner";
			str += "/" + summonerDTO.getId();
			str += "?api_key=" + ApiKey.key;
			try {
				URL url = new URL(str);
				String line = "", result = "";
				BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
				while ((line = bf.readLine()) != null) {
					result = result.concat(line);
				}

				JSONParser parser = new JSONParser();
				JSONArray arr = (JSONArray) parser.parse(result);
				for (int i = 0; i < arr.size(); i++) {
					JSONObject obj = (JSONObject) arr.get(i);
					leagueDto.setLeagueId((String) obj.get("leagueId"));
					leagueDto.setSummonerName(summonerDTO.getName());
					leagueDto.setQueueType((String) obj.get("queueType"));
					leagueDto.setTier((String) obj.get("tier"));
					leagueDto.setRank((String) obj.get("rank"));
					leagueDto.setLeaguePoints(Integer.parseInt(obj.get("leaguePoints").toString()));
					leagueDto.setWins(Integer.parseInt(obj.get("wins").toString()));
					leagueDto.setLosses(Integer.parseInt(obj.get("losses").toString()));
				}
				if (leagueDto.getSummonerName() == null) {
					leagueDto.setSummonerName(summonerDTO.getName());
					leagueDto.setRank("UNRANKED");
				}
				dao.insertSummonerTier(leagueDto);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return leagueDto;
	}

	public JSONObject getSummonerMatch(String summonerName) {
		SummonerDTO tmp = getSummoner(summonerName);
		String str = "https://kr.api.riotgames.com/lol/match/v4/matchlists/by-account";
		str += "/" + tmp.getAccountId();
		str += "?api_key=" + ApiKey.key;
		try {
			URL url = new URL(str);
			String line = "", result = "";
			BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream()));
			while ((line = bf.readLine()) != null) {
				result = result.concat(line);
			}

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(result);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
