package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchRecordDto;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.dto.ProfileDto;
import com.ssafy.lolbody.dto.ProfileReferenceDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.dto.WinRateDto;
import com.ssafy.lolbody.repository.ProfileRepository;

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
	@Autowired
	private ProfileRepository profileRepository;

	public ProfileReferenceDto getProfile(String name) throws Exception {
		SummonerDto summonerDto = summonerService.findByName(name);
		ProfileDto profileDto = profileRepository.findBySummonerId(summonerDto.getId());
		ProfileReferenceDto profileReferenceDto = new ProfileReferenceDto();
		profileReferenceDto.setTimestamp(System.currentTimeMillis());
//		if (profileDto == null) {
		// 프로필 데이터가 없는 유저
		profileDto = new ProfileDto();
		profileDto.setSummonerId(summonerDto.getId());
		List<ProfileReferenceDto> profiles = new ArrayList<>();

		profileReferenceDto.setProfileIconId(summonerDto.getProfileIconId());
		profileReferenceDto.setSummonerLevel(summonerDto.getSummonerLevel());
		profileReferenceDto.setSummonerName(summonerDto.getName());

		List<LeagueEntryDto> leagueEntryList = leagueEntryService.findBySummonerId(summonerDto.getId());
		for (LeagueEntryDto i : leagueEntryList) {
			if (i.getQueueType().equals("RANKED_SOLO_5x5")) {
				profileReferenceDto.setTier(i.getTier());
				profileReferenceDto.setRank(i.getRank());
				profileReferenceDto.setLeaguePoints(i.getLeaguePoints());
			}
		}
		MatchlistDto matchlistDto = matchlistService.findBySummonerId(summonerDto);
		List<MatchReferenceDto> matchReferences = matchlistDto.getMatches();
		int rankedWins = 0, rankedLosses = 0, blindWins = 0, blindLosses = 0;
		for (MatchReferenceDto matchReferenceDto : matchReferences) {
			int queue = matchReferenceDto.getQueue();
			if (queue == 420 || queue == 430) {
				MatchDto matchDto = matchService.findByGameId(matchReferenceDto.getGameId() + "");
				if (matchDto.getGameDuration() < 600)
					continue;
				List<ParticipantIdentityDto> participantIdentityList = matchDto.getParticipantIdentities();
				int participantId = -1;
				for (ParticipantIdentityDto participantIdentityDto : participantIdentityList) {
					if (participantIdentityDto.getPlayer().getSummonerName().equals(summonerDto.getName())) {
						participantId = participantIdentityDto.getParticipantId();
						break;
					}
				}
				List<ParticipantDto> participantList = matchDto.getParticipants();
				if (participantList.get(participantId - 1).getStats().isWin()) {
					if (queue == 420)
						rankedWins++;
					else
						blindWins++;
				} else {
					if (queue == 420)
						rankedLosses++;
					else
						blindLosses++;
				}
			}
		}

		MatchRecordDto rankedRecord = new MatchRecordDto();
		WinRateDto totalRecord = new WinRateDto(rankedWins, rankedLosses,
				100.0 * rankedWins / (rankedWins + rankedLosses));
		WinRateDto mostChamRecord = new WinRateDto();
		WinRateDto mostLineRecord = new WinRateDto();
		WinRateDto secondLineRecord = new WinRateDto();
		rankedRecord.setTotalRecord(totalRecord);
		rankedRecord.setMostChamRecord(mostChamRecord);
		rankedRecord.setMostLineRecord(mostLineRecord);
		rankedRecord.setSecondLineRecord(secondLineRecord);
		profileReferenceDto.setRankedRecord(rankedRecord);

		MatchRecordDto blindRecord = new MatchRecordDto();
		totalRecord = new WinRateDto(blindWins, blindLosses, 100.0 * blindWins / (blindWins + blindLosses));
		mostChamRecord = new WinRateDto();
		mostLineRecord = new WinRateDto();
		secondLineRecord = new WinRateDto();
		blindRecord.setTotalRecord(totalRecord);
		blindRecord.setMostChamRecord(mostChamRecord);
		blindRecord.setMostLineRecord(mostLineRecord);
		blindRecord.setSecondLineRecord(secondLineRecord);
		profileReferenceDto.setBlindRecord(blindRecord);

		profiles.add(profileReferenceDto);
		profileDto.setProfiles(profiles);
//		}

		profileRepository.save(profileDto);
		return profileReferenceDto;
	}
}
