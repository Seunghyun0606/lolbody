package com.ssafy.lolbody.service;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.SummonerValueResultDto;

@Service
public class SummonerValueService {
	@Autowired
	private RadarChartService radarChartService;

	public SummonerValueResultDto getSummonerValue(String summonerName)
			throws JsonSyntaxException, IOException, Exception {
		SummonerValueResultDto summonerValueResultDto = new Gson().fromJson(
				Api.getAnalysisData("SummonerValue.py",
						new JSONObject(radarChartService.getRadarChar(summonerName)).toString().replaceAll("\"", "'")),
				SummonerValueResultDto.class);

		return summonerValueResultDto;
	}
}
