package com.ssafy.lolbody.service;

import java.io.*;
import java.net.URL;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.lolbody.config.ApiKey;
import com.ssafy.lolbody.dao.SummonerDAO;
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

	public JSONArray getSummonerTier(String summonerName) {
		SummonerDTO tmp = getSummoner(summonerName);
		String str = "https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner";
		str += "/" + tmp.getId();
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
			return arr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
