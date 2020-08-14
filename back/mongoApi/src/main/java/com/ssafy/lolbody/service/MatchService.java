package com.ssafy.lolbody.service;

import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.AnalysisDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchPositionDto;
import com.ssafy.lolbody.dto.MatchTimelineDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.repository.MatchRepository;

@Service
public class MatchService {
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private MatchTimelineService matchTimelineService;

	public void save(MatchDto matchDto) {
		matchRepository.save(matchDto);
	}

	public MatchDto findByGameId(long gameId) throws Exception {
		MatchDto matchDto = matchRepository.findByGameId(gameId);
		if (matchDto == null) {
			matchDto = new MatchDto();
			String json = Api.multi("https://kr.api.riotgames.com/lol/match/v4/matches", gameId + "");
			if (json.equals("Fail"))
				throw new TimeoutException("요청이 너무 많습니다.");
			else if (json.equals("Forbidden"))
				throw new TimeoutException("요청이 너무 많습니다. (만료된 key 포함)");
			matchDto = new Gson().fromJson(json, MatchDto.class);

			List<ParticipantDto> participants = matchDto.getParticipants();
			List<ParticipantIdentityDto> participantIdentitys = matchDto.getParticipantIdentities();
			MatchTimelineDto matchTimeline = matchTimelineService.findByGameId(gameId);

			for (int i = 0; i < participants.size(); i++) {
				ParticipantDto participant = participants.get(i);
				participant.setName(participantIdentitys.get(i).getPlayer().getSummonerName());
				MatchPositionDto position = matchTimeline.getFrames().get(2).getParticipantFrames().get((i + 1) + "")
						.getPosition();
				participant.setX(position.getX());
				participant.setY(position.getY());
				participant.setAnalysis(new AnalysisDto());
			}

			save(matchDto);
		}
		return matchDto;
	}
	
	public boolean gameCheck(long gameId) throws Exception {
		MatchDto matchDto = matchRepository.findByGameId(gameId);
		if (matchDto == null) {
			matchDto = new MatchDto();
			String json = Api.multi("https://kr.api.riotgames.com/lol/match/v4/matches", gameId + "");
			if (json.equals("Fail"))
				throw new TimeoutException("요청이 너무 많습니다.");
			else if (json.equals("Forbidden"))
				throw new TimeoutException("요청이 너무 많습니다. (만료된 key 포함)");
			matchDto = new Gson().fromJson(json, MatchDto.class);

			List<ParticipantDto> participants = matchDto.getParticipants();
			List<ParticipantIdentityDto> participantIdentitys = matchDto.getParticipantIdentities();
			MatchTimelineDto matchTimeline = matchTimelineService.findByGameId(gameId);

			for (int i = 0; i < participants.size(); i++) {
				ParticipantDto participant = participants.get(i);
				participant.setName(participantIdentitys.get(i).getPlayer().getSummonerName());
				MatchPositionDto position = matchTimeline.getFrames().get(2).getParticipantFrames().get((i + 1) + "")
						.getPosition();
				participant.setX(position.getX());
				participant.setY(position.getY());
				participant.setAnalysis(new AnalysisDto());
			}

			save(matchDto);
			return true;
		}
		return false;
	}
}
