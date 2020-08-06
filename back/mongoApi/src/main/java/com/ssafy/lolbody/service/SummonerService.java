package com.ssafy.lolbody.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.SummonerDto;
import com.ssafy.lolbody.repository.SummonerRepository;

@Service
public class SummonerService {
	@Autowired
	private SummonerRepository summonerRepository;

	public void insert(SummonerDto summonerDto) {
		summonerRepository.findById(summonerDto.getId()).orElseGet(() -> summonerRepository.save(summonerDto));
	}

	public List<SummonerDto> findAll() {
		return summonerRepository.findAll();
	}

	public SummonerDto findBySubName(String name) throws Exception {
		String json = Api.get("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name", name);
		if (json.equals("Fail")) {
			throw new Exception();
		}
		SummonerDto summonerDto = new Gson().fromJson(json, SummonerDto.class);
		JSONObject object = new JSONObject(json);
		summonerDto.setSubName(object.getString("name").toLowerCase().replaceAll(" ", ""));
		insert(summonerDto);
		return summonerDto;
	}

	public SummonerDto findOnly(String name) throws Exception {
		SummonerDto summonerDto = summonerRepository.findBySubName(name.toLowerCase());
		if (summonerDto == null) {
			String json = Api.get("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name", name);
			if (json.equals("Fail")) {
				throw new Exception("해당하는 소환사가 없습니다.");
			}
			summonerDto = new Gson().fromJson(json, SummonerDto.class);
			JSONObject object = new JSONObject(json);
			summonerDto.setSubName(object.getString("name").toLowerCase().replaceAll(" ", ""));
			insert(summonerDto);
		}
		return summonerDto;
	}

}
