package com.ssafy.lolbody.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import javax.naming.NameNotFoundException;

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

	public List<SummonerDto> findAll() {
		return summonerRepository.findAll();
	}

	public SummonerDto findBySubName(String name) throws Exception {
		String json = Api.get("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name", name);
		if (json.equals("Fail")) {
			throw new NameNotFoundException("존재하지 않는 소환사입니다. ("+name+")");
		} else if (json.equals("Timeout"))
			throw new TimeoutException("메인 키 요청이 너무 많습니다.");
		SummonerDto summonerDto = new Gson().fromJson(json, SummonerDto.class);
		JSONObject object = new JSONObject(json);
		summonerDto.setSubName(object.getString("name").toLowerCase().replaceAll(" ", ""));
		summonerRepository.save(summonerDto);
		return summonerDto;
	}

	public SummonerDto findOnly(String name) throws Exception {
		SummonerDto summonerDto = summonerRepository.findBySubName(name.toLowerCase());
		if (summonerDto == null) {
			return findBySubName(name.toLowerCase());
		}
		return summonerDto;
	}
	
	public List<SummonerDto> findByNameStartingWith(String name) {
		List<SummonerDto> summonerDtos = summonerRepository.findTop3BySubNameStartingWith(name.toLowerCase().replaceAll(" ", ""));
		if(summonerDtos == null) {
			return new ArrayList<>();
		}
		return summonerDtos;
	}

}
