package com.ssafy.apidemo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.apidemo.dto.SummonerDTO;

public interface SummonerRepository extends MongoRepository<SummonerDTO, String> {
	public List<SummonerDTO> findAll();
	public SummonerDTO findByName(String name);
}
