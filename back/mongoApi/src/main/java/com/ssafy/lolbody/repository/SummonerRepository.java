package com.ssafy.lolbody.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.SummonerDTO;

public interface SummonerRepository extends MongoRepository<SummonerDTO, String> {
	public List<SummonerDTO> findAll();
	public SummonerDTO findByName(String name);
}
