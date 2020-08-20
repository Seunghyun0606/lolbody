package com.ssafy.lolbody.service;

import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MatchTimelineDto;
import com.ssafy.lolbody.repository.MatchTimelineRepository;

@Service
public class MatchTimelineService {
	@Autowired
	private MatchTimelineRepository matchTimelineRepository;

	public MatchTimelineDto findByGameId(long gameId) throws Exception {
		MatchTimelineDto matchTimelineDto = matchTimelineRepository.findByGameId(gameId);
		if (matchTimelineDto == null) {
			String json = Api.multi("https://kr.api.riotgames.com/lol/match/v4/timelines/by-match", gameId + "");
			if (json.equals("Fail"))
				throw new TimeoutException("서브 키 요청이 너무 많습니다.");
			else if (json.equals("Forbidden"))
				throw new TimeoutException("서브 키 요청이 너무 많습니다. (만료된 key 포함)");
			matchTimelineDto = new Gson().fromJson(json, MatchTimelineDto.class);
			matchTimelineDto.setGameId(gameId);
			matchTimelineRepository.save(matchTimelineDto);
		}
		return matchTimelineDto;
	}
}
