package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.lolbody.dto.AnalysisDto;
import com.ssafy.lolbody.dto.CountDto;
import com.ssafy.lolbody.dto.LolbodyDto;
import com.ssafy.lolbody.dto.LolbodyResultDto;
import com.ssafy.lolbody.dto.MatchRecordDto;
import com.ssafy.lolbody.dto.MatchResultDto;
import com.ssafy.lolbody.dto.PlayerRecordDto;
import com.ssafy.lolbody.dto.RadarDto;
import com.ssafy.lolbody.dto.RadarReferenceDto;
import com.ssafy.lolbody.dto.StasticsDto;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.repository.LolbodyRepository;
import com.ssafy.lolbody.repository.StasticsRepository;

@Service
public class LolbodyService {
	@Autowired
	private LolbodyRepository lolbodyRepository;
	@Autowired
	private StasticsRepository stasticsRepository;
	@Autowired
	private SummonerService summonerService;
	@Autowired
	private ProfileService profileService;

	public LolbodyDto getLolbody(String name) throws Exception {
		SummonerDto summoner = summonerService.findOnly(name);
		LolbodyResultDto lolbodyResult = lolbodyRepository.findBySummonerId(summoner.getId());
		if (lolbodyResult == null) {
			updateLolbody(name);
			lolbodyResult = lolbodyRepository.findBySummonerId(summoner.getId());
		}
		List<LolbodyDto> lolbodyList = lolbodyResult.getLolbodyList();
		LolbodyDto lolbody = lolbodyList.get(lolbodyList.size() - 1);

		return lolbody;
	}

	public void updateLolbody(String name) throws Exception {
		try {
			profileService.updateProfile(name);

			SummonerDto summoner = summonerService.findOnly(name);
			LolbodyResultDto lolbodyResult = lolbodyRepository.findBySummonerId(summoner.getId());
			List<LolbodyDto> lolbodyList = new ArrayList<>();
			if (lolbodyResult == null) {
				lolbodyResult = new LolbodyResultDto();
				lolbodyResult.setSummonerId(summoner.getId());
			} else {
				lolbodyList = lolbodyResult.getLolbodyList();
			}

			LolbodyDto lolbody = new LolbodyDto();
			lolbody.setTimestamp(System.currentTimeMillis());
			lolbody.setUserCardReference(profileService.getUserCard(name));

			List<RadarDto> radarList = new ArrayList<>();
			Map<String, CountDto> champMap = new HashMap<>();
			Map<String, CountDto> lineMap = new HashMap<>();
			RadarReferenceDto radar = new RadarReferenceDto();
			AnalysisDto analysis = new AnalysisDto();
			AnalysisDto source = new AnalysisDto();

			int cnt = 0;
			double[] radarArr = new double[3];
			double[] analysisArr = new double[21];
			double[] sourceArr = new double[21];

			for (int i = 1; i < 11; i++) {
				MatchResultDto matchResult = profileService.getMatchResult(name, i + "");
				if (matchResult == null)
					break;
				List<MatchRecordDto> matchRecordList = matchResult.getMatchRecordList();
				for (MatchRecordDto matchRecord : matchRecordList) {
					if (matchRecord.getDuration() < 300 || (matchRecord.getQueue() != 420
							&& matchRecord.getQueue() != 430 && matchRecord.getQueue() != 440)) {
						continue;
					}

					PlayerRecordDto playerRecord = (matchRecord.getMyTeam().equals("blueTeam")
							? matchRecord.getBlueTeam().getTeammate().get(matchRecord.getMyIndex())
							: matchRecord.getRedTeam().getTeammate().get(matchRecord.getMyIndex()));

					RadarDto tmp = new RadarDto();
					tmp.setTimestamp(matchRecord.getTimestamp());
					tmp.setName(playerRecord.getChamp());
					tmp.setRadarReference(playerRecord.getRadar());
					radarList.add(tmp);

					String champ = playerRecord.getChamp(), line = playerRecord.getLine();
					boolean isWin = (matchRecord.getMyTeam().equals("blueTeam") ? matchRecord.getBlueTeam().isWin()
							: matchRecord.getRedTeam().isWin());

					CountDto champCount = new CountDto();
					if (champMap.containsKey(champ)) {
						champCount = champMap.get(champ);
					} else {
						champCount.setName(champ);
					}
					champCount.setGames(champCount.getGames() + 1);
					if (isWin)
						champCount.setWins(champCount.getWins() + 1);
					else
						champCount.setLosses(champCount.getLosses() + 1);
					champMap.put(champ, champCount);

					CountDto lineCount = new CountDto();
					if (lineMap.containsKey(line)) {
						lineCount = lineMap.get(line);
					} else {
						lineCount.setName(line);
					}
					lineCount.setGames(lineCount.getGames() + 1);
					if (isWin)
						lineCount.setWins(lineCount.getWins() + 1);
					else
						lineCount.setLosses(lineCount.getLosses() + 1);
					lineMap.put(line, lineCount);

					radarArr[0] += playerRecord.getRadar().getAggressiveness();
					radarArr[1] += playerRecord.getRadar().getStability();
					radarArr[2] += playerRecord.getRadar().getInfluence();
					analysisArr[0] += playerRecord.getAnalysis().getTotalDamageDealtToChampionsPerMin();
					analysisArr[1] += playerRecord.getAnalysis().getDamageDealtToObjectivesPerMin();
					analysisArr[2] += playerRecord.getAnalysis().getVisionScorePerMin();
					analysisArr[3] += playerRecord.getAnalysis().getTotalDamageTakenPerMin();
					analysisArr[4] += playerRecord.getAnalysis().getTotalMinionsKilledPerMin();
					analysisArr[5] += playerRecord.getAnalysis().getKillsRatio();
					analysisArr[6] += playerRecord.getAnalysis().getDeathsRatio();
					analysisArr[7] += playerRecord.getAnalysis().getKillAssistPerMin();
					analysisArr[8] += playerRecord.getAnalysis().getKillsPerMin();
					analysisArr[9] += playerRecord.getAnalysis().getDeathsPerMin();
					analysisArr[10] += playerRecord.getAnalysis().getAssistsPerMin();
					analysisArr[11] += playerRecord.getAnalysis().getTotalHealPerMin();
					analysisArr[12] += playerRecord.getAnalysis().getDamageSelfMitigatedPerMin();
					analysisArr[13] += playerRecord.getAnalysis().getDamageDealtToTurretsPerMin();
					analysisArr[14] += playerRecord.getAnalysis().getTimeCCingOthersPerMin();
					analysisArr[15] += playerRecord.getAnalysis().getNeutralMinionsKilledPerMin();
					analysisArr[16] += playerRecord.getAnalysis().getTotalTimeCrowdControlDealtPerMin();
					analysisArr[17] += playerRecord.getAnalysis().getVisionWardsBoughtInGamePerMin();
					analysisArr[18] += playerRecord.getAnalysis().getNeutralMinionsKilledEnemyJunglePerMin();
					analysisArr[19] += playerRecord.getAnalysis().getWardsPlacedPerMin();
					analysisArr[20] += playerRecord.getAnalysis().getWardsKilledPerMin();
					sourceArr[0] += playerRecord.getSource().getTotalDamageDealtToChampionsPerMin();
					sourceArr[1] += playerRecord.getSource().getDamageDealtToObjectivesPerMin();
					sourceArr[2] += playerRecord.getSource().getVisionScorePerMin();
					sourceArr[3] += playerRecord.getSource().getTotalDamageTakenPerMin();
					sourceArr[4] += playerRecord.getSource().getTotalMinionsKilledPerMin();
					sourceArr[5] += playerRecord.getSource().getKillsRatio();
					sourceArr[6] += playerRecord.getSource().getDeathsRatio();
					sourceArr[7] += playerRecord.getSource().getKillAssistPerMin();
					sourceArr[8] += playerRecord.getSource().getKillsPerMin();
					sourceArr[9] += playerRecord.getSource().getDeathsPerMin();
					sourceArr[10] += playerRecord.getSource().getAssistsPerMin();
					sourceArr[11] += playerRecord.getSource().getTotalHealPerMin();
					sourceArr[12] += playerRecord.getSource().getDamageSelfMitigatedPerMin();
					sourceArr[13] += playerRecord.getSource().getDamageDealtToTurretsPerMin();
					sourceArr[14] += playerRecord.getSource().getTimeCCingOthersPerMin();
					sourceArr[15] += playerRecord.getSource().getNeutralMinionsKilledPerMin();
					sourceArr[16] += playerRecord.getSource().getTotalTimeCrowdControlDealtPerMin();
					sourceArr[17] += playerRecord.getSource().getVisionWardsBoughtInGamePerMin();
					sourceArr[18] += playerRecord.getSource().getNeutralMinionsKilledEnemyJunglePerMin();
					sourceArr[19] += playerRecord.getSource().getWardsPlacedPerMin();
					sourceArr[20] += playerRecord.getSource().getWardsKilledPerMin();
					cnt++;
				}
			}

			List<CountDto> champList = new ArrayList<>(champMap.values());
			List<CountDto> lineList = new ArrayList<>(lineMap.values());
			Collections.sort(champList, (o1, o2) -> o2.getGames() - o1.getGames());
			Collections.sort(lineList, (o1, o2) -> o2.getGames() - o1.getGames());

			StasticsDto stastics = stasticsRepository.findByQueueId(420);
			radar.setAggressiveness(radarArr[0] / cnt);
			radar.setStability(radarArr[1] / cnt);
			radar.setInfluence(radarArr[2] / cnt);
			analysis.setTotalDamageDealtToChampionsPerMin(analysisArr[0] / cnt);
			analysis.setDamageDealtToObjectivesPerMin(analysisArr[1] / cnt);
			analysis.setVisionScorePerMin(analysisArr[2] / cnt);
			analysis.setTotalDamageTakenPerMin(analysisArr[3] / cnt);
			analysis.setTotalMinionsKilledPerMin(analysisArr[4] / cnt);
			analysis.setKillsRatio(analysisArr[5] / cnt);
			analysis.setDeathsRatio(analysisArr[6] / cnt);
			analysis.setKillAssistPerMin(analysisArr[7] / cnt);
			analysis.setKillsPerMin(analysisArr[8] / cnt);
			analysis.setDeathsPerMin(analysisArr[9] / cnt);
			analysis.setAssistsPerMin(analysisArr[10] / cnt);
			analysis.setTotalHealPerMin(analysisArr[11] / cnt);
			analysis.setDamageSelfMitigatedPerMin(analysisArr[12] / cnt);
			analysis.setDamageDealtToTurretsPerMin(analysisArr[13] / cnt);
			analysis.setTimeCCingOthersPerMin(analysisArr[14] / cnt);
			analysis.setNeutralMinionsKilledPerMin(analysisArr[15] / cnt);
			analysis.setTotalTimeCrowdControlDealtPerMin(analysisArr[16] / cnt);
			analysis.setVisionWardsBoughtInGamePerMin(analysisArr[17] / cnt);
			analysis.setNeutralMinionsKilledEnemyJunglePerMin(analysisArr[18] / cnt);
			analysis.setWardsPlacedPerMin(analysisArr[19] / cnt);
			analysis.setWardsKilledPerMin(analysisArr[20] / cnt);
			source.setTotalDamageDealtToChampionsPerMin(sourceArr[0] / cnt);
			source.setDamageDealtToObjectivesPerMin(sourceArr[1] / cnt);
			source.setVisionScorePerMin(sourceArr[2] / cnt);
			source.setTotalDamageTakenPerMin(sourceArr[3] / cnt);
			source.setTotalMinionsKilledPerMin(sourceArr[4] / cnt);
			source.setKillsRatio(sourceArr[5] / cnt);
			source.setDeathsRatio(sourceArr[6] / cnt);
			source.setKillAssistPerMin(sourceArr[7] / cnt);
			source.setKillsPerMin(sourceArr[8] / cnt);
			source.setDeathsPerMin(sourceArr[9] / cnt);
			source.setAssistsPerMin(sourceArr[10] / cnt);
			source.setTotalHealPerMin(sourceArr[11] / cnt);
			source.setDamageSelfMitigatedPerMin(sourceArr[12] / cnt);
			source.setDamageDealtToTurretsPerMin(sourceArr[13] / cnt);
			source.setTimeCCingOthersPerMin(sourceArr[14] / cnt);
			source.setNeutralMinionsKilledPerMin(sourceArr[15] / cnt);
			source.setTotalTimeCrowdControlDealtPerMin(sourceArr[16] / cnt);
			source.setVisionWardsBoughtInGamePerMin(sourceArr[17] / cnt);
			source.setNeutralMinionsKilledEnemyJunglePerMin(sourceArr[18] / cnt);
			source.setWardsPlacedPerMin(sourceArr[19] / cnt);
			source.setWardsKilledPerMin(sourceArr[20] / cnt);

			lolbody.setRadarList(radarList);
			lolbody.setChampList(champList);
			lolbody.setLineList(lineList);
			lolbody.setStastics(stastics);
			lolbody.setRadar(radar);
			lolbody.setAnalysis(analysis);
			lolbody.setSource(source);

			lolbodyList.add(lolbody);
			lolbodyResult.setLolbodyList(lolbodyList);

			lolbodyRepository.save(lolbodyResult);
		} catch (Exception e) {
			profileService.deleteMatchInfo(name);
			throw new Exception(e);
		}
	}

}
