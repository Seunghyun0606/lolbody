package com.ssafy.lolbody.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.SummonerDto;

public interface SummonerRepository extends MongoRepository<SummonerDto, String> {
	public List<SummonerDto> findAll();
	public SummonerDto findBySubName(String name);
}
