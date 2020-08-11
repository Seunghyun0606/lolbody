package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.MatchDto;
import com.ssafy.lolbody.dto.MatchFrameDto;
import com.ssafy.lolbody.dto.MatchParticipantFrameDto;
import com.ssafy.lolbody.dto.MatchPositionDto;
import com.ssafy.lolbody.dto.MatchTimelineDto;
import com.ssafy.lolbody.dto.ParticipantDto;
import com.ssafy.lolbody.dto.ParticipantIdentityDto;

@Service
public class PositionService {

	@Autowired
	private MatchService matchService;

	public String getPosition(String name, long gameId) throws Exception {
		MatchTimelineDto matchTimelineDto = new Gson().fromJson(
				Api.multi("https://kr.api.riotgames.com/lol/match/v4/timelines/by-match", String.valueOf(gameId)),
				MatchTimelineDto.class);
//		File file = new File("./position.txt");
//		FileWriter fw = new FileWriter(file);
//		fw.write(new JSONObject(matchTimelineDto).toString().replaceAll("\"", "'"));
//		fw.flush();
//		fw.close();
		MatchDto match = matchService.findByGameId(gameId);
		int participantId = 0;
		for (ParticipantIdentityDto identites : match.getParticipantIdentities()) {
			if (identites.getPlayer().getSummonerName().toLowerCase().replaceAll(" ", "")
					.equals(name.toLowerCase().replaceAll(" ", ""))) {
				participantId = identites.getParticipantId();
				break;
			}
		}
		final int pi = participantId;
		List<MatchFrameDto> frames = new ArrayList<>();
		int idx = 0;
		for(MatchFrameDto matchf: matchTimelineDto.getFrames()) {
			MatchFrameDto matchF = new MatchFrameDto();
			MatchParticipantFrameDto matchParticipantFrameDto = matchf.getParticipantFrames().get(String.valueOf(participantId));
			Map<String,MatchParticipantFrameDto> participantFrames = new HashMap<>();
			participantFrames.put(String.valueOf(participantId), matchParticipantFrameDto);
			matchF.setParticipantFrames(participantFrames);
			frames.add(matchF);
			idx++;
			if(idx == 10) break;
		}
		MatchTimelineDto matcht = new MatchTimelineDto();
		matcht.setFrames(frames);
		JSONArray jsonArray = new JSONArray(Api.getAnalysisData("Position.py", ""+participantId+" "
		+ new Gson().toJson(matcht, MatchTimelineDto.class).toString().replaceAll("\"", "'")));
		List<MatchPositionDto> matchPositions = new ArrayList<>();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject o = jsonArray.getJSONObject(i);
			MatchPositionDto matchPosition = new MatchPositionDto();
			matchPosition.setX((int) o.getDouble("x"));
			matchPosition.setY((int) o.getDouble("y"));
			matchPositions.add(matchPosition);
		}
		matchPositions = matchPositions.stream().limit(10).collect(Collectors.toList());

		// smite여부
		List<Integer> hasSmiteParticipantId = new ArrayList<>();
		for (ParticipantDto participant : match.getParticipants()) {
			if((participantId <= 5 && participant.getParticipantId() > 5)
					|| participantId > 5 && participant.getParticipantId() <= 5) continue;
			if (participant.getSpell1Id() == 11 || participant.getSpell2Id() == 11) {
				hasSmiteParticipantId.add(participant.getParticipantId());
			}
		}
		// 강타 든 사람이 한 명이면 그 사람이 정글러
		if (hasSmiteParticipantId.size() == 1 && hasSmiteParticipantId.contains(participantId))
			return "JUNGLE";

		// 강타 든 사람이 0명 혹은 여러명일 경우 중립 미니언 수로 정글러 결정
		int pid = 0, nkc = 0;
		for (ParticipantDto participant : match.getParticipants()) {
			if (participant.getStats().getNeutralMinionsKilled() > nkc) {
				pid = participant.getParticipantId();
				nkc = participant.getStats().getNeutralMinionsKilled();
			}
		}

		if (pid == participantId)
			return "JUNGLE";

		Map<String, Integer> lane = new LinkedHashMap<>();
		lane.put("TOP", 0);
		lane.put("JUNGLE", 0);
		lane.put("MID", 0);
		lane.put("BOTTOM", 0);
		for (MatchPositionDto matchPosition : matchPositions) {
			int x = matchPosition.getX();
			int y = matchPosition.getY();
			if (x > 20 && x < 60 && y > 30 && y < 220)
				lane.put("TOP", lane.get("TOP") + 1);
			else if (x >= 20 && x <= 150 && y >= 20 && y <= 160)
				lane.put("TOP", lane.get("TOP") + 1);
			else if (x >= 30 && x <= 200 && y >= 20 && y <= 60)
				lane.put("TOP", lane.get("TOP") + 1);
			else if (x >= 195 && x <= 265 && y >= 250 && y <= 310)
				lane.put("MID", lane.get("MID") + 1);
			else if (x >= 220 && x <= 295 && y >= 220 && y <= 290)
				lane.put("MID", lane.get("MID") + 1);
			else if (x >= 250 && x <= 320 && y >= 200 && y <= 260)
				lane.put("MID", lane.get("MID") + 1);
			else if (x >= 290 && x <= 350 && y >= 160 && y <= 215)
				lane.put("MID", lane.get("MID") + 1);
			else if (x >= 160 && x <= 220 && y >= 290 && y <= 340)
				lane.put("MID", lane.get("MID") + 1);
			else if (x >= 310 && x <= 460 && y >= 435 && y <= 485)
				lane.put("BOTTOM", lane.get("BOTTOM") + 1);
			else if (x >= 400 && x <= 490 && y >= 385 && y <= 480)
				lane.put("BOTTOM", lane.get("BOTTOM") + 1);
			else if (x >= 440 && x <= 490 && y >= 310 && y <= 455)
				lane.put("BOTTOM", lane.get("BOTTOM") + 1);
		}

		List<Map.Entry<String, Integer>> laneEntries = new LinkedList<>(lane.entrySet());
		Collections.sort(laneEntries, (o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()));

		String result = laneEntries.get(0).getKey();
		if (!result.equals("BOTTOM"))
			return result;
		else {
			pid = 0;
			int cs = Integer.MAX_VALUE;
			for (ParticipantDto participant : match.getParticipants()) {
				if (participant.getStats().getTotalMinionsKilled()
						+ participant.getStats().getNeutralMinionsKilled() < cs)
					pid = participant.getParticipantId();
				nkc = participant.getStats().getTotalMinionsKilled() + participant.getStats().getNeutralMinionsKilled();
			}
			if (pid == participantId)
				return "SUPPORT";
			else
				return "BOTTOM";
		}
	}
}
