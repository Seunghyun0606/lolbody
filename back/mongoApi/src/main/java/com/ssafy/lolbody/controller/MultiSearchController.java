package com.ssafy.lolbody.controller;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.MultiSearchDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.dto.RecentGamesDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.preset.ChampKeyDto;
import com.ssafy.lolbody.preset.MongoDBPreset;
import com.ssafy.lolbody.service.LeagueEntryService;
import com.ssafy.lolbody.service.MatchService;
import com.ssafy.lolbody.service.MatchlistService;
import com.ssafy.lolbody.service.SummonerService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
public class MultiSearchController {
	
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
	
	@GetMapping("/api/multisearch/{summonerName}")
	@ApiOperation(value="사용자의 소환사 이름을 name 변수로 받아 멀티서치에 필요한 정보를 반환합니다.")
	public ResponseEntity<MultiSearchDto> getMultiSearch(@PathVariable String summonerName) {
		MultiSearchDto result = new MultiSearchDto();
		// 존재하는 소환사인지
		SummonerDto summonerDto = summonerService.findBySubName(summonerName);
		if(summonerDto == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
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
		for(MatchReferenceDto matchRef: matchlistDto.getMatches()) {
			if(matchRef.getTimestamp() < 1578596400000L) break;
			if(matchRef.getQueue() != 420) continue;
			if(matchRef.getRole().equals("DUO_SUPPORT")) {
				if(!lane.containsKey("SUPPORT"))
					lane.put("SUPPORT", 0);
				lane.put("SUPPORT", lane.get("SUPPORT")+1);
			} else {
				if(!lane.containsKey(matchRef.getLane()))
					lane.put(matchRef.getLane(), 0);
				lane.put(matchRef.getLane(), lane.get(matchRef.getLane())+1);
			}
		}
		
		List<Map.Entry<String, Integer>> entries = new LinkedList<>(lane.entrySet());
		Collections.sort(entries, (o1,o2) -> o2.getValue().compareTo(o1.getValue()));
		
		result.setLane(entries.get(0).getKey());
		
		// 최근 5게임 스펠, 챔피언, 라인정보, 승 패 여부
		// spell1Id,spell2Id,champName,lane,result
		List<MatchReferenceDto> matchRefList = matchlistDto.getMatches();

		List<RecentGamesDto> recentGames = new LinkedList<>();
		int idx = 0;
		for(MatchReferenceDto matchRefDto: matchRefList) {
			RecentGamesDto recentGame = new RecentGamesDto();
			int participantId = 0;
			if(matchRefDto.getTimestamp() < 1578596400000L) break;
			if(matchRefDto.getQueue() != 420) continue;
			MatchDto matchDto = matchService.findByGameId(matchRefDto.getGameId());
			if(matchDto == null) break;
			for(ParticipantIdentityDto identites: matchDto.getParticipantIdentities()) {
				if(identites.getPlayer().getSummonerName().equals(summonerService.findBySubName(summonerName).getName())) {
					participantId = identites.getParticipantId();
					break;
				}
			}
			if(matchRefDto.getRole().equals("DUO_SUPPORT")) {
				recentGame.setLane("SUPPORT");
			} else {
				recentGame.setLane(matchRefDto.getLane());
			}
			for(ParticipantDto participant: matchDto.getParticipants()) {
				if(participant.getParticipantId() == participantId) {
					recentGame.setSpell1Id(participant.getSpell1Id());
					recentGame.setSpell2Id(participant.getSpell2Id());
					ChampKeyDto champkey = mongodbPreset.findById(String.valueOf(participant.getChampionId())).get();
					recentGame.setChampName(champkey.getName());
					recentGame.setWin(participant.getStats().isWin());
					if(participant.getHighestAchievedSeasonTier() != null)
						result.setHighestAchievedSeasonTier(participant.getHighestAchievedSeasonTier());
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
		for(int i = 0; i < 3; i++) {
			ChampKeyDto champkey = mongodbPreset.findById(String.valueOf(mostPickEntries.get(i).getKey())).get();
			mostPicks.add(champkey.getName());
		}
		result.setMostChamp(mostPicks);
		
		// 최근 20게임 전적(승, 패)
		idx = 0;
		List<Boolean> recentMatchResults = new LinkedList<>();
		if(matchlistDto.getMatches() != null) {
			for(MatchReferenceDto matchRefDto: matchlistDto.getMatches()) {
				if(matchRefDto.getTimestamp() < 1578596400000L) break;
				if(matchRefDto.getQueue() != 420) continue;
				int participantId = 0;
				MatchDto matchDto = matchService.findByGameId(matchRefDto.getGameId());
				if(matchDto == null) break;
				for(ParticipantIdentityDto identites: matchDto.getParticipantIdentities()) {
					if(identites.getPlayer().getSummonerName().equals(summonerService.findBySubName(summonerName).getName())) {
						participantId = identites.getParticipantId();
						break;
					}
				}
				for(ParticipantDto participant: matchDto.getParticipants()) {
					if(participant.getParticipantId() == participantId) {
						recentMatchResults.add(participant.getStats().isWin());
					}
				}
				idx++;
				if(idx == 20) break;
			}
		}
		result.setRecentMatchResults(recentMatchResults);
		return new ResponseEntity<>(result,HttpStatus.OK);
	}
}
