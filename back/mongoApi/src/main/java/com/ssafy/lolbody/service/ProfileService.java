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
import com.ssafy.lolbody.dto.BadgeDto;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchRecordDto;
import com.ssafy.lolbody.dto.PlayerRecordDto;
import com.ssafy.lolbody.dto.RankDto;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchResultDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.dto.TeamRecordDto;
import com.ssafy.lolbody.dto.UserCardDto;
import com.ssafy.lolbody.dto.UserCardReferenceDto;
import com.ssafy.lolbody.preset.ChampkeyRepository;
import com.ssafy.lolbody.preset.PerkRepository;
import com.ssafy.lolbody.preset.PerkStyleRepository;
import com.ssafy.lolbody.repository.MatchRecordRepository;
import com.ssafy.lolbody.repository.MatchRepository;
import com.ssafy.lolbody.repository.UserCardRepository;
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
	private ChampkeyRepository preset;
	@Autowired
	private SpellRepository spellRepository;
	@Autowired
	private PerkRepository perkRepository;
	@Autowired
	private PerkStyleRepository perkStyleRepository;
	@Autowired
	private MatchRepository matchRepository;
	@Autowired
	private MatchRecordRepository matchRecordRepository;
	@Autowired
	private UserCardRepository userCardRepository;

	public UserCardReferenceDto getUserCard(String name) throws Exception {
		SummonerDto summoner = summonerService.findOnly(name);
		UserCardDto userCard = userCardRepository.findBySummonerId(summoner.getId());
		if (userCard == null) {
			updateProfile(name);
			userCard = userCardRepository.findBySummonerId(summoner.getId());
		}
		List<UserCardReferenceDto> userCardList = userCard.getUserCardList();
		UserCardReferenceDto userCardReference = userCardList.get(userCardList.size() - 1);

		return userCardReference;
	}

	public void updateProfile(String name) throws Exception {
		SummonerDto summoner = summonerService.findBySubName(name.toLowerCase());
		List<LeagueEntryDto> leagueEntryList = leagueEntryService.findBySummonerId(summoner.getId());
		matchlistService.findBySummonerId(summoner);

		UserCardDto userCard = userCardRepository.findBySummonerId(summoner.getId());
		List<UserCardReferenceDto> userCardList = new ArrayList<>();
		if (userCard == null) {
			userCard = new UserCardDto();
			userCard.setSummonerId(summoner.getId());
		} else {
			userCardList = userCard.getUserCardList();
		}

		UserCardReferenceDto userCardReference = new UserCardReferenceDto();
		userCardReference.setTimestamp(System.currentTimeMillis());
		userCardReference.setSummonerName(summoner.getName());
		userCardReference.setProfileIconId(summoner.getProfileIconId());
		userCardReference.setSummonerLevel(summoner.getSummonerLevel());
		userCardReference.setSoloRank(new RankDto());
		userCardReference.getSoloRank().setTier("UNRANKED");
		userCardReference.setFreeRank(new RankDto());
		userCardReference.getFreeRank().setTier("UNRANKED");
		for (LeagueEntryDto leagueEntry : leagueEntryList) {
			RankDto rank = new RankDto();
			rank.setTier(leagueEntry.getTier());
			rank.setRank(leagueEntry.getRank());
			rank.setLeaguePoints(leagueEntry.getLeaguePoints());
			rank.setWins(leagueEntry.getWins());
			rank.setLosses(leagueEntry.getLosses());
			rank.setWinRate(100.0 * leagueEntry.getWins() / (leagueEntry.getWins() + leagueEntry.getLosses()));

			if (leagueEntry.getQueueType().equals("RANKED_SOLO_5x5"))
				userCardReference.setSoloRank(rank);
			else if (leagueEntry.getQueueType().equals("RANKED_FLEX_SR"))
				userCardReference.setFreeRank(rank);
		}
		userCardList.add(userCardReference);
		userCard.setUserCardList(userCardList);

		userCardRepository.save(userCard);
	}

	public MatchResultDto getMatchResult(String name, String num) throws Exception {
		MatchResultDto matchResult = new MatchResultDto();
		Map<String, BadgeDto> badgeMap = new HashMap<>();
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
		map.put("None", 5);

		List<LeagueEntryDto> leagueEntryList = leagueEntryService.findOnly(summonerDto.getId());
		String tier = "IRON";
		for (LeagueEntryDto l : leagueEntryList) {
			if (l.getQueueType().equals("RANKED_SOLO_5x5")) {
				tier = l.getTier();
				break;
			}
		}
		if (tier.equals("CHALLENGER") || tier.equals("GRANDMASTER") || tier.equals("MASTER"))
			tier = "DIAMOND";

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

//			System.out.println("----------match 불러오기----------");
			boolean isNew = false;
			try {
				for (int i = s; i >= 0; i--) {
					if (matchService.gameCheck(matchReferences.get(i).getGameId()))
						isNew = true;
				}
			} catch (Exception e) {
				for (int i = s; i >= 0; i--) {
					matchService.deleteByGameId(matchReferences.get(i).getGameId());
				}
				throw new Exception(e);
			}

//			System.out.println("----------python 코드 실행----------");
			if (isNew) {
				try {
//					System.out.println(summonerDto.getId() + " " + left + " " + right + " " + tier);
					Api.runAnalysis("SetDataBase.py", summonerDto.getId() + " " + left + " " + right + " " + tier);
				} catch (Exception e) {
					for (int i = s; i >= 0; i--) {
						matchService.deleteByGameId(matchReferences.get(i).getGameId());
					}
					throw new Exception(e);
				}
			}
			for (int i = s; i >= 0; i--) {
//				System.out.println("----------match 데이터 정리----------");
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
						try {
							tmp.setSpell1(spellRepository.findByKey(p.getSpell1Id() + "").getName());
							tmp.setSpell2(spellRepository.findByKey(p.getSpell2Id() + "").getName());
						} catch (Exception e) {
						}
						tmp.setItem0(p.getStats().getItem0());
						tmp.setItem1(p.getStats().getItem1());
						tmp.setItem2(p.getStats().getItem2());
						tmp.setItem3(p.getStats().getItem3());
						tmp.setItem4(p.getStats().getItem4());
						tmp.setItem5(p.getStats().getItem5());
						tmp.setItem6(p.getStats().getItem6());
						tmp.setPerk(perkRepository.findByKey(p.getStats().getPerk0()).getName().replaceAll(" ", ""));
						tmp.setPerkStyle(p.getStats().getPerkSubStyle() == 0 ? ""
								: perkStyleRepository.findByKey(p.getStats().getPerkSubStyle()).getName());
						tmp.setLevel(p.getStats().getChampLevel());
						tmp.setGold(p.getStats().getGoldEarned());
						tmp.setCs(p.getStats().getNeutralMinionsKilled() + p.getStats().getTotalMinionsKilled());
						tmp.setCsPerMin(60.0 * tmp.getCs() / match.getGameDuration());
						tmp.setLine(p.getLine() == null ? "None" : p.getLine());
						tmp.setMatchGrade(p.getMatchGrade());
						tmp.setAnalysis(p.getAnalysis());
						tmp.setRadar(p.getRadar());
						tmp.setBadges(p.getBadges());
						tmp.setSource(p.getSource());
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

				List<BadgeDto> badges;
				if (matchRecordDto.getMyTeam().equals("blueTeam")) {
					badges = matchRecordDto.getBlueTeam().getTeammate().get(matchRecordDto.getMyIndex()).getBadges();
				} else {
					badges = matchRecordDto.getRedTeam().getTeammate().get(matchRecordDto.getMyIndex()).getBadges();
				}
				if (badges != null) {
					for (BadgeDto badge : badges) {
						String key = badge.getName() + badge.getTier();
						if (badgeMap.containsKey(key)) {
							badge.setCnt(badgeMap.get(key).getCnt() + 1);
						} else {
							badge.setCnt(1);
						}
						badge.setComment(badge.getComment());
						badgeMap.put(key, badge);
					}
				}

				matchRecords.add(matchRecordDto);
			}
		}
		matchResult.setMatchRecordList(matchRecords);
		matchResult.setBadgeMap(badgeMap);

//		System.out.println("----------return----------");
		return matchResult;
	}

	public void deleteMatchInfo(String name) throws Exception {
		SummonerDto summonerDto = summonerService.findOnly(name);
		MatchlistDto matchlistDto = matchlistService.findOnly(summonerDto);
		List<MatchReferenceDto> matchReferences = matchlistDto.getMatches();
		matchReferences = matchReferences.stream().filter(o -> o.getTimestamp() >= 1578596400000l)
				.filter(o -> o.getQueue() != 2000 && o.getQueue() != 2010 && o.getQueue() != 2020)
				.collect(Collectors.toList());
		for (MatchReferenceDto matchReference : matchReferences) {
			matchRepository.deleteById(matchReference.getGameId());
			matchRecordRepository.deleteById(matchReference.getGameId());
		}
	}

}
