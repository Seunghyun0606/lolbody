package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.dto.LaneInfoDto;
import com.ssafy.lolbody.dto.LeagueEntryDto;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchReferenceDto;
import com.ssafy.lolbody.dto.MatchlistDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;
import com.ssafy.lolbody.dto.ParticipantStatsDto;
import com.ssafy.lolbody.dto.RadarChartDto;
import com.ssafy.lolbody.dto.SummonerDto;

@Service
public class RadarChartService {
	@Autowired
	private SummonerService summonerService;
	@Autowired
	private MatchlistService matchlistService;
	@Autowired
	private MatchService matchService;
	@Autowired
	private LeagueEntryService leagueEntryService;
	
	public RadarChartDto getRadarChar(String summonerName) throws Exception {
		SummonerDto summonerDto = summonerService.findBySubName(summonerName);
		
		RadarChartDto radarChart = new RadarChartDto();
		List<LeagueEntryDto> leagueEntries = leagueEntryService.findBySummonerId(summonerDto.getId())
		.stream().filter(o -> o.getQueueType().contains("SOLO")).collect(Collectors.toList());
		if(leagueEntries.size() != 0)
			radarChart.setTier(leagueEntries.get(0).getTier());
		else
			radarChart.setTier("iron");
		Map<String,Integer> lane = matchlistService.getLaneFrequency(summonerDto);
		
		List<Map.Entry<String, Integer>> entries = new LinkedList<>(lane.entrySet());
		entries = entries.stream().filter(o -> !o.getKey().equals("NONE")).collect(Collectors.toList());
		Collections.sort(entries, (o1,o2) -> o2.getValue().compareTo(o1.getValue()));
		String mainLane="",subLane="";
		if(entries.size() > 0)
			mainLane = entries.get(0).getKey();
		if(entries.size() > 1)
			subLane = entries.get(1).getKey();
		
		MatchlistDto matchlist = matchlistService.findBySummonerId(summonerDto);
		List<MatchReferenceDto> matches = matchlist.getMatches()
				.stream().filter(o -> o.getTimestamp() >= 1578596400000l && o.getQueue() == 420).collect(Collectors.toList());
		matches.sort((o1,o2) -> Long.compare(o2.getTimestamp(), o1.getTimestamp()));
		if(mainLane.length() != 0) 
			radarChart.setLane1(getLaneInfos(mainLane,matches,summonerName));
		if(subLane.length() != 0)
			radarChart.setLane2(getLaneInfos(subLane, matches, summonerName));
		
		
		return radarChart;
	}
	
	private List<LaneInfoDto> getLaneInfos(String lane, List<MatchReferenceDto> matches, String summonerName) throws Exception {
		List<LaneInfoDto> laneInfos = new ArrayList<>();
		for(MatchReferenceDto matchRef: matches.stream().filter(o -> o.getRole().contains(lane) || o.getLane().equals(lane)).limit(20).collect(Collectors.toList())) {
			LaneInfoDto laneInfo = new LaneInfoDto();
			MatchDto matchDto = matchService.findByGameId(matchRef.getGameId());
			if(matchDto == null) break;
			int participantId = 0;
			for(ParticipantIdentityDto identites: matchDto.getParticipantIdentities()) {
				if(identites.getPlayer().getSummonerName().toLowerCase().replaceAll(" ", "").equals(summonerName.toLowerCase().replaceAll(" ", ""))) {
					participantId = identites.getParticipantId();
					break;
				}
			}
			if(participantId == 0) break;
			final int pi = participantId;
			List<ParticipantDto> participants = matchDto.getParticipants().
					stream().filter(o -> o.getParticipantId() == pi).collect(Collectors.toList());
			final int teamId = participants.get(0).getTeamId();
			List<ParticipantDto> participantss = matchDto.getParticipants()
					.stream().filter(o -> o.getTeamId() == teamId).collect(Collectors.toList());
			int teamKills = 0, teamDeaths = 0;
			for(ParticipantDto participant: participantss) {
				teamKills += participant.getStats().getKills();
				teamDeaths += participant.getStats().getDeaths();
			}
			laneInfo.setTeamKills(teamKills);
			laneInfo.setTeamDeaths(teamDeaths);
			ParticipantStatsDto stats = participants.get(0).getStats();
			
			laneInfo.setAssist(stats.getAssists());
			laneInfo.setVisionScore(stats.getVisionScore());
			laneInfo.setKills(stats.getKills());
			laneInfo.setDeaths(stats.getDeaths());
			laneInfo.setGameDuration(matchDto.getGameDuration());
			laneInfo.setTotalDamageDealtToChampions(stats.getTotalDamageDealtToChampions());
			laneInfo.setTotalDamageTaken(stats.getTotalDamageTaken());
			double csPerMin = 1.0*(stats.getTotalMinionsKilled()+stats.getNeutralMinionsKilled())/(laneInfo.getGameDuration()/60);
			csPerMin = 1.0*Math.round(csPerMin*10)/10;
			laneInfo.setCsPerMin(csPerMin);
			if(matchRef.getRole().equals("DUO_SUPPORT")) {
				if(matchRef.getLane().equals("NONE"))
					laneInfo.setPosition("SUPPORT");
				else
					laneInfo.setPosition(matchRef.getLane());
			} else {
				laneInfo.setPosition(matchRef.getLane());
			}
			laneInfos.add(laneInfo);
		}
		return laneInfos;
	}
}
