package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchRecordDto;
import com.ssafy.lolbody.dto.PlayerRecordDto;
import com.ssafy.lolbody.dto.RankDto;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.dto.TeamRecordDto;
import com.ssafy.lolbody.dto.UserCardReferenceDto;
import com.ssafy.lolbody.preset.MongoDBPreset;
import com.ssafy.lolbody.preset.PerkRepository;
import com.ssafy.lolbody.preset.PerkStyleRepository;
import com.ssafy.lolbody.repository.MatchRecordRepository;
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
	private MongoDBPreset preset;
	@Autowired
	private SpellRepository spellRepository;
	@Autowired
	private PerkRepository perkRepository;
	@Autowired
	private PerkStyleRepository perkStyleRepository;
	@Autowired
	private MatchRecordRepository matchRecordRepository;

	public UserCardReferenceDto getUserCard(String name) throws Exception {
		UserCardReferenceDto userCard = new UserCardReferenceDto();
		SummonerDto summoner = summonerService.findOnly(name);
		List<LeagueEntryDto> leagueEntryList = leagueEntryService.findOnly(summoner.getId());
		userCard.setTimestamp(System.currentTimeMillis());
		userCard.setSummonerName(summoner.getName());
		userCard.setProfileIconId(summoner.getProfileIconId());
		userCard.setSummonerLevel(summoner.getSummonerLevel());
		userCard.setSoloRank(new RankDto());
		userCard.getSoloRank().setTier("UNRANKED");
		userCard.setFreeRank(new RankDto());
		userCard.getFreeRank().setTier("UNRANKED");
		for (LeagueEntryDto leagueEntry : leagueEntryList) {
			RankDto rank = new RankDto();
			rank.setTier(leagueEntry.getTier());
			rank.setRank(leagueEntry.getRank());
			rank.setLeaguePoints(leagueEntry.getLeaguePoints());
			rank.setWins(leagueEntry.getWins());
			rank.setLosses(leagueEntry.getLosses());
			rank.setWinRate(100.0 * leagueEntry.getWins() / (leagueEntry.getWins() + leagueEntry.getLosses()));

			if (leagueEntry.getQueueType().equals("RANKED_SOLO_5x5"))
				userCard.setSoloRank(rank);
			else if (leagueEntry.getQueueType().equals("RANKED_FLEX_SR"))
				userCard.setFreeRank(rank);
		}

		return userCard;
	}

	public void updateProfile(String name) throws Exception {
		SummonerDto summoner = summonerService.findBySubName(name.toLowerCase());
		leagueEntryService.findBySummonerId(summoner.getId());
		matchlistService.findBySummonerId(summoner);
	}

	public List<MatchRecordDto> getMatchRecord(String name, String num) throws Exception {
		List<MatchRecordDto> matchRecords = new ArrayList<>();
		SummonerDto summonerDto = summonerService.findOnly(name);
		MatchlistDto matchlistDto = matchlistService.findOnly(summonerDto);
		List<MatchReferenceDto> matchReferences = matchlistDto.getMatches();
		matchReferences = matchReferences.stream().filter(o -> o.getTimestamp() >= 1578596400000l)
				.filter(o -> o.getQueue() != 2000 && o.getQueue() != 2010 && o.getQueue() != 2020)
				.collect(Collectors.toList());
		Map<String, Integer> map = new HashMap<>();
		map.put("TOP", 0);
		map.put("JUNGLE", 1);
		map.put("MID", 2);
		map.put("BOTTOM", 3);
		map.put("SUPPORT", 4);
		map.put("NONE", 5);

		List<LeagueEntryDto> leagueEntryList = leagueEntryService.findOnly(summonerDto.getId());
		String tier = "IRON";
		for (LeagueEntryDto l : leagueEntryList) {
			if (l.getQueueType().equals("RANKED_SOLO_5x5")) {
				tier = l.getTier();
				break;
			}
		}

		int size = matchReferences.size(), idx = Integer.parseInt(num);
		if (size - ((idx - 1) * 10) <= 0) {
			return null;
		} else {
			int s = 0, left = size - (idx * 10), right = size - ((idx - 1) * 10);
			if (size - (idx * 10) >= 0) {
				matchReferences = matchReferences.subList(size - (idx * 10), size - ((idx - 1) * 10));
				s = 9;
			} else {
				matchReferences = matchReferences.subList(0, size - ((idx - 1) * 10));
				s = size - ((idx - 1) * 10) - 1;
				left = 0;
			}

			boolean isNew = false;
			for (int i = s; i >= 0; i--) {
				if (matchService.gameCheck(matchReferences.get(i).getGameId()))
					isNew = true;
			}

			if (isNew)
				Api.runAnalysis("Analysis\\SetDataBase.py",
						summonerDto.getId() + " " + left + " " + right + " " + tier);

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
					int blueKills = 0, redKills = 0;
					for (int j = 0; j < 10; j++) {
						ParticipantDto p = participants.get(j);
						kda[j][0] = p.getStats().getKills();
						kda[j][1] = p.getStats().getDeaths();
						kda[j][2] = p.getStats().getAssists();
						if (j < 5)
							blueKills += kda[j][0];
						else
							redKills += kda[j][0];
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
						tmp.setLine(p.getLine() == null ? "NONE" : p.getLine());
						tmp.setMatchGrade(p.getMatchGrade());
						tmp.setAnalysis(p.getAnalysis());
						if (j < 5)
							blueTeammate.add(tmp);
						else
							redTeammate.add(tmp);
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
