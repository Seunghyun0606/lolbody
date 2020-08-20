package com.ssafy.lolbody.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.SubSummonerDto;

public interface SubSummonerRepository extends MongoRepository<SubSummonerDto, String> {
	public SubSummonerDto findBySummonerId(String summonerId);
}
