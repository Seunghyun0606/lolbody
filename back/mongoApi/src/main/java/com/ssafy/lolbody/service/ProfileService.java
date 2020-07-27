package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		if (profileDto == null) {
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

			WinRateDto rankedTotal = new WinRateDto(0, 0, 0, 0.0);
			WinRateDto blindTotal = new WinRateDto(0, 0, 0, 0.0);
			Map<String, WinRateDto> rankedLine = new LinkedHashMap<>();
			Map<String, WinRateDto> blindLine = new LinkedHashMap<>();
			Map<Integer, WinRateDto> rankedCham = new LinkedHashMap<>();
			Map<Integer, WinRateDto> blindCham = new LinkedHashMap<>();

			for (MatchReferenceDto matchReferenceDto : matchReferences) {
				int queue = matchReferenceDto.getQueue();
				if (queue == 420 || queue == 430) {
					MatchDto matchDto = matchService.findByGameId(matchReferenceDto.getGameId() + "");
					if (matchDto.getGameDuration() < 600) // 다시하기는 통계에 집계되지 않음
						continue;

					String line = "";
					if (matchReferenceDto.getRole().equals("DUO_SUPPORT")) {
						line = "SUPPORT";
					} else {
						line = matchReferenceDto.getLane();
					}
					int cham = matchReferenceDto.getChampion();

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
						if (queue == 420) {
							calcWinRate(rankedTotal, true);
							if (!rankedLine.containsKey(line))
								rankedLine.put(line, new WinRateDto(0, 0, 0, 0.0));
							rankedLine.put(line, calcWinRate(rankedLine.get(line), true));
							if (!rankedCham.containsKey(cham))
								rankedCham.put(cham, new WinRateDto(0, 0, 0, 0.0));
							rankedCham.put(cham, calcWinRate(rankedCham.get(cham), true));

						} else {
							calcWinRate(blindTotal, true);
							if (!blindLine.containsKey(line))
								blindLine.put(line, new WinRateDto(0, 0, 0, 0.0));
							blindLine.put(line, calcWinRate(blindLine.get(line), true));
							if (!blindCham.containsKey(cham))
								blindCham.put(cham, new WinRateDto(0, 0, 0, 0.0));
							blindCham.put(cham, calcWinRate(blindCham.get(cham), true));

						}
					} else {
						if (queue == 420) {
							calcWinRate(rankedTotal, false);
							if (!rankedLine.containsKey(line))
								rankedLine.put(line, new WinRateDto(0, 0, 0, 0.0));
							rankedLine.put(line, calcWinRate(rankedLine.get(line), false));
							if (!rankedCham.containsKey(cham))
								rankedCham.put(cham, new WinRateDto(0, 0, 0, 0.0));
							rankedCham.put(cham, calcWinRate(rankedCham.get(cham), false));

						} else {
							calcWinRate(blindTotal, false);
							if (!blindLine.containsKey(line))
								blindLine.put(line, new WinRateDto(0, 0, 0, 0.0));
							blindLine.put(line, calcWinRate(blindLine.get(line), false));
							if (!blindCham.containsKey(cham))
								blindCham.put(cham, new WinRateDto(0, 0, 0, 0.0));
							blindCham.put(cham, calcWinRate(blindCham.get(cham), false));

						}
					}
				}
			}

			List<Map.Entry<Integer, WinRateDto>> rcEntries = new LinkedList<>(rankedCham.entrySet());
			Collections.sort(rcEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> rlEntries = new LinkedList<>(rankedLine.entrySet());
			Collections.sort(rlEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<Integer, WinRateDto>> bcEntries = new LinkedList<>(blindCham.entrySet());
			Collections.sort(bcEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> blEntries = new LinkedList<>(blindLine.entrySet());
			Collections.sort(blEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());

			MatchRecordDto rankedRecord = new MatchRecordDto();
			rankedRecord.setTotalRecord(rankedTotal);
			rankedRecord.setMostChamRecord(rcEntries.get(0).getValue());
			rankedRecord.setMostLineRecord(rlEntries.get(0).getValue());
			rankedRecord.setSecondLineRecord(rlEntries.get(1).getValue());
			rankedRecord.setLineRecord(rankedLine);
			rankedRecord.setChamRecord(rankedCham);
			rankedRecord.setMostCham(rcEntries.get(0).getKey());
			rankedRecord.setMostLine(rlEntries.get(0).getKey());
			rankedRecord.setSecondLine(rlEntries.get(1).getKey());
			profileReferenceDto.setRankedRecord(rankedRecord);

			MatchRecordDto blindRecord = new MatchRecordDto();
			blindRecord.setTotalRecord(blindTotal);
			blindRecord.setMostChamRecord(bcEntries.get(0).getValue());
			blindRecord.setMostLineRecord(blEntries.get(0).getValue());
			blindRecord.setSecondLineRecord(blEntries.get(1).getValue());
			blindRecord.setLineRecord(blindLine);
			blindRecord.setChamRecord(blindCham);
			blindRecord.setMostCham(bcEntries.get(0).getKey());
			blindRecord.setMostLine(blEntries.get(0).getKey());
			blindRecord.setSecondLine(blEntries.get(1).getKey());
			profileReferenceDto.setBlindRecord(blindRecord);

			profiles.add(profileReferenceDto);
			profileDto.setProfiles(profiles);
		} else {
			// 프로필 데이터가 있는 유저
			List<ProfileReferenceDto> profiles = profileDto.getProfiles();

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

			ProfileReferenceDto lastProfile = profiles.get(profiles.size() - 1);

			MatchlistDto matchlistDto = matchlistService.findBySummonerId(summonerDto);
			List<MatchReferenceDto> matchReferences = matchlistDto.getMatches();
			long timestamp = matchReferences.get(matchReferences.size() - 1).getTimestamp();

			WinRateDto rankedTotal = lastProfile.getRankedRecord().getTotalRecord();
			WinRateDto blindTotal = lastProfile.getBlindRecord().getTotalRecord();
			Map<String, WinRateDto> rankedLine = lastProfile.getRankedRecord().getLineRecord();
			Map<String, WinRateDto> blindLine = lastProfile.getBlindRecord().getLineRecord();
			Map<Integer, WinRateDto> rankedCham = lastProfile.getRankedRecord().getChamRecord();
			Map<Integer, WinRateDto> blindCham = lastProfile.getBlindRecord().getChamRecord();

			for (MatchReferenceDto matchReferenceDto : matchReferences) {
				int queue = matchReferenceDto.getQueue();
				if (queue == 420 || queue == 430) {
					MatchDto matchDto = matchService.findByGameId(matchReferenceDto.getGameId() + "");
					if (timestamp >= matchDto.getGameCreation() || matchDto.getGameDuration() < 600) // 다시하기는 통계에 집계되지
						continue;

					String line = "";
					if (matchReferenceDto.getRole().equals("DUO_SUPPORT")) {
						line = "SUPPORT";
					} else {
						line = matchReferenceDto.getLane();
					}
					int cham = matchReferenceDto.getChampion();

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
						if (queue == 420) {
							calcWinRate(rankedTotal, true);
							if (!rankedLine.containsKey(line))
								rankedLine.put(line, new WinRateDto(0, 0, 0, 0.0));
							rankedLine.put(line, calcWinRate(rankedLine.get(line), true));
							if (!rankedCham.containsKey(cham))
								rankedCham.put(cham, new WinRateDto(0, 0, 0, 0.0));
							rankedCham.put(cham, calcWinRate(rankedCham.get(cham), true));

						} else {
							calcWinRate(blindTotal, true);
							if (!blindLine.containsKey(line))
								blindLine.put(line, new WinRateDto(0, 0, 0, 0.0));
							blindLine.put(line, calcWinRate(blindLine.get(line), true));
							if (!blindCham.containsKey(cham))
								blindCham.put(cham, new WinRateDto(0, 0, 0, 0.0));
							blindCham.put(cham, calcWinRate(blindCham.get(cham), true));

						}
					} else {
						if (queue == 420) {
							calcWinRate(rankedTotal, false);
							if (!rankedLine.containsKey(line))
								rankedLine.put(line, new WinRateDto(0, 0, 0, 0.0));
							rankedLine.put(line, calcWinRate(rankedLine.get(line), false));
							if (!rankedCham.containsKey(cham))
								rankedCham.put(cham, new WinRateDto(0, 0, 0, 0.0));
							rankedCham.put(cham, calcWinRate(rankedCham.get(cham), false));

						} else {
							calcWinRate(blindTotal, false);
							if (!blindLine.containsKey(line))
								blindLine.put(line, new WinRateDto(0, 0, 0, 0.0));
							blindLine.put(line, calcWinRate(blindLine.get(line), false));
							if (!blindCham.containsKey(cham))
								blindCham.put(cham, new WinRateDto(0, 0, 0, 0.0));
							blindCham.put(cham, calcWinRate(blindCham.get(cham), false));

						}
					}
				}
			}

			List<Map.Entry<Integer, WinRateDto>> rcEntries = new LinkedList<>(rankedCham.entrySet());
			Collections.sort(rcEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> rlEntries = new LinkedList<>(rankedLine.entrySet());
			Collections.sort(rlEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<Integer, WinRateDto>> bcEntries = new LinkedList<>(blindCham.entrySet());
			Collections.sort(bcEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> blEntries = new LinkedList<>(blindLine.entrySet());
			Collections.sort(blEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());

			MatchRecordDto rankedRecord = new MatchRecordDto();
			rankedRecord.setTotalRecord(rankedTotal);
			rankedRecord.setMostChamRecord(rcEntries.get(0).getValue());
			rankedRecord.setMostLineRecord(rlEntries.get(0).getValue());
			rankedRecord.setSecondLineRecord(rlEntries.get(1).getValue());
			rankedRecord.setLineRecord(rankedLine);
			rankedRecord.setChamRecord(rankedCham);
			rankedRecord.setMostCham(rcEntries.get(0).getKey());
			rankedRecord.setMostLine(rlEntries.get(0).getKey());
			rankedRecord.setSecondLine(rlEntries.get(1).getKey());
			profileReferenceDto.setRankedRecord(rankedRecord);

			MatchRecordDto blindRecord = new MatchRecordDto();
			blindRecord.setTotalRecord(blindTotal);
			blindRecord.setMostChamRecord(bcEntries.get(0).getValue());
			blindRecord.setMostLineRecord(blEntries.get(0).getValue());
			blindRecord.setSecondLineRecord(blEntries.get(1).getValue());
			blindRecord.setLineRecord(blindLine);
			blindRecord.setChamRecord(blindCham);
			blindRecord.setMostCham(bcEntries.get(0).getKey());
			blindRecord.setMostLine(blEntries.get(0).getKey());
			blindRecord.setSecondLine(blEntries.get(1).getKey());
			profileReferenceDto.setBlindRecord(blindRecord);

			profiles.add(profileReferenceDto);
			profileDto.setProfiles(profiles);
		}

		profileRepository.save(profileDto);
		return profileReferenceDto;
	}

	public WinRateDto calcWinRate(WinRateDto winRateDto, boolean isWin) {
		winRateDto.setTotalGames(winRateDto.getTotalGames() + 1);
		if (isWin)
			winRateDto.setWins(winRateDto.getWins() + 1);
		else
			winRateDto.setLosses(winRateDto.getLosses() + 1);
		winRateDto.setWinRate(100.0 * winRateDto.getWins() / winRateDto.getTotalGames());
		return winRateDto;
	}
}
