package com.ssafy.lolbody.dao;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ssafy.lolbody.dto.LeagueEntryDTO;
import com.ssafy.lolbody.dto.SummonerDTO;

@Component
public class SummonerDAO {
	@Autowired
	SqlSession sqlSession;

	public SummonerDTO getSummoner(String summonerName) {
		return sqlSession.selectOne("mapper.getSummoner", summonerName);
	}

	public int insertSummoner(SummonerDTO summonerDto) {
		return sqlSession.insert("mapper.insertSummoner", summonerDto);
	}

	public LeagueEntryDTO getSummonerTier(String summonerName) {
		return sqlSession.selectOne("mapper.getSummonerTier", summonerName);
	}

	public int insertSummonerTier(LeagueEntryDTO leagueDto) {
		return sqlSession.insert("mapper.insertSummonerTier", leagueDto);
	}

	public JSONObject getSummonerMatch(String summonerName) {
		return null;
	}
}
