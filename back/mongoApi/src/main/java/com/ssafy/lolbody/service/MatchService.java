package com.ssafy.lolbody.service;

import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.repository.MatchRepository;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;

	public void save(MatchDto matchDto) {
		matchRepository.save(matchDto);
	}

	public MatchDto findByGameId(long gameId) throws Exception{
		MatchDto matchDto = matchRepository.findByGameId(gameId);
		if (matchDto == null) {
			matchDto = new MatchDto();
			String json = Api.multi("https://kr.api.riotgames.com/lol/match/v4/matches", gameId + "");
			if(json.equals("Fail"))
				throw new TimeoutException("요청이 너무 많습니다.");
			else if(json.equals("Forbidden"))
				throw new TimeoutException("Api key가 만료되었습니다.");
			matchDto = new Gson().fromJson(json, MatchDto.class);
			save(matchDto);
		}
		return matchDto;
	}

}
