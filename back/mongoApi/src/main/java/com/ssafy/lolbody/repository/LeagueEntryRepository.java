package com.ssafy.lolbody.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssafy.lolbody.dto.LeagueEntryListDto;

public interface LeagueEntryRepository extends MongoRepository<LeagueEntryListDto, String>{
	public LeagueEntryListDto findBySummonerId(String summonerId);
}
