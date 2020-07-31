package com.ssafy.apidemo.service;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.ssafy.apidemo.api.Api;
import com.ssafy.apidemo.dto.LeagueEntryDTO;
import com.ssafy.apidemo.dto.SummonerDTO;
import com.ssafy.apidemo.repository.LeagueEntryRepository;
import com.ssafy.apidemo.repository.SummonerRepository;

@DataMongoTest
public class SummonerServiceTest {
	@Autowired
	SummonerService summonerService;
	@Autowired
	LeagueEntryService leagueEntryService;
	
	private static final Logger log = LoggerFactory.getLogger(SummonerServiceTest.class);
	
	@Test
	public void test() {
		try {
			JSONObject object = new JSONObject(Api.get("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name", "foxnpenguin"));
			SummonerDTO summonerDto = new SummonerDTO();
			summonerDto.setId(object.getString("id"));
			summonerDto.setAccountId(object.getString("accountId"));
			summonerDto.setPuuid(object.getString("puuid"));
			summonerDto.setName(object.getString("name"));
			summonerDto.setProfileIconId(object.getInt("profileIconId"));
			summonerDto.setRevisionDate(object.getLong("revisionDate"));
			summonerDto.setSummonerLevel(object.getInt("summonerLevel"));
			
			log.info("summonerDto save");
			log.info("------------------------------");
			summonerService.insert(summonerDto);
			for(SummonerDTO summoner: summonerService.findAll()) {
				log.info(summoner.toString());
				JSONArray arr = new JSONArray(Api.get("https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner", summoner.getId()));
				List<LeagueEntryDTO> leagueEntryList = new ArrayList<>();
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
					log.info(leagueEntry.toString());
					leagueEntryList.add(leagueEntry);
				}
				leagueEntryService.save(leagueEntryList);
			}
			log.info("LeagueEntry findAll()");
			log.info("------------------------------");
//			for(LeagueEntryDTO leagueEntry: leagueEntryService.findAll()) {
//				log.info(leagueEntry.toString());
//			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
