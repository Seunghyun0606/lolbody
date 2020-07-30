package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.dto.KDADto;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.MultiSearchDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.dto.ParticipantStatsDto;
import com.ssafy.lolbody.dto.PositionDto;
import com.ssafy.lolbody.dto.RecentGamesDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.preset.ChampKeyDto;
import com.ssafy.lolbody.preset.MongoDBPreset;
import com.ssafy.lolbody.preset.SpellRepository;

@Service
public class MultiSearchService {
	
	@Autowired
	private SummonerService summonerService;
	@Autowired
	private LeagueEntryService leagueEntryService;
	@Autowired
	private MatchlistService matchlistService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private MongoDBPreset mongodbPreset;
	@Autowired
	private SpellRepository spellRepository;
	
	private static final int RECENT_GAME = 20;
	
	public MultiSearchDto getMultiSearch(String summonerName) throws Exception{
		MultiSearchDto result = new MultiSearchDto();
		// 존재하는 소환사인지
		SummonerDto summonerDto = summonerService.findBySubName(summonerName);
		
		// 소환사 이름, 소환사 레벨
		result.setSummonerName(summonerName);
		result.setSummonerLevel(summonerDto.getSummonerLevel());
		
		// 전체 랭크 ( 전적, 승 / 패 )
		List<LeagueEntryDto> leagueEntryDtoList = leagueEntryService.findBySummonerId(summonerDto.getId());
		for(LeagueEntryDto leagueEntry: leagueEntryDtoList) {
			if(leagueEntry.getQueueType().contains("SOLO")) {
				result.setWins(leagueEntry.getWins());
				result.setLosses(leagueEntry.getLosses());
				result.setTotalGame(result.getLosses()+result.getWins());
				break;
			}
		}
		// 라인(통계)
		MatchlistDto matchlistDto = matchlistService.findBySummonerId(summonerDto);
		Collections.sort(matchlistDto.getMatches(), (o1,o2) -> Long.compare(o2.getTimestamp(),o1.getTimestamp()));
		Map<String,Integer> lane = new LinkedHashMap<>();
		List<MatchReferenceDto> matchRefs = matchlistDto.getMatches().stream().filter((o1) -> o1.getTimestamp() >= 1578596400000L && o1.getQueue() == 420).collect(Collectors.toList());
		for(MatchReferenceDto matchRef: matchRefs) {
			if(matchRef.getRole().equals("DUO_SUPPORT")) {
				if(!matchRef.getLane().equals("NONE")) {
					if(!lane.containsKey(matchRef.getLane()))
						lane.put(matchRef.getLane(), 0);
					lane.put(matchRef.getLane(), lane.get(matchRef.getLane())+1);
				} else {
					if(!lane.containsKey("SUPPORT"))
						lane.put("SUPPORT", 0);
					lane.put("SUPPORT", lane.get("SUPPORT")+1);
				}
			} else {
				if(!lane.containsKey(matchRef.getLane()))
					lane.put(matchRef.getLane(), 0);
				lane.put(matchRef.getLane(), lane.get(matchRef.getLane())+1);
			}
		}
		
		List<Map.Entry<String, Integer>> entries = new LinkedList<>(lane.entrySet());
		Collections.sort(entries, (o1,o2) -> o2.getValue().compareTo(o1.getValue()));
		if(entries.size() > 0)
			result.setMainLane(entries.get(0).getKey());
		if(entries.size() > 1)
			result.setMainLane(entries.get(1).getKey());
		
		// 최근 5게임 스펠, 챔피언, 라인정보, 승 패 여부
		// spell1Id,spell2Id,champName,lane,result
		List<RecentGamesDto> recentGames = new LinkedList<>();
		int idx = 0;
		for(MatchReferenceDto matchRefDto: matchRefs) {
			RecentGamesDto recentGame = new RecentGamesDto();
			int participantId = 0;
			MatchDto matchDto = matchService.findByGameId(matchRefDto.getGameId());
			if(matchDto == null) break;
			for(ParticipantIdentityDto identites: matchDto.getParticipantIdentities()) {
				if(identites.getPlayer().getSummonerName().toLowerCase().equals(summonerName.toLowerCase())) {
					participantId = identites.getParticipantId();
					break;
				}
			}
			if(matchRefDto.getRole().equals("DUO_SUPPORT")) {
				if(!matchRefDto.getLane().equals("NONE"))
					recentGame.setLane(matchRefDto.getLane());
				else
					recentGame.setLane("SUPPORT");
			} else {
				recentGame.setLane(matchRefDto.getLane());
			}
			for(ParticipantDto participant: matchDto.getParticipants()) {
				if(participant.getParticipantId() == participantId) {
					recentGame.setSpell1Id(spellRepository.findById(participant.getSpell1Id()+"").get().getName());
					recentGame.setSpell2Id(spellRepository.findById(participant.getSpell2Id()+"").get().getName());
					ChampKeyDto champkey = mongodbPreset.findById(String.valueOf(participant.getChampionId())).get();
					recentGame.setChampName(champkey.getName());
					recentGame.setWin(participant.getStats().isWin());
					if(participant.getHighestAchievedSeasonTier() != null)
						result.setHighestAchievedSeasonTier(participant.getHighestAchievedSeasonTier());
					KDADto kda = new KDADto();
					kda.setKills(participant.getStats().getKills());
					kda.setDeaths(participant.getStats().getDeaths());
					kda.setAssists(participant.getStats().getAssists());
					recentGame.setKda(kda);
				}
			}
			recentGames.add(recentGame);
			idx++;
			if(idx == 5) break;
		}
		result.setRecentGames(recentGames);
		
		// 모스트 챔프 3개
		Map<Integer,Integer> mostPick = new LinkedHashMap<>();
		for(MatchReferenceDto matchRef: matchlistDto.getMatches()) {
			if(matchRef.getTimestamp() < 1578596400000L) break;
			if(matchRef.getQueue() != 420) continue;
			if(!mostPick.containsKey(matchRef.getChampion()))
				mostPick.put(matchRef.getChampion(), 0);
			mostPick.put(matchRef.getChampion(), mostPick.get(matchRef.getChampion())+1);
		}
		
		List<Map.Entry<Integer, Integer>> mostPickEntries = new LinkedList<>(mostPick.entrySet());
		Collections.sort(mostPickEntries, (o1,o2) -> Integer.compare(o2.getValue(), o1.getValue()));
		
		List<String> mostPicks = new LinkedList<>();
		if(mostPickEntries.size() != 0) {
			for(int i = 0; i < 3; i++) {
				ChampKeyDto champkey = mongodbPreset.findById(String.valueOf(mostPickEntries.get(i).getKey())).get();
				mostPicks.add(champkey.getName());
			}
		}
		result.setMostChamp(mostPicks);
		
		// 최근 20게임 전적(승, 패)
		idx = 0;
		List<Boolean> recentMatchResults = new LinkedList<>();
		Map<String,Integer> laneRate = new HashMap<>();
		if(matchlistDto.getMatches() != null) {
			for(MatchReferenceDto matchRefDto: matchlistDto.getMatches()) {
				if(matchRefDto.getTimestamp() < 1578596400000L) break;
				if(matchRefDto.getQueue() != 420) continue;
				int participantId = 0;
				MatchDto matchDto = matchService.findByGameId(matchRefDto.getGameId());
				if(matchDto == null) break;
				for(ParticipantIdentityDto identites: matchDto.getParticipantIdentities()) {
					if(identites.getPlayer().getSummonerName().toLowerCase().equals(summonerName.toLowerCase())) {
						participantId = identites.getParticipantId();
						break;
					}
				}
				for(ParticipantDto participant: matchDto.getParticipants()) {
					if(participant.getParticipantId() == participantId) {
						recentMatchResults.add(participant.getStats().isWin());
					}
				}
				if(matchRefDto.getRole().equals("DUO_SUPPORT")) {
					if(!matchRefDto.getLane().equals("NONE")) {
						if(!laneRate.containsKey(matchRefDto.getLane()))
							laneRate.put(matchRefDto.getLane(), 0);
						laneRate.put(matchRefDto.getLane(), laneRate.get(matchRefDto.getLane())+1);
					} else {
						if(!laneRate.containsKey("SUPPORT"))
							laneRate.put("SUPPORT", 0);
						laneRate.put("SUPPORT", laneRate.get("SUPPORT")+1);
					}
				} else {
					if(!laneRate.containsKey(matchRefDto.getLane()))
						laneRate.put(matchRefDto.getLane(), 0);
					laneRate.put(matchRefDto.getLane(), laneRate.get(matchRefDto.getLane())+1);
				}
				idx++;
				if(idx == RECENT_GAME) break;
			}
		}
		List<PositionDto> positions = new LinkedList<>();
		for(String key: laneRate.keySet()) {
			PositionDto position = new PositionDto();
			position.setLane(key);
			position.setRate(laneRate.get(key)*100/RECENT_GAME);
			positions.add(position);
		}
		result.setPositionRates(positions);
		result.setRecentMatchResults(recentMatchResults);
		return result;
	}
}
