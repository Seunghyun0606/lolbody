package com.ssafy.lolbody.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ssafy.lolbody.api.Api;
import com.ssafy.lolbody.dto.SummonerDTO;
import com.ssafy.lolbody.repository.SummonerRepository;

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
			String json = Api.get("https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name", name);
			summonerDto = new Gson().fromJson(json, SummonerDTO.class);
			insert(summonerDto);
		}
		return summonerDto;
	}
	
}
