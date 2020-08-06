package com.ssafy.lolbody.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchRecordDto;
import com.ssafy.lolbody.dto.PlayerRecordDto;
import com.ssafy.lolbody.dto.TotalRecordDto;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.dto.ProfileDto;
import com.ssafy.lolbody.dto.ProfileReferenceDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.dto.TeamRecordDto;
import com.ssafy.lolbody.dto.WinRateDto;
import com.ssafy.lolbody.preset.MongoDBPreset;
import com.ssafy.lolbody.preset.PerkRepository;
import com.ssafy.lolbody.preset.PerkStyleRepository;
import com.ssafy.lolbody.repository.MatchRecordRepository;
import com.ssafy.lolbody.repository.ProfileRepository;
import com.ssafy.lolbody.preset.SpellRepository;

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
	@Autowired
	MongoDBPreset preset;
	@Autowired
	SpellRepository spellRepository;
	@Autowired
	PerkRepository perkRepository;
	@Autowired
	PerkStyleRepository perkStyleRepository;
	@Autowired
	MatchRecordRepository matchRecordRepository;

	public void updateProfile(String name) throws Exception {
		SummonerDto summonerDto = summonerService.findBySubName(name);
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
					break;
				}
			}
			MatchlistDto matchlistDto = matchlistService.findBySummonerId(summonerDto);
			List<MatchReferenceDto> matchReferences = matchlistDto.getMatches();
			matchReferences = matchReferences.stream().filter(o -> o.getTimestamp() >= 1578596400000l)
					.collect(Collectors.toList());
			int e = matchReferences.size();
			profileReferenceDto.setGames(e);

			WinRateDto rankedTotal = new WinRateDto(0, 0, 0, 0.0);
			WinRateDto blindTotal = new WinRateDto(0, 0, 0, 0.0);
			Map<String, WinRateDto> rankedLine = new LinkedHashMap<>();
			Map<String, WinRateDto> blindLine = new LinkedHashMap<>();
			Map<String, WinRateDto> rankedCham = new LinkedHashMap<>();
			Map<String, WinRateDto> blindCham = new LinkedHashMap<>();

			for (int i = 0; i < e; i++) {
				MatchReferenceDto matchReferenceDto = matchReferences.get(i);
				int queue = matchReferenceDto.getQueue();
				if (queue == 420 || queue == 430) {
					MatchDto matchDto = matchService.findByGameId(matchReferenceDto.getGameId());
					if (matchDto.getGameDuration() < 300) // 다시하기는 통계에 집계되지 않음
						continue;

					String line = matchReferenceDto.getLane();
					if (matchReferenceDto.getRole().equals("DUO_SUPPORT")) {
						if (line.equals("BOTTOM") || line.equals("NONE"))
							line = "SUPPORT";
					}

					String cham = preset.findByKey(matchReferenceDto.getChampion() + "").getName();
					List<ParticipantIdentityDto> participantIdentityList = matchDto.getParticipantIdentities();
					int participantId = -1;
					for (ParticipantIdentityDto participantIdentityDto : participantIdentityList) {
						if (participantIdentityDto.getPlayer().getSummonerName().equals(summonerDto.getName())) {
							participantId = participantIdentityDto.getParticipantId();
							break;
						}
					}
					if (participantId == -1)
						continue;
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

			List<Map.Entry<String, WinRateDto>> rcEntries = new LinkedList<>(rankedCham.entrySet());
			Collections.sort(rcEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> rlEntries = new LinkedList<>(rankedLine.entrySet());
			Collections.sort(rlEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> bcEntries = new LinkedList<>(blindCham.entrySet());
			Collections.sort(bcEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> blEntries = new LinkedList<>(blindLine.entrySet());
			Collections.sort(blEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());

			TotalRecordDto rankedRecord = new TotalRecordDto();
			rankedRecord.setTotalRecord(rankedTotal);
			if (rcEntries.size() > 0)
				rankedRecord.setMostChamRecord(rcEntries.get(0).getValue());
			if (rlEntries.size() > 0)
				rankedRecord.setMostLineRecord(rlEntries.get(0).getValue());
			if (rlEntries.size() > 1)
				rankedRecord.setSecondLineRecord(rlEntries.get(1).getValue());
			rankedRecord.setLineRecord(rankedLine);
			rankedRecord.setChamRecord(rankedCham);
			if (rcEntries.size() > 0)
				rankedRecord.setMostCham(rcEntries.get(0).getKey());
			if (rlEntries.size() > 0)
				rankedRecord.setMostLine(rlEntries.get(0).getKey());
			if (rlEntries.size() > 1)
				rankedRecord.setSecondLine(rlEntries.get(1).getKey());
			profileReferenceDto.setRankedRecord(rankedRecord);

			TotalRecordDto blindRecord = new TotalRecordDto();
			blindRecord.setTotalRecord(blindTotal);
			if (bcEntries.size() > 0)
				blindRecord.setMostChamRecord(bcEntries.get(0).getValue());
			if (blEntries.size() > 0)
				blindRecord.setMostLineRecord(blEntries.get(0).getValue());
			if (blEntries.size() > 1)
				blindRecord.setSecondLineRecord(blEntries.get(1).getValue());
			blindRecord.setLineRecord(blindLine);
			blindRecord.setChamRecord(blindCham);
			if (bcEntries.size() > 0)
				blindRecord.setMostCham(bcEntries.get(0).getKey());
			if (blEntries.size() > 0)
				blindRecord.setMostLine(blEntries.get(0).getKey());
			if (blEntries.size() > 1)
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
			matchReferences = matchReferences.stream().filter(o -> o.getTimestamp() >= 1578596400000l)
					.collect(Collectors.toList());
			int s = lastProfile.getGames(), e = matchReferences.size();
			profileReferenceDto.setGames(e);

			WinRateDto rankedTotal = lastProfile.getRankedRecord().getTotalRecord();
			WinRateDto blindTotal = lastProfile.getBlindRecord().getTotalRecord();
			Map<String, WinRateDto> rankedLine = lastProfile.getRankedRecord().getLineRecord();
			Map<String, WinRateDto> blindLine = lastProfile.getBlindRecord().getLineRecord();
			Map<String, WinRateDto> rankedCham = lastProfile.getRankedRecord().getChamRecord();
			Map<String, WinRateDto> blindCham = lastProfile.getBlindRecord().getChamRecord();

			for (int i = s; i < e; i++) {
				MatchReferenceDto matchReferenceDto = matchReferences.get(i);
				int queue = matchReferenceDto.getQueue();
				if (queue == 420 || queue == 430) {
					MatchDto matchDto = matchService.findByGameId(matchReferenceDto.getGameId());
					if (matchDto.getGameDuration() < 300) // 다시하기는 통계에 집계되지 않음
						continue;

					String line = matchReferenceDto.getLane();
					if (matchReferenceDto.getRole().equals("DUO_SUPPORT")) {
						if (line.equals("BOTTOM") || line.equals("NONE"))
							line = "SUPPORT";
					}

					String cham = preset.findByKey(matchReferenceDto.getChampion() + "").getName();
					List<ParticipantIdentityDto> participantIdentityList = matchDto.getParticipantIdentities();
					int participantId = -1;
					for (ParticipantIdentityDto participantIdentityDto : participantIdentityList) {
						if (participantIdentityDto.getPlayer().getSummonerName().equals(summonerDto.getName())) {
							participantId = participantIdentityDto.getParticipantId();
							break;
						}
					}
					if (participantId == -1)
						continue;
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

			List<Map.Entry<String, WinRateDto>> rcEntries = new LinkedList<>(rankedCham.entrySet());
			Collections.sort(rcEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> rlEntries = new LinkedList<>(rankedLine.entrySet());
			Collections.sort(rlEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> bcEntries = new LinkedList<>(blindCham.entrySet());
			Collections.sort(bcEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());
			List<Map.Entry<String, WinRateDto>> blEntries = new LinkedList<>(blindLine.entrySet());
			Collections.sort(blEntries, (o1, o2) -> o2.getValue().getTotalGames() - o1.getValue().getTotalGames());

			TotalRecordDto rankedRecord = new TotalRecordDto();
			rankedRecord.setTotalRecord(rankedTotal);
			if (rcEntries.size() > 0)
				rankedRecord.setMostChamRecord(rcEntries.get(0).getValue());
			if (rlEntries.size() > 0)
				rankedRecord.setMostLineRecord(rlEntries.get(0).getValue());
			if (rlEntries.size() > 1)
				rankedRecord.setSecondLineRecord(rlEntries.get(1).getValue());
			rankedRecord.setLineRecord(rankedLine);
			rankedRecord.setChamRecord(rankedCham);
			if (rcEntries.size() > 0)
				rankedRecord.setMostCham(rcEntries.get(0).getKey());
			if (rlEntries.size() > 0)
				rankedRecord.setMostLine(rlEntries.get(0).getKey());
			if (rlEntries.size() > 1)
				rankedRecord.setSecondLine(rlEntries.get(1).getKey());
			profileReferenceDto.setRankedRecord(rankedRecord);

			TotalRecordDto blindRecord = new TotalRecordDto();
			blindRecord.setTotalRecord(blindTotal);
			if (bcEntries.size() > 0)
				blindRecord.setMostChamRecord(bcEntries.get(0).getValue());
			if (blEntries.size() > 0)
				blindRecord.setMostLineRecord(blEntries.get(0).getValue());
			if (blEntries.size() > 1)
				blindRecord.setSecondLineRecord(blEntries.get(1).getValue());
			blindRecord.setLineRecord(blindLine);
			blindRecord.setChamRecord(blindCham);
			if (bcEntries.size() > 0)
				blindRecord.setMostCham(bcEntries.get(0).getKey());
			if (blEntries.size() > 0)
				blindRecord.setMostLine(blEntries.get(0).getKey());
			if (blEntries.size() > 1)
				blindRecord.setSecondLine(blEntries.get(1).getKey());
			profileReferenceDto.setBlindRecord(blindRecord);

			profiles.add(profileReferenceDto);
			profileDto.setProfiles(profiles);
		}

		profileRepository.save(profileDto);
	}

	public ProfileReferenceDto getProfile(String name) throws Exception {
		SummonerDto summonerDto = summonerService.findOnly(name);
		ProfileDto profileDto = profileRepository.findBySummonerId(summonerDto.getId());
		if (profileDto == null) {
			updateProfile(name);
		}
		List<ProfileReferenceDto> profiles = profileDto.getProfiles();
		return profiles.get(profiles.size() - 1);
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

	public List<MatchRecordDto> getMatchRecord(String name, String num) throws Exception {
		List<MatchRecordDto> matchRecords = new ArrayList<>();
		SummonerDto summonerDto = summonerService.findOnly(name);
		MatchlistDto matchlistDto = matchlistService.findOnly(summonerDto);
		List<MatchReferenceDto> matchReferences = matchlistDto.getMatches();
		matchReferences = matchReferences.stream().filter(o -> o.getTimestamp() >= 1578596400000l)
				.collect(Collectors.toList());
		Map<String, Integer> map = new HashMap<>();
		map.put("TOP", 0);
		map.put("JUNGLE", 1);
		map.put("MIDDLE", 2);
		map.put("BOTTOM", 3);
		map.put("SUPPORT", 4);
		map.put("NONE", 5);

		int size = matchReferences.size(), idx = Integer.parseInt(num);
		if (size - ((idx - 1) * 10) <= 0) {
			return null;
		} else {
			int s = 0;
			if (size - (idx * 10) >= 0) {
				matchReferences = matchReferences.subList(size - (idx * 10), size - ((idx - 1) * 10));
				s = 9;
			} else {
				matchReferences = matchReferences.subList(0, size - ((idx - 1) * 10));
				s = size - ((idx - 1) * 10) - 1;
			}
			for (int i = s; i >= 0; i--) {
				MatchReferenceDto matchReferenceDto = matchReferences.get(i);
				MatchDto match = matchService.findByGameId(matchReferenceDto.getGameId());
				List<ParticipantIdentityDto> users = match.getParticipantIdentities();
				List<ParticipantDto> participants = match.getParticipants();

				MatchRecordDto matchRecordDto = matchRecordRepository.findByGameId(matchReferenceDto.getGameId());
				if (matchRecordDto == null) {
					matchRecordDto = new MatchRecordDto();

					matchRecordDto.setGameId(match.getGameId());
					matchRecordDto.setQueue(match.getQueueId());
					matchRecordDto.setTimestamp(match.getGameCreation());
					matchRecordDto.setDuration(match.getGameDuration());
					matchRecordDto.setNoGame(match.getGameDuration() < 300 ? true : false);

					TeamRecordDto blueTeam = new TeamRecordDto();
					TeamRecordDto redTeam = new TeamRecordDto();

					blueTeam.setTeam(100);
					blueTeam.setWin(match.getTeams().get(0).getWin().equals("Win") ? true : false);
					redTeam.setTeam(200);
					redTeam.setWin(match.getTeams().get(1).getWin().equals("Win") ? true : false);

					List<PlayerRecordDto> blueTeammate = new ArrayList<>();
					List<PlayerRecordDto> redTeammate = new ArrayList<>();

					int[][] kda = new int[10][3];
					int blueKills = 0, redKills = 0, blueDeaths = 0, redDeaths = 0;
					for (int j = 0; j < 10; j++) {
						ParticipantDto p = participants.get(j);
						kda[j][0] = p.getStats().getKills();
						kda[j][1] = p.getStats().getDeaths();
						kda[j][2] = p.getStats().getAssists();
						if (j < 5) {
							blueKills += kda[j][0];
							blueDeaths += kda[j][1];
						} else {
							redKills += kda[j][0];
							redDeaths += kda[j][1];
						}
					}
					for (int j = 0; j < 10; j++) {
						PlayerRecordDto tmp = new PlayerRecordDto();
						ParticipantDto p = participants.get(j);
						tmp.setName(users.get(j).getPlayer().getSummonerName());
						tmp.setChamp(preset.findByKey(p.getChampionId() + "").getName());
						tmp.setKills(kda[j][0]);
						tmp.setDeaths(kda[j][1]);
						tmp.setAssists(kda[j][2]);
						if (tmp.getKills() + tmp.getAssists() == 0)
							tmp.setKda(0.0);
						else
							tmp.setKda(((double) tmp.getKills() + tmp.getAssists()) / tmp.getDeaths());
						if (tmp.getKills() + tmp.getAssists() == 0)
							tmp.setKa(0.0);
						else
							tmp.setKa(100.0 * (tmp.getKills() + tmp.getAssists()) / (j < 5 ? blueKills : redKills));
						tmp.setSpell1(spellRepository.findByKey(p.getSpell1Id() + "").getName());
						tmp.setSpell2(spellRepository.findByKey(p.getSpell2Id() + "").getName());
						tmp.setItem0(p.getStats().getItem0());
						tmp.setItem1(p.getStats().getItem1());
						tmp.setItem2(p.getStats().getItem2());
						tmp.setItem3(p.getStats().getItem3());
						tmp.setItem4(p.getStats().getItem4());
						tmp.setItem5(p.getStats().getItem5());
						tmp.setItem6(p.getStats().getItem6());
						tmp.setPerk(perkRepository.findByKey(p.getStats().getPerk0()).getName().replaceAll(" ", ""));
						tmp.setPerkStyle(perkStyleRepository.findByKey(p.getStats().getPerkSubStyle()).getName());
						tmp.setLevel(p.getStats().getChampLevel());
						tmp.setGold(p.getStats().getGoldEarned());
						tmp.setCs(p.getStats().getNeutralMinionsKilled() + p.getStats().getTotalMinionsKilled());
						tmp.setCsPerMin(60.0 * tmp.getCs() / match.getGameDuration());
						String line = p.getTimeline().getLane();
						if (p.getTimeline().getRole().equals("DUO_SUPPORT")
								&& (line.equals("BOTTOM") || line.equals("NONE"))) {
							tmp.setLine("SUPPORT");
						} else
							tmp.setLine(line);
						if (j < 5)
							blueTeammate.add(tmp);
						else
							redTeammate.add(tmp);

						JSONObject obj = new JSONObject();
						obj.put("kill", tmp.getKills());
						obj.put("assist", tmp.getAssists());
						obj.put("death", tmp.getDeaths());
						obj.put("duration", matchRecordDto.getDuration());
						if (Double.isInfinite(tmp.getKda()))
							obj.put("kda", -1.0);
						else if (Double.isNaN(tmp.getKda()))
							obj.put("kda", 0.0);
						else
							obj.put("kda", tmp.getKda());
						obj.put("killRatio", tmp.getKa());
						double deathRatio = 100.0 * tmp.getDeaths() / (j < 5 ? blueDeaths : redDeaths);
						if (Double.isNaN(deathRatio))
							obj.put("deathRatio", 0.0);
						else
							obj.put("deathRatio", deathRatio);
						obj.put("gold", tmp.getGold());
						obj.put("cs", tmp.getCs());
						obj.put("csPerMin", tmp.getCsPerMin());
						obj.put("damageDealt", p.getStats().getTotalDamageDealtToChampions());
						obj.put("damageTaken", p.getStats().getTotalDamageTaken());
						obj.put("visionScore", p.getStats().getVisionScore());
						try {
							List<LeagueEntryDto> leagueEntryList = leagueEntryService
									.findOnly(summonerService.findOnly(tmp.getName()).getId());
							for (LeagueEntryDto l : leagueEntryList) {
								if (l.getQueueType().equals("RANKED_SOLO_5x5")) {
									obj.put("tier", l.getTier());
									break;
								}
							}
							if (!obj.has("tier"))
								obj.put("tier", "null");
							tmp.setMatchGrade(Double.parseDouble(
									Api.getAnalysisData("MatchGrade.py", obj.toString().replaceAll("\"", "'"))));
						} catch (Exception e) {
							continue;
						}
					}
					Collections.sort(blueTeammate, (o1, o2) -> map.get(o1.getLine()) - map.get(o2.getLine()));
					Collections.sort(redTeammate, (o1, o2) -> map.get(o1.getLine()) - map.get(o2.getLine()));

					blueTeam.setTeammate(blueTeammate);
					redTeam.setTeammate(redTeammate);
					matchRecordDto.setBlueTeam(blueTeam);
					matchRecordDto.setRedTeam(redTeam);

					matchRecordRepository.save(matchRecordDto);
				}

				matchRecordDto.setMyTeam("blueTeam");
				matchRecordDto.setMyIndex(0);

				for (int j = 0; j < 5; j++) {
					if (summonerDto.getName().equals(matchRecordDto.getBlueTeam().getTeammate().get(j).getName())) {
						matchRecordDto.setMyTeam("blueTeam");
						matchRecordDto.setMyIndex(j);
						break;
					}
					if (summonerDto.getName().equals(matchRecordDto.getRedTeam().getTeammate().get(j).getName())) {
						matchRecordDto.setMyTeam("redTeam");
						matchRecordDto.setMyIndex(j);
						break;
					}
				}
				matchRecords.add(matchRecordDto);
			}
		}
		return matchRecords;
	}

}
