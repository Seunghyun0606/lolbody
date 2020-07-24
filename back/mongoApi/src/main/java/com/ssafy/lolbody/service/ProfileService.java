package com.ssafy.lolbody.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.dto.ProfileDto;
import com.ssafy.lolbody.dto.SummonerDto;

@Service
public class ProfileService {
	@Autowired
	private SummonerService summonerService;
	@Autowired
	private LeagueEntryService leagueEntryService;
	@Autowired
	private MatchlistService matchlistService;
	@Autowired
	private MatchService matchService;

	public ProfileDto getProfile(String name) throws Exception {
		ProfileDto profile = new ProfileDto();

		SummonerDto summonerDto = summonerService.findByName(name);
		profile.setProfileIconId(summonerDto.getProfileIconId());
		profile.setSummonerLevel(summonerDto.getSummonerLevel());
		profile.setSummonerName(summonerDto.getName());

		List<LeagueEntryDto> leagueEntryList = leagueEntryService.findBySummonerId(summonerDto.getId());
		for (LeagueEntryDto i : leagueEntryList) {
			if (i.getQueueType().equals("RANKED_SOLO_5x5")) {
				profile.setTier(i.getTier());
				profile.setRank(i.getRank());
				profile.setLeaguePoints(i.getLeaguePoints());
				profile.setRankedWins(i.getWins());
				profile.setRankedLosses(i.getLosses());
				profile.setRankedWinRate(100.0 * i.getWins() / (i.getWins() + i.getLosses()));
			}
		}
		/*
		MatchlistDto matchlistDto = matchlistService.findBySummonerId(summonerDto);
		List<MatchReferenceDto> matchReferenceList = matchlistDto.getMatches();
		int blindWins = 0, blindLosses = 0;
		for (MatchReferenceDto matchReferenceDto : matchReferenceList) {
			if (matchReferenceDto.getQueue() == 430) {
				MatchDto matchDto = matchService.findByGameId(matchReferenceDto.getGameId() + "");
				List<ParticipantIdentityDto> participantIdentityList = matchDto.getParticipantIdentities();
				int participantId = -1;
				for (ParticipantIdentityDto participantIdentityDto : participantIdentityList) {
					if (participantIdentityDto.getPlayer().getSummonerName().equals(summonerDto.getName())) {
						participantId = participantIdentityDto.getParticipantId();
						break;
					}
				}
				List<ParticipantDto> participantList = matchDto.getParticipants();
				for (ParticipantDto participantDto : participantList) {
					if (participantDto.getParticipantId() == participantId) {
						if (participantDto.getStats().isWin()) {
							blindWins++;
						} else {
							blindLosses++;
						}
						break;
					}
				}
				System.out.println(matchReferenceDto.getGameId()+" wins:"+blindWins+" losses:"+blindLosses);
			}
		}
		profile.setBlindWins(blindWins);
		profile.setBlindLosses(blindLosses);
		profile.setBlindWinRate(100.0 * blindWins / (blindWins + blindLosses));
		*/
		return profile;

	}
}
