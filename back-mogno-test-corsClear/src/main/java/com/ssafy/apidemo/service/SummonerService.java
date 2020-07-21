package com.ssafy.apidemo.service;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apidemo.api.Api;
import com.ssafy.apidemo.dto.SummonerDTO;
import com.ssafy.apidemo.repository.SummonerRepository;

@Service
public class SummonerService {
	@Autowired
	private SummonerRepository summonerRepository;
	
	public void insert(SummonerDTO summonerDto) {
		summonerRepository.findById(summonerDto.getId()).orElseGet(()->summonerRepository.save(summonerDto));
	}
	
	public List<SummonerDTO> findAll() {
		return summonerRepository.findAll();
	}
	
	public SummonerDTO findByName(String name) {
		SummonerDTO summonerDto = summonerRepository.findByName(name);
		if(summonerDto == null) {
			JSONObject object = new JSONObject(Api.get("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name", name));
			summonerDto = new SummonerDTO();
			summonerDto.setId(object.getString("id"));
			summonerDto.setAccountId(object.getString("accountId"));
			summonerDto.setPuuid(object.getString("puuid"));
			summonerDto.setName(object.getString("name"));
			summonerDto.setProfileIconId(object.getInt("profileIconId"));
			summonerDto.setRevisionDate(object.getLong("revisionDate"));
			summonerDto.setSummonerLevel(object.getInt("summonerLevel"));
			insert(summonerDto);
		}
		return summonerDto;
	}
	
}
